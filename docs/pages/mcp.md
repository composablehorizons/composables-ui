---
title: MCP Server
description: Use the Composables MCP server to let your agent know about components, installation steps, code examples.
---

The Composables MCP server enables your AI agents to build Compose apps with Composables UI by searching for documentation straight from the source without having to rely on web search and outdated information.

### Quick Start

The fastest way to use the Composables MCP server is installing it to Android Studio, using the [Composables CLI](https://github.com/composablehorizons/composables-cli).

Make sure you have the CLI installed and then run the following command:

```shell
composables install --client android-studio
```

then on Android Studio go to: **Settings > Tools > AI > MCP Servers**

and in the **JSON View** tab turn on the `Enable MCP Servers` option.

For more information, visit Android Studio's [Add an MCP server](https://developer.android.com/studio/gemini/add-mcp-server)

## Configurations

### HTTP MCP Server

The Composables MCP server can be used with MCP clients that support streamable HTTP transport.

Use this endpoint when adding a new MCP server:

```text
https://composables.com/mcp
```

## Limitations

We currently support streamable HTTP transport only, because this is the transport Android Studio supports right now.

If you are working with a different IDE or editor and you need support for it, we are happy to support it.

Kindly  [open a feature request](https://github.com/composablehorizons/composables-cli/issues/new?category=ideas).

## Example prompts

- Install Composables UI in this project
- Create a home screen, with an app bar and bottom navigation with 4 tabs.
- How to use Composables UI bottom sheets?
- Build a sign-in screen with email and password fields plus a primary submit button.
- Add scrollbars to this screen using Composables UI.

## Learn More
- [What is the Model Context Protocol (MCP)?](https://modelcontextprotocol.io/docs/getting-started/intro) - Learn about the Model Context Protocol
