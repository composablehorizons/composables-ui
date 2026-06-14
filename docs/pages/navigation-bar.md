---
title: Navigation Bar
description: Bottom navigation bars for switching between top-level destinations.
---

Use a navigation bar when users need to move between a small set of primary destinations.

<UiDemo id="navigation-bar" />

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

<ComponentSource file="components/NavigationBar.kt" />
<ComponentSource file="components/Utils.kt" />
</Tab>
</Tabs>

## Examples

### Disabled items

<UiDemo id="disabled-navigation-bar" />

### Custom insets

<UiDemo id="custom-insets-navigation-bar" />

## API Reference

<ApiReference id="navigation-bar" />
