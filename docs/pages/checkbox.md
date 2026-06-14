---
title: Checkbox
description: Checkbox controls for independent on and off selections.
---

Use checkboxes when people can enable or disable options independently.

<UiDemo id="checkbox" />

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

<ComponentSource file="components/Checkbox.kt" />
<ComponentSource file="components/Utils.kt" />
</Tab>
</Tabs>

## Examples

### Disabled

<UiDemo id="checkbox-disabled" />

### Hierarchical selection

For parent and child selection flows, use the tri-state checkbox variant.

See [Tri-State Checkbox](tri-state-checkbox.md).

## API Reference

<ApiReference id="checkbox" />
