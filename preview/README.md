# Preview

The preview module provides `DevicePreviewHost`, a Compose component for checking how a UI behaves across common device shapes without leaving the app.

Use it to wrap a screen, sample, or demo content when you want quick controls for:

- phone, tablet, and desktop previews
- portrait and landscape orientation for devices that support rotation
- LTR and RTL layout direction
- zooming in and out
- triggering Compose Hot Reload from the toolbar when the app is running with Hot Reload enabled

## Basic Usage

```kotlin
DevicePreviewHost {
    App()
}
```

By default, the host shows a toolbar with the built-in phone, tablet, and desktop devices.
When the app is launched through a Compose Hot Reload run task, the toolbar also shows a reload button.
Clicking it runs Gradle's `reload` task from inside the app.

## Controlled State

Pass selected values and callbacks when the surrounding app should own the preview state:

```kotlin
var selectedDevice by remember { mutableStateOf(DevicePreviewDevices.Desktop) }
var selectedOrientation by remember { mutableStateOf(DevicePreviewOrientation.Portrait) }
var selectedLayoutDirection by remember { mutableStateOf(LayoutDirection.Ltr) }
var selectedZoom by remember { mutableStateOf(DevicePreviewZoom.Default) }

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

## Hot Reload

The hot reload button is shown only when Hot Reload is active. Pass `showHotReload = false` to hide it, or pass
`onHotReloadRequest` when the surrounding app should own the reload trigger.

## Keyboard Shortcuts

The module includes helpers for wiring common shortcuts from your window `onKeyEvent`:

- `Cmd+1`, `Cmd+2`, `Cmd+3`: select phone, tablet, desktop
- `Cmd+-`, `Cmd++`, `Cmd+0`: zoom out, zoom in, reset zoom
- `Cmd+R`: trigger Compose Hot Reload when Hot Reload is active
- `Cmd+Shift+R`: rotate the selected device when rotation is available
- `Cmd+backtick`: toggle LTR and RTL

Use `deviceForPreviewShortcut`, `devicePreviewZoomForShortcut`, `isDevicePreviewHotReloadShortcut`, `isDevicePreviewRotationShortcut`, and `isDevicePreviewLayoutDirectionShortcut` if you want the sample app to support those shortcuts.
