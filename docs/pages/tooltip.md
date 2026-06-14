---
title: Tooltip
description: Anchored tooltips with configurable side, alignment, and panel styling.
---

Use tooltips for short, contextual explanations that appear near an anchor.

<UiDemo id="tooltip" />

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

<ComponentSource file="components/Tooltip.kt" />
<ComponentSource file="components/Utils.kt" />
</Tab>
</Tabs>

## Examples

### Side

<UiDemo id="tooltip-side" />

### Alignment

<UiDemo id="tooltip-alignment" />

### Hover delay

<UiDemo id="tooltip-hover-delay" />

### Long press duration

<UiDemo id="tooltip-long-press-duration" />

## API Reference

<ApiReference id="tooltip" />
