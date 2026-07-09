# Composables Devtools

Local Compose Multiplatform tooling for running a composable inside a device preview host.

## Install

Add the plugin build in `settings.gradle.kts`:

```kotlin
pluginManagement {
    includeBuild("devtools/gradle-plugin")
}
```

Include the runtime modules:

```kotlin
include(":devtools:runtime")
include(":devtools:insets")
```

Apply the plugin to the module that owns the composable you want to preview:

```kotlin
plugins {
    id("com.composables.devtools")
}
```

Configure the entry composable:

```kotlin
composablesDevTools {
    appComposable.set("com.example.App")
}
```

## Run

```bash
./gradlew :module:runDevTools
```
