package com.composables.one.demo

import androidx.compose.animation.expandVertically
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.composables.icons.lucide.ArrowLeft
import com.composables.icons.lucide.ChevronRight
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.MousePointer
import com.composables.icons.lucide.Pointer
import com.composables.one.AppScaffold
import com.composables.one.Button
import com.composables.one.ButtonStyle
import com.composables.one.Icon
import com.composables.one.IconButton
import com.composables.one.ScreenScaffold
import com.composables.one.Text
import com.composables.one.Toolbar
import com.composables.one.ToolbarSize
import com.composables.one.demo.examples.AlertDialogExample
import com.composables.one.demo.examples.AlertDialogThreeActionsExample
import com.composables.one.demo.examples.AlertDialogWithIconExample
import com.composables.one.demo.examples.BottomSheetActionMenuExample
import com.composables.one.demo.examples.BottomSheetConfirmationExample
import com.composables.one.demo.examples.BottomSheetFormExample
import com.composables.one.demo.examples.ButtonSizesExample
import com.composables.one.demo.examples.CenteredToolbarExample
import com.composables.one.demo.examples.DefaultTextFieldExample
import com.composables.one.demo.examples.DestructiveButtonExample
import com.composables.one.demo.examples.DisabledTextFieldExample
import com.composables.one.demo.examples.DropdownMenuExample
import com.composables.one.demo.examples.DropdownMenuToolbarExample
import com.composables.one.demo.examples.GhostButtonExample
import com.composables.one.demo.examples.LargeToolbarExample
import com.composables.one.demo.examples.MultilineTextFieldExample
import com.composables.one.demo.examples.OutlinedButtonExample
import com.composables.one.demo.examples.PrimaryButtonExample
import com.composables.one.demo.examples.ReadOnlyTextFieldExample
import com.composables.one.demo.examples.SearchTextFieldExample
import com.composables.one.demo.examples.SecondaryButtonExample
import com.composables.one.demo.examples.ToolbarWithActionsExample
import com.composables.one.demo.examples.TypographyExample
import com.composables.one.styling.InteractionMode
import com.composables.one.styling.LocalInteractionMode
import com.composables.one.styling.OneTheme
import com.composables.one.styling.body
import com.composables.one.styling.border
import com.composables.one.styling.colors
import com.composables.one.styling.componentSizes
import com.composables.one.styling.focusRing
import com.composables.one.styling.focusRingOffset
import com.composables.one.styling.focusRingWidth
import com.composables.one.styling.muted
import com.composables.one.styling.secondary
import com.composables.one.styling.textStyles
import com.composeunstyled.DisclosedContent
import com.composeunstyled.DisclosureButton
import com.composeunstyled.UnstyledDisclosure
import com.composeunstyled.focusRing
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
    val padding: PaddingValues = PaddingValues(0.dp),
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
            DemoItem(
                "Button (Destructive)",
                "button-destructive",
                content = { DestructiveButtonExample() },
                listName = "Destructive"
            ),
            DemoItem("Button (Ghost)", "button-ghost", content = { GhostButtonExample() }, listName = "Ghost"),
            DemoItem(
                "Button (Outlined)",
                "button-outlined",
                content = { OutlinedButtonExample() },
                listName = "Outlined"
            ),
            DemoItem("Button (Primary)", "button-primary", content = { PrimaryButtonExample() }, listName = "Primary"),
            DemoItem(
                "Button (Secondary)",
                "button-secondary",
                content = { SecondaryButtonExample() },
                listName = "Secondary"
            ),
            DemoItem("Button (Sizes)", "button-sizes", content = { ButtonSizesExample() }, listName = "Sizes"),
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
)

private val componentDemos = componentDemoGroups.flatMap { it.demos }

private val themingDemos = listOf(
    DemoItem("Typography", "typography", content = { TypographyExample() }),
)

private val themingDemoGroups = listOf(
    DemoGroup(
        name = "Typography",
        id = "typography",
        demos = listOf(
            DemoItem("Typography", "typography", content = { TypographyExample() }, listName = "Default"),
        ),
    ),
)

private val demos = componentDemos + themingDemos

private const val NavigationTransitionDurationMillis = 350
private const val NavigationParallaxDivisor = 5
private const val NavigationDimmedAlpha = 0.86f
private val NavigationTransitionEasing = CubicBezierEasing(0.32f, 0.72f, 0f, 1f)

@Composable
fun Demo() {
    val navController = rememberNavController()
    val demoListScrollState = rememberScrollState()
    val expandedDemoGroups = remember { mutableStateMapOf<String, Boolean>() }
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val currentDemo = demos.firstOrNull { it.id == currentRoute }
    var interactionMode by remember { mutableStateOf(InteractionMode.Touch) }

    CompositionLocalProvider(LocalInteractionMode provides InteractionMode.Pointer) {
        AppScaffold {
            NavHost(
                navController = navController,
                startDestination = "home",
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
                                title = { Text("Composables One") },
                                size = ToolbarSize.Large,
                            )
                            DemoList(
                                onSelectDemo = { navController.navigate(it.id) },
                                scrollState = demoListScrollState,
                                expandedGroups = expandedDemoGroups,
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
                            interactionMode = interactionMode,
                            onInteractionModeChange = { interactionMode = it },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DemoList(
    onSelectDemo: (DemoItem) -> Unit,
    scrollState: ScrollState,
    expandedGroups: MutableMap<String, Boolean>,
    modifier: Modifier = Modifier,
) {
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.TopStart) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(horizontal = 8.dp, vertical = 12.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            DemoSection("Theming", themingDemoGroups, expandedGroups, onSelectDemo)
            Spacer(Modifier.height(8.dp))
            DemoSection("Components", componentDemoGroups, expandedGroups, onSelectDemo)
        }
    }
}

@Composable
private fun DemoRoute(
    demo: DemoItem,
    onBack: () -> Unit,
    interactionMode: InteractionMode,
    onInteractionModeChange: (InteractionMode) -> Unit,
) {
    ScreenScaffold(backgroundColor = Theme[colors][secondary]) {
        Column(Modifier.fillMaxSize()) {
            Toolbar(
                leading = {
                    IconButton(
                        onClick = onBack,
                        style = ButtonStyle.Ghost,
                    ) {
                        Icon(Lucide.ArrowLeft, contentDescription = "Go back")
                    }
                    Text(demo.name)
                },
                trailing = {
                    InteractionModeAction(
                        interactionMode = interactionMode,
                        onInteractionModeChange = onInteractionModeChange,
                    )
                },
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .background(Color.White)
                    .border(
                        width = 1.dp,
                        color = Theme[colors][border],
                        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                    )
                    .padding(demo.previewOptions.padding),
                contentAlignment = demo.previewOptions.contentAlignment,
            ) {
                Box(
                    modifier = Modifier
                        .widthIn(max = demo.previewOptions.maxWidth ?: Dp.Infinity)
                        .fillMaxWidth(),
                    contentAlignment = demo.previewOptions.contentAlignment,
                ) {
                    CompositionLocalProvider(LocalInteractionMode provides interactionMode) {
                        OneTheme {
                            demo.content()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun InteractionModeAction(
    interactionMode: InteractionMode,
    onInteractionModeChange: (InteractionMode) -> Unit,
) {
    IconButton(
        onClick = {
            onInteractionModeChange(
                if (interactionMode == InteractionMode.Touch) {
                    InteractionMode.Pointer
                } else {
                    InteractionMode.Touch
                },
            )
        },
        style = ButtonStyle.Secondary,
    ) {
        Icon(
            imageVector = if (interactionMode == InteractionMode.Touch) {
                Lucide.Pointer
            } else {
                Lucide.MousePointer
            },
            contentDescription = if (interactionMode == InteractionMode.Touch) {
                "Touch preview mode"
            } else {
                "Pointer preview mode"
            },
        )
    }
}

@Composable
private fun DemoSection(
    title: String,
    groups: List<DemoGroup>,
    expandedGroups: MutableMap<String, Boolean>,
    onClick: (DemoItem) -> Unit,
) {
    Text(
        text = title,
        modifier = Modifier.padding(horizontal = 16.dp),
        style = TextStyle(fontWeight = FontWeight.SemiBold),
    )
    groups.forEach { group ->
        if (group.demos.size == 1) {
            val demo = group.demos.first()
            DemoListButton(
                onClick = { onClick(demo) },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = group.name,
                    style = Theme[textStyles][body],
                )
            }
            return@forEach
        }

        val expanded = expandedGroups[group.id] ?: false
        val interactionSource = remember { MutableInteractionSource() }
        UnstyledDisclosure(
            expanded = expanded,
            onExpandedChange = { expandedGroups[group.id] = it },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(Modifier.fillMaxWidth()) {
                DisclosureButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRing(
                            interactionSource = interactionSource,
                            width = Theme[componentSizes][focusRingWidth],
                            color = Theme[colors][focusRing],
                            shape = RoundedCornerShape(8.dp),
                            offset = Theme[componentSizes][focusRingOffset],
                        ),
                    interactionSource = interactionSource,
                    contentAlignment = Alignment.CenterStart,
                ) {
                    DemoGroupHeader(
                        name = group.name,
                        expanded = expanded,
                    )
                }
                DisclosedContent(
                    enter = expandVertically(
                        animationSpec = spring(
                            stiffness = Spring.StiffnessMediumLow,
                        ),
                    ),
                    exit = shrinkVertically(),
                ) {
                    Column(
                        modifier = Modifier
                            .padding(start = 24.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        group.demos.forEach { demo ->
                            DemoListButton(
                                onClick = { onClick(demo) },
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Text(
                                    text = demo.listName,
                                    style = Theme[textStyles][body],
                                )
                            }
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
) {
    val iconRotation by animateFloatAsState(
        targetValue = if (expanded) 90f else 0f,
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Lucide.ChevronRight,
            contentDescription = null,
            modifier = Modifier
                .size(16.dp)
                .rotate(iconRotation),
            tint = Theme[colors][muted],
        )
        Text(
            text = name,
            style = Theme[textStyles][body],
        )
    }
}

@Composable
private fun DemoListButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Button(
        onClick = onClick,
        style = ButtonStyle.Ghost,
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterStart,
        ) {
            content()
        }
    }
}
