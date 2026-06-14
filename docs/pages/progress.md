---
title: Progress Indicators
description: Determinate and indeterminate indicators for loading and task progress.
---

Use progress indicators to show completion state or ongoing work.

<UiDemo id="progress-indicator" />

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

<ComponentSource file="components/Progress.kt" />
<ComponentSource file="components/Utils.kt" />
</Tab>
</Tabs>

## Examples

### Indeterminate

<UiDemo id="progress-indicator-indeterminate" />

## API Reference

<ApiReference id="progress" />
