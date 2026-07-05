import { readFile } from "node:fs/promises";

export async function readJsonFile(path) {
  return JSON.parse(await readFile(new URL(`../${path}`, import.meta.url), "utf8"));
}

export async function readUiPackageVersion() {
  const uiPackageJson = await readJsonFile("packages/ui/package.json");
  return uiPackageJson.version;
}

export async function readGradleUiVersion() {
  const versionCatalog = await readFile(
    new URL("../gradle/libs.versions.toml", import.meta.url),
    "utf8",
  );
  const match = versionCatalog.match(/^ui = "([^"]+)"$/m);

  if (match == null) {
    throw new Error("Could not find the ui version in gradle/libs.versions.toml");
  }

  return match[1];
}
