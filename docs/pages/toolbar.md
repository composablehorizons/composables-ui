---
title: Toolbar
description: Medium, centered, and large title toolbars for page-level actions and navigation.
---

Use toolbars to anchor a screen title, navigation affordances, and top-level actions.

<UiDemo id="toolbar-actions" />

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

<ComponentSource file="components/Toolbar.kt" />
<ComponentSource file="components/Utils.kt" />
</Tab>
</Tabs>

## Examples

### Centered

<UiDemo id="centered-toolbar" />

### Large

<UiDemo id="large-toolbar" />

## API Reference

<ApiReference id="toolbar" />
