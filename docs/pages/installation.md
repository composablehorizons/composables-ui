---
title: Installation
description: How to install Composables UI.
---

There are 2 ways to add Composables UI to your project.

Either via the gradle dependency, or by manually copy and pasting the sources in your project.

<Tabs>
<Tab title="Gradle">
Add the Gradle dependency in your `app/build.gradle.kts` file. This will add all components and theming functionality to your project:

```kotlin title="app/build.gradle.kts"
implementation("com.composables:ui:{{ libs.versions.ui }}")
```

</Tab>
<Tab title="Copy & Paste">
Add the required dependencies in your `app/build.gradle.kts` file. These are used by the components and themes that bring each components UX patterns and missing Jetpack Compose styling APIs:

```kotlin title="app/build.gradle.kts"
implementation("com.composables:composeunstyled:{{ libs.versions.unstyled }}")
implementation("com.composables:compose-interaction-capabilities:{{ libs.versions.interactionCapabilities }}")
```

Once that is done, head over to [components] and pick any component you wish to add to your project.

Also head over to [theming] to add design token based theming functionality to your app if you don't have one.
</Tab>
</Tabs>
