# Preview

The preview module provides `DevicePreviewHost`, a Compose component for checking how a UI behaves across common device shapes without leaving the app.

Use it to wrap a screen, sample, or demo content when you want quick controls for:

- phone, tablet, and desktop previews
- portrait and landscape orientation for devices that support rotation
- LTR and RTL layout direction
- zooming in and out

## Basic Usage

```kotlin
DevicePreviewHost {
    App()
}
```

By default, the host shows a toolbar with the built-in phone, tablet, and desktop devices.

## Controlled State

Pass selected values and callbacks when the surrounding app should own the preview state:

```kotlin
var selectedDevice by remember { mutableStateOf(DevicePreviewDevices.Desktop) }
var selectedOrientation by remember { mutableStateOf(DevicePreviewOrientation.Portrait) }
var selectedLayoutDirection by remember { mutableStateOf(LayoutDirection.Ltr) }
var selectedZoom by remember { mutableStateOf(DevicePreviewZoom.Default) }
val screenshotState = rememberDevicePreviewScreenshotState()

DevicePreviewHost(
    screenshotState = screenshotState,
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

Provide your own device list when the defaults do not match what you need:

```kotlin
DevicePreviewHost(
    devices = listOf(
        DevicePreviewDevice(
            id = "compact",
            label = "Compact",
            width = 360.dp,
            height = 640.dp,
        ),
        DevicePreviewDevices.Desktop,
    ),
) {
    App()
}
```

Devices with both `width` and `height` can rotate. A device without dimensions behaves like a desktop preview and fills the available window.

## Keyboard Shortcuts

The module includes helpers for wiring common shortcuts from your window `onKeyEvent`:

- `Cmd+1`, `Cmd+2`, `Cmd+3`: select phone, tablet, desktop
- `Cmd+-`, `Cmd++`, `Cmd+0`: zoom out, zoom in, reset zoom
- `Cmd+R`: rotate the selected device when rotation is available
- `Cmd+backtick`: toggle LTR and RTL
- `Cmd+P`: copy a screenshot of the selected preview frame to the clipboard

Use `deviceForPreviewShortcut`, `devicePreviewZoomForShortcut`, `isDevicePreviewRotationShortcut`, `isDevicePreviewLayoutDirectionShortcut`, and `isDevicePreviewScreenshotShortcut` if you want the sample app to support those shortcuts.
