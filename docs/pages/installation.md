---
title: Installation
description: How to install Composables UI.
---

To use Composables UI in your project, you need to follow these steps:

### Add dependencies

Composables UI components are built on top of [Compose Unstyled](https://composables.com/compose-unstyled). You need to add the following dependencies to your project:

```kotlin title="app/build.gradle.kts"
implementation("com.composables:composeunstyled:2.2.2")
implementation("com.composables:compose-interaction-capabilities:1.0.0")
```

### Copy and Paste

Composables UI is not a library in the traditional sense. Instead of adding a dependency for the components themselves, you copy and paste the source code of the components you want to use directly into your project.

This gives you full control over the code, allowing you to customize it to fit your specific needs without being constrained by a library's API.

Start by setting up [Theming](theming.md) and then check out the available [Components](components.md).

