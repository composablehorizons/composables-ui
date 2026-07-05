import {
  readCliPackageVersion,
  readGradleCliVersion,
  readGradleUiVersion,
  readUiPackageVersion,
} from "./release-versions.mjs";

const uiPackageVersion = await readUiPackageVersion();
const gradleUiVersion = await readGradleUiVersion();
const cliPackageVersion = await readCliPackageVersion();
const gradleCliVersion = await readGradleCliVersion();
const mismatches = [];

if (uiPackageVersion !== gradleUiVersion) {
  mismatches.push([
    "UI",
    `packages/ui/package.json: ${uiPackageVersion}`,
    `gradle/libs.versions.toml ui: ${gradleUiVersion}`,
  ]);
}

if (cliPackageVersion !== gradleCliVersion) {
  mismatches.push([
    "CLI",
    `packages/cli/package.json: ${cliPackageVersion}`,
    `gradle/libs.versions.toml composables-cli: ${gradleCliVersion}`,
  ]);
}

if (mismatches.length > 0) {
  console.error("Release versions are out of sync:");
  for (const [name, packageVersion, gradleVersion] of mismatches) {
    console.error(`- ${name}:`);
    console.error(`  - ${packageVersion}`);
    console.error(`  - ${gradleVersion}`);
  }
  console.error("");
  console.error("Run `npm run changeset:version` to sync release versions.");
  process.exit(1);
}

console.log(`Release versions are in sync: composables-cli ${cliPackageVersion}, @composables/ui ${uiPackageVersion}`);
