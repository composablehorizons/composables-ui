package com.composables.one

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.composables.layout.WindowInsets
import com.composables.layout.WindowInsetsSides
import com.composables.layout.only
import com.composables.layout.safeContent
import com.composables.layout.windowInsetsPadding
import com.composables.one.Sample
import com.composables.one.styling.accent
import com.composables.one.styling.colors
import com.composables.one.styling.navigation
import com.composables.one.styling.onAccent
import com.composables.one.styling.onNavigation
import com.composables.one.styling.outline
import com.composeunstyled.LocalContentColor
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.theme.Theme

@Sample("SideBarExample")
@Composable
fun SideBar(
    modifier: Modifier = Modifier,
    bottom: (@Composable () -> Unit)? = null,
    navigationItems: @Composable ColumnScope.() -> Unit,
) {
    val outlineColor = Theme[colors][outline]
    Column(
        modifier
            .zIndex(5f)
            .fillMaxHeight()
            .width(240.dp)
            .background(Theme[colors][navigation])
            .drawBehind {
                val strokeWidth = 1.dp
                drawLine(
                    color = outlineColor,
                    start = Offset(size.width - strokeWidth.toPx(), 0f),
                    end = Offset(size.width - strokeWidth.toPx(), size.height),
                    strokeWidth = strokeWidth.toPx()
                )
            }
            .windowInsetsPadding(WindowInsets.safeContent.only(WindowInsetsSides.Top))
            .padding(horizontal = 8.dp),
    ) {
        ProvideContentColor(Theme[colors][onNavigation]) {
            Column(
                modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(top = 36.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                navigationItems()
            }
            if (bottom != null) {
                Divider()
                bottom()
            }
        }
    }
}

@Composable
fun SideBarItem(
    onClick: () -> Unit,
    selected: Boolean,
    icon: @Composable () -> Unit,
    title: @Composable (() -> Unit),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    modifier: Modifier = Modifier,
) {
    GhostButton(
        onClick = onClick,
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        contentColor = if (selected) Theme[colors][onAccent] else LocalContentColor.current,
        backgroundColor = if (selected) Theme[colors][accent] else Color.Unspecified,
        interactionSource = interactionSource,
    ) {
        icon()
        Spacer(Modifier.width(12.dp))
        title()
    }
}