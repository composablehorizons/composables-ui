---
title: Button
description: Button components for primary actions, secondary actions, outlines, destructive actions, and icon-only controls.
---

Use buttons for actions that submit, confirm, cancel, create, delete, or move a user through a workflow.

<UiDemo id="button-default" />

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

<ComponentSource file="components/Button.kt" />
<ComponentSource file="components/Utils.kt" />
</Tab>
</Tabs>

## Examples


### Size

<UiDemo id="button-sizes" />

### Primary

<UiDemo id="button-primary" />

### Secondary

<UiDemo id="button-secondary" />

### Outlined

<UiDemo id="button-outlined" />

### Ghost

<UiDemo id="button-ghost" />

### Destructive

<UiDemo id="button-destructive" />

### Disabled

<UiDemo id="button-disabled" />

## API Reference

<ApiReference id="buttons" />
