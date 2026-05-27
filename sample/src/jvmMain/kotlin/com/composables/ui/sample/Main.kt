package com.composables.ui.sample

import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.composables.ui.preview.DevicePreviewDevices
import com.composables.ui.preview.DevicePreviewHost
import com.composables.ui.preview.DevicePreviewOrientation
import com.composables.ui.preview.DevicePreviewZoom
import com.composables.ui.preview.canRotate
import com.composables.ui.preview.deviceForPreviewShortcut
import com.composables.ui.preview.devicePreviewZoomForShortcut
import com.composables.ui.preview.isDevicePreviewLayoutDirectionShortcut
import com.composables.ui.preview.isDevicePreviewRotationShortcut
import com.composables.ui.preview.oppositePreviewLayoutDirection
import com.composables.ui.preview.rotated
import java.awt.Dimension

fun main() = application {
    val windowState = rememberWindowState(width = 960.dp, height = 860.dp)
    var selectedDevice by remember { mutableStateOf(DevicePreviewDevices.Desktop) }
    var selectedOrientation by remember { mutableStateOf(DevicePreviewOrientation.Portrait) }
    var selectedLayoutDirection by remember { mutableStateOf(LayoutDirection.Ltr) }
    var selectedZoom by remember { mutableStateOf(DevicePreviewZoom.Default) }

    Window(
        onCloseRequest = ::exitApplication,
        state = windowState,
        title = "Sample App",
        onKeyEvent = { event ->
            val shortcutDevice = deviceForPreviewShortcut(event)
            if (shortcutDevice != null) {
                selectedDevice = shortcutDevice
                true
            } else if (isDevicePreviewRotationShortcut(event) && selectedDevice.canRotate) {
                selectedOrientation = selectedOrientation.rotated()
                true
            } else if (isDevicePreviewLayoutDirectionShortcut(event)) {
                selectedLayoutDirection = selectedLayoutDirection.oppositePreviewLayoutDirection()
                true
            } else {
                val shortcutZoom = devicePreviewZoomForShortcut(event, selectedZoom)
                if (shortcutZoom != null) {
                    selectedZoom = shortcutZoom
                    true
                } else {
                    false
                }
            }
        },
    ) {
        val density = LocalDensity.current
        val minimumWidth = with(density) { 220.dp.roundToPx() }
        val minimumHeight = with(density) { 220.dp.roundToPx() }

        DisposableEffect(window, minimumWidth, minimumHeight) {
            val previousMinimumSize = window.minimumSize
            val rootPane = window.rootPane
            val previousFullWindowContent = rootPane.getClientProperty(FullWindowContentProperty)
            val previousTransparentTitleBar = rootPane.getClientProperty(TransparentTitleBarProperty)
            val previousWindowTitleVisible = rootPane.getClientProperty(WindowTitleVisibleProperty)

            window.minimumSize = Dimension(minimumWidth, minimumHeight)
            rootPane.putClientProperty(FullWindowContentProperty, true)
            rootPane.putClientProperty(TransparentTitleBarProperty, true)
            rootPane.putClientProperty(WindowTitleVisibleProperty, false)

            onDispose {
                window.minimumSize = previousMinimumSize
                rootPane.putClientProperty(FullWindowContentProperty, previousFullWindowContent)
                rootPane.putClientProperty(TransparentTitleBarProperty, previousTransparentTitleBar)
                rootPane.putClientProperty(WindowTitleVisibleProperty, previousWindowTitleVisible)
            }
        }

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
        ) {
            SocialApp()
        }
    }
}

private const val FullWindowContentProperty = "apple.awt.fullWindowContent"
private const val TransparentTitleBarProperty = "apple.awt.transparentTitleBar"
private const val WindowTitleVisibleProperty = "apple.awt.windowTitleVisible"
