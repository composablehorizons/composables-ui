---
title: CLI
description: Create Compose Multiplatform projects, add modules, browse docs, and install MCP clients with the Composables CLI.
---

The Composables CLI helps you start Compose Multiplatform projects with Composables UI, add modules to existing Gradle projects, browse docs from the terminal, and configure MCP clients.

## Install

Install the CLI from npm:

```shell
npm install -g composables-cli
```

Check that it is available:

```shell
composables --help
```

## Create a Project

Create a new Compose Multiplatform project:

```shell
composables init my-app --package com.example.app --app-name "My App" --targets android,jvm,ios,wasm
```

Omit the options to use the interactive wizard:

```shell
composables init
```

Available targets are `android`, `jvm`, `ios`, and `wasm`.

## Add a Module

Run module commands from the root of an existing Gradle project.

Add an app module group:

```shell
composables add module features/chat --type app --package com.example.chat --app-name "Chat" --targets android,jvm,ios,wasm
```

Add a library module:

```shell
composables add module features/chat-ui --type library --package com.example.chat.ui --targets android,jvm,ios,wasm
```

Omit the options to use the interactive wizard:

```shell
composables add module
```

## Browse Docs

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

## MCP

Install Composables MCP for a supported client:

```shell
composables mcp install --client cursor
```

Run this from the root of your Gradle project for project-scoped clients.

Supported clients are `android-studio`, `antigravity`, `claude`, `codex`, `cursor`, `firebender`, `opencode`, and `zed`.

The local stdio MCP server command is:

```shell
composables mcp start
```

You normally do not run `mcp start` yourself. MCP clients start it after `mcp install` writes their configuration.

See the [MCP Server](mcp.md) page for client-specific setup.
