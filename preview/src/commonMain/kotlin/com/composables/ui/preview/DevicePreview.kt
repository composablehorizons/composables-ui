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
package com.composables.ui.preview

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
import com.composables.ui.preview.iconography.ArrowBigUp
import com.composables.ui.preview.iconography.Battery
import com.composables.ui.preview.iconography.Camera
import com.composables.ui.preview.iconography.Check
import com.composables.ui.preview.iconography.ChevronDown
import com.composables.ui.preview.iconography.Delete
import com.composables.ui.preview.iconography.Icons as PreviewIcons
import com.composables.ui.preview.iconography.Icons
import com.composables.ui.preview.iconography.Minus
import com.composables.ui.preview.iconography.Monitor
import com.composables.ui.preview.iconography.Moon
import com.composables.ui.preview.iconography.Plus
import com.composables.ui.preview.iconography.RotateCcwSquare
import com.composables.ui.preview.iconography.RotateCwSquare
import com.composables.ui.preview.iconography.Signal
import com.composables.ui.preview.iconography.Smartphone
import com.composables.ui.preview.iconography.Sun
import com.composables.ui.preview.iconography.Tablet
import com.composables.ui.preview.iconography.Wifi
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
import com.composeunstyled.DialogHost
import com.composeunstyled.ModalBottomSheetHost
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.theme.Theme
import kotlin.jvm.JvmInline
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.math.roundToLong
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.launch

data class DevicePreviewDevice(
    val id: String,
    val label: String,
    val size: DevicePreviewSize = DevicePreviewSize.Fill,
    val renderSystemBars: Boolean = false,
)

sealed interface DevicePreviewSize {
  data class Fixed(
      val width: Dp,
      val height: Dp,
  ) : DevicePreviewSize

  data object Fill : DevicePreviewSize
}

@JvmInline
value class DevicePreviewZoom internal constructor(val scale: Float) {
  companion object {
    val Default = DevicePreviewZoom(1f)
  }
}

object DevicePreviewZoomLevels {
  val Default =
      listOf(
          DevicePreviewZoom(0.25f),
          DevicePreviewZoom(0.33f),
          DevicePreviewZoom(0.5f),
          DevicePreviewZoom(0.67f),
          DevicePreviewZoom(0.75f),
          DevicePreviewZoom(0.8f),
          DevicePreviewZoom(0.9f),
          DevicePreviewZoom.Default,
          DevicePreviewZoom(1.1f),
          DevicePreviewZoom(1.25f),
          DevicePreviewZoom(1.5f),
          DevicePreviewZoom(1.75f),
          DevicePreviewZoom(2f),
          DevicePreviewZoom(2.5f),
          DevicePreviewZoom(3f),
          DevicePreviewZoom(4f),
          DevicePreviewZoom(5f),
      )
}

fun DevicePreviewZoom.zoomedIn(
    levels: List<DevicePreviewZoom> = DevicePreviewZoomLevels.Default,
): DevicePreviewZoom = nextZoomLevel(levels, direction = 1)

fun DevicePreviewZoom.zoomedOut(
    levels: List<DevicePreviewZoom> = DevicePreviewZoomLevels.Default,
): DevicePreviewZoom = nextZoomLevel(levels, direction = -1)

private fun DevicePreviewZoom.nextZoomLevel(
    levels: List<DevicePreviewZoom>,
    direction: Int,
): DevicePreviewZoom {
  require(levels.isNotEmpty()) { "Device preview zoom levels cannot be empty." }

  val sortedLevels = levels.sortedBy { it.scale }
  return if (direction > 0) {
    sortedLevels.firstOrNull { it.scale > scale } ?: sortedLevels.last()
  } else {
    sortedLevels.lastOrNull { it.scale < scale } ?: sortedLevels.first()
  }
}

@JvmInline
value class DevicePreviewOrientation
internal constructor(@Suppress("unused") private val value: Int) {
  companion object {
    val Portrait = DevicePreviewOrientation(0)
    val Landscape = DevicePreviewOrientation(1)
  }
}

fun DevicePreviewOrientation.rotated(): DevicePreviewOrientation {
  return if (this == DevicePreviewOrientation.Portrait) {
    DevicePreviewOrientation.Landscape
  } else {
    DevicePreviewOrientation.Portrait
  }
}

@JvmInline
value class DevicePreviewColorScheme
internal constructor(@Suppress("unused") private val value: Int) {
  companion object {
    val Light = DevicePreviewColorScheme(0)
    val Dark = DevicePreviewColorScheme(1)
  }
}

private fun DevicePreviewColorScheme.next(): DevicePreviewColorScheme {
  return when (this) {
    DevicePreviewColorScheme.Light -> DevicePreviewColorScheme.Dark
    else -> DevicePreviewColorScheme.Light
  }
}

private fun DevicePreviewColorScheme.resolve(): ColorScheme {
  return when (this) {
    DevicePreviewColorScheme.Light -> ColorScheme.Light
    else -> ColorScheme.Dark
  }
}

private val DevicePreviewColorScheme.icon: ImageVector
  get() =
      when (this) {
        DevicePreviewColorScheme.Light -> Icons.Sun
        else -> Icons.Moon
      }

private val DevicePreviewColorScheme.contentDescription: String
  get() =
      when (this) {
        DevicePreviewColorScheme.Light -> "Switch color scheme from light"
        else -> "Switch color scheme from dark"
      }

private fun devicePreviewColorSchemeFromSystem(
    systemInDarkTheme: Boolean
): DevicePreviewColorScheme {
  return if (systemInDarkTheme) DevicePreviewColorScheme.Dark else DevicePreviewColorScheme.Light
}

object DevicePreviewDevices {
  val Mobile =
      DevicePreviewDevice(
          id = "mobile",
          label = "Mobile",
          size = DevicePreviewSize.Fixed(width = 411.dp, height = 891.dp),
          renderSystemBars = true,
      )

  val Tablet =
      DevicePreviewDevice(
          id = "tablet",
          label = "Tablet",
          size = DevicePreviewSize.Fixed(width = 800.dp, height = 1280.dp),
          renderSystemBars = true,
      )

  val Desktop =
      DevicePreviewDevice(
          id = "desktop",
          label = "Desktop",
      )

  val Default = listOf(Mobile, Tablet, Desktop)
}

@Composable
fun DevicePreviewHost(
    modifier: Modifier = Modifier,
    devices: List<DevicePreviewDevice> = DevicePreviewDevices.Default,
    initialDevice: DevicePreviewDevice = DevicePreviewDevices.Mobile,
    selectedDevice: DevicePreviewDevice? = null,
    onDeviceSelected: (DevicePreviewDevice) -> Unit = {},
    selectedOrientation: DevicePreviewOrientation? = null,
    onOrientationSelected: (DevicePreviewOrientation) -> Unit = {},
    selectedLayoutDirection: LayoutDirection? = null,
    onLayoutDirectionSelected: (LayoutDirection) -> Unit = {},
    initialColorScheme: DevicePreviewColorScheme? = null,
    selectedColorScheme: DevicePreviewColorScheme? = null,
    onColorSchemeSelected: (DevicePreviewColorScheme) -> Unit = {},
    selectedZoom: DevicePreviewZoom? = null,
    onZoomSelected: (DevicePreviewZoom) -> Unit = {},
    zoomLevels: List<DevicePreviewZoom> = DevicePreviewZoomLevels.Default,
    showControls: Boolean = true,
    showScreenshot: Boolean = true,
    saveScreenshotRequest: Any? = null,
    copyScreenshotRequest: Any? = null,
    content: @Composable () -> Unit,
) {
  ModalBottomSheetHost {
    DialogHost {
      require(devices.isNotEmpty()) { "DevicePreviewHost requires at least one device." }
      require(zoomLevels.isNotEmpty()) { "DevicePreviewHost requires at least one zoom level." }

      val initialSelection =
          remember(devices, initialDevice) {
            devices.firstOrNull { it.id == initialDevice.id } ?: devices.first()
          }
      var internalSelectedDevice by
          remember(devices, initialDevice) { mutableStateOf(initialSelection) }
      var internalOrientation by remember { mutableStateOf(DevicePreviewOrientation.Portrait) }
      val currentDevice =
          selectedDevice?.let { selected -> devices.firstOrNull { it.id == selected.id } }
              ?: internalSelectedDevice
      val currentOrientation = selectedOrientation ?: internalOrientation
      var internalLayoutDirection by remember { mutableStateOf(LayoutDirection.Ltr) }
      val currentLayoutDirection = selectedLayoutDirection ?: internalLayoutDirection
      val systemInDarkTheme = isSystemInDarkTheme()
      val defaultColorScheme =
          initialColorScheme ?: devicePreviewColorSchemeFromSystem(systemInDarkTheme)
      var internalColorScheme by remember { mutableStateOf(defaultColorScheme) }
      val currentColorScheme = selectedColorScheme ?: internalColorScheme
      val currentResolvedColorScheme = currentColorScheme.resolve()
      var internalZoom by remember { mutableStateOf(DevicePreviewZoom.Default) }
      val currentZoom = selectedZoom ?: internalZoom
      val selectDevice = { device: DevicePreviewDevice ->
        internalSelectedDevice = device
        onDeviceSelected(device)
      }
      val selectOrientation = { orientation: DevicePreviewOrientation ->
        internalOrientation = orientation
        onOrientationSelected(orientation)
      }
      val selectLayoutDirection = { layoutDirection: LayoutDirection ->
        internalLayoutDirection = layoutDirection
        onLayoutDirectionSelected(layoutDirection)
      }
      val selectColorScheme = { colorScheme: DevicePreviewColorScheme ->
        internalColorScheme = colorScheme
        onColorSchemeSelected(colorScheme)
      }
      val selectZoom = { zoom: DevicePreviewZoom ->
        internalZoom = zoom
        onZoomSelected(zoom)
      }
      val previewContent = remember { movableContentOf(content) }

      CompositionLocalProvider(LocalColorScheme provides currentResolvedColorScheme) {
        ComposablesTheme {
          val windowShape = Theme[shapes][smallShape]

          Box(
              modifier =
                  modifier
                      .fillMaxSize()
                      .clip(windowShape)
                      .background(PreviewBackground, windowShape),
          ) {
            DevicePreviewStage(
                device = currentDevice,
                devices = devices,
                orientation = currentOrientation,
                layoutDirection = currentLayoutDirection,
                colorScheme = currentResolvedColorScheme,
                selectedColorScheme = currentColorScheme,
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
                content = previewContent,
            )
          }
        }
      }
    }
  }
}

@Composable
private fun DevicePreviewControls(
    devices: List<DevicePreviewDevice>,
    selectedDevice: DevicePreviewDevice,
    orientation: DevicePreviewOrientation,
    layoutDirection: LayoutDirection,
    colorScheme: DevicePreviewColorScheme,
    zoom: DevicePreviewZoom,
    zoomLevels: List<DevicePreviewZoom>,
    showScreenshot: Boolean,
    onSaveScreenshotRequest: () -> Unit,
    onDeviceSelected: (DevicePreviewDevice) -> Unit,
    onOrientationChange: (DevicePreviewOrientation) -> Unit,
    onLayoutDirectionChange: (LayoutDirection) -> Unit,
    onColorSchemeChange: (DevicePreviewColorScheme) -> Unit,
    onZoomChange: (DevicePreviewZoom) -> Unit,
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
              .padding(PreviewToolbarPanelPadding),
  ) {
    ProvideContentColor(contentColor) {
      Row(
          modifier =
              Modifier.horizontalScroll(controlsScrollState).padding(PreviewToolbarContentPadding),
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
        PreviewOptionsGroup(
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
    devices: List<DevicePreviewDevice>,
    selectedDevice: DevicePreviewDevice,
    orientation: DevicePreviewOrientation,
    onDeviceSelected: (DevicePreviewDevice) -> Unit,
) {
  devices.forEach { device ->
    DevicePreviewButton(
        device = device,
        orientation = orientation,
        selected = device.id == selectedDevice.id,
        onClick = { onDeviceSelected(device) },
    )
  }
}

@Composable
private fun PreviewOptionsGroup(
    orientation: DevicePreviewOrientation,
    layoutDirection: LayoutDirection,
    colorScheme: DevicePreviewColorScheme,
    enabled: Boolean,
    onLayoutDirectionChange: (LayoutDirection) -> Unit,
    onColorSchemeChange: (DevicePreviewColorScheme) -> Unit,
    onOrientationChange: (DevicePreviewOrientation) -> Unit,
) {
  LayoutDirectionButton(
      layoutDirection = layoutDirection,
      onClick = { onLayoutDirectionChange(layoutDirection.oppositePreviewLayoutDirection()) },
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
    colorScheme: DevicePreviewColorScheme,
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
    orientation: DevicePreviewOrientation,
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
            if (orientation == DevicePreviewOrientation.Portrait) {
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
    zoom: DevicePreviewZoom,
    zoomLevels: List<DevicePreviewZoom>,
    onZoomChange: (DevicePreviewZoom) -> Unit,
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
    zoom: DevicePreviewZoom,
    zoomLevels: List<DevicePreviewZoom>,
    onZoomChange: (DevicePreviewZoom) -> Unit,
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
private fun DevicePreviewButton(
    device: DevicePreviewDevice,
    orientation: DevicePreviewOrientation,
    selected: Boolean,
    onClick: () -> Unit,
) {
  val targetIconRotation =
      if (device.hasOrientationIcon && orientation == DevicePreviewOrientation.Landscape) {
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

private val DevicePreviewDevice.hasOrientationIcon: Boolean
  get() = id == DevicePreviewDevices.Mobile.id || id == DevicePreviewDevices.Tablet.id

private fun iconFor(device: DevicePreviewDevice): ImageVector {
  return when (device.id) {
    DevicePreviewDevices.Mobile.id -> Icons.Smartphone
    DevicePreviewDevices.Tablet.id -> Icons.Tablet
    else -> Icons.Monitor
  }
}

private fun iconForRotation(orientation: DevicePreviewOrientation): ImageVector {
  return if (orientation == DevicePreviewOrientation.Portrait) {
    Icons.RotateCwSquare
  } else {
    Icons.RotateCcwSquare
  }
}

private val DevicePreviewZoom.label: String
  get() = "${(scale * 100).roundToInt()}%"

val DevicePreviewDevice.canRotate: Boolean
  get() = size is DevicePreviewSize.Fixed

private fun previewTargetSize(
    deviceSize: DevicePreviewSize,
    orientation: DevicePreviewOrientation,
    containerWidth: Dp,
    containerHeight: Dp,
): DpSize {
  return when (deviceSize) {
    is DevicePreviewSize.Fixed -> {
      if (orientation == DevicePreviewOrientation.Landscape) {
        DpSize(width = deviceSize.height, height = deviceSize.width)
      } else {
        DpSize(width = deviceSize.width, height = deviceSize.height)
      }
    }
    DevicePreviewSize.Fill -> DpSize(width = containerWidth, height = containerHeight)
  }
}

fun LayoutDirection.oppositePreviewLayoutDirection(): LayoutDirection {
  return if (this == LayoutDirection.Ltr) {
    LayoutDirection.Rtl
  } else {
    LayoutDirection.Ltr
  }
}

fun deviceForPreviewShortcut(
    event: KeyEvent,
    devices: List<DevicePreviewDevice> = DevicePreviewDevices.Default,
): DevicePreviewDevice? {
  if (event.type != KeyEventType.KeyDown || !event.isMetaPressed) {
    return null
  }

  return when (event.key) {
    Key.One -> devices.firstOrNull { it.id == DevicePreviewDevices.Mobile.id }
            ?: devices.getOrNull(0)
    Key.Two -> devices.firstOrNull { it.id == DevicePreviewDevices.Tablet.id }
            ?: devices.getOrNull(1)
    Key.Three -> devices.firstOrNull { it.id == DevicePreviewDevices.Desktop.id }
            ?: devices.getOrNull(2)
    else -> null
  }
}

fun isDevicePreviewRotationShortcut(event: KeyEvent): Boolean {
  return event.type == KeyEventType.KeyDown &&
      event.isMetaPressed &&
      !event.isShiftPressed &&
      event.key == Key.R
}

fun isDevicePreviewHotReloadShortcut(event: KeyEvent): Boolean {
  return event.type == KeyEventType.KeyDown &&
      event.isMetaPressed &&
      event.isShiftPressed &&
      event.key == Key.R
}

fun isDevicePreviewScreenshotSaveShortcut(event: KeyEvent): Boolean {
  return event.type == KeyEventType.KeyDown &&
      event.isMetaPressed &&
      !event.isShiftPressed &&
      event.key == Key.P
}

fun isDevicePreviewScreenshotCopyShortcut(event: KeyEvent): Boolean {
  return event.type == KeyEventType.KeyDown &&
      event.isMetaPressed &&
      event.isShiftPressed &&
      event.key == Key.P
}

fun isDevicePreviewLayoutDirectionShortcut(event: KeyEvent): Boolean {
  return event.type == KeyEventType.KeyDown && event.isMetaPressed && event.key == Key.Grave
}

fun isDevicePreviewToolbarShortcut(event: KeyEvent): Boolean {
  return event.type == KeyEventType.KeyDown && event.isMetaPressed && event.key == Key.Period
}

fun devicePreviewZoomForShortcut(
    event: KeyEvent,
    currentZoom: DevicePreviewZoom,
    levels: List<DevicePreviewZoom> = DevicePreviewZoomLevels.Default,
): DevicePreviewZoom? {
  if (event.type != KeyEventType.KeyDown || !event.isMetaPressed) {
    return null
  }

  return when {
    event.key == Key.Zero -> DevicePreviewZoom.Default
    event.key == Key.Minus -> currentZoom.zoomedOut(levels)
    event.key == Key.Equals || event.key.keyCode == PlusKeyCode -> currentZoom.zoomedIn(levels)
    else -> null
  }
}

@Composable
private fun DevicePreviewStage(
    device: DevicePreviewDevice,
    devices: List<DevicePreviewDevice>,
    orientation: DevicePreviewOrientation,
    layoutDirection: LayoutDirection,
    colorScheme: ColorScheme,
    selectedColorScheme: DevicePreviewColorScheme,
    zoom: DevicePreviewZoom,
    zoomLevels: List<DevicePreviewZoom>,
    showControls: Boolean,
    showScreenshot: Boolean,
    saveScreenshotRequest: Any?,
    copyScreenshotRequest: Any?,
    onDeviceSelected: (DevicePreviewDevice) -> Unit,
    onOrientationChange: (DevicePreviewOrientation) -> Unit,
    onLayoutDirectionChange: (LayoutDirection) -> Unit,
    onColorSchemeChange: (DevicePreviewColorScheme) -> Unit,
    onZoomChange: (DevicePreviewZoom) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
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

  RunOnRequestChange(saveScreenshotRequest) { saveCurrentDevicePreviewScreenshot(screenshotLayer) }
  RunOnRequestChange(copyScreenshotRequest) { copyCurrentDevicePreviewScreenshot(screenshotLayer) }

  LaunchedEffect(device.id, device.canRotate, orientation) {
    suspend fun animateFrameRotationTo(
        targetRotation: Float,
        renderedOrientation: DevicePreviewOrientation,
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

    suspend fun animateOrientationChangeTo(targetOrientation: DevicePreviewOrientation) {
      val startOrientation = rotationFrame.orientation
      val rotationDirection =
          if (targetOrientation == DevicePreviewOrientation.Landscape) 1f else -1f
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
              label = "DevicePreviewContentAlpha",
          )
      val targetSize =
          previewTargetSize(
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
              animationSpec = DevicePreviewZoomAnimationSpec,
              label = "DevicePreviewZoom",
          )
      val animatedWidth by
          animateDpAsState(
              targetValue = targetWidth,
              animationSpec =
                  if (rotationFrame.rotating || (!deviceChanged && !framedDevice)) {
                    DevicePreviewSizeSnapSpec
                  } else {
                    DevicePreviewAnimationSpec
                  },
              label = "DevicePreviewFrameWidth",
          )
      val animatedHeight by
          animateDpAsState(
              targetValue = targetHeight,
              animationSpec =
                  if (rotationFrame.rotating || (!deviceChanged && !framedDevice)) {
                    DevicePreviewSizeSnapSpec
                  } else {
                    DevicePreviewAnimationSpec
                  },
              label = "DevicePreviewFrameHeight",
          )
      val previewWidth = if (rotationFrame.rotating) targetWidth else animatedWidth
      val previewHeight = if (rotationFrame.rotating) targetHeight else animatedHeight
      val animatedStagePadding by
          animateDpAsState(
              targetValue = if (framedDevice) DevicePreviewFramedPadding else 0.dp,
              animationSpec = DevicePreviewAnimationSpec,
              label = "DevicePreviewStagePadding",
          )
      val animatedFramePadding by
          animateDpAsState(
              targetValue = if (framedDevice) DeviceFramePadding else 0.dp,
              animationSpec = DevicePreviewAnimationSpec,
              label = "DevicePreviewFramePadding",
          )
      val animatedFrameCornerRadius by
          animateDpAsState(
              targetValue = if (framedDevice) DeviceFrameCornerRadius else 0.dp,
              animationSpec = DevicePreviewAnimationSpec,
              label = "DevicePreviewFrameCornerRadius",
          )
      val animatedContentCornerRadius by
          animateDpAsState(
              targetValue = if (framedDevice) DeviceContentCornerRadius else 0.dp,
              animationSpec = DevicePreviewAnimationSpec,
              label = "DevicePreviewContentCornerRadius",
          )
      val animatedSoftKeyboardHeight by
          animateDpAsState(
              targetValue =
                  if (supportsSoftKeyboard &&
                      activeTextInputSessions > 0 &&
                      !softKeyboardDismissed) {
                    PreviewSoftKeyboardHeight
                  } else {
                    0.dp
                  },
              animationSpec = PreviewSoftKeyboardAnimationSpec,
              label = "PreviewSoftKeyboardHeight",
          )
      val animatedFrameBorderWidth by
          animateDpAsState(
              targetValue = if (framedDevice) 1.dp else 0.dp,
              animationSpec = DevicePreviewAnimationSpec,
              label = "DevicePreviewFrameBorderWidth",
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
                PreviewStatusBarHeight
              } else {
                0.dp
              }
          val navigationBarSize =
              if (renderSystemBars) {
                PreviewNavigationBarHeight
              } else {
                0.dp
              }
          val previewInsetsOrientation =
              previewInsetsOrientationFor(
                  orientation = rotationFrame.orientation,
              )
          val navigationBarAtEnd = previewInsetsOrientation == PreviewInsetsOrientation.Landscape
          ProvidePreviewWindowInsets(
              windowInsets =
                  PreviewWindowInsets(
                      statusBarSize = statusBarSize,
                      navigationBarSize = navigationBarSize,
                      softKeyboardSize = animatedSoftKeyboardHeight,
                      orientation = previewInsetsOrientation,
                  ),
          ) {
            PreviewSoftKeyboardInterceptor(
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
                ZoomedPreview(
                    width = previewWidth + animatedFramePadding * 2,
                    height = previewHeight + animatedFramePadding * 2,
                    zoom = animatedZoom,
                ) {
                  DevicePreviewFrame(
                      width = previewWidth,
                      height = previewHeight,
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
                DesktopZoomedPreview(
                    width = previewWidth,
                    height = previewHeight,
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
        DevicePreviewControls(
            devices = devices,
            selectedDevice = device,
            orientation = orientation,
            layoutDirection = layoutDirection,
            colorScheme = selectedColorScheme,
            zoom = zoom,
            zoomLevels = zoomLevels,
            showScreenshot = showScreenshot,
            onSaveScreenshotRequest = {
              coroutineScope.launch { saveCurrentDevicePreviewScreenshot(screenshotLayer) }
            },
            onDeviceSelected = onDeviceSelected,
            onOrientationChange = onOrientationChange,
            onLayoutDirectionChange = onLayoutDirectionChange,
            onColorSchemeChange = onColorSchemeChange,
            onZoomChange = onZoomChange,
            modifier =
                Modifier.align(Alignment.BottomCenter)
                    .padding(
                        start = PreviewToolbarHorizontalMargin,
                        end = PreviewToolbarHorizontalMargin,
                        bottom = PreviewToolbarBottomMargin,
                    ),
        )
      }
    }
  }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun PreviewSoftKeyboardInterceptor(
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
private fun DesktopZoomedPreview(
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
              .captureDevicePreviewScreenshot(screenshotLayer)
              .background(PreviewContentBackground),
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
      ProvidePreviewWindowInfo(width = layoutWidth, height = layoutHeight) {
        ProvidePreviewCompositionLocals(
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
private fun ZoomedPreview(
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
private fun DevicePreviewFrame(
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
                .captureDevicePreviewScreenshot(screenshotLayer)
                .background(PreviewContentBackground)
                .graphicsLayer { alpha = contentAlpha },
    ) {
      ProvidePreviewWindowInfo(width = width, height = height) {
        ProvidePreviewCompositionLocals(
            interactionMode = interactionMode,
            colorScheme = colorScheme,
            layoutDirection = layoutDirection,
            content = content,
        )
      }
      if (renderSystemBars && statusBarHeight > 0.dp) {
        PreviewStatusBar(
            height = statusBarHeight,
            endPadding = if (navigationBarAtEnd) navigationBarSize else 0.dp,
            modifier = Modifier.align(Alignment.TopCenter),
        )
      }
      if (renderSystemBars && navigationBarSize > 0.dp) {
        PreviewNavigationBar(
            size = navigationBarSize,
            atEnd = navigationBarAtEnd,
            modifier =
                if (navigationBarAtEnd) {
                  Modifier.align(Alignment.CenterEnd)
                } else {
                  Modifier.align(Alignment.BottomCenter)
                },
        )
      }
      PreviewSoftKeyboard(
          visibleHeight = softKeyboardHeight,
          onDismissRequest = onSoftKeyboardDismissRequest,
          modifier = Modifier.align(Alignment.BottomCenter),
      )
    }
  }
}

@Composable
private fun PreviewStatusBar(
    height: Dp,
    endPadding: Dp,
    modifier: Modifier = Modifier,
) {
  Box(
      modifier =
          modifier
              .fillMaxWidth()
              .padding(end = endPadding)
              .height(height)
              .background(PreviewSystemBarBackground)
              .padding(horizontal = 16.dp),
  ) {
    Text(
        text = "12:00",
        color = PreviewSystemBarContent,
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
          imageVector = PreviewIcons.Signal,
          contentDescription = null,
          tint = PreviewSystemBarContent,
          modifier = Modifier.size(width = 15.dp, height = 15.dp),
      )
      Icon(
          imageVector = PreviewIcons.Wifi,
          contentDescription = null,
          tint = PreviewSystemBarContent,
          modifier = Modifier.size(width = 14.dp, height = 14.dp),
      )
      Icon(
          imageVector = PreviewIcons.Battery,
          contentDescription = null,
          tint = PreviewSystemBarContent,
          modifier = Modifier.size(width = 18.dp, height = 18.dp),
      )
    }
  }
}

@Composable
private fun PreviewNavigationBar(
    size: Dp,
    atEnd: Boolean,
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
              .background(PreviewSystemBarBackground),
      contentAlignment = if (atEnd) Alignment.CenterEnd else Alignment.BottomCenter,
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
                .background(PreviewSystemBarContent, RoundedCornerShape(2.dp)),
    )
  }
}

@Composable
private fun PreviewSoftKeyboard(
    visibleHeight: Dp,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
  if (visibleHeight <= 0.dp) {
    return
  }

  val density = LocalDensity.current
  val hiddenOffset = PreviewSoftKeyboardHeight - visibleHeight

  Box(
      modifier = modifier.fillMaxWidth().height(PreviewSoftKeyboardHeight).clipToBounds(),
  ) {
    Column(
        modifier =
            Modifier.fillMaxSize()
                .graphicsLayer { translationY = with(density) { hiddenOffset.toPx() } }
                .background(PreviewSoftKeyboardBackground)
                .padding(
                    horizontal = PreviewSoftKeyboardHorizontalPadding,
                    vertical = PreviewSoftKeyboardVerticalPadding),
        verticalArrangement = Arrangement.spacedBy(PreviewSoftKeyboardRowSpacing),
    ) {
      PreviewSoftKeyboardKeyRow(keys = listOf("Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"))
      PreviewSoftKeyboardKeyRow(
          keys = listOf("A", "S", "D", "F", "G", "H", "J", "K", "L"),
          horizontalPadding = 16.dp,
      )
      Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(PreviewSoftKeyboardKeySpacing),
          verticalAlignment = Alignment.CenterVertically,
      ) {
        PreviewSoftKeyboardKey(
            icon = Icons.ArrowBigUp, modifier = Modifier.weight(1.35f), accent = true)
        PreviewSoftKeyboardKeyRow(
            keys = listOf("Z", "X", "C", "V", "B", "N", "M"),
            modifier = Modifier.weight(7f),
        )
        PreviewSoftKeyboardKey(
            icon = Icons.Delete, modifier = Modifier.weight(1.35f), accent = true)
      }
      Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(PreviewSoftKeyboardKeySpacing),
          verticalAlignment = Alignment.CenterVertically,
      ) {
        PreviewSoftKeyboardDismissKey(
            modifier = Modifier.weight(1.2f),
            onClick = onDismissRequest,
        )
        Box(Modifier.weight(8.8f))
      }
    }
  }
}

@Composable
private fun PreviewSoftKeyboardKeyRow(
    keys: List<String>,
    modifier: Modifier = Modifier,
    horizontalPadding: Dp = 0.dp,
) {
  Row(
      modifier = modifier.fillMaxWidth().padding(horizontal = horizontalPadding),
      horizontalArrangement = Arrangement.spacedBy(PreviewSoftKeyboardKeySpacing),
      verticalAlignment = Alignment.CenterVertically,
  ) {
    keys.forEach { key -> PreviewSoftKeyboardKey(label = key, modifier = Modifier.weight(1f)) }
  }
}

@Composable
private fun PreviewSoftKeyboardKey(
    label: String? = null,
    icon: ImageVector? = null,
    modifier: Modifier = Modifier,
    accent: Boolean = false,
) {
  Box(
      modifier =
          modifier
              .height(PreviewSoftKeyboardKeyHeight)
              .clip(PreviewSoftKeyboardKeyShape)
              .background(if (accent) PreviewSoftKeyboardAccentKey else PreviewSoftKeyboardKey),
      contentAlignment = Alignment.Center,
  ) {
    if (icon != null) {
      Icon(
          imageVector = icon,
          contentDescription = null,
          tint = PreviewSoftKeyboardKeyContent,
          modifier = Modifier.size(16.dp),
      )
    } else if (label != null) {
      Text(
          text = label,
          color = PreviewSoftKeyboardKeyContent,
          fontSize = if (label.length == 1) 13.sp else 10.sp,
          fontWeight = FontWeight.Medium,
          singleLine = true,
      )
    }
  }
}

@Composable
private fun PreviewSoftKeyboardDismissKey(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
  Box(
      modifier = modifier.height(PreviewSoftKeyboardKeyHeight).clickable(onClick = onClick),
      contentAlignment = Alignment.Center,
  ) {
    Icon(
        imageVector = Icons.ChevronDown,
        contentDescription = "Hide soft keyboard",
        tint = PreviewSoftKeyboardKeyContent,
        modifier = Modifier.size(16.dp),
    )
  }
}

private fun previewInsetsOrientationFor(
    orientation: DevicePreviewOrientation,
): PreviewInsetsOrientation {
  return if (orientation == DevicePreviewOrientation.Landscape) {
    PreviewInsetsOrientation.Landscape
  } else {
    PreviewInsetsOrientation.Portrait
  }
}

private suspend fun saveCurrentDevicePreviewScreenshot(screenshotLayer: GraphicsLayer) {
  runCatching { saveDevicePreviewScreenshot(screenshotLayer.toImageBitmap()) }
}

private suspend fun copyCurrentDevicePreviewScreenshot(screenshotLayer: GraphicsLayer) {
  runCatching { copyDevicePreviewScreenshotToClipboard(screenshotLayer.toImageBitmap()) }
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

private fun Modifier.captureDevicePreviewScreenshot(
    screenshotLayer: GraphicsLayer,
): Modifier {
  return drawWithContent {
    screenshotLayer.record { this@drawWithContent.drawContent() }
    drawLayer(screenshotLayer)
  }
}

@Composable
private fun ProvidePreviewCompositionLocals(
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
private fun ProvidePreviewWindowInfo(
    width: Dp,
    height: Dp,
    content: @Composable () -> Unit,
) {
  val density = LocalDensity.current
  val parentWindowInfo = LocalWindowInfo.current
  val containerSize =
      with(density) { IntSize(width = width.roundToPx(), height = height.roundToPx()) }
  val containerDpSize = DpSize(width, height)
  val previewWindowInfo =
      remember(parentWindowInfo, containerSize, containerDpSize) {
        PreviewWindowInfo(
            parentWindowInfo = parentWindowInfo,
            containerSize = containerSize,
            containerDpSize = containerDpSize,
        )
      }

  CompositionLocalProvider(
      LocalWindowInfo provides previewWindowInfo,
      LocalDropdownMenuWindowInfo provides parentWindowInfo,
  ) {
    content()
  }
}

private class PreviewWindowInfo(
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
    val orientation: DevicePreviewOrientation,
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

private val PreviewBackground = Color(0xFF151515)
private val PreviewContentBackground = Color(0xFFFAFAFA)
private val PreviewStatusBarHeight = 22.dp
private val PreviewNavigationBarHeight = 24.dp
private val PreviewSoftKeyboardKeyHeight = 44.dp
private val PreviewSoftKeyboardRowSpacing = 8.dp
private val PreviewSoftKeyboardKeySpacing = 6.dp
private val PreviewSoftKeyboardHorizontalPadding = 8.dp
private val PreviewSoftKeyboardVerticalPadding = 10.dp
private val PreviewSoftKeyboardHeight =
    PreviewSoftKeyboardVerticalPadding * 2 +
        PreviewSoftKeyboardKeyHeight * 4 +
        PreviewSoftKeyboardRowSpacing * 3
private val PreviewSoftKeyboardBackground = Color(0xFFE5E7EB)
private val PreviewSoftKeyboardKey = Color(0xFFFAFAFA)
private val PreviewSoftKeyboardAccentKey = Color(0xFFD1D5DB)
private val PreviewSoftKeyboardKeyContent = Color(0xFF202124)
private val PreviewSoftKeyboardKeyShape = RoundedCornerShape(6.dp)
private val PreviewSystemBarBackground = Color.Transparent
private val PreviewSystemBarContent = Color.Black
private val DeviceBezel = Color(0xFF070707)
private val FrameBorder = Color(0xFF4A4A4A)
private val DevicePreviewAnimationSpec =
    spring<Dp>(
        dampingRatio = Spring.DampingRatioNoBouncy,
        stiffness = Spring.StiffnessMediumLow,
    )
private val DevicePreviewSizeSnapSpec = snap<Dp>()
private val DevicePreviewZoomAnimationSpec =
    spring<Float>(
        dampingRatio = Spring.DampingRatioNoBouncy,
        stiffness = Spring.StiffnessMediumLow,
    )
private val PreviewSoftKeyboardAnimationSpec = tween<Dp>(durationMillis = 250)
private val DeviceRotationContentFadeSpec = tween<Float>(durationMillis = 120)
private val DevicePreviewFramedPadding = 24.dp
private val PreviewToolbarPanelPadding = 4.dp
private val PreviewToolbarContentPadding = PreviewToolbarPanelPadding
private val PreviewToolbarHorizontalMargin = PreviewToolbarPanelPadding
private val PreviewToolbarBottomMargin = 12.dp
private val DeviceFramePadding = 10.dp
private val DeviceFrameCornerRadius = 40.dp
private val DeviceContentCornerRadius = 28.dp
private const val DeviceRotationDegrees = 90f
private const val DeviceRotationSwapProgress = 0.5f
private const val DeviceRotationSwapDegrees = 45f
private const val DeviceRotationOvershoot = 1.1f
private val DeviceRotationDuration = 360.milliseconds
private const val PlusKeyCode = 521L
