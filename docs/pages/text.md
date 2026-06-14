---
title: Text
description: Styled text that inherits local typography and content color by default.
---

Use text for labels, titles, body copy, and inline content that should stay consistent with the surrounding theme.

<UiDemo id="text" />

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

<ComponentSource file="components/Text.kt" />
<ComponentSource file="components/Utils.kt" />
</Tab>
</Tabs>

## Examples

### Styled

<UiDemo id="text-styled" />

### Themed

<UiDemo id="text-themed" />

## API Reference

<ApiReference id="text" />
