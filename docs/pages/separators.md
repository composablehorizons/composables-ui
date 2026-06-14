---
title: Separators
description: Horizontal and vertical dividers for grouping adjacent content.
---

Use separators to break content into visual groups without adding extra chrome.

<UiDemo id="separators-horizontal" />

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

<ComponentSource file="components/Separators.kt" />
<ComponentSource file="components/Utils.kt" />
</Tab>
</Tabs>

## Examples

### Vertical

<UiDemo id="separators-vertical" />

## API Reference

<ApiReference id="separators" />
