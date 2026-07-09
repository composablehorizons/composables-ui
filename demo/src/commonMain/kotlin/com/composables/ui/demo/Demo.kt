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
package com.composables.ui.demo

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.scene.Scene
import androidx.navigation3.ui.NavDisplay
import com.composables.icons.lucide.ArrowLeft
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Moon
import com.composables.icons.lucide.Sun
import com.composables.ui.components.Button
import com.composables.ui.components.ButtonStyle
import com.composables.ui.components.Icon
import com.composables.ui.components.IconButton
import com.composables.ui.components.Text
import com.composables.ui.components.Toolbar
import com.composables.ui.components.ToolbarSize
import com.composables.ui.components.focusRing
import com.composables.ui.demo.examples.AlertDialogExample
import com.composables.ui.demo.examples.AlertDialogThreeActionsExample
import com.composables.ui.demo.examples.AlertDialogWithIconExample
import com.composables.ui.demo.examples.AlwaysVisibleScrollbarExample
import com.composables.ui.demo.examples.BottomSheetActionMenuExample
import com.composables.ui.demo.examples.BottomSheetConfirmationExample
import com.composables.ui.demo.examples.BottomSheetFormExample
import com.composables.ui.demo.examples.ButtonExample
import com.composables.ui.demo.examples.ButtonSizesExample
import com.composables.ui.demo.examples.CenteredToolbarExample
import com.composables.ui.demo.examples.CheckboxExample
import com.composables.ui.demo.examples.CustomInsetsNavigationBarExample
import com.composables.ui.demo.examples.DefaultTextFieldExample
import com.composables.ui.demo.examples.DestructiveButtonExample
import com.composables.ui.demo.examples.DisabledButtonExample
import com.composables.ui.demo.examples.DisabledCheckboxExample
import com.composables.ui.demo.examples.DisabledDisclosureExample
import com.composables.ui.demo.examples.DisabledHorizontalScrollbarExample
import com.composables.ui.demo.examples.DisabledNavigationBarExample
import com.composables.ui.demo.examples.DisabledRadioGroupExample
import com.composables.ui.demo.examples.DisabledScrollbarExample
import com.composables.ui.demo.examples.DisabledSliderExample
import com.composables.ui.demo.examples.DisabledSwitchExample
import com.composables.ui.demo.examples.DisabledTabsExample
import com.composables.ui.demo.examples.DisabledTextFieldExample
import com.composables.ui.demo.examples.DisabledTriStateCheckboxExample
import com.composables.ui.demo.examples.DisclosureExample
import com.composables.ui.demo.examples.DisclosureWithIndicatorExample
import com.composables.ui.demo.examples.DropdownMenuExample
import com.composables.ui.demo.examples.DropdownMenuToolbarExample
import com.composables.ui.demo.examples.GhostButtonExample
import com.composables.ui.demo.examples.GhostTextFieldExample
import com.composables.ui.demo.examples.HorizontalScrollbarExample
import com.composables.ui.demo.examples.HorizontalSeparatorExample
import com.composables.ui.demo.examples.IconExample
import com.composables.ui.demo.examples.IndeterminateProgressIndicatorExample
import com.composables.ui.demo.examples.LargeToolbarExample
import com.composables.ui.demo.examples.LazyColumnScrollbarExample
import com.composables.ui.demo.examples.MultilineTextFieldExample
import com.composables.ui.demo.examples.NavigationBarExample
import com.composables.ui.demo.examples.OutlinedButtonExample
import com.composables.ui.demo.examples.PrimaryButtonExample
import com.composables.ui.demo.examples.ProgressIndicatorExample
import com.composables.ui.demo.examples.RadioGroupExample
import com.composables.ui.demo.examples.ReadOnlyTextFieldExample
import com.composables.ui.demo.examples.ScrollbarExample
import com.composables.ui.demo.examples.SearchTextFieldExample
import com.composables.ui.demo.examples.SecondaryButtonExample
import com.composables.ui.demo.examples.SliderExample
import com.composables.ui.demo.examples.SteppedSliderExample
import com.composables.ui.demo.examples.StyledTextExample
import com.composables.ui.demo.examples.SwitchExample
import com.composables.ui.demo.examples.TabsExample
import com.composables.ui.demo.examples.TabsWithIconsExample
import com.composables.ui.demo.examples.TextExample
import com.composables.ui.demo.examples.ThemedIconExample
import com.composables.ui.demo.examples.ThemedTextExample
import com.composables.ui.demo.examples.TintedIconExample
import com.composables.ui.demo.examples.ToolbarWithActionsExample
import com.composables.ui.demo.examples.TooltipAlignmentExample
import com.composables.ui.demo.examples.TooltipExample
import com.composables.ui.demo.examples.TooltipHoverDelayExample
import com.composables.ui.demo.examples.TooltipLongPressDurationExample
import com.composables.ui.demo.examples.TooltipSideExample
import com.composables.ui.demo.examples.TriStateCheckboxExample
import com.composables.ui.demo.examples.VerticalSeparatorExample
import com.composables.ui.demo.examples.VerticalSliderExample
import com.composables.ui.demo.examples.VerticalTabsExample
import com.composables.ui.demo.examples.WeightedTabsExample
import com.composables.ui.theme.ColorScheme
import com.composables.ui.theme.ComposablesTheme
import com.composables.ui.theme.InteractionMode
import com.composables.ui.theme.LocalColorScheme
import com.composables.ui.theme.LocalInteractionMode
import com.composables.ui.theme.backgroundColor
import com.composables.ui.theme.borderColor
import com.composables.ui.theme.colors
import com.composables.ui.theme.mutedColor
import com.composables.ui.theme.onBackgroundColor
import com.composables.ui.theme.onPanelColor
import com.composables.ui.theme.panelColor
import com.composables.ui.theme.secondaryColor
import com.composeunstyled.FocusVisibilityProvider
import com.composeunstyled.LocalTextStyle
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.TooltipHost
import com.composeunstyled.UnstyledRadioButton
import com.composeunstyled.UnstyledRadioGroup
import com.composeunstyled.buildModifier
import com.composeunstyled.theme.Theme
import kotlinx.serialization.Serializable

private data class DemoItem(
    val name: String,
    val id: String,
    val content: @Composable () -> Unit,
    val variantName: String = name,
    val previewOptions: PreviewOptions = PreviewOptions(),
)

private data class DemoGroup(
    val name: String,
    val id: String,
    val demos: List<DemoItem>,
)

private data class PreviewOptions(
    val contentAlignment: Alignment = Alignment.Center,
    val padding: PaddingValues = PaddingValues(),
    val maxWidth: Dp? = null,
)

@Serializable
private sealed interface DemoRoute : NavKey {
  @Serializable data object Home : DemoRoute

  @Serializable data class ComponentGroup(val id: String) : DemoRoute

  @Serializable data class SingleDemoPreview(val id: String) : DemoRoute
}

private val componentDemoGroups =
    listOf(
            DemoGroup(
                name = "AlertDialog",
                id = "alert-dialog",
                demos =
                    listOf(
                        DemoItem(
                            "AlertDialog",
                            "alert-dialog",
                            content = { AlertDialogExample() },
                            variantName = "Default"),
                        DemoItem(
                            "AlertDialog (3 Actions)",
                            "alert-dialog-3-actions",
                            content = { AlertDialogThreeActionsExample() },
                            variantName = "3 Actions",
                        ),
                        DemoItem(
                            "AlertDialog (Icon)",
                            "alert-dialog-icon",
                            content = { AlertDialogWithIconExample() },
                            variantName = "Icon",
                        ),
                    ),
            ),
            DemoGroup(
                name = "BottomSheet",
                id = "bottom-sheet",
                demos =
                    listOf(
                        DemoItem(
                            "BottomSheet (Action menu)",
                            "bottom-sheet-action-menu",
                            content = { BottomSheetActionMenuExample() },
                            variantName = "Action menu",
                        ),
                        DemoItem(
                            "BottomSheet (Confirmation)",
                            "bottom-sheet-confirmation",
                            content = { BottomSheetConfirmationExample() },
                            variantName = "Confirmation",
                        ),
                        DemoItem(
                            "BottomSheet (Form)",
                            "bottom-sheet-form",
                            content = { BottomSheetFormExample() },
                            variantName = "Form",
                        ),
                    ),
            ),
            DemoGroup(
                name = "Button",
                id = "button",
                demos =
                    listOf(
                        DemoItem(
                            "Button",
                            "button-default",
                            content = { ButtonExample() },
                            variantName = "Default"),
                        DemoItem(
                            "Button (Disabled)",
                            "button-disabled",
                            content = { DisabledButtonExample() },
                            variantName = "Disabled",
                        ),
                        DemoItem(
                            "Button (Size)",
                            "button-sizes",
                            content = { ButtonSizesExample() },
                            variantName = "Size"),
                        DemoItem(
                            "Button (Primary)",
                            "button-primary",
                            content = { PrimaryButtonExample() },
                            variantName = "Primary",
                        ),
                        DemoItem(
                            "Button (Secondary)",
                            "button-secondary",
                            content = { SecondaryButtonExample() },
                            variantName = "Secondary",
                        ),
                        DemoItem(
                            "Button (Outlined)",
                            "button-outlined",
                            content = { OutlinedButtonExample() },
                            variantName = "Outlined",
                        ),
                        DemoItem(
                            "Button (Ghost)",
                            "button-ghost",
                            content = { GhostButtonExample() },
                            variantName = "Ghost"),
                        DemoItem(
                            "Button (Destructive)",
                            "button-destructive",
                            content = { DestructiveButtonExample() },
                            variantName = "Destructive",
                        ),
                    ),
            ),
            DemoGroup(
                name = "Checkbox",
                id = "checkbox",
                demos =
                    listOf(
                        DemoItem(
                            "Checkbox",
                            "checkbox",
                            content = { CheckboxExample() },
                            variantName = "Default"),
                        DemoItem(
                            "Checkbox (Disabled)",
                            "checkbox-disabled",
                            content = { DisabledCheckboxExample() },
                            variantName = "Disabled",
                        ),
                    ),
            ),
            DemoGroup(
                name = "TriStateCheckbox",
                id = "tri-state-checkbox",
                demos =
                    listOf(
                        DemoItem(
                            "TriStateCheckbox",
                            "checkbox-tri-state",
                            content = { TriStateCheckboxExample() },
                            variantName = "Default",
                        ),
                        DemoItem(
                            "TriStateCheckbox (Disabled)",
                            "checkbox-tri-state-disabled",
                            content = { DisabledTriStateCheckboxExample() },
                            variantName = "Disabled",
                        ),
                    ),
            ),
            DemoGroup(
                name = "Disclosure",
                id = "disclosure",
                demos =
                    listOf(
                        DemoItem(
                            name = "Disclosure",
                            id = "disclosure",
                            content = { DisclosureExample() },
                            variantName = "Default",
                            previewOptions =
                                PreviewOptions(
                                    contentAlignment = Alignment.TopCenter,
                                    maxWidth = 360.dp,
                                    padding = PaddingValues(top = 90.dp),
                                ),
                        ),
                        DemoItem(
                            name = "Disclosure (Indicator)",
                            id = "disclosure-indicator",
                            content = { DisclosureWithIndicatorExample() },
                            variantName = "Indicator",
                            previewOptions =
                                PreviewOptions(
                                    contentAlignment = Alignment.TopCenter,
                                    maxWidth = 360.dp,
                                    padding = PaddingValues(top = 90.dp),
                                ),
                        ),
                        DemoItem(
                            name = "Disclosure (Disabled)",
                            id = "disclosure-disabled",
                            content = { DisabledDisclosureExample() },
                            variantName = "Disabled",
                            previewOptions =
                                PreviewOptions(
                                    contentAlignment = Alignment.TopCenter,
                                    maxWidth = 360.dp,
                                    padding = PaddingValues(top = 90.dp),
                                ),
                        ),
                    ),
            ),
            DemoGroup(
                name = "DropdownMenu",
                id = "dropdown-menu",
                demos =
                    listOf(
                        DemoItem(
                            "DropdownMenu (Single selection)",
                            "dropdown-menu",
                            content = { DropdownMenuExample() },
                            variantName = "Single selection",
                            previewOptions = PreviewOptions(maxWidth = 390.dp),
                        ),
                        DemoItem(
                            "DropdownMenu (Overflow menu)",
                            "dropdown-menu-toolbar",
                            content = { DropdownMenuToolbarExample() },
                            variantName = "Overflow menu",
                            previewOptions = PreviewOptions(maxWidth = 390.dp),
                        ),
                    ),
            ),
            DemoGroup(
                name = "Icon",
                id = "icon",
                demos =
                    listOf(
                        DemoItem(
                            "Icon", "icon", content = { IconExample() }, variantName = "Default"),
                        DemoItem(
                            "Icon (Tinted)",
                            "icon-tinted",
                            content = { TintedIconExample() },
                            variantName = "Tinted"),
                        DemoItem(
                            "Icon (Themed)",
                            "icon-themed",
                            content = { ThemedIconExample() },
                            variantName = "Themed"),
                    ),
            ),
            DemoGroup(
                name = "ProgressIndicator",
                id = "progress-indicator",
                demos =
                    listOf(
                        DemoItem(
                            "ProgressIndicator",
                            "progress-indicator",
                            content = { ProgressIndicatorExample() },
                            variantName = "Default",
                            previewOptions = PreviewOptions(maxWidth = 360.dp),
                        ),
                        DemoItem(
                            "ProgressIndicator (Indeterminate)",
                            "progress-indicator-indeterminate",
                            content = { IndeterminateProgressIndicatorExample() },
                            variantName = "Indeterminate",
                            previewOptions = PreviewOptions(maxWidth = 360.dp),
                        ),
                    ),
            ),
            DemoGroup(
                name = "RadioGroup",
                id = "radio-group",
                demos =
                    listOf(
                        DemoItem(
                            "RadioGroup",
                            "radio-group",
                            content = { RadioGroupExample() },
                            variantName = "Default"),
                        DemoItem(
                            "RadioGroup (Disabled)",
                            "radio-group-disabled",
                            content = { DisabledRadioGroupExample() },
                            variantName = "Disabled",
                        ),
                    ),
            ),
            DemoGroup(
                name = "Scrollbars",
                id = "scrollbars",
                demos =
                    listOf(
                        DemoItem(
                            "Scrollbars (Vertical)",
                            "scrollbars-vertical",
                            content = { ScrollbarExample() },
                            variantName = "Vertical",
                            previewOptions =
                                PreviewOptions(maxWidth = 380.dp, padding = PaddingValues(24.dp)),
                        ),
                        DemoItem(
                            "Scrollbars (Horizontal)",
                            "scrollbars-horizontal",
                            content = { HorizontalScrollbarExample() },
                            variantName = "Horizontal",
                            previewOptions =
                                PreviewOptions(maxWidth = 380.dp, padding = PaddingValues(24.dp)),
                        ),
                        DemoItem(
                            "Scrollbars (LazyColumn)",
                            "scrollbars-lazy-column",
                            content = { LazyColumnScrollbarExample() },
                            variantName = "LazyColumn",
                            previewOptions =
                                PreviewOptions(maxWidth = 380.dp, padding = PaddingValues(24.dp)),
                        ),
                        DemoItem(
                            "Scrollbars (Always visible)",
                            "scrollbars-always-visible",
                            content = { AlwaysVisibleScrollbarExample() },
                            variantName = "Always visible",
                            previewOptions =
                                PreviewOptions(maxWidth = 380.dp, padding = PaddingValues(24.dp)),
                        ),
                        DemoItem(
                            "Scrollbars (Vertical disabled)",
                            "scrollbars-vertical-disabled",
                            content = { DisabledScrollbarExample() },
                            variantName = "Disabled",
                            previewOptions =
                                PreviewOptions(maxWidth = 380.dp, padding = PaddingValues(24.dp)),
                        ),
                        DemoItem(
                            "Scrollbars (Horizontal disabled)",
                            "scrollbars-horizontal-disabled",
                            content = { DisabledHorizontalScrollbarExample() },
                            variantName = "Horizontal disabled",
                            previewOptions =
                                PreviewOptions(maxWidth = 380.dp, padding = PaddingValues(24.dp)),
                        ),
                    ),
            ),
            DemoGroup(
                name = "Separators",
                id = "separators",
                demos =
                    listOf(
                        DemoItem(
                            "Separators (Horizontal)",
                            "separators-horizontal",
                            content = { HorizontalSeparatorExample() },
                            variantName = "Horizontal",
                            previewOptions = PreviewOptions(maxWidth = 380.dp),
                        ),
                        DemoItem(
                            "Separators (Vertical)",
                            "separators-vertical",
                            content = { VerticalSeparatorExample() },
                            variantName = "Vertical",
                            //                previewOptions = PreviewOptions(maxHeight = 180.dp),
                        ),
                    ),
            ),
            DemoGroup(
                name = "Slider",
                id = "slider",
                demos =
                    listOf(
                        DemoItem(
                            "Slider",
                            "slider",
                            content = { SliderExample() },
                            variantName = "Default",
                            previewOptions = PreviewOptions(maxWidth = 360.dp),
                        ),
                        DemoItem(
                            "Slider (Vertical)",
                            "slider-vertical",
                            content = { VerticalSliderExample() },
                            variantName = "Vertical",
                            previewOptions = PreviewOptions(maxWidth = 360.dp),
                        ),
                        DemoItem(
                            "Slider (Disabled)",
                            "slider-disabled",
                            content = { DisabledSliderExample() },
                            variantName = "Disabled",
                            previewOptions = PreviewOptions(maxWidth = 360.dp),
                        ),
                        DemoItem(
                            "Slider (Steps)",
                            "slider-steps",
                            content = { SteppedSliderExample() },
                            variantName = "Steps",
                            previewOptions = PreviewOptions(maxWidth = 360.dp),
                        ),
                    ),
            ),
            DemoGroup(
                name = "Tabs",
                id = "tabs",
                demos =
                    listOf(
                        DemoItem(
                            "Tabs",
                            "tabs",
                            content = { TabsExample() },
                            variantName = "Default",
                            previewOptions = PreviewOptions(maxWidth = 390.dp),
                        ),
                        DemoItem(
                            "Tabs (Weighted)",
                            "tabs-weighted",
                            content = { WeightedTabsExample() },
                            variantName = "Weighted",
                            previewOptions = PreviewOptions(maxWidth = 390.dp),
                        ),
                        DemoItem(
                            "Tabs (Icons)",
                            "tabs-icons",
                            content = { TabsWithIconsExample() },
                            variantName = "Icons",
                            previewOptions = PreviewOptions(maxWidth = 390.dp),
                        ),
                        DemoItem(
                            "Tabs (Vertical)",
                            "tabs-vertical",
                            content = { VerticalTabsExample() },
                            variantName = "Vertical",
                            previewOptions = PreviewOptions(maxWidth = 460.dp),
                        ),
                        DemoItem(
                            "Tabs (Disabled)",
                            "tabs-disabled",
                            content = { DisabledTabsExample() },
                            variantName = "Disabled",
                            previewOptions = PreviewOptions(maxWidth = 390.dp),
                        ),
                    ),
            ),
            DemoGroup(
                name = "Text",
                id = "text",
                demos =
                    listOf(
                        DemoItem(
                            "Text",
                            "text",
                            content = { TextExample() },
                            variantName = "Default",
                            previewOptions = PreviewOptions(maxWidth = 360.dp),
                        ),
                        DemoItem(
                            "Text (Styled)",
                            "text-styled",
                            content = { StyledTextExample() },
                            variantName = "Styled",
                            previewOptions = PreviewOptions(maxWidth = 360.dp),
                        ),
                        DemoItem(
                            "Text (Themed)",
                            "text-themed",
                            content = { ThemedTextExample() },
                            variantName = "Themed",
                            previewOptions = PreviewOptions(maxWidth = 360.dp),
                        ),
                    ),
            ),
            DemoGroup(
                name = "TextField",
                id = "text-field",
                demos =
                    listOf(
                        DemoItem(
                            "TextField",
                            "text-field",
                            content = { DefaultTextFieldExample() },
                            variantName = "Default",
                            previewOptions = PreviewOptions(maxWidth = 360.dp),
                        ),
                        DemoItem(
                            "TextField (Ghost)",
                            "text-field-ghost",
                            content = { GhostTextFieldExample() },
                            variantName = "Ghost",
                            previewOptions = PreviewOptions(maxWidth = 360.dp),
                        ),
                        DemoItem(
                            "TextField (Search)",
                            "text-field-search",
                            content = { SearchTextFieldExample() },
                            variantName = "Search",
                            previewOptions = PreviewOptions(maxWidth = 360.dp),
                        ),
                        DemoItem(
                            "TextField (Multiline)",
                            "text-field-multiline",
                            content = { MultilineTextFieldExample() },
                            variantName = "Multiline",
                            previewOptions = PreviewOptions(maxWidth = 360.dp),
                        ),
                        DemoItem(
                            "TextField (Disabled)",
                            "text-field-disabled",
                            content = { DisabledTextFieldExample() },
                            variantName = "Disabled",
                            previewOptions = PreviewOptions(maxWidth = 360.dp),
                        ),
                        DemoItem(
                            "TextField (Read-only)",
                            "text-field-read-only",
                            content = { ReadOnlyTextFieldExample() },
                            variantName = "Read-only",
                            previewOptions = PreviewOptions(maxWidth = 360.dp),
                        ),
                    ),
            ),
            DemoGroup(
                name = "Switch",
                id = "switch",
                demos =
                    listOf(
                        DemoItem(
                            "Switch",
                            "switch",
                            content = { SwitchExample() },
                            variantName = "Default"),
                        DemoItem(
                            "Switch (Disabled)",
                            "switch-disabled",
                            content = { DisabledSwitchExample() },
                            variantName = "Disabled",
                        ),
                    ),
            ),
            DemoGroup(
                name = "Toolbar",
                id = "toolbar",
                demos =
                    listOf(
                        DemoItem(
                            name = "Toolbar (Actions)",
                            id = "toolbar-actions",
                            content = { ToolbarWithActionsExample() },
                            variantName = "Actions",
                            previewOptions =
                                PreviewOptions(
                                    contentAlignment = Alignment.TopCenter,
                                    padding = PaddingValues(top = 90.dp),
                                ),
                        ),
                        DemoItem(
                            name = "Toolbar (Centered)",
                            id = "centered-toolbar",
                            content = { CenteredToolbarExample() },
                            variantName = "Centered",
                            previewOptions =
                                PreviewOptions(
                                    contentAlignment = Alignment.TopCenter,
                                    padding = PaddingValues(top = 90.dp),
                                ),
                        ),
                        DemoItem(
                            name = "Toolbar (Large)",
                            id = "large-toolbar",
                            content = { LargeToolbarExample() },
                            variantName = "Large",
                            previewOptions =
                                PreviewOptions(
                                    contentAlignment = Alignment.TopCenter,
                                    padding = PaddingValues(top = 90.dp),
                                ),
                        ),
                    ),
            ),
            DemoGroup(
                name = "NavigationBar",
                id = "navigation-bar",
                demos =
                    listOf(
                        DemoItem(
                            name = "NavigationBar",
                            id = "navigation-bar",
                            content = { NavigationBarExample() },
                            variantName = "Default",
                            previewOptions =
                                PreviewOptions(
                                    contentAlignment = Alignment.BottomCenter,
                                    padding = PaddingValues(0.dp),
                                ),
                        ),
                        DemoItem(
                            name = "Disabled NavigationBar",
                            id = "disabled-navigation-bar",
                            content = { DisabledNavigationBarExample() },
                            variantName = "Disabled",
                            previewOptions =
                                PreviewOptions(
                                    contentAlignment = Alignment.BottomCenter,
                                    padding = PaddingValues(0.dp),
                                ),
                        ),
                        DemoItem(
                            name = "Custom Insets NavigationBar",
                            id = "custom-insets-navigation-bar",
                            content = { CustomInsetsNavigationBarExample() },
                            variantName = "Custom Insets",
                            previewOptions =
                                PreviewOptions(
                                    contentAlignment = Alignment.BottomCenter,
                                    padding = PaddingValues(0.dp),
                                ),
                        ),
                    ),
            ),
            DemoGroup(
                name = "Tooltip",
                id = "tooltip",
                demos =
                    listOf(
                        DemoItem(
                            "Tooltip",
                            "tooltip",
                            content = { TooltipExample() },
                            variantName = "Default"),
                        DemoItem(
                            "Tooltip",
                            "tooltip-side",
                            content = { TooltipSideExample() },
                            variantName = "Side"),
                        DemoItem(
                            "Tooltip",
                            "tooltip-alignment",
                            content = { TooltipAlignmentExample() },
                            variantName = "Alignment"),
                        DemoItem(
                            "Tooltip",
                            "tooltip-hover-delay",
                            content = { TooltipHoverDelayExample() },
                            variantName = "Hover delay"),
                        DemoItem(
                            "Tooltip",
                            "tooltip-long-press-duration",
                            content = { TooltipLongPressDurationExample() },
                            variantName = "Long press duration"),
                    ),
            ),
        )
        .sortedBy { it.name }

private val componentDemos = componentDemoGroups.flatMap { it.demos }

private val demos = componentDemos

private const val NavigationTransitionDurationMillis = 350
private const val NavigationParallaxDivisor = 5
private const val NavigationDimmedAlpha = 0.86f
private val NavigationTransitionEasing = CubicBezierEasing(0.32f, 0.72f, 0f, 1f)
private val DemoListItemShape = RoundedCornerShape(12.dp)
private val DemoPreviewShape = RoundedCornerShape(16.dp)

@Composable
fun Demo(initialDemoId: String? = null) {
  val demoListScrollState = rememberScrollState()
  val initialDemo = demos.firstOrNull { it.id == initialDemoId }
  val previewSpecificDemo = initialDemo != null
  val startDestination = initialDemo?.let { DemoRoute.SingleDemoPreview(it.id) } ?: DemoRoute.Home
  val backStack = remember { NavBackStack<NavKey>(startDestination) }
  var interactionMode by remember { mutableStateOf(InteractionMode.Touch) }
  var colorScheme by remember { mutableStateOf(ColorScheme.Light) }

  DemoScaffold {
    NavDisplay(
        backStack = backStack,
        modifier = Modifier.fillMaxSize().background(Color.Black),
        transitionSpec = { demoForwardTransition() },
        popTransitionSpec = { demoBackTransition() },
        predictivePopTransitionSpec = { _ -> demoBackTransition() },
        entryProvider =
            entryProvider {
              entry<DemoRoute.Home> {
                CompositionLocalProvider(LocalColorScheme provides colorScheme) {
                  ComposablesTheme {
                    ScreenScaffold {
                      Column(Modifier.fillMaxSize()) {
                        Toolbar(
                            modifier = Modifier.fillMaxWidth(),
                            title = { Text("Composables UI") },
                            size = ToolbarSize.Large,
                            trailing = {
                              ColorSchemeAction(
                                  colorScheme = colorScheme,
                                  onColorSchemeChange = { colorScheme = it },
                              )
                            },
                        )
                        DemoList(
                            onSelectGroup = { backStack.add(DemoRoute.ComponentGroup(it.id)) },
                            scrollState = demoListScrollState,
                            modifier = Modifier.weight(1f),
                        )
                      }
                    }
                  }
                }
              }

              entry<DemoRoute.ComponentGroup> { route ->
                val group = componentDemoGroups.first { it.id == route.id }
                DemoGroupRoute(
                    group = group,
                    onBack = { backStack.removeLastOrNull() },
                    interactionMode = interactionMode,
                    onInteractionModeChange = { interactionMode = it },
                    colorScheme = colorScheme,
                    onColorSchemeChange = { colorScheme = it },
                )
              }

              entry<DemoRoute.SingleDemoPreview> { route ->
                val demo = demos.first { it.id == route.id }
                SingleDemoPreviewRoute(
                    demo = demo,
                    onBack = { backStack.removeLastOrNull() },
                    showNavigation = !previewSpecificDemo,
                    interactionMode = interactionMode,
                    onInteractionModeChange = { interactionMode = it },
                    colorScheme = colorScheme,
                    onColorSchemeChange = { colorScheme = it },
                )
              }
            },
    )
  }
}

@Composable
private fun DemoScaffold(content: @Composable () -> Unit) {
  ComposablesTheme {
    TooltipHost {
      FocusVisibilityProvider {
        ProvideContentColor(Theme[colors][onBackgroundColor]) {
          Box(
              modifier = Modifier.fillMaxSize().background(Theme[colors][backgroundColor]),
          ) {
            content()
          }
        }
      }
    }
  }
}

private fun AnimatedContentTransitionScope<Scene<NavKey>>.demoForwardTransition():
    ContentTransform {
  return slideInHorizontally(
      animationSpec =
          tween(
              durationMillis = NavigationTransitionDurationMillis,
              easing = NavigationTransitionEasing,
          ),
      initialOffsetX = { it },
  ) togetherWith
      (slideOutHorizontally(
          animationSpec =
              tween(
                  durationMillis = NavigationTransitionDurationMillis,
                  easing = NavigationTransitionEasing,
              ),
          targetOffsetX = { -it / NavigationParallaxDivisor },
      ) +
          fadeOut(
              animationSpec =
                  tween(
                      durationMillis = NavigationTransitionDurationMillis,
                      easing = NavigationTransitionEasing,
                  ),
              targetAlpha = NavigationDimmedAlpha,
          ))
}

private fun AnimatedContentTransitionScope<Scene<NavKey>>.demoBackTransition(): ContentTransform {
  return slideInHorizontally(
      animationSpec =
          tween(
              durationMillis = NavigationTransitionDurationMillis,
              easing = NavigationTransitionEasing,
          ),
      initialOffsetX = { -it / NavigationParallaxDivisor },
  ) +
      fadeIn(
          animationSpec =
              tween(
                  durationMillis = NavigationTransitionDurationMillis,
                  easing = NavigationTransitionEasing,
              ),
          initialAlpha = NavigationDimmedAlpha,
      ) togetherWith
      slideOutHorizontally(
          animationSpec =
              tween(
                  durationMillis = NavigationTransitionDurationMillis,
                  easing = NavigationTransitionEasing,
              ),
          targetOffsetX = { it },
      )
}

@Composable
private fun ScreenScaffold(
    backgroundColor: Color = Theme[colors][panelColor],
    contentColor: Color = Theme[colors][onPanelColor],
    content: @Composable () -> Unit,
) {
  ProvideContentColor(contentColor) {
    Box(
        modifier = Modifier.fillMaxSize().background(backgroundColor),
    ) {
      content()
    }
  }
}

@Composable
private fun DemoList(
    onSelectGroup: (DemoGroup) -> Unit,
    scrollState: ScrollState,
    modifier: Modifier = Modifier,
) {
  Box(modifier.fillMaxSize(), contentAlignment = Alignment.TopStart) {
    Column(
        modifier =
            Modifier.verticalScroll(scrollState)
                .padding(horizontal = 8.dp, vertical = 12.dp)
                .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(DemoListItemSpacing),
    ) {
      DemoSection(componentDemoGroups, onSelectGroup)
    }
  }
}

@Composable
private fun DemoGroupRoute(
    group: DemoGroup,
    onBack: () -> Unit,
    interactionMode: InteractionMode,
    onInteractionModeChange: (InteractionMode) -> Unit,
    colorScheme: ColorScheme,
    onColorSchemeChange: (ColorScheme) -> Unit,
) {
  CompositionLocalProvider(
      LocalColorScheme provides colorScheme,
      LocalInteractionMode provides interactionMode,
  ) {
    ComposablesTheme {
      ScreenScaffold(
          backgroundColor = Theme[colors][panelColor], contentColor = Theme[colors][onPanelColor]) {
            Box(Modifier.fillMaxSize()) {
              Column(
                  modifier =
                      Modifier.fillMaxSize()
                          .verticalScroll(rememberScrollState())
                          .padding(horizontal = 16.dp)
                          .padding(top = 72.dp, bottom = 24.dp),
                  verticalArrangement = Arrangement.spacedBy(24.dp),
              ) {
                group.demos.forEach { demo ->
                  DemoExampleSection(
                      title = demo.titleIn(group),
                      demo = demo,
                  )
                }
              }
              Toolbar(
                  modifier = Modifier.fillMaxWidth(),
                  leading = {
                    IconButton(
                        onClick = onBack,
                        style = ButtonStyle.Ghost,
                    ) {
                      Icon(Lucide.ArrowLeft, contentDescription = "Go back")
                    }
                  },
                  title = { Text(group.name) },
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
private fun DemoExampleSection(
    title: String,
    demo: DemoItem,
) {
  Column(
      verticalArrangement = Arrangement.spacedBy(12.dp),
  ) {
    Text(
        text = title,
        style = LocalTextStyle.current.merge(DemoListTextStyle),
    )
    DemoPreviewSurface(
        demo = demo,
        framed = true,
    )
  }
}

private fun DemoItem.titleIn(group: DemoGroup): String {
  return if (variantName == "Default") group.name else "$variantName ${group.name}"
}

@Composable
private fun SingleDemoPreviewRoute(
    demo: DemoItem,
    onBack: () -> Unit,
    showNavigation: Boolean,
    interactionMode: InteractionMode,
    onInteractionModeChange: (InteractionMode) -> Unit,
    colorScheme: ColorScheme,
    onColorSchemeChange: (ColorScheme) -> Unit,
) {
  val initialInteractionMode = LocalInteractionMode.current ?: InteractionMode.Touch
  val fixedInteractionMode = remember { initialInteractionMode }

  CompositionLocalProvider(
      LocalColorScheme provides colorScheme,
      LocalInteractionMode provides fixedInteractionMode,
  ) {
    ComposablesTheme {
      ScreenScaffold(
          backgroundColor = Theme[colors][panelColor], contentColor = Theme[colors][onPanelColor]) {
            Box(Modifier.fillMaxSize()) {
              CompositionLocalProvider(LocalInteractionMode provides interactionMode) {
                ComposablesTheme {
                  ProvideContentColor(Theme[colors][onPanelColor]) {
                    DemoPreviewSurface(
                        demo = demo,
                        framed = false,
                        modifier = Modifier.fillMaxSize().background(Theme[colors][panelColor]),
                    )
                  }
                }
              }
              Toolbar(
                  modifier = Modifier.fillMaxWidth(),
                  title =
                      if (showNavigation) {
                        { Text(demo.name) }
                      } else {
                        {}
                      },
                  backgroundColor = Color.Transparent,
                  leading =
                      if (showNavigation) {
                        {
                          IconButton(
                              onClick = onBack,
                              style = ButtonStyle.Ghost,
                          ) {
                            Icon(Lucide.ArrowLeft, contentDescription = "Go back")
                          }
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
private fun DemoPreviewSurface(
    demo: DemoItem,
    framed: Boolean,
    modifier: Modifier = Modifier,
) {
  Box(
      modifier =
          modifier
              .fillMaxWidth()
              .then(
                  buildModifier {
                    if (framed) {
                      add(
                          Modifier.height(180.dp)
                              .clip(DemoPreviewShape)
                              .border(1.dp, Theme[colors][borderColor], DemoPreviewShape),
                      )
                    }
                  },
              ),
  ) {
    DemoPreviewContent(demo)
  }
}

@Composable
private fun DemoPreviewContent(demo: DemoItem) {
  Box(
      modifier = Modifier.fillMaxSize().padding(demo.previewOptions.padding),
      contentAlignment = demo.previewOptions.contentAlignment,
  ) {
    Box(
        modifier =
            Modifier.widthIn(max = demo.previewOptions.maxWidth ?: Dp.Infinity).fillMaxWidth(),
        contentAlignment = demo.previewOptions.contentAlignment,
    ) {
      demo.content()
    }
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
    content: @Composable () -> Unit,
) {
  val shape = RoundedCornerShape(999.dp)
  val borderColor = Theme[colors][borderColor]
  UnstyledRadioGroup(
      value = value,
      onValueChange = onValueChange,
      accessibilityLabel = accessibilityLabel,
      modifier =
          Modifier.clip(shape)
              .background(Theme[colors][secondaryColor], shape)
              .border(1.dp, borderColor, shape)
              .padding(4.dp),
  ) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
      content()
    }
  }
}

@Composable
private fun <T> SegmentedRadioOption(
    value: T,
    selected: Boolean,
    minWidth: Dp,
    content: @Composable (selected: Boolean) -> Unit,
) {
  val shape = RoundedCornerShape(999.dp)
  val interactionSource = remember { MutableInteractionSource() }
  UnstyledRadioButton(
      value = value,
      indication = LocalIndication.current,
      interactionSource = interactionSource,
      modifier =
          Modifier.focusRing(
                  interactionSource = interactionSource,
                  shape = shape,
              )
              .clip(shape)
              .then(
                  buildModifier {
                    if (selected) {
                      add(Modifier.background(Theme[colors][panelColor], shape))
                    }
                  },
              ),
  ) {
    Box(
        modifier = Modifier.height(32.dp).widthIn(min = minWidth).padding(horizontal = 10.dp),
        contentAlignment = Alignment.Center,
    ) {
      content(selected)
    }
  }
}

@Composable
private fun segmentedRadioContentColor(selected: Boolean): Color {
  return if (selected) {
    Theme[colors][onPanelColor]
  } else {
    Theme[colors][mutedColor]
  }
}

@Composable
private fun DemoSection(
    groups: List<DemoGroup>,
    onClick: (DemoGroup) -> Unit,
) {
  groups.forEach { group ->
    DemoListButton(
        onClick = { onClick(group) },
        modifier = Modifier.fillMaxWidth(),
    ) {
      DemoGroupHeader(group.name)
    }
  }
}

@Composable
private fun DemoGroupHeader(name: String) {
  Row(
      modifier =
          Modifier.fillMaxWidth()
              .padding(start = DemoListHorizontalPadding, end = DemoListHorizontalPadding),
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      verticalAlignment = Alignment.CenterVertically,
  ) {
    Text(
        text = name,
        style = LocalTextStyle.current.merge(DemoListTextStyle),
    )
    Spacer(Modifier.weight(1f))
  }
}

private val DemoListHorizontalPadding = 16.dp
private val DemoListItemSpacing = 8.dp
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
