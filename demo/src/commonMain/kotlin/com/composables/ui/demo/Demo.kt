package com.composables.ui.demo

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.composables.icons.lucide.ArrowLeft
import com.composables.icons.lucide.ChevronRight
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Moon
import com.composables.icons.lucide.Sun
import com.composables.ui.components.AppScaffold
import com.composables.ui.components.Button
import com.composables.ui.components.ButtonStyle
import com.composables.ui.components.Disclosure
import com.composables.ui.components.DisclosureHeading
import com.composables.ui.components.DisclosurePanel
import com.composables.ui.components.Icon
import com.composables.ui.components.IconButton
import com.composables.ui.components.ScreenScaffold
import com.composables.ui.components.Text
import com.composables.ui.components.Toolbar
import com.composables.ui.components.ToolbarSize
import com.composables.ui.components.focusRing
import com.composables.ui.components.rememberDisclosureState
import com.composables.ui.demo.examples.AlertDialogExample
import com.composables.ui.demo.examples.AlertDialogThreeActionsExample
import com.composables.ui.demo.examples.AlertDialogWithIconExample
import com.composables.ui.demo.examples.BottomSheetActionMenuExample
import com.composables.ui.demo.examples.BottomSheetConfirmationExample
import com.composables.ui.demo.examples.BottomSheetFormExample
import com.composables.ui.demo.examples.ButtonExample
import com.composables.ui.demo.examples.ButtonSizesExample
import com.composables.ui.demo.examples.CenteredToolbarExample
import com.composables.ui.demo.examples.CheckboxExample
import com.composables.ui.demo.examples.DefaultTextFieldExample
import com.composables.ui.demo.examples.DestructiveButtonExample
import com.composables.ui.demo.examples.DisabledTextFieldExample
import com.composables.ui.demo.examples.DisclosureExample
import com.composables.ui.demo.examples.DropdownMenuExample
import com.composables.ui.demo.examples.DropdownMenuToolbarExample
import com.composables.ui.demo.examples.GhostButtonExample
import com.composables.ui.demo.examples.HorizontalScrollbarExample
import com.composables.ui.demo.examples.LargeToolbarExample
import com.composables.ui.demo.examples.MultilineTextFieldExample
import com.composables.ui.demo.examples.OutlinedButtonExample
import com.composables.ui.demo.examples.PrimaryButtonExample
import com.composables.ui.demo.examples.ProgressIndicatorExample
import com.composables.ui.demo.examples.RadioGroupExample
import com.composables.ui.demo.examples.ReadOnlyTextFieldExample
import com.composables.ui.demo.examples.ScrollbarExample
import com.composables.ui.demo.examples.SearchTextFieldExample
import com.composables.ui.demo.examples.SecondaryButtonExample
import com.composables.ui.demo.examples.SliderExample
import com.composables.ui.demo.examples.TabGroupExample
import com.composables.ui.demo.examples.ToggleSwitchExample
import com.composables.ui.demo.examples.ToolbarWithActionsExample
import com.composables.ui.demo.examples.TooltipExample
import com.composables.ui.demo.examples.TriStateCheckboxExample
import com.composables.ui.theme.AppTheme
import com.composables.ui.theme.ColorScheme
import com.composables.ui.theme.InteractionMode
import com.composables.ui.theme.LocalColorScheme
import com.composables.ui.theme.LocalInteractionMode
import com.composables.ui.theme.border
import com.composables.ui.theme.colors
import com.composables.ui.theme.componentSizes
import com.composables.ui.theme.control
import com.composables.ui.theme.focusRing
import com.composables.ui.theme.focusRingOffset
import com.composables.ui.theme.focusRingWidth
import com.composables.ui.theme.muted
import com.composables.ui.theme.onPanel
import com.composables.ui.theme.onSelectedControl
import com.composables.ui.theme.panel
import com.composables.ui.theme.selectedControl
import com.composeunstyled.LocalTextStyle
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.RadioButton
import com.composeunstyled.RadioGroupScope
import com.composeunstyled.UnstyledRadioGroup
import com.composeunstyled.buildModifier
import com.composeunstyled.theme.Theme

private data class DemoItem(
    val name: String,
    val id: String,
    val content: @Composable () -> Unit,
    val listName: String = name,
    val previewOptions: PreviewOptions = PreviewOptions(),
)

private data class DemoGroup(
    val name: String,
    val id: String,
    val demos: List<DemoItem>,
)

private data class PreviewOptions(
    val contentAlignment: Alignment = Alignment.Center,
    val padding: PaddingValues = PaddingValues(top = 60.dp),
    val maxWidth: Dp? = null,
)

private val componentDemoGroups = listOf(
    DemoGroup(
        name = "AlertDialog",
        id = "alert-dialog",
        demos = listOf(
            DemoItem("AlertDialog", "alert-dialog", content = { AlertDialogExample() }, listName = "Default"),
            DemoItem(
                "AlertDialog (3 Actions)",
                "alert-dialog-3-actions",
                content = { AlertDialogThreeActionsExample() },
                listName = "3 Actions",
            ),
            DemoItem(
                "AlertDialog (Icon)",
                "alert-dialog-icon",
                content = { AlertDialogWithIconExample() },
                listName = "Icon"
            ),
        ),
    ),
    DemoGroup(
        name = "BottomSheet",
        id = "bottom-sheet",
        demos = listOf(
            DemoItem(
                "BottomSheet (Action menu)",
                "bottom-sheet-action-menu",
                content = { BottomSheetActionMenuExample() },
                listName = "Action menu",
            ),
            DemoItem(
                "BottomSheet (Confirmation)",
                "bottom-sheet-confirmation",
                content = { BottomSheetConfirmationExample() },
                listName = "Confirmation",
            ),
            DemoItem(
                "BottomSheet (Form)",
                "bottom-sheet-form",
                content = { BottomSheetFormExample() },
                listName = "Form",
            ),
        ),
    ),
    DemoGroup(
        name = "Button",
        id = "button",
        demos = listOf(
            DemoItem("Button", "button-default", content = { ButtonExample() }, listName = "Default"),
            DemoItem("Button (Size)", "button-sizes", content = { ButtonSizesExample() }, listName = "Size"),
            DemoItem("Button (Primary)", "button-primary", content = { PrimaryButtonExample() }, listName = "Primary"),
            DemoItem(
                "Button (Secondary)",
                "button-secondary",
                content = { SecondaryButtonExample() },
                listName = "Secondary"
            ),
            DemoItem(
                "Button (Outlined)",
                "button-outlined",
                content = { OutlinedButtonExample() },
                listName = "Outlined"
            ),
            DemoItem("Button (Ghost)", "button-ghost", content = { GhostButtonExample() }, listName = "Ghost"),
            DemoItem(
                "Button (Destructive)",
                "button-destructive",
                content = { DestructiveButtonExample() },
                listName = "Destructive"
            ),
        ),
    ),
    DemoGroup(
        name = "Checkbox",
        id = "checkbox",
        demos = listOf(
            DemoItem("Checkbox", "checkbox", content = { CheckboxExample() }, listName = "Default"),
            DemoItem(
                "Checkbox (Tri-state)",
                "checkbox-tri-state",
                content = { TriStateCheckboxExample() },
                listName = "Tri-state",
            ),
        ),
    ),
    DemoGroup(
        name = "Disclosure",
        id = "disclosure",
        demos = listOf(
            DemoItem(
                name = "Disclosure",
                id = "disclosure",
                content = { DisclosureExample() }, listName = "Default",
                previewOptions = PreviewOptions(
                    contentAlignment = Alignment.TopCenter,
                )
            )
        ),
    ),
    DemoGroup(
        name = "DropdownMenu",
        id = "dropdown-menu",
        demos = listOf(
            DemoItem(
                "DropdownMenu (Single selection)",
                "dropdown-menu",
                content = { DropdownMenuExample() },
                listName = "Single selection",
                previewOptions = PreviewOptions(maxWidth = 390.dp),
            ),
            DemoItem(
                "DropdownMenu (Overflow menu)",
                "dropdown-menu-toolbar",
                content = { DropdownMenuToolbarExample() },
                listName = "Overflow menu",
                previewOptions = PreviewOptions(maxWidth = 390.dp),
            ),
        ),
    ),
    DemoGroup(
        name = "ProgressIndicator",
        id = "progress-indicator",
        demos = listOf(
            DemoItem(
                "ProgressIndicator",
                "progress-indicator",
                content = { ProgressIndicatorExample() },
                listName = "Default",
                previewOptions = PreviewOptions(maxWidth = 360.dp),
            ),
        ),
    ),
    DemoGroup(
        name = "RadioGroup",
        id = "radio-group",
        demos = listOf(
            DemoItem("RadioGroup", "radio-group", content = { RadioGroupExample() }, listName = "Default"),
        ),
    ),
    DemoGroup(
        name = "Scrollbars",
        id = "scrollbars",
        demos = listOf(
            DemoItem(
                "Scrollbars (Vertical)",
                "scrollbars-vertical",
                content = { ScrollbarExample() },
                listName = "Vertical",
                previewOptions = PreviewOptions(maxWidth = 380.dp),
            ),
            DemoItem(
                "Scrollbars (Horizontal)",
                "scrollbars-horizontal",
                content = { HorizontalScrollbarExample() },
                listName = "Horizontal",
                previewOptions = PreviewOptions(maxWidth = 380.dp),
            ),
        ),
    ),
    DemoGroup(
        name = "Slider",
        id = "slider",
        demos = listOf(
            DemoItem(
                "Slider",
                "slider",
                content = { SliderExample() },
                listName = "Default",
                previewOptions = PreviewOptions(maxWidth = 360.dp),
            ),
        ),
    ),
    DemoGroup(
        name = "TabGroup",
        id = "tab-group",
        demos = listOf(
            DemoItem(
                "TabGroup",
                "tab-group",
                content = { TabGroupExample() },
                listName = "Default",
                previewOptions = PreviewOptions(maxWidth = 390.dp),
            ),
        ),
    ),
    DemoGroup(
        name = "TextField",
        id = "text-field",
        demos = listOf(
            DemoItem(
                "TextField",
                "text-field",
                content = { DefaultTextFieldExample() },
                listName = "Default",
                previewOptions = PreviewOptions(maxWidth = 360.dp),
            ),
            DemoItem(
                "TextField (Search)",
                "text-field-search",
                content = { SearchTextFieldExample() },
                listName = "Search",
                previewOptions = PreviewOptions(maxWidth = 360.dp),
            ),
            DemoItem(
                "TextField (Multiline)",
                "text-field-multiline",
                content = { MultilineTextFieldExample() },
                listName = "Multiline",
                previewOptions = PreviewOptions(maxWidth = 360.dp),
            ),
            DemoItem(
                "TextField (Disabled)",
                "text-field-disabled",
                content = { DisabledTextFieldExample() },
                listName = "Disabled",
                previewOptions = PreviewOptions(maxWidth = 360.dp),
            ),
            DemoItem(
                "TextField (Read-only)",
                "text-field-read-only",
                content = { ReadOnlyTextFieldExample() },
                listName = "Read-only",
                previewOptions = PreviewOptions(maxWidth = 360.dp),
            ),
        ),
    ),
    DemoGroup(
        name = "ToggleSwitch",
        id = "toggle-switch",
        demos = listOf(
            DemoItem("ToggleSwitch", "toggle-switch", content = { ToggleSwitchExample() }, listName = "Default"),
        ),
    ),
    DemoGroup(
        name = "Toolbar",
        id = "toolbar",
        demos = listOf(
            DemoItem(
                name = "Toolbar (Actions)",
                id = "toolbar-actions",
                content = { ToolbarWithActionsExample() },
                listName = "Actions",
                previewOptions = PreviewOptions(
                    contentAlignment = Alignment.TopCenter,
                    padding = PaddingValues(vertical = 24.dp),
                ),
            ),
            DemoItem(
                name = "Toolbar (Centered)",
                id = "centered-toolbar",
                content = { CenteredToolbarExample() },
                listName = "Centered",
                previewOptions = PreviewOptions(
                    contentAlignment = Alignment.TopCenter,
                    padding = PaddingValues(vertical = 24.dp),
                ),
            ),
            DemoItem(
                name = "Toolbar (Large)",
                id = "large-toolbar",
                content = { LargeToolbarExample() },
                listName = "Large",
                previewOptions = PreviewOptions(
                    contentAlignment = Alignment.TopCenter,
                    padding = PaddingValues(vertical = 24.dp),
                ),
            ),
        ),
    ),
    DemoGroup(
        name = "Tooltip",
        id = "tooltip",
        demos = listOf(
            DemoItem("Tooltip", "tooltip", content = { TooltipExample() }, listName = "Default"),
        ),
    ),
)

private val componentDemos = componentDemoGroups.flatMap { it.demos }

private val demos = componentDemos

private const val NavigationTransitionDurationMillis = 350
private const val NavigationParallaxDivisor = 5
private const val NavigationDimmedAlpha = 0.86f
private val NavigationTransitionEasing = CubicBezierEasing(0.32f, 0.72f, 0f, 1f)
private val DemoListItemShape = RoundedCornerShape(12.dp)

@Composable
fun Demo(initialDemoId: String? = null) {
    val navController = rememberNavController()
    val demoListScrollState = rememberScrollState()
    val initialDemo = demos.firstOrNull { it.id == initialDemoId }
    val previewSpecificDemo = initialDemo != null
    val startDestination = initialDemo?.id ?: "home"
    val initialInteractionMode = LocalInteractionMode.current
    var interactionMode by remember { mutableStateOf(initialInteractionMode) }
    var colorScheme by remember { mutableStateOf(ColorScheme.Light) }

    AppScaffold {
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            enterTransition = {
                slideInHorizontally(
                    animationSpec = tween(
                        durationMillis = NavigationTransitionDurationMillis,
                        easing = NavigationTransitionEasing,
                    ),
                    initialOffsetX = { it },
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    animationSpec = tween(
                        durationMillis = NavigationTransitionDurationMillis,
                        easing = NavigationTransitionEasing,
                    ),
                    targetOffsetX = { -it / NavigationParallaxDivisor },
                ) + fadeOut(
                    animationSpec = tween(
                        durationMillis = NavigationTransitionDurationMillis,
                        easing = NavigationTransitionEasing,
                    ),
                    targetAlpha = NavigationDimmedAlpha,
                )
            },
            popEnterTransition = {
                slideInHorizontally(
                    animationSpec = tween(
                        durationMillis = NavigationTransitionDurationMillis,
                        easing = NavigationTransitionEasing,
                    ),
                    initialOffsetX = { -it / NavigationParallaxDivisor },
                ) + fadeIn(
                    animationSpec = tween(
                        durationMillis = NavigationTransitionDurationMillis,
                        easing = NavigationTransitionEasing,
                    ),
                    initialAlpha = NavigationDimmedAlpha,
                )
            },
            popExitTransition = {
                slideOutHorizontally(
                    animationSpec = tween(
                        durationMillis = NavigationTransitionDurationMillis,
                        easing = NavigationTransitionEasing,
                    ),
                    targetOffsetX = { it },
                )
            },
        ) {
            composable("home") {
                ScreenScaffold {
                    Column(Modifier.fillMaxSize()) {
                        Toolbar(
                            title = { Text("Composables UI") },
                            size = ToolbarSize.Large,
                        )
                        DemoList(
                            onSelectDemo = { navController.navigate(it.id) },
                            scrollState = demoListScrollState,
                            modifier = Modifier.weight(1f),
                        )
                    }
                }
            }

            demos.forEach { demo ->
                composable(demo.id) {
                    DemoRoute(
                        demo = demo,
                        onBack = { navController.navigateUp() },
                        showNavigation = !previewSpecificDemo,
                        interactionMode = interactionMode,
                        onInteractionModeChange = { interactionMode = it },
                        colorScheme = colorScheme,
                        onColorSchemeChange = { colorScheme = it },
                    )
                }
            }
        }
    }
}

@Composable
private fun DemoList(
    onSelectDemo: (DemoItem) -> Unit,
    scrollState: ScrollState,
    modifier: Modifier = Modifier,
) {
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.TopStart) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(horizontal = 8.dp, vertical = 12.dp)
                .fillMaxWidth(),
        ) {
            DemoSection(componentDemoGroups, onSelectDemo)
        }
    }
}

@Composable
private fun DemoRoute(
    demo: DemoItem,
    onBack: () -> Unit,
    showNavigation: Boolean,
    interactionMode: InteractionMode,
    onInteractionModeChange: (InteractionMode) -> Unit,
    colorScheme: ColorScheme,
    onColorSchemeChange: (ColorScheme) -> Unit,
) {
    val initialInteractionMode = LocalInteractionMode.current
    val fixedInteractionMode = remember { initialInteractionMode }

    CompositionLocalProvider(
        LocalColorScheme provides colorScheme,
        LocalInteractionMode provides fixedInteractionMode
    ) {
        AppTheme {
            ScreenScaffold(backgroundColor = Theme[colors][panel], contentColor = Theme[colors][onPanel]) {
                Box(Modifier.fillMaxSize()) {
                    CompositionLocalProvider(LocalInteractionMode provides interactionMode) {
                        AppTheme {
                            ProvideContentColor(Theme[colors][onPanel]) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Theme[colors][panel])
                                        .padding(demo.previewOptions.padding),
                                    contentAlignment = demo.previewOptions.contentAlignment,
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .widthIn(max = demo.previewOptions.maxWidth ?: Dp.Infinity)
                                            .fillMaxWidth(),
                                        contentAlignment = demo.previewOptions.contentAlignment,
                                    ) {
                                        demo.content()
                                    }
                                }
                            }
                        }
                    }
                    Toolbar(
                        backgroundColor = Color.Transparent,
                        leading = if (showNavigation) {
                            {
                                DemoTitlePill(
                                    title = demo.name,
                                    onBack = onBack,
                                )
                            }
                        } else {
                            null
                        },
                        trailing = {
                            DemoToolbarActions(
                                interactionMode = interactionMode,
                                onInteractionModeChange = onInteractionModeChange,
                                colorScheme = colorScheme,
                                onColorSchemeChange = onColorSchemeChange,
                            )
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun DemoTitlePill(
    title: String,
    onBack: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            onClick = onBack,
            style = ButtonStyle.Ghost,
        ) {
            Icon(Lucide.ArrowLeft, contentDescription = "Go back")
        }
        Text(title)
    }
}

@Composable
private fun DemoToolbarActions(
    interactionMode: InteractionMode,
    onInteractionModeChange: (InteractionMode) -> Unit,
    colorScheme: ColorScheme,
    onColorSchemeChange: (ColorScheme) -> Unit,
) {
    InteractionModeAction(
        interactionMode = interactionMode,
        onInteractionModeChange = onInteractionModeChange,
    )
    ColorSchemeAction(
        colorScheme = colorScheme,
        onColorSchemeChange = onColorSchemeChange,
    )
}

@Composable
private fun ColorSchemeAction(
    colorScheme: ColorScheme,
    onColorSchemeChange: (ColorScheme) -> Unit,
) {
    SegmentedRadioGroup(
        value = colorScheme,
        onValueChange = onColorSchemeChange,
        accessibilityLabel = "Color scheme",
    ) {
        SegmentedRadioOption(
            value = ColorScheme.Light,
            selected = colorScheme == ColorScheme.Light,
            minWidth = 32.dp,
        ) { selected ->
            Icon(
                imageVector = Lucide.Sun,
                modifier = Modifier.size(18.dp),
                tint = segmentedRadioContentColor(selected),
                contentDescription = "Light color scheme",
            )
        }
        SegmentedRadioOption(
            value = ColorScheme.Dark,
            selected = colorScheme == ColorScheme.Dark,
            minWidth = 32.dp,
        ) { selected ->
            Icon(
                imageVector = Lucide.Moon,
                modifier = Modifier.size(18.dp),
                tint = segmentedRadioContentColor(selected),
                contentDescription = "Dark color scheme",
            )
        }
    }
}

@Composable
private fun InteractionModeAction(
    interactionMode: InteractionMode,
    onInteractionModeChange: (InteractionMode) -> Unit,
) {
    SegmentedRadioGroup(
        value = interactionMode,
        onValueChange = onInteractionModeChange,
        accessibilityLabel = "Interaction mode",
    ) {
        SegmentedRadioOption(
            value = InteractionMode.Touch,
            selected = interactionMode == InteractionMode.Touch,
            minWidth = 56.dp,
        ) { selected ->
            Text(
                text = "Touch",
                color = segmentedRadioContentColor(selected),
                singleLine = true,
            )
        }
        SegmentedRadioOption(
            value = InteractionMode.Pointer,
            selected = interactionMode == InteractionMode.Pointer,
            minWidth = 56.dp,
        ) { selected ->
            Text(
                text = "Mouse",
                color = segmentedRadioContentColor(selected),
                singleLine = true,
            )
        }
    }
}

@Composable
private fun <T> SegmentedRadioGroup(
    value: T,
    onValueChange: (T) -> Unit,
    accessibilityLabel: String,
    content: @Composable RadioGroupScope.() -> Unit,
) {
    val shape = RoundedCornerShape(999.dp)
    val borderColor = Theme[colors][border]
    UnstyledRadioGroup(
        value = value,
        onValueChange = onValueChange,
        accessibilityLabel = accessibilityLabel,
        modifier = Modifier
            .clip(shape)
            .background(Theme[colors][control], shape)
            .border(1.dp, borderColor, shape)
            .padding(4.dp),
    ) {
        val radioGroupScope = this
        Row(
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            content(radioGroupScope)
        }
    }
}

@Composable
private fun <T> RadioGroupScope.SegmentedRadioOption(
    value: T,
    selected: Boolean,
    minWidth: Dp,
    content: @Composable (selected: Boolean) -> Unit,
) {
    val shape = RoundedCornerShape(999.dp)
    val interactionSource = remember { MutableInteractionSource() }
    RadioButton(
        value = value,
        indication = LocalIndication.current,
        interactionSource = interactionSource,
        modifier = Modifier
            .focusRing(
                interactionSource = interactionSource,
                width = Theme[componentSizes][focusRingWidth],
                color = Theme[colors][focusRing],
                shape = shape,
                offset = Theme[componentSizes][focusRingOffset],
            )
            .clip(shape)
            .then(buildModifier {
                if (selected) {
                    add(Modifier.background(Theme[colors][selectedControl], shape))
                }
            }),
    ) {
        Box(
            modifier = Modifier
                .height(32.dp)
                .widthIn(min = minWidth)
                .padding(horizontal = 10.dp),
            contentAlignment = Alignment.Center,
        ) {
            content(selected)
        }
    }
}

@Composable
private fun segmentedRadioContentColor(selected: Boolean): Color {
    return if (selected) {
        Theme[colors][onSelectedControl]
    } else {
        Theme[colors][muted]
    }
}

@Composable
private fun DemoSection(
    groups: List<DemoGroup>,
    onClick: (DemoItem) -> Unit,
) {
    groups.forEach { group ->
        if (group.demos.size == 1) {
            val demo = group.demos.first()
            DemoListButton(
                onClick = { onClick(demo) },
                modifier = Modifier.fillMaxWidth(),
            ) {
                DemoGroupHeader(
                    name = group.name,
                    expanded = false,
                    showChevron = false,
                )
            }
            return@forEach
        }

        val state = rememberDisclosureState()
        Disclosure(
            state = state,
            modifier = Modifier.fillMaxWidth(),
        ) {
            DisclosureHeading(
                shape = DemoListItemShape,
                contentPadding = PaddingValues(0.dp),
            ) {
                DemoGroupHeader(
                    name = group.name,
                    expanded = state.expanded,
                    showChevron = true,
                )
            }
            DisclosurePanel {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    group.demos.forEach { demo ->
                        DemoListButton(
                            onClick = { onClick(demo) },
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(
                                text = demo.listName,
                                style = LocalTextStyle.current.merge(DemoListTextStyle),
                                modifier = Modifier.padding(start = DemoListTextStart),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DemoGroupHeader(
    name: String,
    expanded: Boolean,
    showChevron: Boolean,
) {
    val iconRotation by animateFloatAsState(
        targetValue = if (expanded) 90f else 0f,
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = DemoListHorizontalPadding, end = DemoListHorizontalPadding),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = name,
            style = LocalTextStyle.current.merge(DemoListTextStyle),
        )
        if (showChevron) {
            Spacer(Modifier.weight(1f))
            Icon(
                imageVector = Lucide.ChevronRight,
                contentDescription = null,
                modifier = Modifier
                    .size(16.dp)
                    .rotate(iconRotation),
                tint = Theme[colors][muted],
            )
        }
    }
}

private val DemoListHorizontalPadding = 16.dp
private val DemoListTextStart = DemoListHorizontalPadding
private val DemoListTextStyle = TextStyle()

@Composable
private fun DemoListButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Button(
        onClick = onClick,
        style = ButtonStyle.Secondary,
        modifier = modifier,
        shape = DemoListItemShape,
        contentPadding = PaddingValues(0.dp),
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterStart,
        ) {
            content()
        }
    }
}
