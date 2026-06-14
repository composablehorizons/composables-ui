---
title: Slider
description: Sliders for continuous values, stepped ranges, and vertical controls.
---

Use sliders when people need to adjust a value across a bounded range.

<UiDemo id="slider" />

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

<ComponentSource file="components/Slider.kt" />
<ComponentSource file="components/Utils.kt" />
</Tab>
</Tabs>

## Examples

### Vertical

<UiDemo id="slider-vertical" />

### Disabled

<UiDemo id="slider-disabled" />

### Stepped

<UiDemo id="slider-steps" />

## API Reference

<ApiReference id="slider" />
