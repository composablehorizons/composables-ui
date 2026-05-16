package com.composables.one.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.isUnspecified
import androidx.compose.ui.zIndex
import com.composables.one.Sample
import com.composables.one.styling.accent
import com.composables.one.styling.body
import com.composables.one.styling.colors
import com.composables.one.styling.elevated
import com.composables.one.styling.focusRing
import com.composables.one.styling.medium
import com.composables.one.styling.navigation
import com.composables.one.styling.onAccent
import com.composables.one.styling.onNavigation
import com.composables.one.styling.outline
import com.composables.one.styling.primary
import com.composables.one.styling.shadows
import com.composables.one.styling.shapes
import com.composables.one.styling.small
import com.composables.one.styling.textStyles
import com.composeunstyled.LocalContentColor
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.ProvideTextStyle
import com.composeunstyled.buildModifier
import com.composeunstyled.outline
import com.composeunstyled.theme.Theme
import com.composeunstyled.UnstyledTabGroup
import com.composeunstyled.Tab as UnstyledTab
import com.composeunstyled.TabList as UnstyledTabList
import com.composeunstyled.TabPanel as UnstyledTabPanel
import com.composeunstyled.TabGroupScope as UnstyledTabGroupScope
import com.composeunstyled.TabListScope as UnstyledTabListScope

class TabGroupScope internal constructor(
    private val columnScope: ColumnScope,
    internal val unstyledScope: UnstyledTabGroupScope<String>,
) : ColumnScope by columnScope

class TabListScope internal constructor(
    private val rowScope: RowScope,
    val unstyledScope: UnstyledTabListScope<String>,
) : RowScope by rowScope

@Sample("BottomNavigationBarExample")
@Sample("TabBarExample")
@Composable
fun TabGroup(
    selectedTab: String,
    tabs: List<String>,
    onSelectedTabChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable TabGroupScope.() -> Unit,
) {
    UnstyledTabGroup(
        selectedTab = selectedTab,
        onSelectedTabChange = onSelectedTabChange,
        tabs = tabs,
        modifier = modifier,
    ) {
        Column {
            TabGroupScope(this, this@UnstyledTabGroup).content()
        }
    }
}

@Composable
fun TabGroupScope.TabPanel(
    key: String,
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopStart,
    content: @Composable BoxScope.() -> Unit,
) {
    with(unstyledScope) {
        UnstyledTabPanel(key, modifier = modifier) {
            Box(contentAlignment = contentAlignment, content = content)
        }
    }
}

@Composable
fun TabGroupScope.BottomNavigationBar(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    backgroundColor: Color = Theme[colors][navigation],
    contentColor: Color = Theme[colors][onNavigation],
    content: @Composable TabListScope.() -> Unit,
) {
    with(unstyledScope) {
        UnstyledTabList(
        modifier = modifier
            .dropShadow(shape, Theme[shadows][elevated])
            .outline(Dp.Hairline, Theme[colors][outline])
            .zIndex(5f)
            .fillMaxWidth(),
    ) {
            ProvideContentColor(contentColor) {
                Row(
                    Modifier
                        .clip(shape)
                        .background(backgroundColor, shape)
                        .padding(4.dp)
                        .navigationBarsPadding()
                        .height(64.dp),
                ) {
                    DisableFontScaling {
                        with(TabListScope(this@Row, this@UnstyledTabList)) {
                            content()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    backgroundColor: Color = Theme[colors][navigation],
    contentColor: Color = Theme[colors][onNavigation],
    content: @Composable RowScope.() -> Unit,
) {
    ProvideContentColor(contentColor) {
        Row(
            modifier
                .dropShadow(shape, Theme[shadows][elevated])
                .outline(Dp.Hairline, Theme[colors][outline])
                .zIndex(5f)
                .fillMaxWidth()
                .clip(shape)
                .background(backgroundColor, shape)
                .padding(4.dp)
                .navigationBarsPadding()
                .height(64.dp),
        ) {
            DisableFontScaling {
                content()
            }
        }
    }
}

@Composable
fun DisableFontScaling(content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalDensity provides Density(density = LocalDensity.current.density, fontScale = 1f)) {
        content()
    }
}

@Composable
fun TabListScope.PrimaryTab(
    key: String,
    selected: Boolean,
    onSelected: () -> Unit,
    icon: @Composable () -> Unit,
    title: @Composable (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    modifier: Modifier = Modifier,
) {
    val selected = selected
    val focused by interactionSource.collectIsFocusedAsState()
    val outlineColor = if (focused) Theme[colors][focusRing] else Color.Transparent

    with(unstyledScope) {
            UnstyledTab(
                key = key,
                activateOnFocus = false,
                modifier = modifier
                    .outline(2.dp, outlineColor, Theme[shapes][medium])
                    .clip(Theme[shapes][medium])
                    .padding(8.dp),
                interactionSource = interactionSource,
            ) {
                ProvideContentColor(if (selected) Theme[colors][primary] else LocalContentColor.current) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                        icon()
                        if (title != null) {
                            Spacer(Modifier.height(4.dp))
                            title()
                        }
                    }
                }
            }
    }
}

@Composable
fun PrimaryTab(
    selected: Boolean,
    onSelected: () -> Unit,
    icon: @Composable () -> Unit,
    title: @Composable (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    modifier: Modifier = Modifier,
) {
    val selected = selected
    val focused by interactionSource.collectIsFocusedAsState()
    val outlineColor = if (focused) Theme[colors][focusRing] else Color.Transparent

    OneButton(
        onClick = onSelected,
        modifier = modifier.outline(2.dp, outlineColor, Theme[shapes][medium]),
        shape = Theme[shapes][medium],
        contentPadding = PaddingValues(8.dp),
        contentColor = if (selected) Theme[colors][accent] else LocalContentColor.current,
        interactionSource = interactionSource,
        backgroundColor = Color.Transparent,
        borderWidth = Dp.Hairline,
        indication = null,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            icon()
            if (title != null) {
                Spacer(Modifier.height(4.dp))
                title()
            }
        }
    }
}


@Composable
fun TabGroupScope.TabBar(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    backgroundColor: Color = Theme[colors][navigation],
    contentColor: Color = Theme[colors][onNavigation],
    content: @Composable TabListScope.() -> Unit,
) {
    with(unstyledScope) {
        UnstyledTabList(
        modifier = modifier
            .zIndex(5f)
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        orientation = Orientation.Horizontal,
    ) {
            ProvideContentColor(contentColor) {
                Row(
                    modifier = Modifier
                        .clip(shape)
                        .background(backgroundColor, shape)
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    with(TabListScope(this@Row, this@UnstyledTabList)) {
                        content()
                    }
                }
            }
        }
    }
}

@Composable
fun TabListScope.SecondaryTab(
    key: String,
    selected: Boolean,
    onSelected: () -> Unit,
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    icon: (@Composable () -> Unit)? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val focused by interactionSource.collectIsFocusedAsState()
    val outlineColor = if (focused) Theme[colors][focusRing] else Color.Transparent
    val shape = Theme[shapes][small]

    Column(modifier.width(IntrinsicSize.Min)) {
        // visually balance the bottom indicator
        Spacer(Modifier.height(10.dp))

        with(this@SecondaryTab.unstyledScope) {
                UnstyledTab(
                    key = key,
                    activateOnFocus = true,
                    modifier = Modifier
                        .outline(2.dp, outlineColor, shape)
                        .clip(shape)
                        .padding(8.dp),
                    interactionSource = interactionSource,
                ) {
                    ProvideContentColor(if (selected) Theme[colors][primary] else LocalContentColor.current) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                val normalTextStyle = Theme[textStyles][body]
                val selectedTextStyle = Theme[textStyles][body].merge(fontWeight = FontWeight.SemiBold)
                val actualTextStyle = if (selected) selectedTextStyle else normalTextStyle

                if (icon != null) {
                    icon()
                    Spacer(Modifier.width(8.dp))
                }

                var paddedWidth by remember { mutableStateOf(Dp.Unspecified) }
                val density = LocalDensity.current

                // font size will change when the tab is selected, but we don't want our layout to change size
                // for this, we need to measure the width of the max sized text and set that as the fixed width
                // to the layout
                val providedTextStyle = if (paddedWidth.isUnspecified) selectedTextStyle else actualTextStyle
                ProvideTextStyle(providedTextStyle) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = buildModifier {
                            if (paddedWidth.isUnspecified) {
                                add(Modifier.onSizeChanged {
                                    paddedWidth = with(density) { it.width.toDp() } + 16.dp
                                })
                            } else {
                                add(Modifier.width(paddedWidth))
                            }
                        }
                    ) {
                        title()
                    }
                }
                        }
                    }
                }
        }
        Box(
            Modifier
                .padding(top = 8.dp)
                .height(2.dp)
                .fillMaxWidth()
                .background(
                    if (selected) Theme[colors][primary] else Color.Transparent,
                    RoundedCornerShape(topStart = 100.dp, topEnd = 100.dp)
                )
        )
    }
}
