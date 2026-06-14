# Preview Insets

The `preview-insets` module contains shared Compose helpers for UI that needs to run both inside `DevicePreviewHost` and on real platforms.
It lets preview tooling provide simulated status bar, navigation bar, and soft keyboard sizes while preserving normal platform inset behavior everywhere else.

Use this module from shared UI code.
Use `:preview` only from JVM/desktop code that needs the actual preview host.

## What It Provides

- `LocalWindowInsets`, a composition local for preview-provided inset values.
- `PreviewWindowInsets`, the status bar, navigation bar, soft keyboard, and orientation values supplied by preview tooling.
- `ProvidePreviewWindowInsets`, a small provider used by `DevicePreviewHost` and available for custom preview hosts.
- Padding helpers that use preview values when present and Compose platform insets when absent.

## Padding Helpers

Use these helpers in UI that should respond to previewed device chrome:

```kotlin
Modifier.previewStatusBarPadding()
Modifier.previewNavigationBarPadding()
Modifier.previewSoftKeyboardPadding()
```

Use these value helpers when an API needs explicit `Dp` padding instead of a modifier:

```kotlin
val statusBarPadding = previewStatusBarPaddingValue()
val navigationBarPadding = previewNavigationBarPaddingValue()
```

When no preview insets are provided, the helpers fall back to `WindowInsets.statusBars`, `WindowInsets.navigationBars`, and `WindowInsets.ime`.

## How The Sample App Uses It

The sample app depends on `:preview-insets` from `commonMain`.
That allows shared screens to react to preview system bars and the simulated soft keyboard while still compiling for Android and Wasm.

Examples from the sample:

- `App.kt` applies `previewNavigationBarPadding()` to bottom UI only when the layout needs mobile navigation bar spacing.
- `NewPost.kt` uses `previewSoftKeyboardPadding()` so focused text input leaves room for the preview keyboard.
- `PostDetails.kt` and `SampleInsets.kt` read `previewStatusBarPaddingValue()` and `previewNavigationBarPaddingValue()` when constructing explicit `WindowInsets`.

The desktop sample then wraps the shared `App()` with `DevicePreviewHost` from `:preview`.
Inside that host, `preview-insets` receives simulated values for fixed mobile and tablet devices.
Outside that host, the same shared UI reads the real platform insets.

## Providing Custom Preview Insets

Custom preview tooling can provide its own insets:

```kotlin
ProvidePreviewWindowInsets(
    windowInsets = PreviewWindowInsets(
        statusBarSize = 22.dp,
        navigationBarSize = 24.dp,
        softKeyboardSize = 0.dp,
        orientation = PreviewInsetsOrientation.Portrait,
    ),
) {
    Content()
}
```

For landscape previews, set `orientation = PreviewInsetsOrientation.Landscape`.
`previewNavigationBarPadding()` places the navigation bar padding on the end side in landscape and on the bottom side in portrait.
If the soft keyboard is taller than the navigation bar, the bottom navigation padding is reduced so the two insets do not stack incorrectly.
