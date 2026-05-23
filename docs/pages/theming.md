---
title: Theming
description: Learn how to set up and use the theme in your project.
---

Composables UI uses a centralized theme to manage colors, shapes, shadows, and component sizes. This allows you to easily switch between light and dark modes.

## Installation

### Add dependencies

```kotlin title="app/build.gradle.kts"
implementation("com.composables:composeunstyled:2.2.2")
implementation("com.composables:compose-interaction-capabilities:1.0.0")
```

### Copy files

Copy and paste the following sources into your project:

<ComponentSource file="Theme.kt" />

## Usage

To use the theme, wrap your root Composable with `AppTheme`.

```kotlin
import com.composables.ui.theme.AppTheme

@Composable
fun App() {
    AppTheme {
        // Your application content
    }
}
```

### Accessing theme values

You can access theme values anywhere inside `AppTheme` using the `Theme` object.

```kotlin
import com.composeunstyled.theme.Theme
import com.composables.ui.theme.colors
import com.composables.ui.theme.primary

@Composable
fun MyComponent() {
    Box(Modifier.background(Theme[colors][primary])) {
        // ...
    }
}
```

## Tokens

The following tokens are available in the theme. You can access them using `Theme[property][token]`.

### Colors

| Token | Description |
| --- | --- |
| `background` | The main background color of the application. |
| `onBackground` | Color for content on top of the background. |
| `panel` | Background color for panels, cards, and dialogs. |
| `onPanel` | Color for content on top of panels. |
| `muted` | Color for secondary or less important text. |
| `primary` | The primary brand color. |
| `onPrimary` | Color for content on top of primary color. |
| `secondary` | The secondary brand color. |
| `onSecondary` | Color for content on top of secondary color. |
| `control` | Background color for UI controls like text fields or buttons. |
| `onControl` | Color for content on top of controls. |
| `selectedControl` | Background color for selected UI controls. |
| `onSelectedControl` | Color for content on top of selected controls. |
| `destructive` | Color for destructive actions (e.g., delete). |
| `onDestructive` | Color for content on top of destructive color. |
| `border` | Color for borders and separators. |
| `field` | Background color for input fields. |
| `onField` | Color for content within input fields. |
| `input` | Color for input text. |
| `onInput` | Color for content on top of input. |
| `inputPlaceholder` | Color for placeholder text in input fields. |
| `inputDisabled` | Color for disabled input fields. |
| `textSelectionHandle` | Color for the text selection handle. |
| `textSelectionBackground` | Color for the text selection background. |
| `scrim` | Color for the scrim behind modals. |
| `focusRing` | Color for the focus ring. |

### Shapes

| Token | Description |
| --- | --- |
| `buttonShape` | Shape for buttons. |
| `alertDialogShape` | Shape for alert dialogs. |
| `bottomSheetShape` | Shape for bottom sheets. |
| `dropdownMenuShape` | Shape for dropdown menus. |
| `textFieldShape` | Shape for text fields. |

### Component Sizes

| Token | Description |
| --- | --- |
| `buttonHeight` | Height of buttons. |
| `buttonHorizontalPadding` | Horizontal padding within buttons. |
| `iconButtonSize` | Size of icon buttons. |
| `dropdownMenuItemHeight` | Height of items in a dropdown menu. |
| `textFieldHeight` | Height of text fields. |
| `textFieldHorizontalPadding` | Horizontal padding within text fields. |
| `focusRingWidth` | Width of the focus ring. |
| `focusRingOffset` | Offset of the focus ring. |

### Shadows

| Token | Description |
| --- | --- |
| `dropdownMenuShadow` | Shadow for dropdown menus. |

### Alphas

| Token | Description |
| --- | --- |
| `disabledAlpha` | Alpha value applied to disabled components. |

### Indications

| Token | Description |
| --- | --- |
| `bright` | A bright ripple indication. |
| `dim` | A dimmed ripple indication. |

