/*
 * Copyright (c) 2026 Composable Horizons
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.composables.devtools

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.isMetaPressed
import androidx.compose.ui.input.key.isShiftPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.pointer.PointerKeyboardModifiers
import androidx.compose.ui.platform.InterceptPlatformTextInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.platform.WindowInfo
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.devtools.iconography.ArrowBigUp
import com.composables.devtools.iconography.Battery
import com.composables.devtools.iconography.Camera
import com.composables.devtools.iconography.Check
import com.composables.devtools.iconography.ChevronDown
import com.composables.devtools.iconography.Delete
import com.composables.devtools.iconography.Icons as DevToolsIcons
import com.composables.devtools.iconography.Icons
import com.composables.devtools.iconography.Minus
import com.composables.devtools.iconography.Monitor
import com.composables.devtools.iconography.Moon
import com.composables.devtools.iconography.Plus
import com.composables.devtools.iconography.RotateCcwSquare
import com.composables.devtools.iconography.RotateCwSquare
import com.composables.devtools.iconography.Signal
import com.composables.devtools.iconography.Smartphone
import com.composables.devtools.iconography.Sun
import com.composables.devtools.iconography.Tablet
import com.composables.devtools.iconography.Wifi
import com.composables.tooling.insets.PreviewInsetsOrientation
import com.composables.tooling.insets.PreviewWindowInsets
import com.composables.tooling.insets.ProvidePreviewWindowInsets
import com.composables.ui.components.Button
import com.composables.ui.components.ButtonSize
import com.composables.ui.components.ButtonStyle
import com.composables.ui.components.DropdownMenu
import com.composables.ui.components.DropdownMenuAlignment
import com.composables.ui.components.DropdownMenuItem
import com.composables.ui.components.DropdownMenuPanel
import com.composables.ui.components.DropdownMenuSide
import com.composables.ui.components.Icon
import com.composables.ui.components.IconButton
import com.composables.ui.components.LocalDropdownMenuWindowInfo
import com.composables.ui.components.Text
import com.composables.ui.components.VerticalSeparator
import com.composables.ui.theme.ColorScheme
import com.composables.ui.theme.ComposablesTheme
import com.composables.ui.theme.InteractionMode
import com.composables.ui.theme.LocalColorScheme
import com.composables.ui.theme.LocalInteractionMode
import com.composables.ui.theme.borderColor
import com.composables.ui.theme.colors
import com.composables.ui.theme.mediumShape
import com.composables.ui.theme.onPanelColor
import com.composables.ui.theme.panelColor
import com.composables.ui.theme.raisedShadow
import com.composables.ui.theme.shadows
import com.composables.ui.theme.shapes
import com.composables.ui.theme.smallShape
import com.composeunstyled.PortalHost
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.theme.Theme
import kotlin.jvm.JvmInline
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.math.roundToLong
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.launch

data class DevToolsDevice(
    val id: String,
    val label: String,
    val size: DevToolsSize = DevToolsSize.Fill,
    val renderSystemBars: Boolean = false,
)

sealed interface DevToolsSize {
  data class Fixed(
      val width: Dp,
      val height: Dp,
  ) : DevToolsSize

  data object Fill : DevToolsSize
}

@JvmInline
value class DevToolsZoom internal constructor(val scale: Float) {
  companion object {
    val Default = DevToolsZoom(1f)
  }
}

object DevToolsZoomLevels {
  val Default =
      listOf(
          DevToolsZoom(0.25f),
          DevToolsZoom(0.33f),
          DevToolsZoom(0.5f),
          DevToolsZoom(0.67f),
          DevToolsZoom(0.75f),
          DevToolsZoom(0.8f),
          DevToolsZoom(0.9f),
          DevToolsZoom.Default,
          DevToolsZoom(1.1f),
          DevToolsZoom(1.25f),
          DevToolsZoom(1.5f),
          DevToolsZoom(1.75f),
          DevToolsZoom(2f),
          DevToolsZoom(2.5f),
          DevToolsZoom(3f),
          DevToolsZoom(4f),
          DevToolsZoom(5f),
      )
}

fun DevToolsZoom.zoomedIn(
    levels: List<DevToolsZoom> = DevToolsZoomLevels.Default,
): DevToolsZoom = nextZoomLevel(levels, direction = 1)

fun DevToolsZoom.zoomedOut(
    levels: List<DevToolsZoom> = DevToolsZoomLevels.Default,
): DevToolsZoom = nextZoomLevel(levels, direction = -1)

private fun DevToolsZoom.nextZoomLevel(
    levels: List<DevToolsZoom>,
    direction: Int,
): DevToolsZoom {
  require(levels.isNotEmpty()) { "Dev tools zoom levels cannot be empty." }

  val sortedLevels = levels.sortedBy { it.scale }
  return if (direction > 0) {
    sortedLevels.firstOrNull { it.scale > scale } ?: sortedLevels.last()
  } else {
    sortedLevels.lastOrNull { it.scale < scale } ?: sortedLevels.first()
  }
}

@JvmInline
value class DevToolsOrientation internal constructor(@Suppress("unused") private val value: Int) {
  companion object {
    val Portrait = DevToolsOrientation(0)
    val Landscape = DevToolsOrientation(1)
  }
}

fun DevToolsOrientation.rotated(): DevToolsOrientation {
  return if (this == DevToolsOrientation.Portrait) {
    DevToolsOrientation.Landscape
  } else {
    DevToolsOrientation.Portrait
  }
}

@JvmInline
value class DevToolsColorScheme internal constructor(@Suppress("unused") private val value: Int) {
  companion object {
    val Light = DevToolsColorScheme(0)
    val Dark = DevToolsColorScheme(1)
  }
}

private fun DevToolsColorScheme.next(): DevToolsColorScheme {
  return when (this) {
    DevToolsColorScheme.Light -> DevToolsColorScheme.Dark
    else -> DevToolsColorScheme.Light
  }
}

private fun DevToolsColorScheme.toColorScheme(): ColorScheme {
  return when (this) {
    DevToolsColorScheme.Light -> ColorScheme.Light
    else -> ColorScheme.Dark
  }
}

private val DevToolsColorScheme.icon: ImageVector
  get() =
      when (this) {
        DevToolsColorScheme.Light -> Icons.Sun
        else -> Icons.Moon
      }

private val DevToolsColorScheme.contentDescription: String
  get() =
      when (this) {
        DevToolsColorScheme.Light -> "Switch color scheme from light"
        else -> "Switch color scheme from dark"
      }

private fun devToolsColorSchemeFromSystem(systemInDarkTheme: Boolean): DevToolsColorScheme {
  return if (systemInDarkTheme) DevToolsColorScheme.Dark else DevToolsColorScheme.Light
}

object DevToolsDevices {
  val Mobile =
      DevToolsDevice(
          id = "mobile",
          label = "Mobile",
          size = DevToolsSize.Fixed(width = 411.dp, height = 891.dp),
          renderSystemBars = true,
      )

  val Tablet =
      DevToolsDevice(
          id = "tablet",
          label = "Tablet",
          size = DevToolsSize.Fixed(width = 800.dp, height = 1280.dp),
          renderSystemBars = true,
      )

  val Desktop =
      DevToolsDevice(
          id = "desktop",
          label = "Desktop",
      )

  val Default = listOf(Mobile, Tablet, Desktop)
}

@Composable
fun DevTools(
    modifier: Modifier = Modifier,
    devices: List<DevToolsDevice> = DevToolsDevices.Default,
    initialDevice: DevToolsDevice = DevToolsDevices.Mobile,
    selectedDevice: DevToolsDevice? = null,
    onDeviceSelected: (DevToolsDevice) -> Unit = {},
    selectedOrientation: DevToolsOrientation? = null,
    onOrientationSelected: (DevToolsOrientation) -> Unit = {},
    selectedLayoutDirection: LayoutDirection? = null,
    onLayoutDirectionSelected: (LayoutDirection) -> Unit = {},
    initialColorScheme: DevToolsColorScheme? = null,
    selectedColorScheme: DevToolsColorScheme? = null,
    onColorSchemeSelected: (DevToolsColorScheme) -> Unit = {},
    selectedZoom: DevToolsZoom? = null,
    onZoomSelected: (DevToolsZoom) -> Unit = {},
    zoomLevels: List<DevToolsZoom> = DevToolsZoomLevels.Default,
    showControls: Boolean = true,
    showScreenshot: Boolean = true,
    saveScreenshotRequest: Any? = null,
    copyScreenshotRequest: Any? = null,
    content: @Composable () -> Unit,
) {
  PortalHost {
    require(devices.isNotEmpty()) { "DevTools requires at least one device." }
    require(zoomLevels.isNotEmpty()) { "DevTools requires at least one zoom level." }

    val initialSelection =
        remember(devices, initialDevice) {
          devices.firstOrNull { it.id == initialDevice.id } ?: devices.first()
        }
    var internalSelectedDevice by
        remember(devices, initialDevice) { mutableStateOf(initialSelection) }
    var internalOrientation by remember { mutableStateOf(DevToolsOrientation.Portrait) }
    val currentDevice =
        selectedDevice?.let { selected -> devices.firstOrNull { it.id == selected.id } }
            ?: internalSelectedDevice
    val currentOrientation = selectedOrientation ?: internalOrientation
    var internalLayoutDirection by remember { mutableStateOf(LayoutDirection.Ltr) }
    val currentLayoutDirection = selectedLayoutDirection ?: internalLayoutDirection
    val systemInDarkTheme = isSystemInDarkTheme()
    val defaultColorScheme = initialColorScheme ?: devToolsColorSchemeFromSystem(systemInDarkTheme)
    var internalColorScheme by remember { mutableStateOf(defaultColorScheme) }
    val currentColorScheme = selectedColorScheme ?: internalColorScheme
    var internalZoom by remember { mutableStateOf(DevToolsZoom.Default) }
    val currentZoom = selectedZoom ?: internalZoom
    val selectDevice = { device: DevToolsDevice ->
      internalSelectedDevice = device
      onDeviceSelected(device)
    }
    val selectOrientation = { orientation: DevToolsOrientation ->
      internalOrientation = orientation
      onOrientationSelected(orientation)
    }
    val selectLayoutDirection = { layoutDirection: LayoutDirection ->
      internalLayoutDirection = layoutDirection
      onLayoutDirectionSelected(layoutDirection)
    }
    val selectColorScheme = { colorScheme: DevToolsColorScheme ->
      internalColorScheme = colorScheme
      onColorSchemeSelected(colorScheme)
    }
    val selectZoom = { zoom: DevToolsZoom ->
      internalZoom = zoom
      onZoomSelected(zoom)
    }
    val devToolsContent = remember { movableContentOf(content) }

    CompositionLocalProvider(LocalColorScheme provides ColorScheme.Light) {
      ComposablesTheme {
        val windowShape = Theme[shapes][smallShape]

        Box(
            modifier =
                modifier
                    .fillMaxSize()
                    .clip(windowShape)
                    .background(DevToolsBackground, windowShape),
        ) {
          DevToolsStage(
              device = currentDevice,
              devices = devices,
              orientation = currentOrientation,
              layoutDirection = currentLayoutDirection,
              currentColorScheme = currentColorScheme,
              zoom = currentZoom,
              zoomLevels = zoomLevels,
              showControls = showControls,
              showScreenshot = showScreenshot,
              saveScreenshotRequest = saveScreenshotRequest,
              copyScreenshotRequest = copyScreenshotRequest,
              onDeviceSelected = selectDevice,
              onOrientationChange = selectOrientation,
              onLayoutDirectionChange = selectLayoutDirection,
              onColorSchemeChange = selectColorScheme,
              onZoomChange = selectZoom,
              modifier = Modifier.fillMaxSize(),
              content = devToolsContent,
          )
        }
      }
    }
  }
}

@Composable
private fun DevToolsControls(
    devices: List<DevToolsDevice>,
    selectedDevice: DevToolsDevice,
    orientation: DevToolsOrientation,
    layoutDirection: LayoutDirection,
    colorScheme: DevToolsColorScheme,
    zoom: DevToolsZoom,
    zoomLevels: List<DevToolsZoom>,
    showScreenshot: Boolean,
    onSaveScreenshotRequest: () -> Unit,
    onDeviceSelected: (DevToolsDevice) -> Unit,
    onOrientationChange: (DevToolsOrientation) -> Unit,
    onLayoutDirectionChange: (LayoutDirection) -> Unit,
    onColorSchemeChange: (DevToolsColorScheme) -> Unit,
    onZoomChange: (DevToolsZoom) -> Unit,
    modifier: Modifier = Modifier,
) {
  val controlsShape = Theme[shapes][mediumShape]
  val controlsScrollState = rememberScrollState()
  val backgroundColor = Theme[colors][panelColor]
  val contentColor = Theme[colors][onPanelColor]
  val borderColor = Theme[colors][borderColor]
  val controlsShadow = Theme[shadows][raisedShadow]

  Box(
      modifier =
          modifier
              .dropShadow(controlsShape, controlsShadow)
              .clip(controlsShape)
              .background(backgroundColor, controlsShape)
              .border(width = 1.dp, color = borderColor, shape = controlsShape)
              .padding(DevToolsToolbarPanelPadding),
  ) {
    ProvideContentColor(contentColor) {
      Row(
          modifier =
              Modifier.horizontalScroll(controlsScrollState).padding(DevToolsToolbarContentPadding),
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          verticalAlignment = Alignment.CenterVertically,
      ) {
        DeviceControlsGroup(
            devices = devices,
            selectedDevice = selectedDevice,
            orientation = orientation,
            onDeviceSelected = onDeviceSelected,
        )
        VerticalSeparator(modifier = Modifier.height(24.dp))
        ZoomControls(
            zoom = zoom,
            zoomLevels = zoomLevels,
            onZoomChange = onZoomChange,
        )
        VerticalSeparator(modifier = Modifier.height(24.dp))
        DevToolsOptionsGroup(
            orientation = orientation,
            layoutDirection = layoutDirection,
            colorScheme = colorScheme,
            enabled = selectedDevice.canRotate,
            onLayoutDirectionChange = onLayoutDirectionChange,
            onColorSchemeChange = onColorSchemeChange,
            onOrientationChange = onOrientationChange,
        )
        if (showScreenshot) {
          VerticalSeparator(modifier = Modifier.height(24.dp))
          ScreenshotButton(onClick = onSaveScreenshotRequest)
        }
      }
    }
  }
}

@Composable
private fun ScreenshotButton(
    onClick: () -> Unit,
) {
  IconButton(
      onClick = onClick,
      style = ButtonStyle.Ghost,
      buttonSize = ButtonSize.Regular,
  ) {
    Icon(
        imageVector = Icons.Camera,
        contentDescription = "Save screenshot",
        modifier = Modifier.size(18.dp),
    )
  }
}

@Composable
private fun DeviceControlsGroup(
    devices: List<DevToolsDevice>,
    selectedDevice: DevToolsDevice,
    orientation: DevToolsOrientation,
    onDeviceSelected: (DevToolsDevice) -> Unit,
) {
  devices.forEach { device ->
    DevToolsButton(
        device = device,
        orientation = orientation,
        selected = device.id == selectedDevice.id,
        onClick = { onDeviceSelected(device) },
    )
  }
}

@Composable
private fun DevToolsOptionsGroup(
    orientation: DevToolsOrientation,
    layoutDirection: LayoutDirection,
    colorScheme: DevToolsColorScheme,
    enabled: Boolean,
    onLayoutDirectionChange: (LayoutDirection) -> Unit,
    onColorSchemeChange: (DevToolsColorScheme) -> Unit,
    onOrientationChange: (DevToolsOrientation) -> Unit,
) {
  LayoutDirectionButton(
      layoutDirection = layoutDirection,
      onClick = { onLayoutDirectionChange(layoutDirection.oppositeDevToolsLayoutDirection()) },
  )
  ColorSchemeButton(
      colorScheme = colorScheme,
      onClick = { onColorSchemeChange(colorScheme.next()) },
  )
  OrientationButton(
      orientation = orientation,
      enabled = enabled,
      onClick = { onOrientationChange(orientation.rotated()) },
  )
}

@Composable
private fun LayoutDirectionButton(
    layoutDirection: LayoutDirection,
    onClick: () -> Unit,
) {
  IconButton(
      onClick = onClick,
      style = ButtonStyle.Ghost,
      buttonSize = ButtonSize.Regular,
  ) {
    Text(
        text = if (layoutDirection == LayoutDirection.Ltr) "LTR" else "RTL",
        fontSize = 11.sp,
        fontWeight = FontWeight.Normal,
        singleLine = true,
    )
  }
}

@Composable
private fun ColorSchemeButton(
    colorScheme: DevToolsColorScheme,
    onClick: () -> Unit,
) {
  IconButton(
      onClick = onClick,
      style = ButtonStyle.Ghost,
      buttonSize = ButtonSize.Regular,
  ) {
    Icon(
        imageVector = colorScheme.icon,
        contentDescription = colorScheme.contentDescription,
        modifier = Modifier.size(18.dp),
    )
  }
}

@Composable
private fun OrientationButton(
    orientation: DevToolsOrientation,
    enabled: Boolean,
    onClick: () -> Unit,
) {
  IconButton(
      onClick = onClick,
      enabled = enabled,
      style = ButtonStyle.Ghost,
      buttonSize = ButtonSize.Regular,
  ) {
    Icon(
        imageVector = iconForRotation(orientation),
        contentDescription =
            if (orientation == DevToolsOrientation.Portrait) {
              "Switch to landscape"
            } else {
              "Switch to portrait"
            },
        modifier = Modifier.size(18.dp),
    )
  }
}

@Composable
private fun ZoomControls(
    zoom: DevToolsZoom,
    zoomLevels: List<DevToolsZoom>,
    onZoomChange: (DevToolsZoom) -> Unit,
) {
  Row(
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      verticalAlignment = Alignment.CenterVertically,
  ) {
    IconButton(
        onClick = { onZoomChange(zoom.zoomedOut(zoomLevels)) },
        enabled = zoom.scale > zoomLevels.minOf { it.scale },
        style = ButtonStyle.Ghost,
        buttonSize = ButtonSize.Regular,
    ) {
      Icon(
          imageVector = Icons.Minus,
          contentDescription = "Zoom out",
          modifier = Modifier.size(18.dp),
      )
    }
    ZoomMenu(
        zoom = zoom,
        zoomLevels = zoomLevels,
        onZoomChange = onZoomChange,
    )
    IconButton(
        onClick = { onZoomChange(zoom.zoomedIn(zoomLevels)) },
        enabled = zoom.scale < zoomLevels.maxOf { it.scale },
        style = ButtonStyle.Ghost,
        buttonSize = ButtonSize.Regular,
    ) {
      Icon(
          imageVector = Icons.Plus,
          contentDescription = "Zoom in",
          modifier = Modifier.size(18.dp),
      )
    }
  }
}

@Composable
private fun ZoomMenu(
    zoom: DevToolsZoom,
    zoomLevels: List<DevToolsZoom>,
    onZoomChange: (DevToolsZoom) -> Unit,
) {
  var expanded by remember { mutableStateOf(false) }

  DropdownMenu(
      expanded = expanded,
      onExpandedChange = { expanded = it },
      side = DropdownMenuSide.Bottom,
      alignment = DropdownMenuAlignment.End,
      panel = {
        DropdownMenuPanel(minWidth = 112.dp) {
          zoomLevels.forEach { zoomLevel ->
            DropdownMenuItem(
                onClick = {
                  onZoomChange(zoomLevel)
                  expanded = false
                },
                leading = {
                  if (zoomLevel == zoom) {
                    Icon(
                        imageVector = Icons.Check,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                    )
                  }
                },
            ) {
              Text(zoomLevel.label)
            }
          }
        }
      },
      anchor = {
        Button(
            onClick = { expanded = !expanded },
            style = ButtonStyle.Ghost,
            buttonSize = ButtonSize.Regular,
            modifier = Modifier.width(92.dp),
        ) {
          Text(zoom.label, singleLine = true)
          Icon(
              imageVector = Icons.ChevronDown,
              contentDescription = null,
              modifier = Modifier.size(16.dp),
          )
        }
      },
  )
}

@Composable
private fun DevToolsButton(
    device: DevToolsDevice,
    orientation: DevToolsOrientation,
    selected: Boolean,
    onClick: () -> Unit,
) {
  val targetIconRotation =
      if (device.hasOrientationIcon && orientation == DevToolsOrientation.Landscape) {
        DeviceRotationDegrees
      } else {
        0f
      }
  var iconRotation by remember(device.hasOrientationIcon) { mutableStateOf(targetIconRotation) }

  LaunchedEffect(targetIconRotation) {
    animateDeviceRotationTo(
        startRotation = iconRotation,
        targetRotation = targetIconRotation,
        easing = ::easeOutBack,
    ) { rotation, _ ->
      iconRotation = rotation
    }
  }

  IconButton(
      onClick = onClick,
      style = if (selected) ButtonStyle.Primary else ButtonStyle.Ghost,
      buttonSize = ButtonSize.Regular,
  ) {
    Icon(
        imageVector = iconFor(device),
        contentDescription = device.label,
        modifier = Modifier.size(18.dp).graphicsLayer { rotationZ = iconRotation },
    )
  }
}

private val DevToolsDevice.hasOrientationIcon: Boolean
  get() = id == DevToolsDevices.Mobile.id || id == DevToolsDevices.Tablet.id

private fun iconFor(device: DevToolsDevice): ImageVector {
  return when (device.id) {
    DevToolsDevices.Mobile.id -> Icons.Smartphone
    DevToolsDevices.Tablet.id -> Icons.Tablet
    else -> Icons.Monitor
  }
}

private fun iconForRotation(orientation: DevToolsOrientation): ImageVector {
  return if (orientation == DevToolsOrientation.Portrait) {
    Icons.RotateCwSquare
  } else {
    Icons.RotateCcwSquare
  }
}

private val DevToolsZoom.label: String
  get() = "${(scale * 100).roundToInt()}%"

val DevToolsDevice.canRotate: Boolean
  get() = size is DevToolsSize.Fixed

private fun devToolsTargetSize(
    deviceSize: DevToolsSize,
    orientation: DevToolsOrientation,
    containerWidth: Dp,
    containerHeight: Dp,
): DpSize {
  return when (deviceSize) {
    is DevToolsSize.Fixed -> {
      if (orientation == DevToolsOrientation.Landscape) {
        DpSize(width = deviceSize.height, height = deviceSize.width)
      } else {
        DpSize(width = deviceSize.width, height = deviceSize.height)
      }
    }
    DevToolsSize.Fill -> DpSize(width = containerWidth, height = containerHeight)
  }
}

fun LayoutDirection.oppositeDevToolsLayoutDirection(): LayoutDirection {
  return if (this == LayoutDirection.Ltr) {
    LayoutDirection.Rtl
  } else {
    LayoutDirection.Ltr
  }
}

fun deviceForDevToolsShortcut(
    event: KeyEvent,
    devices: List<DevToolsDevice> = DevToolsDevices.Default,
): DevToolsDevice? {
  if (event.type != KeyEventType.KeyDown || !event.isMetaPressed) {
    return null
  }

  return when (event.key) {
    Key.One -> devices.firstOrNull { it.id == DevToolsDevices.Mobile.id } ?: devices.getOrNull(0)
    Key.Two -> devices.firstOrNull { it.id == DevToolsDevices.Tablet.id } ?: devices.getOrNull(1)
    Key.Three -> devices.firstOrNull { it.id == DevToolsDevices.Desktop.id } ?: devices.getOrNull(2)
    else -> null
  }
}

fun isDevToolsRotationShortcut(event: KeyEvent): Boolean {
  return event.type == KeyEventType.KeyDown &&
      event.isMetaPressed &&
      !event.isShiftPressed &&
      event.key == Key.R
}

fun isDevToolsHotReloadShortcut(event: KeyEvent): Boolean {
  return event.type == KeyEventType.KeyDown &&
      event.isMetaPressed &&
      event.isShiftPressed &&
      event.key == Key.R
}

fun isDevToolsScreenshotSaveShortcut(event: KeyEvent): Boolean {
  return event.type == KeyEventType.KeyDown &&
      event.isMetaPressed &&
      !event.isShiftPressed &&
      event.key == Key.P
}

fun isDevToolsScreenshotCopyShortcut(event: KeyEvent): Boolean {
  return event.type == KeyEventType.KeyDown &&
      event.isMetaPressed &&
      event.isShiftPressed &&
      event.key == Key.P
}

fun isDevToolsLayoutDirectionShortcut(event: KeyEvent): Boolean {
  return event.type == KeyEventType.KeyDown && event.isMetaPressed && event.key == Key.Grave
}

fun isDevToolsToolbarShortcut(event: KeyEvent): Boolean {
  return event.type == KeyEventType.KeyDown && event.isMetaPressed && event.key == Key.Period
}

fun devToolsZoomForShortcut(
    event: KeyEvent,
    currentZoom: DevToolsZoom,
    levels: List<DevToolsZoom> = DevToolsZoomLevels.Default,
): DevToolsZoom? {
  if (event.type != KeyEventType.KeyDown || !event.isMetaPressed) {
    return null
  }

  return when {
    event.key == Key.Zero -> DevToolsZoom.Default
    event.key == Key.Minus -> currentZoom.zoomedOut(levels)
    event.key == Key.Equals || event.key.keyCode == PlusKeyCode -> currentZoom.zoomedIn(levels)
    else -> null
  }
}

@Composable
private fun DevToolsStage(
    device: DevToolsDevice,
    devices: List<DevToolsDevice>,
    orientation: DevToolsOrientation,
    layoutDirection: LayoutDirection,
    currentColorScheme: DevToolsColorScheme,
    zoom: DevToolsZoom,
    zoomLevels: List<DevToolsZoom>,
    showControls: Boolean,
    showScreenshot: Boolean,
    saveScreenshotRequest: Any?,
    copyScreenshotRequest: Any?,
    onDeviceSelected: (DevToolsDevice) -> Unit,
    onOrientationChange: (DevToolsOrientation) -> Unit,
    onLayoutDirectionChange: (LayoutDirection) -> Unit,
    onColorSchemeChange: (DevToolsColorScheme) -> Unit,
    onZoomChange: (DevToolsZoom) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
  val colorScheme = currentColorScheme.toColorScheme()
  val horizontalScrollState = rememberScrollState()
  val verticalScrollState = rememberScrollState()
  val coroutineScope = rememberCoroutineScope()
  val screenshotLayer = rememberGraphicsLayer()
  var activeTextInputSessions by remember { mutableStateOf(0) }
  var softKeyboardDismissed by remember { mutableStateOf(false) }
  var rotationFrame by
      remember(device.id) {
        mutableStateOf(DeviceRotationFrame(orientation = orientation, rotationZ = 0f))
      }
  var previousRenderedDeviceId by remember { mutableStateOf(device.id) }
  val deviceChanged = previousRenderedDeviceId != device.id

  SideEffect { previousRenderedDeviceId = device.id }

  RunOnRequestChange(saveScreenshotRequest) { saveCurrentDevToolsScreenshot(screenshotLayer) }
  RunOnRequestChange(copyScreenshotRequest) { copyCurrentDevToolsScreenshot(screenshotLayer) }

  LaunchedEffect(device.id, device.canRotate, orientation) {
    suspend fun animateFrameRotationTo(
        targetRotation: Float,
        renderedOrientation: DevToolsOrientation,
    ) {
      val startRotation = rotationFrame.rotationZ
      animateDeviceRotationTo(
          startRotation = startRotation,
          targetRotation = targetRotation,
      ) { rotation, finished ->
        rotationFrame =
            DeviceRotationFrame(
                orientation = renderedOrientation,
                rotationZ = rotation,
                rotating = !finished || rotation != 0f,
            )
      }
    }

    suspend fun animateOrientationChangeTo(targetOrientation: DevToolsOrientation) {
      val startOrientation = rotationFrame.orientation
      val rotationDirection = if (targetOrientation == DevToolsOrientation.Landscape) 1f else -1f
      val startTime = withFrameNanos { it }
      var finished = false

      while (!finished) {
        val frameTime = withFrameNanos { it }
        val progress =
            ((frameTime - startTime).toFloat() /
                    DeviceRotationDuration.inWholeNanoseconds.toFloat())
                .coerceIn(
                    0f,
                    1f,
                )

        finished = progress == 1f
        rotationFrame =
            if (progress < DeviceRotationSwapProgress) {
              val phaseProgress = easeInCubic(progress / DeviceRotationSwapProgress)
              DeviceRotationFrame(
                  orientation = startOrientation,
                  rotationZ = phaseProgress * DeviceRotationSwapDegrees * rotationDirection,
                  rotating = true,
              )
            } else {
              val phaseProgress =
                  easeOutBack(
                      (progress - DeviceRotationSwapProgress) / (1f - DeviceRotationSwapProgress),
                  )
              DeviceRotationFrame(
                  orientation = targetOrientation,
                  rotationZ = (phaseProgress - 1f) * DeviceRotationSwapDegrees * rotationDirection,
                  rotating = !finished,
              )
            }
      }

      rotationFrame =
          DeviceRotationFrame(
              orientation = targetOrientation,
              rotationZ = 0f,
          )
    }

    if (!device.canRotate) {
      rotationFrame = DeviceRotationFrame(orientation = orientation, rotationZ = 0f)
      return@LaunchedEffect
    }
    if (orientation == rotationFrame.orientation) {
      animateFrameRotationTo(targetRotation = 0f, renderedOrientation = rotationFrame.orientation)
      return@LaunchedEffect
    }

    animateOrientationChangeTo(targetOrientation = orientation)
  }

  BoxWithConstraints(modifier = modifier) {
    val containerWidth = maxWidth
    val containerHeight = maxHeight

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
      val framedDevice = device.canRotate
      val supportsSoftKeyboard = framedDevice && device.renderSystemBars
      val contentAlpha by
          animateFloatAsState(
              targetValue =
                  if (framedDevice && rotationFrame.rotating) {
                    1f - (abs(rotationFrame.rotationZ) / DeviceRotationSwapDegrees).coerceIn(0f, 1f)
                  } else {
                    1f
                  },
              animationSpec = DeviceRotationContentFadeSpec,
              label = "DevToolsContentAlpha",
          )
      val targetSize =
          devToolsTargetSize(
              deviceSize = device.size,
              orientation = rotationFrame.orientation,
              containerWidth = containerWidth,
              containerHeight = containerHeight,
          )
      val targetWidth = targetSize.width
      val targetHeight = targetSize.height
      val animatedZoom by
          animateFloatAsState(
              targetValue = zoom.scale,
              animationSpec = DevToolsZoomAnimationSpec,
              label = "DevToolsZoom",
          )
      val animatedWidth by
          animateDpAsState(
              targetValue = targetWidth,
              animationSpec =
                  if (rotationFrame.rotating || (!deviceChanged && !framedDevice)) {
                    DevToolsSizeSnapSpec
                  } else {
                    DevToolsAnimationSpec
                  },
              label = "DevToolsFrameWidth",
          )
      val animatedHeight by
          animateDpAsState(
              targetValue = targetHeight,
              animationSpec =
                  if (rotationFrame.rotating || (!deviceChanged && !framedDevice)) {
                    DevToolsSizeSnapSpec
                  } else {
                    DevToolsAnimationSpec
                  },
              label = "DevToolsFrameHeight",
          )
      val devToolsWidth = if (rotationFrame.rotating) targetWidth else animatedWidth
      val devToolsHeight = if (rotationFrame.rotating) targetHeight else animatedHeight
      val animatedStagePadding by
          animateDpAsState(
              targetValue = if (framedDevice) DevToolsFramedPadding else 0.dp,
              animationSpec = DevToolsAnimationSpec,
              label = "DevToolsStagePadding",
          )
      val animatedFramePadding by
          animateDpAsState(
              targetValue = if (framedDevice) DeviceFramePadding else 0.dp,
              animationSpec = DevToolsAnimationSpec,
              label = "DevToolsFramePadding",
          )
      val animatedFrameCornerRadius by
          animateDpAsState(
              targetValue = if (framedDevice) DeviceFrameCornerRadius else 0.dp,
              animationSpec = DevToolsAnimationSpec,
              label = "DevToolsFrameCornerRadius",
          )
      val animatedContentCornerRadius by
          animateDpAsState(
              targetValue = if (framedDevice) DeviceContentCornerRadius else 0.dp,
              animationSpec = DevToolsAnimationSpec,
              label = "DevToolsContentCornerRadius",
          )
      val animatedSoftKeyboardHeight by
          animateDpAsState(
              targetValue =
                  if (supportsSoftKeyboard &&
                      activeTextInputSessions > 0 &&
                      !softKeyboardDismissed) {
                    DevToolsSoftKeyboardHeight
                  } else {
                    0.dp
                  },
              animationSpec = DevToolsSoftKeyboardAnimationSpec,
              label = "DevToolsSoftKeyboardHeight",
          )
      val animatedFrameBorderWidth by
          animateDpAsState(
              targetValue = if (framedDevice) 1.dp else 0.dp,
              animationSpec = DevToolsAnimationSpec,
              label = "DevToolsFrameBorderWidth",
          )

      Box(
          modifier =
              Modifier.fillMaxSize()
                  .horizontalScroll(horizontalScrollState)
                  .verticalScroll(verticalScrollState),
          contentAlignment = Alignment.Center,
      ) {
        Box(
            modifier = Modifier.fillMaxSize().padding(animatedStagePadding),
            contentAlignment = Alignment.Center,
        ) {
          val renderSystemBars = supportsSoftKeyboard
          val statusBarSize =
              if (renderSystemBars) {
                DevToolsStatusBarHeight
              } else {
                0.dp
              }
          val navigationBarSize =
              if (renderSystemBars) {
                DevToolsNavigationBarHeight
              } else {
                0.dp
              }
          val devToolsInsetsOrientation =
              devToolsInsetsOrientationFor(
                  orientation = rotationFrame.orientation,
              )
          val navigationBarAtEnd = devToolsInsetsOrientation == PreviewInsetsOrientation.Landscape
          ProvidePreviewWindowInsets(
              windowInsets =
                  PreviewWindowInsets(
                      statusBarSize = statusBarSize,
                      navigationBarSize = navigationBarSize,
                      softKeyboardSize = animatedSoftKeyboardHeight,
                      orientation = devToolsInsetsOrientation,
                  ),
          ) {
            DevToolsSoftKeyboardInterceptor(
                onInputStarted = {
                  activeTextInputSessions++
                  softKeyboardDismissed = false
                },
                onInputStopped = {
                  activeTextInputSessions = (activeTextInputSessions - 1).coerceAtLeast(0)
                  if (activeTextInputSessions == 0) {
                    softKeyboardDismissed = false
                  }
                },
            ) {
              if (framedDevice) {
                ZoomedDevTools(
                    width = devToolsWidth + animatedFramePadding * 2,
                    height = devToolsHeight + animatedFramePadding * 2,
                    zoom = animatedZoom,
                ) {
                  DevToolsFrame(
                      width = devToolsWidth,
                      height = devToolsHeight,
                      interactionMode = InteractionMode.Touch,
                      colorScheme = colorScheme,
                      layoutDirection = layoutDirection,
                      contentAlpha = contentAlpha,
                      framePadding = animatedFramePadding,
                      frameCornerRadius = animatedFrameCornerRadius,
                      contentCornerRadius = animatedContentCornerRadius,
                      borderWidth = animatedFrameBorderWidth,
                      renderSystemBars = renderSystemBars,
                      statusBarHeight = statusBarSize,
                      navigationBarSize = navigationBarSize,
                      softKeyboardHeight = animatedSoftKeyboardHeight,
                      onSoftKeyboardDismissRequest = { softKeyboardDismissed = true },
                      navigationBarAtEnd = navigationBarAtEnd,
                      screenshotLayer = screenshotLayer,
                      modifier = Modifier.graphicsLayer { rotationZ = rotationFrame.rotationZ },
                      content = content,
                  )
                }
              } else {
                DesktopZoomedDevTools(
                    width = devToolsWidth,
                    height = devToolsHeight,
                    zoom = animatedZoom,
                    colorScheme = colorScheme,
                    layoutDirection = layoutDirection,
                    frameCornerRadius = animatedFrameCornerRadius,
                    screenshotLayer = screenshotLayer,
                    content = content,
                )
              }
            }
          }
        }
      }

      if (showControls) {
        DevToolsControls(
            devices = devices,
            selectedDevice = device,
            orientation = orientation,
            layoutDirection = layoutDirection,
            colorScheme = currentColorScheme,
            zoom = zoom,
            zoomLevels = zoomLevels,
            showScreenshot = showScreenshot,
            onSaveScreenshotRequest = {
              coroutineScope.launch { saveCurrentDevToolsScreenshot(screenshotLayer) }
            },
            onDeviceSelected = onDeviceSelected,
            onOrientationChange = onOrientationChange,
            onLayoutDirectionChange = onLayoutDirectionChange,
            onColorSchemeChange = onColorSchemeChange,
            onZoomChange = onZoomChange,
            modifier =
                Modifier.align(Alignment.BottomCenter)
                    .padding(
                        start = DevToolsToolbarHorizontalMargin,
                        end = DevToolsToolbarHorizontalMargin,
                        bottom = DevToolsToolbarBottomMargin,
                    ),
        )
      }
    }
  }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun DevToolsSoftKeyboardInterceptor(
    onInputStarted: () -> Unit,
    onInputStopped: () -> Unit,
    content: @Composable () -> Unit,
) {
  InterceptPlatformTextInput(
      interceptor = { request, nextHandler ->
        onInputStarted()
        try {
          nextHandler.startInputMethod(request)
        } finally {
          onInputStopped()
        }
      },
      content = content,
  )
}

@Composable
private fun DesktopZoomedDevTools(
    width: Dp,
    height: Dp,
    zoom: Float,
    colorScheme: ColorScheme,
    layoutDirection: LayoutDirection,
    frameCornerRadius: Dp,
    screenshotLayer: GraphicsLayer,
    content: @Composable () -> Unit,
) {
  val layoutWidth = width * (1f / zoom)
  val layoutHeight = height * (1f / zoom)
  val shape = RoundedCornerShape(frameCornerRadius)

  Box(
      modifier =
          Modifier.requiredSize(width, height)
              .clip(shape)
              .captureDevToolsScreenshot(screenshotLayer)
              .background(DevToolsContentBackground),
      contentAlignment = Alignment.Center,
  ) {
    Box(
        modifier =
            Modifier.requiredSize(layoutWidth, layoutHeight).graphicsLayer {
              scaleX = zoom
              scaleY = zoom
              transformOrigin = TransformOrigin.Center
            },
    ) {
      ProvideDevToolsWindowInfo(width = layoutWidth, height = layoutHeight) {
        ProvideDevToolsCompositionLocals(
            interactionMode = InteractionMode.Pointer,
            colorScheme = colorScheme,
            layoutDirection = layoutDirection,
            content = content,
        )
      }
    }
  }
}

@Composable
private fun ZoomedDevTools(
    width: Dp,
    height: Dp,
    zoom: Float,
    content: @Composable () -> Unit,
) {
  Box(
      modifier = Modifier.requiredSize(width * zoom, height * zoom),
      contentAlignment = Alignment.Center,
  ) {
    Box(
        modifier =
            Modifier.requiredSize(width, height).graphicsLayer {
              scaleX = zoom
              scaleY = zoom
              transformOrigin = TransformOrigin.Center
            },
        contentAlignment = Alignment.Center,
    ) {
      content()
    }
  }
}

@Composable
private fun DevToolsFrame(
    width: Dp,
    height: Dp,
    interactionMode: InteractionMode,
    colorScheme: ColorScheme,
    layoutDirection: LayoutDirection,
    contentAlpha: Float,
    framePadding: Dp,
    frameCornerRadius: Dp,
    contentCornerRadius: Dp,
    borderWidth: Dp,
    renderSystemBars: Boolean,
    statusBarHeight: Dp,
    navigationBarSize: Dp,
    softKeyboardHeight: Dp,
    onSoftKeyboardDismissRequest: () -> Unit,
    navigationBarAtEnd: Boolean,
    screenshotLayer: GraphicsLayer,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
  val frameShape = RoundedCornerShape(frameCornerRadius)
  val contentShape = RoundedCornerShape(contentCornerRadius)

  Box(
      modifier =
          modifier
              .background(DeviceBezel, frameShape)
              .border(width = borderWidth, color = FrameBorder, shape = frameShape)
              .padding(framePadding),
      contentAlignment = Alignment.Center,
  ) {
    Box(
        modifier =
            Modifier.requiredSize(width, height)
                .clip(contentShape)
                .captureDevToolsScreenshot(screenshotLayer)
                .background(DevToolsContentBackground)
                .graphicsLayer { alpha = contentAlpha },
    ) {
      ProvideDevToolsWindowInfo(width = width, height = height) {
        ProvideDevToolsCompositionLocals(
            interactionMode = interactionMode,
            colorScheme = colorScheme,
            layoutDirection = layoutDirection,
            content = content,
        )
      }
      if (renderSystemBars && statusBarHeight > 0.dp) {
        DevToolsStatusBar(
            height = statusBarHeight,
            endPadding = if (navigationBarAtEnd) navigationBarSize else 0.dp,
            layoutDirection = layoutDirection,
            modifier = Modifier.align(Alignment.TopCenter),
        )
      }
      if (renderSystemBars && navigationBarSize > 0.dp) {
        DevToolsNavigationBar(
            size = navigationBarSize,
            atEnd = navigationBarAtEnd,
            layoutDirection = layoutDirection,
            modifier =
                if (navigationBarAtEnd) {
                  Modifier.align(layoutDirection.endAlignment)
                } else {
                  Modifier.align(Alignment.BottomCenter)
                },
        )
      }
      DevToolsSoftKeyboard(
          visibleHeight = softKeyboardHeight,
          onDismissRequest = onSoftKeyboardDismissRequest,
          modifier = Modifier.align(Alignment.BottomCenter),
      )
    }
  }
}

@Composable
private fun DevToolsStatusBar(
    height: Dp,
    endPadding: Dp,
    layoutDirection: LayoutDirection,
    modifier: Modifier = Modifier,
) {
  CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(end = endPadding)
                .height(height)
                .background(DevToolsSystemBarBackground)
                .padding(horizontal = 16.dp),
    ) {
      Text(
          text = "12:00",
          color = DevToolsSystemBarContent,
          fontSize = 11.sp,
          fontWeight = FontWeight.Medium,
          modifier = Modifier.align(Alignment.CenterStart),
      )
      Row(
          modifier = Modifier.align(Alignment.CenterEnd),
          horizontalArrangement = Arrangement.spacedBy(5.dp),
          verticalAlignment = Alignment.CenterVertically,
      ) {
        Icon(
            imageVector = DevToolsIcons.Signal,
            contentDescription = null,
            tint = DevToolsSystemBarContent,
            modifier = Modifier.size(width = 15.dp, height = 15.dp),
        )
        Icon(
            imageVector = DevToolsIcons.Wifi,
            contentDescription = null,
            tint = DevToolsSystemBarContent,
            modifier = Modifier.size(width = 14.dp, height = 14.dp),
        )
        Icon(
            imageVector = DevToolsIcons.Battery,
            contentDescription = null,
            tint = DevToolsSystemBarContent,
            modifier = Modifier.size(width = 18.dp, height = 18.dp),
        )
      }
    }
  }
}

@Composable
private fun DevToolsNavigationBar(
    size: Dp,
    atEnd: Boolean,
    layoutDirection: LayoutDirection,
    modifier: Modifier = Modifier,
) {
  Box(
      modifier =
          modifier
              .then(
                  if (atEnd) {
                    Modifier.fillMaxHeight().width(size)
                  } else {
                    Modifier.fillMaxWidth().height(size)
                  },
              )
              .background(DevToolsSystemBarBackground),
      contentAlignment = if (atEnd) layoutDirection.endAlignment else Alignment.BottomCenter,
  ) {
    Box(
        modifier =
            Modifier.padding(
                    end = if (atEnd) 8.dp else 0.dp,
                    bottom = if (atEnd) 0.dp else 8.dp,
                )
                .size(
                    width = if (atEnd) 4.dp else 112.dp,
                    height = if (atEnd) 112.dp else 4.dp,
                )
                .background(DevToolsSystemBarContent, RoundedCornerShape(2.dp)),
    )
  }
}

private val LayoutDirection.endAlignment: Alignment
  get() = if (this == LayoutDirection.Rtl) Alignment.CenterStart else Alignment.CenterEnd

@Composable
private fun DevToolsSoftKeyboard(
    visibleHeight: Dp,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
  if (visibleHeight <= 0.dp) {
    return
  }

  val density = LocalDensity.current
  val hiddenOffset = DevToolsSoftKeyboardHeight - visibleHeight

  Box(
      modifier = modifier.fillMaxWidth().height(DevToolsSoftKeyboardHeight).clipToBounds(),
  ) {
    Column(
        modifier =
            Modifier.fillMaxSize()
                .graphicsLayer { translationY = with(density) { hiddenOffset.toPx() } }
                .background(DevToolsSoftKeyboardBackground)
                .padding(
                    horizontal = DevToolsSoftKeyboardHorizontalPadding,
                    vertical = DevToolsSoftKeyboardVerticalPadding),
        verticalArrangement = Arrangement.spacedBy(DevToolsSoftKeyboardRowSpacing),
    ) {
      DevToolsSoftKeyboardKeyRow(keys = listOf("Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"))
      DevToolsSoftKeyboardKeyRow(
          keys = listOf("A", "S", "D", "F", "G", "H", "J", "K", "L"),
          horizontalPadding = 16.dp,
      )
      Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(DevToolsSoftKeyboardKeySpacing),
          verticalAlignment = Alignment.CenterVertically,
      ) {
        DevToolsSoftKeyboardKey(
            icon = Icons.ArrowBigUp, modifier = Modifier.weight(1.35f), accent = true)
        DevToolsSoftKeyboardKeyRow(
            keys = listOf("Z", "X", "C", "V", "B", "N", "M"),
            modifier = Modifier.weight(7f),
        )
        DevToolsSoftKeyboardKey(
            icon = Icons.Delete, modifier = Modifier.weight(1.35f), accent = true)
      }
      Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(DevToolsSoftKeyboardKeySpacing),
          verticalAlignment = Alignment.CenterVertically,
      ) {
        DevToolsSoftKeyboardDismissKey(
            modifier = Modifier.weight(1.2f),
            onClick = onDismissRequest,
        )
        Box(Modifier.weight(8.8f))
      }
    }
  }
}

@Composable
private fun DevToolsSoftKeyboardKeyRow(
    keys: List<String>,
    modifier: Modifier = Modifier,
    horizontalPadding: Dp = 0.dp,
) {
  Row(
      modifier = modifier.fillMaxWidth().padding(horizontal = horizontalPadding),
      horizontalArrangement = Arrangement.spacedBy(DevToolsSoftKeyboardKeySpacing),
      verticalAlignment = Alignment.CenterVertically,
  ) {
    keys.forEach { key -> DevToolsSoftKeyboardKey(label = key, modifier = Modifier.weight(1f)) }
  }
}

@Composable
private fun DevToolsSoftKeyboardKey(
    label: String? = null,
    icon: ImageVector? = null,
    modifier: Modifier = Modifier,
    accent: Boolean = false,
) {
  Box(
      modifier =
          modifier
              .height(DevToolsSoftKeyboardKeyHeight)
              .clip(DevToolsSoftKeyboardKeyShape)
              .background(if (accent) DevToolsSoftKeyboardAccentKey else DevToolsSoftKeyboardKey),
      contentAlignment = Alignment.Center,
  ) {
    if (icon != null) {
      Icon(
          imageVector = icon,
          contentDescription = null,
          tint = DevToolsSoftKeyboardKeyContent,
          modifier = Modifier.size(16.dp),
      )
    } else if (label != null) {
      Text(
          text = label,
          color = DevToolsSoftKeyboardKeyContent,
          fontSize = if (label.length == 1) 13.sp else 10.sp,
          fontWeight = FontWeight.Medium,
          singleLine = true,
      )
    }
  }
}

@Composable
private fun DevToolsSoftKeyboardDismissKey(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
  Box(
      modifier = modifier.height(DevToolsSoftKeyboardKeyHeight).clickable(onClick = onClick),
      contentAlignment = Alignment.Center,
  ) {
    Icon(
        imageVector = Icons.ChevronDown,
        contentDescription = "Hide soft keyboard",
        tint = DevToolsSoftKeyboardKeyContent,
        modifier = Modifier.size(16.dp),
    )
  }
}

private fun devToolsInsetsOrientationFor(
    orientation: DevToolsOrientation,
): PreviewInsetsOrientation {
  return if (orientation == DevToolsOrientation.Landscape) {
    PreviewInsetsOrientation.Landscape
  } else {
    PreviewInsetsOrientation.Portrait
  }
}

private suspend fun saveCurrentDevToolsScreenshot(screenshotLayer: GraphicsLayer) {
  runCatching { saveDevToolsScreenshot(screenshotLayer.toImageBitmap()) }
}

private suspend fun copyCurrentDevToolsScreenshot(screenshotLayer: GraphicsLayer) {
  runCatching { copyDevToolsScreenshotToClipboard(screenshotLayer.toImageBitmap()) }
}

@Composable
private fun RunOnRequestChange(
    request: Any?,
    block: suspend () -> Unit,
) {
  var previousRequest by remember { mutableStateOf(request) }

  LaunchedEffect(request) {
    if (request != previousRequest) {
      previousRequest = request
      block()
    }
  }
}

private fun Modifier.captureDevToolsScreenshot(
    screenshotLayer: GraphicsLayer,
): Modifier {
  return drawWithContent {
    screenshotLayer.record { this@drawWithContent.drawContent() }
    drawLayer(screenshotLayer)
  }
}

@Composable
private fun ProvideDevToolsCompositionLocals(
    interactionMode: InteractionMode,
    colorScheme: ColorScheme,
    layoutDirection: LayoutDirection,
    content: @Composable () -> Unit,
) {
  CompositionLocalProvider(
      LocalInteractionMode provides interactionMode,
      LocalColorScheme provides colorScheme,
      LocalLayoutDirection provides layoutDirection,
  ) {
    content()
  }
}

@Composable
private fun ProvideDevToolsWindowInfo(
    width: Dp,
    height: Dp,
    content: @Composable () -> Unit,
) {
  val density = LocalDensity.current
  val parentWindowInfo = LocalWindowInfo.current
  val containerSize =
      with(density) { IntSize(width = width.roundToPx(), height = height.roundToPx()) }
  val containerDpSize = DpSize(width, height)
  val devToolsWindowInfo =
      remember(parentWindowInfo, containerSize, containerDpSize) {
        DevToolsWindowInfo(
            parentWindowInfo = parentWindowInfo,
            containerSize = containerSize,
            containerDpSize = containerDpSize,
        )
      }

  CompositionLocalProvider(
      LocalWindowInfo provides devToolsWindowInfo,
      LocalDropdownMenuWindowInfo provides parentWindowInfo,
  ) {
    content()
  }
}

private class DevToolsWindowInfo(
    private val parentWindowInfo: WindowInfo,
    override val containerSize: IntSize,
    override val containerDpSize: DpSize,
) : WindowInfo {
  override val isWindowFocused: Boolean
    get() = parentWindowInfo.isWindowFocused

  override val keyboardModifiers: PointerKeyboardModifiers
    get() = parentWindowInfo.keyboardModifiers
}

private data class DeviceRotationFrame(
    val orientation: DevToolsOrientation,
    val rotationZ: Float,
    val rotating: Boolean = false,
)

private suspend fun animateDeviceRotationTo(
    startRotation: Float,
    targetRotation: Float,
    easing: (Float) -> Float = ::easeOutCubic,
    onFrame: (rotation: Float, finished: Boolean) -> Unit,
) {
  val distance = abs(targetRotation - startRotation)
  if (distance == 0f) {
    onFrame(targetRotation, true)
    return
  }

  val durationNanos =
      (DeviceRotationDuration.inWholeNanoseconds.toFloat() * (distance / DeviceRotationDegrees))
          .roundToLong()
  val startTime = withFrameNanos { it }
  var finished = false
  while (!finished) {
    val frameTime = withFrameNanos { it }
    val progress = ((frameTime - startTime).toFloat() / durationNanos.toFloat()).coerceIn(0f, 1f)
    val rotation = startRotation + (targetRotation - startRotation) * easing(progress)
    finished = progress == 1f
    onFrame(rotation, finished)
  }
}

private fun easeInCubic(progress: Float): Float {
  return progress * progress * progress
}

private fun easeOutCubic(progress: Float): Float {
  val inverseProgress = 1f - progress
  return 1f - inverseProgress * inverseProgress * inverseProgress
}

private fun easeOutBack(progress: Float): Float {
  val shiftedProgress = progress - 1f
  return 1f +
      (DeviceRotationOvershoot + 1f) * shiftedProgress * shiftedProgress * shiftedProgress +
      DeviceRotationOvershoot * shiftedProgress * shiftedProgress
}

private val DevToolsBackground = Color(0xFF151515)
private val DevToolsContentBackground = Color(0xFFFAFAFA)
private val DevToolsStatusBarHeight = 22.dp
private val DevToolsNavigationBarHeight = 24.dp
private val DevToolsSoftKeyboardKeyHeight = 44.dp
private val DevToolsSoftKeyboardRowSpacing = 8.dp
private val DevToolsSoftKeyboardKeySpacing = 6.dp
private val DevToolsSoftKeyboardHorizontalPadding = 8.dp
private val DevToolsSoftKeyboardVerticalPadding = 10.dp
private val DevToolsSoftKeyboardHeight =
    DevToolsSoftKeyboardVerticalPadding * 2 +
        DevToolsSoftKeyboardKeyHeight * 4 +
        DevToolsSoftKeyboardRowSpacing * 3
private val DevToolsSoftKeyboardBackground = Color(0xFFE5E7EB)
private val DevToolsSoftKeyboardKey = Color(0xFFFAFAFA)
private val DevToolsSoftKeyboardAccentKey = Color(0xFFD1D5DB)
private val DevToolsSoftKeyboardKeyContent = Color(0xFF202124)
private val DevToolsSoftKeyboardKeyShape = RoundedCornerShape(6.dp)
private val DevToolsSystemBarBackground = Color.Transparent
private val DevToolsSystemBarContent = Color.Black
private val DeviceBezel = Color(0xFF070707)
private val FrameBorder = Color(0xFF4A4A4A)
private val DevToolsAnimationSpec =
    spring<Dp>(
        dampingRatio = Spring.DampingRatioNoBouncy,
        stiffness = Spring.StiffnessMediumLow,
    )
private val DevToolsSizeSnapSpec = snap<Dp>()
private val DevToolsZoomAnimationSpec =
    spring<Float>(
        dampingRatio = Spring.DampingRatioNoBouncy,
        stiffness = Spring.StiffnessMediumLow,
    )
private val DevToolsSoftKeyboardAnimationSpec = tween<Dp>(durationMillis = 250)
private val DeviceRotationContentFadeSpec = tween<Float>(durationMillis = 120)
private val DevToolsFramedPadding = 24.dp
private val DevToolsToolbarPanelPadding = 4.dp
private val DevToolsToolbarContentPadding = DevToolsToolbarPanelPadding
private val DevToolsToolbarHorizontalMargin = DevToolsToolbarPanelPadding
private val DevToolsToolbarBottomMargin = 12.dp
private val DeviceFramePadding = 10.dp
private val DeviceFrameCornerRadius = 40.dp
private val DeviceContentCornerRadius = 28.dp
private const val DeviceRotationDegrees = 90f
private const val DeviceRotationSwapProgress = 0.5f
private const val DeviceRotationSwapDegrees = 45f
private const val DeviceRotationOvershoot = 1.1f
private val DeviceRotationDuration = 360.milliseconds
private const val PlusKeyCode = 521L
