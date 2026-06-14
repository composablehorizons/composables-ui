---
title: Switch
description: Binary switches for immediate on and off settings.
---

Use switches for settings that change state immediately.

<UiDemo id="switch" />

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

<ComponentSource file="components/Switch.kt" />
<ComponentSource file="components/Utils.kt" />
</Tab>
</Tabs>

## Examples

### Disabled

<UiDemo id="switch-disabled" />

## API Reference

<ApiReference id="switch" />
