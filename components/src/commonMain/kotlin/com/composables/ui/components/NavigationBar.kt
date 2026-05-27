package com.composables.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.composables.ui.theme.buttonShape
import com.composables.ui.theme.colors
import com.composables.ui.theme.navigationBar
import com.composables.ui.theme.navigationBarBorder
import com.composables.ui.theme.onNavigationBar
import com.composables.ui.theme.onSelectedNavigationBarItem
import com.composables.ui.theme.selectedNavigationBarItem
import com.composables.ui.theme.shapes
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.buildModifier
import com.composeunstyled.theme.Theme

@Composable
fun NavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(NavigationBarHeight)
            .pointerInput(Unit) {}
            .border(width = 1.dp, color = Theme[colors][navigationBarBorder])
            .background(Theme[colors][navigationBar])
            .padding(horizontal = 24.dp)
            .navigationBarsPadding(),
        horizontalArrangement = Arrangement.spacedBy(NavigationBarItemSpacing),
        verticalAlignment = Alignment.CenterVertically,
        content = content,
    )
}

@Composable
fun NavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val shape = Theme[shapes][buttonShape]

    Box(
        modifier = modifier
            .height(NavigationBarHeight),
        contentAlignment = Alignment.Center,
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier.then(buildModifier {
                if (selected) {
                    add(Modifier.background(Theme[colors][selectedNavigationBarItem], shape))
                }
            }),
            enabled = enabled,
            style = ButtonStyle.Ghost,
            shape = shape,
        ) {
            ProvideContentColor(if (selected) Theme[colors][onSelectedNavigationBarItem] else Theme[colors][onNavigationBar]) {
                icon()
            }
        }
    }
}

private val NavigationBarHeight = 64.dp
private val NavigationBarItemSpacing = 0.dp
