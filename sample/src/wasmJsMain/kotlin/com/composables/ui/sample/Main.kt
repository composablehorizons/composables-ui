@file:OptIn(ExperimentalComposeUiApi::class, ExperimentalWasmJsInterop::class)

package com.composables.ui.sample

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.composables.ui.preview.DevicePreviewDevices
import com.composables.ui.preview.DevicePreviewDevice
import com.composables.ui.preview.DevicePreviewColorScheme
import com.composables.ui.preview.DevicePreviewHost
import com.composables.ui.preview.DevicePreviewOrientation
import com.composables.ui.preview.canRotate
import com.composables.ui.theme.InteractionMode
import com.composables.ui.theme.LocalInteractionMode
import kotlinx.browser.window

@JsFun("(callback) => { window.setPreviewDevice = callback; }")
private external fun installPreviewDeviceApiBridge(callback: (String) -> Unit)
@JsFun("(callback) => { window.setPreviewOrientation = callback; }")
private external fun installPreviewOrientationApiBridge(callback: (String) -> Unit)
@JsFun("(callback) => { window.setPreviewColorScheme = callback; }")
private external fun installPreviewColorSchemeApiBridge(callback: (String) -> Unit)

fun main() {
    var selectedDevice by mutableStateOf(deviceFromUrl())
    var selectedOrientation by mutableStateOf(
        orientationForDevice(
            selectedDevice,
            orientationFromUrl() ?: DevicePreviewOrientation.Portrait,
        ),
    )
    var selectedColorScheme by mutableStateOf(colorSchemeFromUrl() ?: DevicePreviewColorScheme.Light)
    var selectedInteractionMode by mutableStateOf(interactionModeFromUrl() ?: interactionModeForDevice(selectedDevice))

    installPreviewDeviceApi { deviceId ->
        val device = deviceFromId(deviceId)
        selectedDevice = device
        selectedOrientation = orientationForDevice(device, selectedOrientation)
        selectedInteractionMode = interactionModeForDevice(device)
    }
    installPreviewOrientationApi { orientationId ->
        selectedOrientation = orientationForDevice(selectedDevice, orientationFromId(orientationId))
    }
    installPreviewColorSchemeApi { colorSchemeId ->
        selectedColorScheme = colorSchemeFromId(colorSchemeId)
    }

    ComposeViewport {
        val currentDevice = selectedDevice
        val currentOrientation = selectedOrientation
        val currentColorScheme = selectedColorScheme
        val currentInteractionMode = selectedInteractionMode
        val previewDevice = remember(currentDevice) { currentDevice }
        val content: @Composable () -> Unit = {
            CompositionLocalProvider(
                LocalInteractionMode provides currentInteractionMode,
            ) {
                App()
            }
        }

        DevicePreviewHost(
            selectedDevice = previewDevice,
            onDeviceSelected = {
                selectedDevice = it
                selectedOrientation = orientationForDevice(it, selectedOrientation)
                selectedInteractionMode = interactionModeForDevice(it)
            },
            selectedOrientation = currentOrientation,
            onOrientationSelected = { selectedOrientation = orientationForDevice(currentDevice, it) },
            selectedColorScheme = currentColorScheme,
            onColorSchemeSelected = { selectedColorScheme = it },
            showControls = false,
            showScreenshot = false,
        ) {
            content()
        }
    }
}

private fun interactionModeFromUrl(): InteractionMode? {
    return window.location.search
        .removePrefix("?")
        .split("&")
        .firstNotNullOfOrNull { parameter ->
            val separatorIndex = parameter.indexOf("=")
            if (separatorIndex < 0) return@firstNotNullOfOrNull null

            val key = parameter.take(separatorIndex)
            if (key != "interaction") return@firstNotNullOfOrNull null

            when (parameter.drop(separatorIndex + 1).lowercase()) {
                "touch" -> InteractionMode.Touch
                "pointer" -> InteractionMode.Pointer
                else -> null
            }
        }
}

private fun orientationFromUrl(): DevicePreviewOrientation? {
    return queryParam("orientation")?.let(::orientationFromId)
}

private fun colorSchemeFromUrl(): DevicePreviewColorScheme? {
    return queryParam("theme")?.let(::colorSchemeFromId)
}

private fun deviceFromUrl() = window.location.search
    .let { queryParam("device")?.let(::deviceFromId) ?: DevicePreviewDevices.Mobile }

private fun deviceFromId(value: String) = when (value.lowercase()) {
    "phone", "mobile" -> DevicePreviewDevices.Mobile
    "tablet" -> DevicePreviewDevices.Tablet
    "desktop" -> DevicePreviewDevices.Desktop
    else -> DevicePreviewDevices.Mobile
}

private fun orientationFromId(value: String): DevicePreviewOrientation {
    return when (value.lowercase()) {
        "landscape" -> DevicePreviewOrientation.Landscape
        else -> DevicePreviewOrientation.Portrait
    }
}

private fun colorSchemeFromId(value: String): DevicePreviewColorScheme {
    return when (value.lowercase()) {
        "dark" -> DevicePreviewColorScheme.Dark
        else -> DevicePreviewColorScheme.Light
    }
}

private fun orientationForDevice(
    device: DevicePreviewDevice,
    preferred: DevicePreviewOrientation,
): DevicePreviewOrientation {
    return if (device.canRotate) preferred else DevicePreviewOrientation.Portrait
}

private fun interactionModeForDevice(device: DevicePreviewDevice): InteractionMode {
    return if (device.id == DevicePreviewDevices.Desktop.id) {
        InteractionMode.Pointer
    } else {
        InteractionMode.Touch
    }
}

private fun queryParam(name: String): String? {
    return window.location.search
        .removePrefix("?")
        .split("&")
        .firstNotNullOfOrNull { parameter ->
            val separatorIndex = parameter.indexOf("=")
            if (separatorIndex < 0) return@firstNotNullOfOrNull null

            val key = parameter.take(separatorIndex)
            if (key != name) return@firstNotNullOfOrNull null

            parameter.drop(separatorIndex + 1)
        }
}

private fun installPreviewDeviceApi(setDevice: (String) -> Unit) {
    installPreviewDeviceApiBridge(setDevice)
}

private fun installPreviewOrientationApi(setOrientation: (String) -> Unit) {
    installPreviewOrientationApiBridge(setOrientation)
}

private fun installPreviewColorSchemeApi(setColorScheme: (String) -> Unit) {
    installPreviewColorSchemeApiBridge(setColorScheme)
}
