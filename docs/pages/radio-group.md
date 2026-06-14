---
title: Radio Group
description: Single-selection radio groups with individually selectable options.
---

Use radio groups when exactly one option should be selected from a set.

<UiDemo id="radio-group" />

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

<ComponentSource file="components/RadioGroup.kt" />
<ComponentSource file="components/Utils.kt" />
</Tab>
</Tabs>

## Examples

### Disabled

<UiDemo id="radio-group-disabled" />

## API Reference

<ApiReference id="radio-group" />
