---
title: MCP Server
description: Use the Composables MCP server to let your agent know about components, installation steps, code examples.
---

The Composables MCP server enables your AI agents to build Compose apps with Composables UI by searching for documentation straight from the source without having to rely on web search and outdated information.

### Quick Start

The fastest way to use the Composables MCP server is installing it with the [Composables CLI](https://github.com/composablehorizons/composables-cli).

Make sure you have the CLI installed:

```shell
npm install -g composables-cli
```

<Tabs>
<Tab title="Android Studio">

```shell
composables mcp install --client android-studio
```

Then open Android Studio and go to **Settings > Tools > AI > MCP Servers**.

In the **JSON View** tab turn on the `Enable MCP Servers` option.

For more information, visit Android Studio's [Add an MCP server](https://developer.android.com/studio/gemini/add-mcp-server)

</Tab>
<Tab title="Antigravity">

Run this from the root of your Gradle project:

```shell
composables mcp install --client antigravity
```

</Tab>
<Tab title="Claude">

Run this from the root of your Gradle project:

```shell
composables mcp install --client claude
```

This works both for Claude Code CLI and Claude Code Desktop.

</Tab>
<Tab title="Codex">

Run this from the root of your Gradle project:

```shell
composables mcp install --client codex
```

This works both for the Codex CLI and Codex Desktop app.

</Tab>
<Tab title="Cursor">

Run this from the root of your Gradle project:

```shell
composables mcp install --client cursor
```

</Tab>
<Tab title="Firebender">

Run this from the root of your Gradle project:

```shell
composables mcp install --client firebender
```

</Tab>
<Tab title="OpenCode">

Run this from the root of your Gradle project:

```shell
composables mcp install --client opencode
```

</Tab>
<Tab title="Zed">

Run this from the root of your Gradle project:

```shell
composables mcp install --client zed
```

</Tab>
</Tabs>

<Tab title="Other">

If you are working with a different IDE or editor, and you need support for it, we are happy to add it.

Kindly [open a feature request](https://github.com/composablehorizons/composables-ui/issues/new).

</Tab>
## Configurations

### HTTP MCP Server

The Composables MCP server can be used with MCP clients that support streamable HTTP transport.

Use this endpoint when adding a new MCP server:

```text
https://composables.com/mcp
```

### Stdio MCP Server

The Composables MCP server can also run locally over stdio transport.

Use stdio when your MCP client needs to run local project actions, like creating projects or adding modules:

```shell
composables mcp start
```

You do not need to run this command manually when you install with `composables mcp install`. The MCP client starts it when needed.

## Example prompts

- Install Composables UI in this project
- Create a home screen, with an app bar and bottom navigation with 4 tabs.
- How to use Composables UI bottom sheets?
- Build a sign-in screen with email and password fields plus a primary submit button.
- Add scrollbars to this screen using Composables UI.

## Learn More
- [What is the Model Context Protocol (MCP)?](https://modelcontextprotocol.io/docs/getting-started/intro) - Learn about the Model Context Protocol
