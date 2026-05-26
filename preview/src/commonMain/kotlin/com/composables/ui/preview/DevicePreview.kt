package com.composables.ui.preview

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.isMetaPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.pointer.PointerKeyboardModifiers
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.platform.WindowInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.ArrowLeftRight
import com.composables.icons.lucide.Check
import com.composables.icons.lucide.ChevronDown
import com.composables.icons.lucide.Minus
import com.composables.icons.lucide.Monitor
import com.composables.icons.lucide.Plus
import com.composables.icons.lucide.RotateCw
import com.composables.icons.lucide.Smartphone
import com.composables.icons.lucide.Tablet
import com.composables.ui.components.Button
import com.composables.ui.components.ButtonSize
import com.composables.ui.components.ButtonStyle
import com.composables.ui.components.DropdownMenu
import com.composables.ui.components.DropdownMenuAlignment
import com.composables.ui.components.DropdownMenuItem
import com.composables.ui.components.DropdownMenuPanel
import com.composables.ui.components.DropdownMenuSide
import com.composables.ui.components.Icon
import com.composables.ui.components.Text
import com.composables.ui.theme.AppTheme
import com.composables.ui.theme.shapes
import com.composables.ui.theme.smallShape
import com.composeunstyled.theme.Theme
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.math.roundToLong
import kotlin.time.Duration.Companion.milliseconds

data class DevicePreviewDevice(
    val id: String,
    val label: String,
    val width: Dp? = null,
    val height: Dp? = null,
)

@JvmInline
value class DevicePreviewZoom internal constructor(val scale: Float) {
    companion object {
        val Minimum = DevicePreviewZoom(0.25f)
        val Default = DevicePreviewZoom(1f)
        val Maximum = DevicePreviewZoom(5f)
    }
}

object DevicePreviewZoomLevels {
    val Default = listOf(
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
value class DevicePreviewOrientation internal constructor(@Suppress("unused") private val value: Int) {
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

object DevicePreviewDevices {
    val Mobile = DevicePreviewDevice(
        id = "mobile",
        label = "Mobile",
        width = 340.dp,
        height = 720.dp,
    )

    val Tablet = DevicePreviewDevice(
        id = "tablet",
        label = "Tablet",
        width = 768.dp,
        height = 1024.dp,
    )

    val Desktop = DevicePreviewDevice(
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
    selectedZoom: DevicePreviewZoom? = null,
    onZoomSelected: (DevicePreviewZoom) -> Unit = {},
    zoomLevels: List<DevicePreviewZoom> = DevicePreviewZoomLevels.Default,
    appBarDragArea: @Composable (modifier: Modifier, content: @Composable () -> Unit) -> Unit = { modifier, content ->
        Box(modifier = modifier) { content() }
    },
    content: @Composable () -> Unit,
) {
    require(devices.isNotEmpty()) { "DevicePreviewHost requires at least one device." }
    require(zoomLevels.isNotEmpty()) { "DevicePreviewHost requires at least one zoom level." }

    val initialSelection = remember(devices, initialDevice) {
        devices.firstOrNull { it.id == initialDevice.id } ?: devices.first()
    }
    var internalSelectedDevice by remember(devices, initialDevice) { mutableStateOf(initialSelection) }
    var internalOrientation by remember { mutableStateOf(DevicePreviewOrientation.Portrait) }
    val currentDevice = selectedDevice?.let { selected ->
        devices.firstOrNull { it.id == selected.id }
    } ?: internalSelectedDevice
    val currentOrientation = selectedOrientation ?: internalOrientation
    var internalLayoutDirection by remember { mutableStateOf(LayoutDirection.Ltr) }
    val currentLayoutDirection = selectedLayoutDirection ?: internalLayoutDirection
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
    val selectZoom = { zoom: DevicePreviewZoom ->
        internalZoom = zoom
        onZoomSelected(zoom)
    }

    AppTheme {
        val windowShape = Theme[shapes][smallShape]

        Column(
            modifier = modifier
                .fillMaxSize()
                .clip(windowShape)
                .background(PreviewBackground, windowShape),
        ) {
            DevicePreviewStage(
                device = currentDevice,
                devices = devices,
                orientation = currentOrientation,
                layoutDirection = currentLayoutDirection,
                zoom = currentZoom,
                zoomLevels = zoomLevels,
                onDeviceSelected = selectDevice,
                onOrientationChange = selectOrientation,
                onLayoutDirectionChange = selectLayoutDirection,
                onZoomChange = selectZoom,
                appBarDragArea = appBarDragArea,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                content = content,
            )
        }
    }
}

@Composable
private fun DevicePreviewChrome(
    devices: List<DevicePreviewDevice>,
    selectedDevice: DevicePreviewDevice,
    orientation: DevicePreviewOrientation,
    layoutDirection: LayoutDirection,
    zoom: DevicePreviewZoom,
    zoomLevels: List<DevicePreviewZoom>,
    onDeviceSelected: (DevicePreviewDevice) -> Unit,
    onOrientationChange: (DevicePreviewOrientation) -> Unit,
    onLayoutDirectionChange: (LayoutDirection) -> Unit,
    onZoomChange: (DevicePreviewZoom) -> Unit,
    appBarDragArea: @Composable (modifier: Modifier, content: @Composable () -> Unit) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .requiredHeight(64.dp)
            .background(ToolbarBackground)
            .border(width = 1.dp, color = ToolbarBorder)
            .padding(horizontal = 16.dp),
    ) {
        Row(
            modifier = Modifier.matchParentSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            appBarDragArea(
                Modifier
                    .weight(1f)
                    .fillMaxHeight(),
            ) {}
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                LayoutDirectionButton(
                    layoutDirection = layoutDirection,
                    onClick = { onLayoutDirectionChange(layoutDirection.oppositePreviewLayoutDirection()) },
                )
                ZoomControls(
                    zoom = zoom,
                    zoomLevels = zoomLevels,
                    onZoomChange = onZoomChange,
                )
                if (selectedDevice.canRotate) {
                    OrientationButton(
                        orientation = orientation,
                        onClick = { onOrientationChange(orientation.rotated()) },
                    )
                }
                devices.forEach { device ->
                    DevicePreviewButton(
                        device = device,
                        selected = device.id == selectedDevice.id,
                        onClick = { onDeviceSelected(device) },
                    )
                }
            }
        }
    }
}

@Composable
private fun LayoutDirectionButton(
    layoutDirection: LayoutDirection,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        style = ButtonStyle.Secondary,
        buttonSize = ButtonSize.Small,
    ) {
        Icon(
            imageVector = Lucide.ArrowLeftRight,
            contentDescription = if (layoutDirection == LayoutDirection.Ltr) {
                "Switch to RTL"
            } else {
                "Switch to LTR"
            },
            modifier = Modifier.size(18.dp),
        )
    }
}

@Composable
private fun OrientationButton(
    orientation: DevicePreviewOrientation,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        style = ButtonStyle.Secondary,
        buttonSize = ButtonSize.Small,
    ) {
        Icon(
            imageVector = Lucide.RotateCw,
            contentDescription = if (orientation == DevicePreviewOrientation.Portrait) {
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
        Button(
            onClick = { onZoomChange(zoom.zoomedOut(zoomLevels)) },
            enabled = zoom.scale > zoomLevels.minOf { it.scale },
            style = ButtonStyle.Secondary,
            buttonSize = ButtonSize.Small,
        ) {
            Icon(
                imageVector = Lucide.Minus,
                contentDescription = "Zoom out",
                modifier = Modifier.size(18.dp),
            )
        }
        ZoomMenu(
            zoom = zoom,
            zoomLevels = zoomLevels,
            onZoomChange = onZoomChange,
        )
        Button(
            onClick = { onZoomChange(zoom.zoomedIn(zoomLevels)) },
            enabled = zoom.scale < zoomLevels.maxOf { it.scale },
            style = ButtonStyle.Secondary,
            buttonSize = ButtonSize.Small,
        ) {
            Icon(
                imageVector = Lucide.Plus,
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
                                    imageVector = Lucide.Check,
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
                style = ButtonStyle.Secondary,
                buttonSize = ButtonSize.Small,
                modifier = Modifier.width(92.dp),
            ) {
                Text(zoom.label, singleLine = true)
                Icon(
                    imageVector = Lucide.ChevronDown,
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
    selected: Boolean,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        style = if (selected) ButtonStyle.Primary else ButtonStyle.Secondary,
        buttonSize = ButtonSize.Small,
    ) {
        Icon(
            imageVector = iconFor(device),
            contentDescription = device.label,
            modifier = Modifier.size(18.dp),
        )
    }
}

private fun iconFor(device: DevicePreviewDevice): ImageVector {
    return when (device.id) {
        DevicePreviewDevices.Mobile.id -> Lucide.Smartphone
        DevicePreviewDevices.Tablet.id -> Lucide.Tablet
        else -> Lucide.Monitor
    }
}

private val DevicePreviewZoom.label: String
    get() = "${(scale * 100).roundToInt()}%"

val DevicePreviewDevice.canRotate: Boolean
    get() = width != null && height != null

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
        Key.One -> devices.firstOrNull { it.id == DevicePreviewDevices.Mobile.id } ?: devices.getOrNull(0)
        Key.Two -> devices.firstOrNull { it.id == DevicePreviewDevices.Tablet.id } ?: devices.getOrNull(1)
        Key.Three -> devices.firstOrNull { it.id == DevicePreviewDevices.Desktop.id } ?: devices.getOrNull(2)
        else -> null
    }
}

fun isDevicePreviewRotationShortcut(event: KeyEvent): Boolean {
    return event.type == KeyEventType.KeyDown && event.isMetaPressed && event.key == Key.R
}

fun isDevicePreviewLayoutDirectionShortcut(event: KeyEvent): Boolean {
    return event.type == KeyEventType.KeyDown &&
        event.isMetaPressed &&
        event.key.keyCode == BackQuoteKeyCode
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
    zoom: DevicePreviewZoom,
    zoomLevels: List<DevicePreviewZoom>,
    onDeviceSelected: (DevicePreviewDevice) -> Unit,
    onOrientationChange: (DevicePreviewOrientation) -> Unit,
    onLayoutDirectionChange: (LayoutDirection) -> Unit,
    onZoomChange: (DevicePreviewZoom) -> Unit,
    appBarDragArea: @Composable (modifier: Modifier, content: @Composable () -> Unit) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val horizontalScrollState = rememberScrollState()
    val verticalScrollState = rememberScrollState()
    var rotationFrame by remember(device.id) {
        mutableStateOf(DeviceRotationFrame(orientation = orientation, rotationZ = 0f))
    }

    LaunchedEffect(device.id, device.canRotate, orientation) {
        suspend fun animateFrameRotationTo(
            targetRotation: Float,
            renderedOrientation: DevicePreviewOrientation,
        ) {
            val startRotation = rotationFrame.rotationZ
            val distance = abs(targetRotation - startRotation)
            if (distance == 0f) {
                rotationFrame = DeviceRotationFrame(
                    orientation = renderedOrientation,
                    rotationZ = targetRotation,
                    rotating = targetRotation != 0f,
                )
                return
            }

            val durationNanos = (
                DeviceRotationDuration.inWholeNanoseconds.toFloat() *
                    (distance / DeviceRotationDegrees)
            ).roundToLong()
            val startTime = withFrameNanos { it }
            var finished = false
            while (!finished) {
                val frameTime = withFrameNanos { it }
                val progress = ((frameTime - startTime).toFloat() / durationNanos.toFloat()).coerceIn(0f, 1f)
                val rotation = startRotation + (targetRotation - startRotation) * progress
                finished = progress == 1f
                rotationFrame = DeviceRotationFrame(
                    orientation = renderedOrientation,
                    rotationZ = rotation,
                    rotating = !finished || rotation != 0f,
                )
            }
        }

        suspend fun animateOrientationChangeTo(targetOrientation: DevicePreviewOrientation) {
            val startOrientation = rotationFrame.orientation
            val rotationDirection = if (targetOrientation == DevicePreviewOrientation.Landscape) 1f else -1f
            val startTime = withFrameNanos { it }
            var finished = false

            while (!finished) {
                val frameTime = withFrameNanos { it }
                val progress = (
                    (frameTime - startTime).toFloat() /
                        DeviceRotationDuration.inWholeNanoseconds.toFloat()
                ).coerceIn(0f, 1f)

                finished = progress == 1f
                rotationFrame = if (progress < DeviceRotationSwapProgress) {
                    DeviceRotationFrame(
                        orientation = startOrientation,
                        rotationZ = progress * DeviceRotationDegrees * rotationDirection,
                        rotating = true,
                    )
                } else {
                    DeviceRotationFrame(
                        orientation = targetOrientation,
                        rotationZ = (progress - 1f) * DeviceRotationDegrees * rotationDirection,
                        rotating = !finished,
                    )
                }
            }

            rotationFrame = DeviceRotationFrame(
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

    Column(modifier = modifier) {
        DevicePreviewChrome(
            devices = devices,
            selectedDevice = device,
            orientation = orientation,
            layoutDirection = layoutDirection,
            zoom = zoom,
            zoomLevels = zoomLevels,
            onDeviceSelected = onDeviceSelected,
            onOrientationChange = onOrientationChange,
            onLayoutDirectionChange = onLayoutDirectionChange,
            onZoomChange = onZoomChange,
            appBarDragArea = appBarDragArea,
            modifier = Modifier.fillMaxWidth(),
        )
        BoxWithConstraints(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
        ) {
            val framedDevice = device.canRotate
            var wasFramedDevice by remember { mutableStateOf(framedDevice) }
            val leavingFrame = wasFramedDevice && !framedDevice
            SideEffect {
                wasFramedDevice = framedDevice
            }
            val contentAlpha by animateFloatAsState(
                targetValue = if (framedDevice && rotationFrame.rotating) {
                    1f - (abs(rotationFrame.rotationZ) / DeviceRotationSwapDegrees).coerceIn(0f, 1f)
                } else {
                    1f
                },
                animationSpec = DeviceRotationContentFadeSpec,
                label = "DevicePreviewContentAlpha",
            )
            val targetWidth = if (framedDevice && rotationFrame.orientation == DevicePreviewOrientation.Landscape) {
                device.height
            } else {
                device.width
            } ?: maxWidth
            val targetHeight = if (framedDevice && rotationFrame.orientation == DevicePreviewOrientation.Landscape) {
                device.width
            } else {
                device.height
            } ?: maxHeight
            val targetPadding = if (framedDevice) 24.dp else 0.dp
            val animatedWidth by animateDpAsState(
                targetValue = targetWidth,
                animationSpec = if (rotationFrame.rotating || leavingFrame) {
                    DevicePreviewSizeSnapSpec
                } else {
                    DevicePreviewAnimationSpec
                },
                label = "DevicePreviewWidth",
            )
            val animatedHeight by animateDpAsState(
                targetValue = targetHeight,
                animationSpec = if (rotationFrame.rotating || leavingFrame) {
                    DevicePreviewSizeSnapSpec
                } else {
                    DevicePreviewAnimationSpec
                },
                label = "DevicePreviewHeight",
            )
            val animatedPadding by animateDpAsState(
                targetValue = targetPadding,
                animationSpec = if (leavingFrame) DevicePreviewSizeSnapSpec else DevicePreviewAnimationSpec,
                label = "DevicePreviewPadding",
            )
            val animatedZoom by animateFloatAsState(
                targetValue = zoom.scale,
                animationSpec = DevicePreviewZoomAnimationSpec,
                label = "DevicePreviewZoom",
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .horizontalScroll(horizontalScrollState)
                    .verticalScroll(verticalScrollState)
                    .padding(animatedPadding),
                contentAlignment = if (framedDevice) Alignment.Center else Alignment.TopCenter,
            ) {
                if (framedDevice) {
                    val frameWidth = if (rotationFrame.rotating) targetWidth else animatedWidth
                    val frameHeight = if (rotationFrame.rotating) targetHeight else animatedHeight
                    ZoomedPreview(
                        width = frameWidth + DeviceFramePadding * 2,
                        height = frameHeight + DeviceFramePadding * 2,
                        zoom = animatedZoom,
                    ) {
                        DevicePreviewFrame(
                            width = frameWidth,
                            height = frameHeight,
                            layoutDirection = layoutDirection,
                            contentAlpha = contentAlpha,
                            modifier = Modifier.graphicsLayer {
                                rotationZ = rotationFrame.rotationZ
                            },
                            content = content,
                        )
                    }
                } else {
                    BrowserZoomedPreview(
                        width = animatedWidth,
                        height = animatedHeight,
                        zoom = animatedZoom,
                        layoutDirection = layoutDirection,
                        content = content,
                    )
                }
            }
        }
    }
}

@Composable
private fun BrowserZoomedPreview(
    width: Dp,
    height: Dp,
    zoom: Float,
    layoutDirection: LayoutDirection,
    content: @Composable () -> Unit,
) {
    val layoutWidth = width * (1f / zoom)
    val layoutHeight = height * (1f / zoom)

    Box(
        modifier = Modifier
            .requiredSize(width, height)
            .clipToBounds()
            .background(PreviewContentBackground),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .requiredSize(layoutWidth, layoutHeight)
                .graphicsLayer {
                    scaleX = zoom
                    scaleY = zoom
                    transformOrigin = TransformOrigin.Center
                },
        ) {
            ProvidePreviewWindowInfo(width = layoutWidth, height = layoutHeight) {
                ProvidePreviewLayoutDirection(layoutDirection = layoutDirection, content = content)
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
            modifier = Modifier
                .requiredSize(width, height)
                .graphicsLayer {
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
    layoutDirection: LayoutDirection,
    contentAlpha: Float,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .background(DeviceBezel, RoundedCornerShape(40.dp))
            .border(width = 1.dp, color = FrameBorder, shape = RoundedCornerShape(40.dp))
            .padding(DeviceFramePadding),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .requiredSize(width, height)
                .clip(RoundedCornerShape(28.dp))
                .background(PreviewContentBackground)
                .graphicsLayer {
                    alpha = contentAlpha
                },
        ) {
            ProvidePreviewWindowInfo(width = width, height = height) {
                ProvidePreviewLayoutDirection(layoutDirection = layoutDirection, content = content)
            }
        }
    }
}

@Composable
private fun ProvidePreviewLayoutDirection(
    layoutDirection: LayoutDirection,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
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
    val containerSize = with(density) {
        IntSize(width = width.roundToPx(), height = height.roundToPx())
    }
    val containerDpSize = DpSize(width, height)
    val previewWindowInfo = remember(parentWindowInfo, containerSize, containerDpSize) {
        PreviewWindowInfo(
            parentWindowInfo = parentWindowInfo,
            containerSize = containerSize,
            containerDpSize = containerDpSize,
        )
    }

    CompositionLocalProvider(LocalWindowInfo provides previewWindowInfo) {
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

private val PreviewBackground = Color(0xFF151515)
private val PreviewContentBackground = Color(0xFFFAFAFA)
private val ToolbarBackground = Color(0xFF202020)
private val ToolbarBorder = Color(0xFF303030)
private val DeviceBezel = Color(0xFF070707)
private val FrameBorder = Color(0xFF4A4A4A)
private val DevicePreviewAnimationSpec = spring<Dp>(
    dampingRatio = Spring.DampingRatioNoBouncy,
    stiffness = Spring.StiffnessMediumLow,
)
private val DevicePreviewSizeSnapSpec = snap<Dp>()
private val DevicePreviewZoomAnimationSpec = spring<Float>(
    dampingRatio = Spring.DampingRatioNoBouncy,
    stiffness = Spring.StiffnessMediumLow,
)
private val DeviceRotationContentFadeSpec = tween<Float>(durationMillis = 120)
private val DeviceFramePadding = 10.dp
private const val DeviceRotationDegrees = 90f
private const val DeviceRotationSwapProgress = 0.5f
private const val DeviceRotationSwapDegrees = 45f
private val DeviceRotationDuration = 450.milliseconds
private const val BackQuoteKeyCode = 192L
private const val PlusKeyCode = 521L
