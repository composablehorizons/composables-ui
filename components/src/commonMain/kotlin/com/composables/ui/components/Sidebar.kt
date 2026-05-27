package com.composables.ui.components

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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composables.ui.theme.colors
import com.composables.ui.theme.mediumShape
import com.composables.ui.theme.selectedSidebarItem
import com.composables.ui.theme.shapes
import com.composeunstyled.LocalTextStyle
import com.composeunstyled.ProvideTextStyle
import com.composeunstyled.buildModifier
import com.composeunstyled.theme.Theme

@kotlin.jvm.JvmInline
value class SidebarMode internal constructor(@Suppress("unused") private val value: Int) {
    override fun toString() =
        when (this) {
            Expanded -> "Expanded"
            Compact -> "Compact"
            else -> "Error"
        }

    companion object {
        val Compact = SidebarMode(0)
        val Expanded = SidebarMode(1)
    }
}

class SidebarScope internal constructor(
    val mode: SidebarMode,
)

@Composable
fun Sidebar(
    modifier: Modifier = Modifier,
    mode: SidebarMode = SidebarMode.Expanded,
    header: @Composable SidebarScope.() -> Unit = {},
    footer: @Composable SidebarScope.() -> Unit = {},
    content: @Composable SidebarScope.() -> Unit,
) {
    val sidebarScope = SidebarScope(mode)

    Column(
        modifier = modifier
            .fillMaxHeight()
            .then(buildModifier {
                if (mode == SidebarMode.Compact) add(Modifier.width(SidebarCompactWidth)) else add(Modifier.width(SidebarExpandedWidth))
            })
            .pointerInput(Unit) {}
            .padding(
                start = mode.horizontalPadding,
                top = 40.dp,
                end = mode.horizontalPadding,
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
fun SidebarScope.SidebarItem(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = Theme[shapes][mediumShape],
    contentPadding: PaddingValues = PaddingValues(horizontal = mode.itemHorizontalPadding),
    icon: @Composable () -> Unit,
    text: @Composable () -> Unit,
) {
    if (mode == SidebarMode.Compact) {
        Box(
            modifier = modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            IconButton(
                onClick = onClick,
                modifier = Modifier.then(buildModifier {
                    if (selected) add(Modifier.background(Theme[colors][selectedSidebarItem], shape))
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
                if (selected) add(Modifier.background(Theme[colors][selectedSidebarItem], shape))
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

private val SidebarMode.horizontalPadding: Dp
    get() = if (this == SidebarMode.Compact) 0.dp else 20.dp

private val SidebarMode.itemHorizontalPadding: Dp
    get() = if (this == SidebarMode.Compact) 0.dp else 10.dp

private val NavigationSidebarItemIconSize = 18.dp
private val SidebarExpandedWidth = 230.dp
private val SidebarCompactWidth = 78.dp
