import { readFile, writeFile } from "node:fs/promises";
import { readUiPackageVersion } from "./release-versions.mjs";

const versionCatalogPath = new URL("../gradle/libs.versions.toml", import.meta.url);
const uiPackageVersion = await readUiPackageVersion();
const versionCatalog = await readFile(versionCatalogPath, "utf8");
const uiVersionPattern = /^ui = ".*"$/m;

if (!uiVersionPattern.test(versionCatalog)) {
  throw new Error("Could not find the ui version in gradle/libs.versions.toml");
}

const nextVersionCatalog = versionCatalog.replace(
  uiVersionPattern,
  `ui = "${uiPackageVersion}"`,
);

await writeFile(versionCatalogPath, nextVersionCatalog);
