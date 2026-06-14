package com.composables.ui.sample.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.composables.ui.components.Button
import com.composables.ui.components.ButtonStyle
import com.composables.ui.components.IconButton
import com.composables.ui.theme.colors
import com.composables.ui.theme.mediumShape
import com.composables.ui.theme.selectedControlColor
import com.composables.ui.theme.shapes
import com.composeunstyled.LocalTextStyle
import com.composeunstyled.ProvideTextStyle
import com.composeunstyled.buildModifier
import com.composeunstyled.theme.Theme

internal class SidebarScope internal constructor(
    val expanded: Boolean,
)

@Composable
internal fun Sidebar(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    header: @Composable SidebarScope.() -> Unit = {},
    footer: @Composable SidebarScope.() -> Unit = {},
    content: @Composable SidebarScope.() -> Unit,
) {
    val sidebarScope = SidebarScope(expanded)
    val horizontalPadding = if (expanded) 20.dp else 0.dp

    Column(
        modifier = modifier
            .fillMaxHeight()
            .then(buildModifier {
                if (expanded) add(Modifier.width(SidebarExpandedWidth)) else add(Modifier.width(SidebarCompactWidth))
            })
            .pointerInput(Unit) {}
            .padding(
                start = horizontalPadding,
                top = 40.dp,
                end = horizontalPadding,
                bottom = 24.dp,
            ),
    ) {
        sidebarScope.header()
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            sidebarScope.content()
        }
        sidebarScope.footer()
    }
}

@Composable
internal fun SidebarScope.SidebarItem(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = Theme[shapes][mediumShape],
    contentPadding: PaddingValues = PaddingValues(horizontal = if (expanded) 10.dp else 0.dp),
    icon: @Composable () -> Unit,
    text: @Composable () -> Unit,
) {
    if (!expanded) {
        Box(
            modifier = modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            IconButton(
                onClick = onClick,
                modifier = Modifier.then(buildModifier {
                    if (selected) add(Modifier.background(Theme[colors][selectedControlColor], shape))
                }),
                enabled = enabled,
                style = ButtonStyle.Ghost,
                shape = shape,
            ) {
                Box(Modifier.size(NavigationSidebarItemIconSize)) {
                    icon()
                }
            }
        }
        return
    }

    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .then(buildModifier {
                if (selected) add(Modifier.background(Theme[colors][selectedControlColor], shape))
            }),
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
            ProvideTextStyle(LocalTextStyle.current.merge(SidebarItemTextStyle)) {
                text()
            }
        }
    }
}

private val SidebarItemTextStyle = TextStyle(
    fontWeight = FontWeight.Medium,
)

private val NavigationSidebarItemIconSize = 18.dp
private val SidebarExpandedWidth = 230.dp
private val SidebarCompactWidth = 78.dp
