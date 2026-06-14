---
title: Dropdown Menu
description: Anchored menus for selections, overflow actions, and contextual commands.
---

Use dropdown menus for compact action lists that open from a trigger.

<UiDemo id="dropdown-menu" />

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

<ComponentSource file="components/DropdownMenu.kt" />
<ComponentSource file="components/Utils.kt" />
</Tab>
</Tabs>

## Examples

### Overflow menu

<UiDemo id="dropdown-menu-toolbar" />

## API Reference

<ApiReference id="dropdown-menu" />
