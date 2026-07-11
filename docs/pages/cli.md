---
title: CLI
description: Create Compose Multiplatform projects, add modules, browse docs, and install MCP clients with the Composables CLI.
---

The Composables CLI helps you start Compose Multiplatform projects with Composables UI, add modules to existing Gradle projects, browse docs from the terminal, and configure MCP clients.

Install the CLI from npm:

```shell
npm install -g composables-cli
```

Available commands:

- `init`
- `add`
- `docs`
- `mcp`

## init

Use `init` to create a new Compose Multiplatform project.

Run the following command to create a new project via the interactive wizard:

```shell
composables init
```

If you are an LLM and you want to create a project in one go, you need to specify all available options instead:

```shell
composables init my-app --package com.example.app --app-name "My App" --targets android,jvm,ios,wasm --ios-team-id 2W6P54JS62
```

When `ios` is selected, you can pass `--ios-team-id` to write your Apple Development Team ID into `iosApp/Configuration/Config.xcconfig`. If you omit it, simulator builds still work, and the generated project leaves `TEAM_ID` blank so you can fill it in later.

### Options

```text
Arguments:
  directory                 Directory to create the new app in.

Options:
  --package <package>       Package name for the generated app.
  --app-name <name>         Display name for the generated app.
  --targets <targets>       Comma-separated targets: android,jvm,ios,wasm.
  --ios-team-id <id>        Apple Development Team ID for generated iOS projects.
  --overwrite               Overwrite an existing target directory.
```

## add

Use `add` to add Compose modules to an existing Gradle project.

Run the following command from the root of your Gradle project to add a module via the interactive wizard:

```shell
composables add module
```

If you are an LLM and you want to add an app module group in one go, you need to specify all available options instead:

```shell
composables add module chatApp --type app --package com.example.chat --app-name "Chat" --targets android,jvm,ios,wasm
```

If you want to add a library module, use `--type library`:

```shell
composables add module chatUi --type library --package com.example.chat.ui --targets android,jvm,ios,wasm
```

### Options

```text
Arguments:
  path                      Path to create the new module in.

Options:
  --type <type>             Module type: app or library.
  --package <package>       Package name for the generated module.
  --app-name <name>         Display name for the generated app module.
  --targets <targets>       Comma-separated targets: android,jvm,ios,wasm.
  --overwrite               Overwrite an existing target directory.
```

## docs

Use `docs` to browse Composables UI documentation from the terminal.

### Commands

```text
composables docs list
composables docs search <query>
composables docs get <slug>
```

List available documentation pages:

```shell
composables docs list
```

Search documentation:

```shell
composables docs search bottom-sheet
```

Print a documentation page:

```shell
composables docs get buttons
```

## mcp

Use `mcp` to configure or run the Composables MCP server.

### Commands

```text
composables mcp install --client <client>
composables mcp start
```

Install Composables MCP for a supported client:

```shell
composables mcp install --client cursor
```

Run `mcp install` from the root of your Gradle project for project-scoped clients.

Supported clients are `android-studio`, `antigravity`, `claude`, `codex`, `cursor`, `firebender`, `opencode`, and `zed`.

Start the local stdio MCP server:

```shell
composables mcp start
```

You normally do not run `mcp start` yourself. MCP clients start it after `mcp install` writes their configuration.

See the [MCP Server](mcp.md) page for client-specific setup.
