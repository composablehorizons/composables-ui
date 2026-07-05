import { readFile, writeFile } from "node:fs/promises";

const uiPackageJsonPath = new URL("../packages/ui/package.json", import.meta.url);
const versionCatalogPath = new URL("../gradle/libs.versions.toml", import.meta.url);

const uiPackageJson = JSON.parse(await readFile(uiPackageJsonPath, "utf8"));
const versionCatalog = await readFile(versionCatalogPath, "utf8");
const uiVersionPattern = /^ui = ".*"$/m;

if (!uiVersionPattern.test(versionCatalog)) {
  throw new Error("Could not find the ui version in gradle/libs.versions.toml");
}

const nextVersionCatalog = versionCatalog.replace(
  uiVersionPattern,
  `ui = "${uiPackageJson.version}"`,
);

await writeFile(versionCatalogPath, nextVersionCatalog);
