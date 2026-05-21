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

Primary buttons should be reserved for the main action on a screen.

```kotlin
Button(onClick = onSave) {
    Text("Save")
}
```

Use secondary and outlined buttons when the action is available but not the main path.

## API Reference

<ApiReference id="buttons" />
