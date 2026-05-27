package com.composables.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composables.ui.theme.colors
import com.composables.ui.theme.mediumShape
import com.composables.ui.theme.selectedSidebarItem
import com.composables.ui.theme.shapes
import com.composeunstyled.theme.Theme

@Composable
fun Sidebar(
    modifier: Modifier = Modifier,
    width: Dp = 230.dp,
    header: @Composable ColumnScope.() -> Unit = {},
    footer: @Composable ColumnScope.() -> Unit = {},
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .width(width)
            .pointerInput(Unit) {}
            .padding(horizontal = 20.dp, vertical = 24.dp),
    ) {
        header()
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            content = content,
        )
        footer()
    }
}

@Composable
fun SidebarItem(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = Theme[shapes][mediumShape],
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp),
    icon: @Composable () -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (selected) {
                    Modifier.background(Theme[colors][selectedSidebarItem], shape)
                } else {
                    Modifier
                },
            ),
        enabled = enabled,
        style = ButtonStyle.Ghost,
        shape = shape,
        contentPadding = contentPadding,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(Modifier.size(NavigationSidebarItemIconSize)) {
                icon()
            }
            content()
        }
    }
}

private val NavigationSidebarItemIconSize = 18.dp
