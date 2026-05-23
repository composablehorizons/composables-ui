package com.composables.ui.components

import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.composables.ui.theme.alphas
import com.composables.ui.theme.border
import com.composables.ui.theme.buttonHeight
import com.composables.ui.theme.colors
import com.composables.ui.theme.componentSizes
import com.composables.ui.theme.control
import com.composables.ui.theme.dim
import com.composables.ui.theme.disabledAlpha
import com.composables.ui.theme.focusRing
import com.composables.ui.theme.focusRingOffset
import com.composables.ui.theme.focusRingWidth
import com.composables.ui.theme.indications
import com.composables.ui.theme.muted
import com.composables.ui.theme.onSelectedControl
import com.composables.ui.theme.selectedControl
import com.composeunstyled.LocalTextStyle
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.ProvideTextStyle
import com.composeunstyled.Tab as UnstyledTab
import com.composeunstyled.TabGroupScope as UnstyledTabGroupScope
import com.composeunstyled.TabList as UnstyledTabList
import com.composeunstyled.TabListScope as UnstyledTabListScope
import com.composeunstyled.TabPanel as UnstyledTabPanel
import com.composeunstyled.UnstyledTabGroup
import com.composeunstyled.buildModifier
import com.composeunstyled.theme.Theme

class TabGroupScope<T> internal constructor(internal val unstyledScope: UnstyledTabGroupScope<T>)
class TabListScope<T> internal constructor(internal val unstyledScope: UnstyledTabListScope<T>)

@kotlin.jvm.JvmInline
value class TabOrientation internal constructor(internal val orientation: Orientation) {
    companion object {
        val Horizontal = TabOrientation(Orientation.Horizontal)
        val Vertical = TabOrientation(Orientation.Vertical)
    }
}

@Composable
fun <T> TabGroup(
    selectedTab: T,
    onSelectedTabChange: (T) -> Unit,
    tabs: List<T>,
    modifier: Modifier = Modifier,
    content: @Composable TabGroupScope<T>.() -> Unit,
) {
    UnstyledTabGroup(selectedTab, onSelectedTabChange, tabs, modifier) {
        TabGroupScope(this).content()
    }
}

@Composable
fun <T> TabGroupScope<T>.TabList(
    modifier: Modifier = Modifier,
    orientation: TabOrientation = TabOrientation.Horizontal,
    shape: Shape = RoundedCornerShape(999.dp),
    backgroundColor: Color = Theme[colors][control],
    equalTabWidth: Boolean = false,
    content: @Composable TabListScope<T>.() -> Unit,
) {
    with(unstyledScope) {
        UnstyledTabList(
            orientation = orientation.orientation,
            modifier = modifier
                .clip(shape)
                .background(backgroundColor, shape)
                .border(1.dp, Theme[colors][border], shape)
                .padding(4.dp),
        ) {
            val scope = TabListScope(this)
            if (orientation == TabOrientation.Horizontal) {
                Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                    TabListEqualTabWidthProvider(
                        equalTabWidth = equalTabWidth,
                        rowScope = this,
                    ) {
                        scope.content()
                    }
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) { scope.content() }
            }
        }
    }
}

@Composable
fun <T> TabListScope<T>.Tab(
    key: T,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    activateOnFocus: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    indication: Indication? = Theme[indications][dim],
    content: @Composable RowScope.(selected: Boolean) -> Unit,
) {
    val shape = RoundedCornerShape(999.dp)
    val rowScope = LocalTabListRowScope.current
    val itemModifier = if (LocalTabListEqualTabWidth.current && rowScope != null) {
        with(rowScope) { modifier.weight(1f) }
    } else {
        modifier
    }
    with(unstyledScope) {
        UnstyledTab(
            key = key,
            enabled = enabled,
            activateOnFocus = activateOnFocus,
            interactionSource = interactionSource,
            indication = indication,
            modifier = itemModifier
                .focusRing(interactionSource, Theme[componentSizes][focusRingWidth], Theme[colors][focusRing], shape, Theme[componentSizes][focusRingOffset])
                .clip(shape),
        ) {
            Row(
                modifier = Modifier
                    .heightIn(min = Theme[componentSizes][buttonHeight])
                .then(buildModifier {
                    if (selected) {
                        add(Modifier.background(Theme[colors][selectedControl], shape))
                    }
                    if (!enabled) add(Modifier.alpha(Theme[alphas][disabledAlpha]))
                })
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ProvideTextStyle(LocalTextStyle.current.merge(TabTextStyle)) {
                    ProvideContentColor(if (selected) Theme[colors][onSelectedControl] else Theme[colors][muted]) {
                        content(selected)
                    }
                }
            }
        }
    }
}

private val LocalTabListEqualTabWidth = androidx.compose.runtime.compositionLocalOf { false }
private val LocalTabListRowScope = androidx.compose.runtime.compositionLocalOf<RowScope?> { null }

@Composable
private fun TabListEqualTabWidthProvider(
    equalTabWidth: Boolean,
    rowScope: RowScope,
    content: @Composable () -> Unit,
) {
    androidx.compose.runtime.CompositionLocalProvider(
        LocalTabListEqualTabWidth provides equalTabWidth,
        LocalTabListRowScope provides rowScope,
    ) {
        content()
    }
}

private val TabTextStyle = TextStyle(
    fontWeight = FontWeight.SemiBold,
)

@Composable
fun <T> TabGroupScope<T>.TabPanel(
    key: T,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    with(unstyledScope) {
        UnstyledTabPanel(key = key, modifier = modifier.fillMaxWidth()) {
            Box(Modifier.fillMaxWidth()) { content() }
        }
    }
}
