---
title: Alert Dialog
description: Modal dialogs for confirmations, destructive actions, and focused decisions.
---

Use alert dialogs when you need to pause the current flow and ask for a clear choice.

<UiDemo id="alert-dialog" />

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

<ComponentSource file="components/AlertDialog.kt" />
<ComponentSource file="components/Utils.kt" />
</Tab>
</Tabs>

## Examples

### Three actions

<UiDemo id="alert-dialog-3-actions" />

### With an icon

<UiDemo id="alert-dialog-icon" />

## API Reference

<ApiReference id="alert-dialog" />
