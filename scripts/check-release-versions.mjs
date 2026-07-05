import { readGradleUiVersion, readUiPackageVersion } from "./release-versions.mjs";

const uiPackageVersion = await readUiPackageVersion();
const gradleUiVersion = await readGradleUiVersion();

if (uiPackageVersion !== gradleUiVersion) {
  console.error("Release versions are out of sync:");
  console.error(`- packages/ui/package.json: ${uiPackageVersion}`);
  console.error(`- gradle/libs.versions.toml ui: ${gradleUiVersion}`);
  console.error("");
  console.error("Run `npm run changeset:version` to sync release versions.");
  process.exit(1);
}

console.log(`Release versions are in sync: @composables/ui ${uiPackageVersion}`);
