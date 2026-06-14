---
title: Theming
description: Learn how to set up and use the theme in your project.
---

## Overview

Composables UI components automatically adapt to the device capabilities and properties.

Buttons might look bigger on touch devices so that they are easier to press with a finger, while narrower and sharper on
desktop devices to save screen estate.

This mechanism is powered by the `ComposablesTheme`. The theme also provides the rest of the app with design tokens for various
properties, such as colors, shapes, alphas and indications.

Keep reading to learn how to add and use the theme in your app.

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
implementation("com.composables:compose-interaction-capabilities:{{ libs.versions.interactionCapabilities }}")
implementation("com.composables:ripple-indication:{{ libs.versions.rippleIndication }}")
```

#### Copy and paste the following sources into your project

<ComponentSource file="theme/Theme.kt" />
</Tab>
</Tabs>

## Usage

To use the theme, wrap your root Composable with `ComposablesTheme`.

```kotlin
import com.composables.ui.theme.ComposablesTheme

@Composable
fun App() {
    ComposablesTheme {
        // Your application content
    }
}
```

### Accessing theme values

You can access theme values anywhere inside `ComposablesTheme` using the `Theme` object.

```kotlin
import com.composeunstyled.theme.Theme
import com.composables.ui.theme.colors
import com.composables.ui.theme.primaryColor

@Composable
fun MyComponent() {
    Box(Modifier.background(Theme[colors][primaryColor])) {
        // ...
    }
}
```

## Theme Properties and Tokens

`ComposablesTheme` contains the following theme properties:

- [`colors`](#colors)
- [`shapes`](#shapes)
- [`shadows`](#shadows)
- [`textSelectionColors`](#textselectioncolors)
- [`alphas`](#alphas)
- [`indications`](#indications)

You can access them using `Theme[property][token]`, such as `Theme[colors][backgroundColor]`.

### `colors`

| Token                      | Description                                                                     |
|----------------------------|---------------------------------------------------------------------------------|
| `backgroundColor`          | Background color of a given screen.                                             |
| `onBackgroundColor`        | Content color that sits directly on `backgroundColor`.                          |
| `panelColor`               | Background color for components such as panels, cards, dialogs, sheets, and menus. |
| `onPanelColor`             | Content color that sits directly on `panelColor`.                               |
| `mutedColor`               | Lower-emphasis content color.                                                   |
| `primaryColor`             | Color used for primary actions.                                                 |
| `onPrimaryColor`           | Content color that sits directly on `primaryColor`.                             |
| `secondaryColor`           | Color used for secondary actions.                                               |
| `onSecondaryColor`         | Content color that sits directly on `secondaryColor`.                           |
| `controlColor`             | Background color for controls, such as progressbar track.                       |
| `onControlColor`           | Content color that sits directly on `controlColor`.                             |
| `thumbColor`               | Thumb color for controls such as sliders and toggles.                           |
| `switchTrackColor`         | Default `Switch` track color.                                                   |
| `switchSelectedTrackColor` | Selected `Switch` track color.                                                  |
| `switchThumbColor`         | Switch's Thumb color.                                                           |
| `selectedControlColor`     | Background color for selected controls.                                         |
| `onSelectedControlColor`   | Content color that sits directly on `selectedControlColor`.                     |
| `destructiveColor`         | Color used for destructive actions such as deletion.                            |
| `onDestructiveColor`       | Content color that sits directly on `destructiveColor`.                         |
| `borderColor`              | Border and separator color.                                                     |
| `fieldColor`               | Background color used for input fields such as text fields.                     |
| `onFieldColor`             | Content color that sits directly on `fieldColor`.                               |
| `scrimColor`               | Scrim color behind modal content.                                               |
| `ringColor`                | Focus ring color.                                                               |

### `shapes`

| Token         | Description                                   |
|---------------|-----------------------------------------------|
| `smallShape`  | Small shape used by compact controls.         |
| `mediumShape` | Medium shape used by medium-sized containers. |
| `largeShape`  | Large shape used by larger surfaces.          |
| `buttonShape` | Shape for buttons.                            |
| `dialogShape` | Shape for dialogs.                            |
| `sheetShape`  | Shape for bottom sheets.                      |
| `menuShape`   | Shape for menus.                              |
| `fieldShape`  | Shape for text fields.                        |

### `shadows`

| Token           | Description                                                     |
|-----------------|-----------------------------------------------------------------|
| `raisedShadow`  | Shadow for raised controls and surfaces.                        |
| `overlayShadow` | Shadow for overlay surfaces such as dialogs, sheets, and menus. |

### `textSelectionColors`

| Token                        | Description                                           |
|------------------------------|-------------------------------------------------------|
| `defaultTextSelectionColors` | Default handle and background colors for text selection. |

### `alphas`

| Token           | Description                                 |
|-----------------|---------------------------------------------|
| `disabledAlpha` | Alpha value applied to disabled components. |

### `indications`

| Token               | Description                                                         |
|---------------------|---------------------------------------------------------------------|
| `defaultIndication` | Indication for controls.                                            |
| `inverseIndication` | Ripple indication for content on inverse or high-contrast surfaces. |

## API Reference

<ApiReference id="theming" />
