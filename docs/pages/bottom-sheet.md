---
title: Bottom Sheet
description: Modal bottom sheets for action menus, confirmations, and lightweight forms.
---

Use bottom sheets for contextual actions and short tasks that should stay close to the current screen.

<UiDemo id="bottom-sheet-action-menu" />

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

<ComponentSource file="components/BottomSheet.kt" />
<ComponentSource file="components/Utils.kt" />
</Tab>
</Tabs>

## Examples

### Confirmation

<UiDemo id="bottom-sheet-confirmation" />

### Form

<UiDemo id="bottom-sheet-form" />

## API Reference

<ApiReference id="bottom-sheet" />
