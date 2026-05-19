package com.composables.one

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composables.one.styling.border
import com.composables.one.styling.colors
import com.composables.one.styling.destructive
import com.composables.one.styling.dim
import com.composables.one.styling.dropdownMenuShadow
import com.composables.one.styling.dropdownMenuShape
import com.composables.one.styling.indications
import com.composables.one.styling.muted
import com.composables.one.styling.onPanel
import com.composables.one.styling.panel
import com.composables.one.styling.secondary
import com.composables.one.styling.shapes
import com.composables.one.styling.shadows
import com.composables.one.styling.textStyles
import com.composeunstyled.AnchorAlignment
import com.composeunstyled.AnchorSide
import com.composeunstyled.DropdownMenuPanel
import com.composeunstyled.DropdownMenuPanelScope
import com.composeunstyled.DropdownMenuScope as UnstyledDropdownMenuScope
import com.composeunstyled.MenuItem
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.ProvideTextStyle
import com.composeunstyled.UnstyledDropdownMenu
import com.composeunstyled.outline
import com.composeunstyled.theme.Theme
import com.composables.one.styling.body as bodyTextStyle
import com.composables.one.styling.buttonLabel as buttonLabelTextStyle

private const val DropdownMenuEnterDurationMillis = 120
private const val DropdownMenuExitDurationMillis = 75

class DropdownMenuScope internal constructor(
    internal val unstyledScope: UnstyledDropdownMenuScope,
)

class DropdownMenuContentScope internal constructor(
    internal val unstyledScope: DropdownMenuPanelScope,
)

class DropdownMenuSide private constructor(
    internal val anchorSide: AnchorSide,
) {
    companion object {
        val Top = DropdownMenuSide(AnchorSide.Top)
        val Bottom = DropdownMenuSide(AnchorSide.Bottom)
        val Start = DropdownMenuSide(AnchorSide.Start)
        val End = DropdownMenuSide(AnchorSide.End)
    }
}

class DropdownMenuAlignment private constructor(
    internal val anchorAlignment: AnchorAlignment,
) {
    companion object {
        val Start = DropdownMenuAlignment(AnchorAlignment.Start)
        val Center = DropdownMenuAlignment(AnchorAlignment.Center)
        val End = DropdownMenuAlignment(AnchorAlignment.End)
    }
}

class DropdownMenuItemStyle private constructor(
    internal val destructive: Boolean,
) {
    companion object {
        val Default = DropdownMenuItemStyle(destructive = false)
        val Destructive = DropdownMenuItemStyle(destructive = true)
    }
}

@Composable
fun DropdownMenu(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    side: DropdownMenuSide = DropdownMenuSide.Bottom,
    alignment: DropdownMenuAlignment = DropdownMenuAlignment.Start,
    sideOffset: Dp = 4.dp,
    alignmentOffset: Dp = 0.dp,
    anchor: @Composable () -> Unit,
    content: @Composable DropdownMenuScope.() -> Unit,
) {
    UnstyledDropdownMenu(
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        modifier = modifier,
        side = side.anchorSide,
        alignment = alignment.anchorAlignment,
        sideOffset = sideOffset,
        alignmentOffset = alignmentOffset,
        panel = {
            DropdownMenuScope(this).content()
        },
        anchor = anchor,
    )
}

@Composable
fun DropdownMenuScope.DropdownMenuContent(
    modifier: Modifier = Modifier,
    shape: Shape = Theme[shapes][dropdownMenuShape],
    backgroundColor: Color = Theme[colors][panel],
    contentColor: Color = Theme[colors][onPanel],
    shadow: Shadow = Theme[shadows][dropdownMenuShadow],
    minWidth: Dp = 160.dp,
    maxWidth: Dp = 320.dp,
    enter: EnterTransition = scaleIn(
        animationSpec = tween(
            durationMillis = DropdownMenuEnterDurationMillis,
            easing = LinearOutSlowInEasing,
        ),
        initialScale = 0.96f,
        transformOrigin = TransformOrigin(0f, 0f),
    ) + fadeIn(tween(durationMillis = 30)),
    exit: ExitTransition = scaleOut(
        animationSpec = tween(durationMillis = 1, delayMillis = DropdownMenuExitDurationMillis),
        targetScale = 1f,
        transformOrigin = TransformOrigin(0f, 0f),
    ) + fadeOut(tween(durationMillis = DropdownMenuExitDurationMillis)),
    content: @Composable DropdownMenuContentScope.() -> Unit,
) {
    with(unstyledScope) {
        DropdownMenuPanel(
            modifier = modifier
                .dropShadow(shape, shadow)
                .width(IntrinsicSize.Max)
                .widthIn(min = minWidth, max = maxWidth)
                .outline(1.dp, Theme[colors][border], shape)
                .clip(shape)
                .background(backgroundColor, shape)
                .padding(4.dp),
            enter = enter,
            exit = exit,
        ) {
            ProvideContentColor(contentColor) {
                ProvideTextStyle(Theme[textStyles][bodyTextStyle]) {
                    val panelScope = this@DropdownMenuPanel
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        content = { DropdownMenuContentScope(panelScope).content() },
                    )
                }
            }
        }
    }
}

@Composable
fun DropdownMenuContentScope.DropdownMenuItem(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    closeOnClick: Boolean = true,
    style: DropdownMenuItemStyle = DropdownMenuItemStyle.Default,
    leading: (@Composable () -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val hovered by interactionSource.collectIsHoveredAsState()
    val focused by interactionSource.collectIsFocusedAsState()
    val pressed by interactionSource.collectIsPressedAsState()
    val active = enabled && (hovered || focused || pressed)
    val itemShape = RoundedCornerShape(4.dp)
    val contentColor = if (style.destructive) Theme[colors][destructive] else Theme[colors][onPanel]

    with(unstyledScope) {
        MenuItem(
            onClick = onClick,
            enabled = enabled,
            closeOnClick = closeOnClick,
            interactionSource = interactionSource,
            indication = Theme[indications][dim],
            modifier = modifier
                .fillMaxWidth()
                .clip(itemShape)
                .background(if (active) Theme[colors][secondary] else Color.Transparent, itemShape)
                .alpha(if (enabled) 1f else 0.5f),
        ) {
            ProvideContentColor(contentColor) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 32.dp)
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (leading != null) {
                        Box(
                            modifier = Modifier.size(16.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            leading()
                        }
                    }
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        content = content,
                    )
                    if (trailing != null) {
                        ProvideContentColor(Theme[colors][muted]) {
                            trailing()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DropdownMenuContentScope.DropdownMenuLabel(
    modifier: Modifier = Modifier,
    contentColor: Color = Theme[colors][muted],
    content: @Composable RowScope.() -> Unit,
) {
    ProvideContentColor(contentColor) {
        ProvideTextStyle(Theme[textStyles][buttonLabelTextStyle]) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                content = content,
            )
        }
    }
}

@Composable
fun DropdownMenuContentScope.DropdownMenuSeparator(
    modifier: Modifier = Modifier,
) {
    Spacer(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .height(1.dp)
            .background(Theme[colors][border]),
    )
}
