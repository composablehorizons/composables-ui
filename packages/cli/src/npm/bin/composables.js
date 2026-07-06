#!/usr/bin/env node

const { spawnSync } = require("node:child_process");
const fs = require("node:fs");
const path = require("node:path");

const packageRoot = path.resolve(__dirname, "..");
const jarPath = path.join(packageRoot, "composables.jar");

if (!fs.existsSync(jarPath)) {
    console.error(`Composables CLI jar not found at ${jarPath}`);
    process.exit(1);
}

const result = spawnSync("java", ["-jar", jarPath, ...process.argv.slice(2)], {
    stdio: "inherit",
});

if (result.error) {
    if (result.error.code === "ENOENT") {
        console.error("Java 17 or newer is required to run Composables CLI.");
        console.error("Install Java and try again.");
    } else {
        console.error(result.error.message);
    }
    process.exit(1);
}

if (typeof result.status === "number") {
    process.exit(result.status);
}

process.exit(1);
