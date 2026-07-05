import { execFileSync } from "node:child_process";
import { readJsonFile } from "./release-versions.mjs";

const packages = [
  {
    name: "composables-cli",
    type: "npm",
    versionFile: "packages/cli/package.json",
    publishCommand: "npm publish --workspace packages/cli --dry-run",
  },
  {
    name: "@composables/ui",
    type: "maven",
    versionFile: "packages/ui/package.json",
    publishCommand: "./gradlew :ui:publishToMavenLocal --no-configuration-cache",
  },
];

function readBaseFile(path) {
  try {
    return execFileSync("git", ["show", `HEAD:${path}`], { encoding: "utf8" });
  } catch {
    return null;
  }
}

const releasePlan = [];

for (const releasePackage of packages) {
  const currentPackageJson = await readJsonFile(releasePackage.versionFile);
  const baseContent = readBaseFile(releasePackage.versionFile);
  const baseVersion = baseContent == null ? null : JSON.parse(baseContent).version;

  if (baseVersion !== currentPackageJson.version) {
    releasePlan.push({
      ...releasePackage,
      from: baseVersion ?? "not tracked",
      to: currentPackageJson.version,
    });
  }
}

if (releasePlan.length === 0) {
  console.log("No package version changes detected.");
  process.exit(0);
}

console.log("Dry-run release plan:");
for (const releasePackage of releasePlan) {
  console.log(
    `- ${releasePackage.name}: ${releasePackage.from} -> ${releasePackage.to} (${releasePackage.type})`,
  );
  console.log(`  would run: ${releasePackage.publishCommand}`);
}
