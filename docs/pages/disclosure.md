---
title: Disclosure
description: Expandable sections with a button and an animated content panel.
---

Use disclosures when content should stay collapsed until the user asks to reveal more.

<UiDemo id="disclosure" />

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

<ComponentSource file="components/Disclosure.kt" />
<ComponentSource file="components/Utils.kt" />
</Tab>
</Tabs>

## Examples

### With an indicator

<UiDemo id="disclosure-indicator" />

### Disabled

<UiDemo id="disclosure-disabled" />

## API Reference

<ApiReference id="disclosure" />
