---
title: Installation
description: How to install Composables UI.
---

## Create an app using the CLI

The [Composables CLI](https://github.com/composablehorizons/composables-cli) is the fastest way to get started with Composables UI.

Install it on your computer:

```bash
npm install -g composables-cli
```

then create a new app using the interactive wizard:

```bash
composables create-app
```

Note that the CLI is optimized for agenting coding. Ask your LLM to create an app for specific targets for you.

## Install to an existing project

There are 2 ways to add Composables UI to your project.

Either via the gradle dependency, or by manually copy and pasting the sources in your project.

<Tabs>
<Tab title="Gradle">
Add the Gradle dependency in your `app/build.gradle.kts` file. This will add all components and theming functionality to your project:

```kotlin title="app/build.gradle.kts"
implementation("com.composables:ui:{{ libs.versions.ui }}")
```

</Tab>
<Tab title="Copy & Paste">
Add the required dependencies in your `app/build.gradle.kts` file. These are used by the components and themes that bring each components UX patterns and missing Jetpack Compose styling APIs:

```kotlin title="app/build.gradle.kts"
implementation("com.composables:composeunstyled:{{ libs.versions.unstyled }}")
implementation("com.composables:compose-interaction-capabilities:{{ libs.versions.interactionCapabilities }}")
```

Once that is done, head over to [components] and pick any component you wish to add to your project.

Also head over to [theming] to add design token based theming functionality to your app if you don't have one.
</Tab>
</Tabs>
