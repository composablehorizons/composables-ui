---
title: Icon
description: ImageVector-based icons that inherit the surrounding content color by default.
---

Use icons for compact visual cues inside buttons, toolbars, menus, and status UI.

<UiDemo id="icon" />

## Installation

<Tabs>
<Tab title="Gradle">
```kotlin title="app/build.gradle.kts"
implementation("com.composables:ui:{{ libs.versions.ui }}")
```
</Tab>
<Tab title="Copy & Paste">
#### Add the required dependencies

```kotlin title="app/build.gradle.kts"
implementation("com.composables:composeunstyled:{{ libs.versions.unstyled }}")
```

#### Copy and paste the following sources into your project

<ComponentSource file="components/Icon.kt" />
<ComponentSource file="components/Utils.kt" />
</Tab>
</Tabs>

## Examples

### Custom tint

<UiDemo id="icon-tinted" />

### Themed

<UiDemo id="icon-themed" />

## API Reference

<ApiReference id="icon" />
