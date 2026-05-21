---
title: Buttons
description: Button components for primary actions, secondary actions, outlines, destructive actions, and icon-only controls.
---

Use buttons for actions that submit, confirm, cancel, create, delete, or move a user through a workflow.

<UiDemo id="button-primary" />

## Installation

### Add dependencies

```kotlin title="app/build.gradle.kts"
implementation("com.composables:composeunstyled:2.2.2")
implementation("com.composables:compose-interaction-capabilities:1.0.0")
```

### Copy files

Copy and paste the following sources into your project:

<ComponentSource file="Button.kt" />
<ComponentSource file="Theme.kt" />

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

## API Reference

<ApiReference id="buttons" />
