import { mkdir, writeFile } from "node:fs/promises";
import { execFileSync, spawnSync } from "node:child_process";
import { readJsonFile } from "./release-versions.mjs";

const dryRun = process.argv.includes("--dry-run");
const releaseOutputFile = new URL("../build/release/published-packages.json", import.meta.url);

const packages = [
  {
    name: "composables-cli",
    releaseName: "composables-cli",
    type: "npm",
    versionFile: "packages/cli/package.json",
    publishCommand: ["npm", ["publish", "--workspace", "packages/cli", "--access", "public"]],
  },
  {
    name: "@composables/ui",
    releaseName: "composables-ui",
    type: "maven",
    versionFile: "packages/ui/package.json",
    mavenPomUrl: (version) => `https://repo1.maven.org/maven2/com/composables/ui/${version}/ui-${version}.pom`,
    publishCommand: ["./gradlew", [":ui:publishAndReleaseToMavenCentral", "--no-configuration-cache"]],
  },
];

async function npmPackageExists(name, version) {
  try {
    execFileSync("npm", ["view", `${name}@${version}`, "version", "--json"], {
      encoding: "utf8",
      stdio: ["ignore", "pipe", "pipe"],
    });
    return true;
  } catch {
    return false;
  }
}

async function mavenPackageExists(url) {
  const response = await fetch(url, { method: "HEAD" });
  if (response.status === 404) return false;
  if (response.ok) return true;
  throw new Error(`Could not check Maven publication at ${url}: HTTP ${response.status}`);
}

function run(command, args) {
  console.log(`$ ${[command, ...args].join(" ")}`);
  if (dryRun) return;

  const result = spawnSync(command, args, { stdio: "inherit" });
  if (result.status !== 0) {
    process.exit(result.status ?? 1);
  }
}

const publishedPackages = [];

for (const releasePackage of packages) {
  const packageJson = await readJsonFile(releasePackage.versionFile);
  const version = packageJson.version;
  const tagName = `${releasePackage.releaseName}@${version}`;
  const alreadyPublished =
    releasePackage.type === "npm"
      ? await npmPackageExists(releasePackage.name, version)
      : await mavenPackageExists(releasePackage.mavenPomUrl(version));

  if (alreadyPublished) {
    console.log(`${releasePackage.name}@${version} is already published. Skipping.`);
    continue;
  }

  const [command, args] = releasePackage.publishCommand;
  console.log(`${dryRun ? "Would publish" : "Publishing"} ${releasePackage.name}@${version}.`);
  run(command, args);
  publishedPackages.push({
    name: releasePackage.name,
    releaseName: releasePackage.releaseName,
    version,
    type: releasePackage.type,
    tagName,
  });
}

await mkdir(new URL("../build/release", import.meta.url), { recursive: true });
await writeFile(releaseOutputFile, `${JSON.stringify(publishedPackages, null, 2)}\n`);

if (publishedPackages.length === 0) {
  console.log("No unpublished package versions found.");
} else if (dryRun) {
  console.log("Dry run complete. No packages were published.");
}
