# composables-cli

## 0.11.0

### Minor Changes

- [#26](https://github.com/composablehorizons/composables-ui/pull/26) [`77dab30`](https://github.com/composablehorizons/composables-ui/commit/77dab30ff3fb5601b1bcb18ffcecc89e7c1d1c3a) Thanks [@alexstyl](https://github.com/alexstyl)! - Add a new --ios-team-id param to init for ios, so that you can provide the TEAM_ID when creating a new ios app. This is required when running on a physical device.

- [#25](https://github.com/composablehorizons/composables-ui/pull/25) [`ef8db40`](https://github.com/composablehorizons/composables-ui/commit/ef8db40d09cb980acf15075151b4b5797cbf62a1) Thanks [@alexstyl](https://github.com/alexstyl)! - Add Navigation 3 as the navigation library of generated apps.

## 0.10.1

### Patch Changes

- [`88a3377`](https://github.com/composablehorizons/composables-ui/commit/88a3377b6347d435c502cf514349c7360b175a49) Thanks [@alexstyl](https://github.com/alexstyl)! - Make docs command output plain text instead of JSON so its easier to read. You can still get results as json using `--json`.

## 0.10.0

### Minor Changes

- [`17f2a8a`](https://github.com/composablehorizons/composables-ui/commit/17f2a8a468b4d5d9d7a665d34eb76c7590dad687) Thanks [@alexstyl](https://github.com/alexstyl)! - Add `composables add module --type library` for generating Compose library modules in existing Gradle projects.

- [`17f2a8a`](https://github.com/composablehorizons/composables-ui/commit/17f2a8a468b4d5d9d7a665d34eb76c7590dad687) Thanks [@alexstyl](https://github.com/alexstyl)! - Add project-scoped MCP installers for Antigravity, Claude, Codex, Cursor, Firebender, OpenCode, plus Zed.

- [`17f2a8a`](https://github.com/composablehorizons/composables-ui/commit/17f2a8a468b4d5d9d7a665d34eb76c7590dad687) Thanks [@alexstyl](https://github.com/alexstyl)! - Move MCP setup under `composables mcp install`.

- [`17f2a8a`](https://github.com/composablehorizons/composables-ui/commit/17f2a8a468b4d5d9d7a665d34eb76c7590dad687) Thanks [@alexstyl](https://github.com/alexstyl)! - Add `composables mcp start`, a stdio MCP server that exposes Composables UI docs, project creation, and module creation tools to MCP clients.

- [`17f2a8a`](https://github.com/composablehorizons/composables-ui/commit/17f2a8a468b4d5d9d7a665d34eb76c7590dad687) Thanks [@alexstyl](https://github.com/alexstyl)! - Remove the old `composables target` command. Use `composables add module` to add new Compose app or library modules instead.

### Patch Changes

- [`17f2a8a`](https://github.com/composablehorizons/composables-ui/commit/17f2a8a468b4d5d9d7a665d34eb76c7590dad687) Thanks [@alexstyl](https://github.com/alexstyl)! - Generated projects now include a `.gitignore`, ktfmt-backed Spotless formatting, plus README commands for formatting checks.

- [#13](https://github.com/composablehorizons/composables-ui/pull/13) [`51a08eb`](https://github.com/composablehorizons/composables-ui/commit/51a08ebdce886385b020ee3fb7c0515ede05e808) Thanks [@alexstyl](https://github.com/alexstyl)! - Move the CLI source and npm package publishing into the UI monorepo release flow.
