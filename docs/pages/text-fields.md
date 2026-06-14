---
title: Text Fields
description: Text input fields for standard, ghost, search, multiline, and read-only states.
---

Use text fields for editable text input, inline search, and compact form controls.

<UiDemo id="text-field" />

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

<ComponentSource file="components/TextField.kt" />
<ComponentSource file="components/Utils.kt" />
</Tab>
</Tabs>

## Examples

### Search

<UiDemo id="text-field-search" />

### Ghost

<UiDemo id="text-field-ghost" />

### Multiline

<UiDemo id="text-field-multiline" />

### Disabled

<UiDemo id="text-field-disabled" />

### Read-only

<UiDemo id="text-field-read-only" />

## API Reference

<ApiReference id="text-fields" />
