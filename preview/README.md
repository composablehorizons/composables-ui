# Preview

The `preview` module contains the desktop preview host used by the JVM sample app.
It wraps Compose content in an interactive device frame so UI can be checked across mobile, tablet, and desktop shapes without deploying to each target.

Use it from JVM/desktop code when you want an in-app preview surface for a screen, sample, or demo.
Shared UI code should depend on `:preview-insets` instead of `:preview`.

## What It Provides

- `DevicePreviewHost`, a Compose host with device, orientation, layout direction, zoom, hot reload, and screenshot controls.
- Built-in mobile, tablet, and desktop device presets through `DevicePreviewDevices`.
- Fixed-size device previews with optional system bars and soft keyboard simulation.
- A fill-size desktop preview for checking responsive layouts inside the current window.
- Shortcut helpers that can be wired from a desktop `Window` `onKeyEvent`.
- JVM implementations for requesting Compose Hot Reload and saving or copying screenshots.

## Basic Usage

```kotlin
import com.composables.ui.preview.DevicePreviewHost

DevicePreviewHost {
    App()
}
```

By default, the host shows the built-in mobile, tablet, and desktop devices.
The toolbar lets the user switch device, rotate fixed-size devices, toggle layout direction, change zoom, trigger hot reload when available, and capture screenshots.

## How The Sample App Uses It

The desktop sample depends on `:preview` only from `jvmMain`.
Its shared `commonMain` code depends on `:preview-insets`, which keeps Android and Wasm builds free of desktop preview UI.

In `sample/src/jvmMain/kotlin/com/composables/ui/sample/Main.kt`, the sample:

- owns the selected device, orientation, layout direction, zoom, toolbar visibility, and screenshot request counters;
- maps desktop window shortcuts to preview actions before passing events to the app;
- wraps `App()` in `DevicePreviewHost`;
- passes changing screenshot request values so shortcuts can capture the current preview content;
- starts on `DevicePreviewDevices.Desktop` so the sample first opens as a full desktop layout.

The core wiring looks like this:

```kotlin
var selectedDevice by remember { mutableStateOf(DevicePreviewDevices.Desktop) }
var selectedOrientation by remember { mutableStateOf(DevicePreviewOrientation.Portrait) }
var selectedLayoutDirection by remember { mutableStateOf(LayoutDirection.Ltr) }
var selectedZoom by remember { mutableStateOf(DevicePreviewZoom.Default) }
var toolbarVisible by remember { mutableStateOf(true) }
var saveScreenshotRequest by remember { mutableStateOf(0) }
var copyScreenshotRequest by remember { mutableStateOf(0) }

DevicePreviewHost(
    initialDevice = DevicePreviewDevices.Desktop,
    selectedDevice = selectedDevice,
    onDeviceSelected = { selectedDevice = it },
    selectedOrientation = selectedOrientation,
    onOrientationSelected = { selectedOrientation = it },
    selectedLayoutDirection = selectedLayoutDirection,
    onLayoutDirectionSelected = { selectedLayoutDirection = it },
    selectedZoom = selectedZoom,
    onZoomSelected = { selectedZoom = it },
    showControls = toolbarVisible,
    saveScreenshotRequest = saveScreenshotRequest,
    copyScreenshotRequest = copyScreenshotRequest,
) {
    App()
}
```

## Controlled And Uncontrolled State

`DevicePreviewHost` can manage its own state:

```kotlin
DevicePreviewHost {
    App()
}
```

Pass selected values and callbacks when the surrounding desktop app should own preview state, persist it, or update it from keyboard shortcuts:

```kotlin
DevicePreviewHost(
    selectedDevice = selectedDevice,
    onDeviceSelected = { selectedDevice = it },
    selectedOrientation = selectedOrientation,
    onOrientationSelected = { selectedOrientation = it },
    selectedLayoutDirection = selectedLayoutDirection,
    onLayoutDirectionSelected = { selectedLayoutDirection = it },
    selectedZoom = selectedZoom,
    onZoomSelected = { selectedZoom = it },
) {
    App()
}
```

## Custom Devices

Provide a custom device list when the defaults do not match the UI you need to inspect:

```kotlin
DevicePreviewHost(
    devices = listOf(
        DevicePreviewDevice(
            id = "compact",
            label = "Compact",
            size = DevicePreviewSize.Fixed(width = 360.dp, height = 640.dp),
            renderSystemBars = true,
        ),
        DevicePreviewDevices.Desktop,
    ),
) {
    App()
}
```

Devices with `DevicePreviewSize.Fixed` can rotate.
Devices with `DevicePreviewSize.Fill` behave like desktop previews and fill the available host window.
Set `renderSystemBars = true` to draw preview status/navigation bars, provide matching preview insets, and enable soft keyboard simulation.

## Preview Insets

`DevicePreviewHost` provides `PreviewWindowInsets` from `:preview-insets` around the previewed content.
Code inside the preview can use helpers such as `previewStatusBarPadding()`, `previewNavigationBarPadding()`, and `previewSoftKeyboardPadding()`.

Outside `DevicePreviewHost`, those helpers fall back to normal Compose platform insets.
That lets shared sample UI run unchanged on desktop preview, Android, and Wasm.

## Hot Reload

The hot reload control is visible only when Compose Hot Reload is active.
On JVM, availability is detected from the `compose.reload.isActive` system property.
When triggered, the module finds the Gradle wrapper from the current working directory and starts the `reload` task.

Use `showHotReload = false` to hide the control, or pass `onHotReloadRequest` if the surrounding app should own the reload action.

## Screenshots

The screenshot control captures the preview content without the surrounding device frame.
On JVM it can save the image as a PNG through a native file dialog or copy it to the system clipboard.

For window-level shortcuts, pass changing values to `saveScreenshotRequest` or `copyScreenshotRequest`.
Each value change captures the current preview content.

## Keyboard Shortcuts

The module exposes helpers for wiring common shortcuts from a desktop window `onKeyEvent`:

- `Cmd+1`, `Cmd+2`, `Cmd+3`: select mobile, tablet, or desktop.
- `Cmd+-`, `Cmd++`, `Cmd+0`: zoom out, zoom in, or reset zoom.
- `Cmd+R`: rotate the selected device when rotation is available.
- `Cmd+Shift+R`: trigger Compose Hot Reload when Hot Reload is active.
- `Cmd+P`: save a screenshot of the preview content.
- `Cmd+Shift+P`: copy a screenshot of the preview content to the clipboard.
- `Cmd+backtick`: toggle LTR and RTL layout direction.
- `Cmd+.`: show or hide the preview toolbar.

Use `deviceForPreviewShortcut`, `devicePreviewZoomForShortcut`, `isDevicePreviewRotationShortcut`, `isDevicePreviewHotReloadShortcut`, `isDevicePreviewScreenshotSaveShortcut`, `isDevicePreviewScreenshotCopyShortcut`, `isDevicePreviewLayoutDirectionShortcut`, and `isDevicePreviewToolbarShortcut` to keep shortcut behavior aligned with the preview host.
