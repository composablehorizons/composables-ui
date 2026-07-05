import { readFile } from "node:fs/promises";

export async function readJsonFile(path) {
  return JSON.parse(await readFile(new URL(`../${path}`, import.meta.url), "utf8"));
}

export async function readUiPackageVersion() {
  const uiPackageJson = await readJsonFile("packages/ui/package.json");
  return uiPackageJson.version;
}

export async function readCliPackageVersion() {
  const cliPackageJson = await readJsonFile("packages/cli/package.json");
  return cliPackageJson.version;
}

export async function readGradleUiVersion() {
  return readGradleVersion("ui");
}

export async function readGradleCliVersion() {
  return readGradleVersion("composables-cli");
}

async function readGradleVersion(name) {
  const versionCatalog = await readFile(
    new URL("../gradle/libs.versions.toml", import.meta.url),
    "utf8",
  );
  const match = versionCatalog.match(new RegExp(`^${name} = "([^"]+)"$`, "m"));

  if (match == null) {
    throw new Error(`Could not find the ${name} version in gradle/libs.versions.toml`);
  }

  return match[1];
}
