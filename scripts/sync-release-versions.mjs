import { readFile, writeFile } from "node:fs/promises";
import { readCliPackageVersion, readUiPackageVersion } from "./release-versions.mjs";

const versionCatalogPath = new URL("../gradle/libs.versions.toml", import.meta.url);
const uiPackageVersion = await readUiPackageVersion();
const cliPackageVersion = await readCliPackageVersion();
const versionCatalog = await readFile(versionCatalogPath, "utf8");
const versions = [
  ["ui", uiPackageVersion],
  ["composables-cli", cliPackageVersion],
];

let nextVersionCatalog = versionCatalog;

for (const [name, version] of versions) {
  const versionPattern = new RegExp(`^${name} = ".*"$`, "m");
  if (!versionPattern.test(nextVersionCatalog)) {
    throw new Error(`Could not find the ${name} version in gradle/libs.versions.toml`);
  }
  nextVersionCatalog = nextVersionCatalog.replace(versionPattern, `${name} = "${version}"`);
}

await writeFile(versionCatalogPath, nextVersionCatalog);
