---
title: Tabs
description: Tabs with horizontal and vertical lists, icons, and animated indicators.
---

Use tabs for switching between related views without leaving the current screen.

<UiDemo id="tabs" />

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

<ComponentSource file="components/Tabs.kt" />
<ComponentSource file="components/Utils.kt" />
</Tab>
</Tabs>

## Examples

### Weighted

<UiDemo id="tabs-weighted" />

### With icons

<UiDemo id="tabs-icons" />

### Vertical

<UiDemo id="tabs-vertical" />

### Disabled

<UiDemo id="tabs-disabled" />

## API Reference

<ApiReference id="tabs" />
