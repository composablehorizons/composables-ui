# composables-cli

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
