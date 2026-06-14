---
title: Tri-State Checkbox
description: Parent checkboxes that represent checked, unchecked, and mixed child selection.
---

Use tri-state checkboxes when a parent control summarizes the selection state of nested options.

<UiDemo id="checkbox-tri-state" />

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

<ComponentSource file="components/TriStateCheckbox.kt" />
<ComponentSource file="components/Utils.kt" />
</Tab>
</Tabs>

## Examples

### Disabled

<UiDemo id="checkbox-tri-state-disabled" />

## API Reference

<ApiReference id="tri-state-checkbox" />
