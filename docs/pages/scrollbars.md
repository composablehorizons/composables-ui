---
title: Scrollbars
description: Vertical and horizontal scrollbars for scroll states and lazy layouts.
---

Use scrollbars when content containers need clearer scroll affordances.

<UiDemo id="scrollbars-vertical" />

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

<ComponentSource file="components/Scrollbars.kt" />
<ComponentSource file="components/Utils.kt" />
</Tab>
</Tabs>

## Examples

### Horizontal

<UiDemo id="scrollbars-horizontal" />

### LazyColumn

<UiDemo id="scrollbars-lazy-column" />

### Always visible

<UiDemo id="scrollbars-always-visible" />

### Disabled

<UiDemo id="scrollbars-vertical-disabled" />

### Horizontal disabled

<UiDemo id="scrollbars-horizontal-disabled" />

## API Reference

<ApiReference id="scrollbars" />
