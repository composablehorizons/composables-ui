package com.composables.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.composables.ui.theme.colors
import com.composables.ui.theme.onPanelColor
import com.composables.ui.theme.onSelectedControlColor
import com.composables.ui.theme.panelColor
import com.composables.ui.theme.selectedControlColor
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.buildModifier
import com.composeunstyled.theme.Theme

/**
 * A bottom navigation bar container.
 * @param modifier Modifier applied to the component.
 * @param windowInsets Insets applied around the navigation bar content.
 * @param content Composable content displayed by the component.
 */
@Composable
fun NavigationBar(
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = navigationBarWindowInsets(),
    content: @Composable RowScope.() -> Unit,
) {
    Column(
        modifier
            .pointerInput(Unit) {}
            .background(Theme[colors][panelColor])
    ) {
        HorizontalSeparator()
        Row(
            modifier = Modifier
                .windowInsetsPadding(windowInsets)
                .height(NavigationBarHeight)
                .padding(horizontal = 24.dp, vertical = 2.dp),
            horizontalArrangement = Arrangement.spacedBy(NavigationBarItemSpacing),
            verticalAlignment = Alignment.CenterVertically,
            content = content,
        )
    }
}

/**
 * A single destination action inside a navigation bar.
 * @param selected Whether the navigation item is currently selected.
 * @param onClick Called when the navigation item is activated.
 * @param modifier Modifier applied to the component.
 * @param enabled Whether the navigation item can be interacted with.
 * @param shape Shape used for the selected navigation item background.
 * @param content Composable content displayed by the component.
 */
@Composable
fun NavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(8.dp),
    content: @Composable () -> Unit,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier then buildModifier {
            if (selected) {
                add(Modifier.background(Theme[colors][selectedControlColor], shape))
            }
        }.height(NavigationBarHeight),
        enabled = enabled,
        style = ButtonStyle.Ghost,
        shape = shape,
    ) {
        ProvideContentColor(if (selected) Theme[colors][onSelectedControlColor] else Theme[colors][onPanelColor]) {
            content()
        }
    }
}

private val NavigationBarHeight = 64.dp
private val NavigationBarItemSpacing = 8.dp

@Composable
private fun navigationBarWindowInsets(): WindowInsets {
    return WindowInsets.systemBars.only(WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom)
}
