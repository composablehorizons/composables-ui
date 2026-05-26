package com.composables.ui.components

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.platform.WindowInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composables.ui.theme.border
import com.composables.ui.theme.colors
import com.composables.ui.theme.componentSizes
import com.composables.ui.theme.destructive
import com.composables.ui.theme.dim
import com.composables.ui.theme.dropdownMenuItemHeight
import com.composables.ui.theme.dropdownMenuShadow
import com.composables.ui.theme.dropdownMenuShape
import com.composables.ui.theme.indications
import com.composables.ui.theme.muted
import com.composables.ui.theme.onPanel
import com.composables.ui.theme.panel
import com.composables.ui.theme.secondary
import com.composables.ui.theme.shadows
import com.composables.ui.theme.shapes
import com.composeunstyled.AnchorAlignment
import com.composeunstyled.AnchorSide
import com.composeunstyled.DropdownMenuPanelScope
import com.composeunstyled.LocalTextStyle
import com.composeunstyled.MenuItem
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.ProvideTextStyle
import com.composeunstyled.UnstyledDropdownMenu
import com.composeunstyled.outline
import com.composeunstyled.theme.Theme
import com.composeunstyled.DropdownMenuPanel as UnstyledDropdownMenuPanel
import com.composeunstyled.DropdownMenuScope as UnstyledDropdownMenuScope

private const val DropdownMenuEnterDurationMillis = 120
private const val DropdownMenuExitDurationMillis = 75

val LocalDropdownMenuWindowInfo = staticCompositionLocalOf<WindowInfo?> { null }

class DropdownMenuScope internal constructor(
    internal val unstyledScope: UnstyledDropdownMenuScope,
    internal val side: DropdownMenuSide,
    internal val alignment: DropdownMenuAlignment,
)

class DropdownMenuPanelContentScope internal constructor(
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
    panel: @Composable DropdownMenuScope.() -> Unit,
    anchor: @Composable () -> Unit,
) {
    val dropdownMenuWindowInfo = LocalDropdownMenuWindowInfo.current
    val dropdownMenuContent = @Composable {
        UnstyledDropdownMenu(
            expanded = expanded,
            onExpandedChange = onExpandedChange,
            modifier = modifier,
            side = side.anchorSide,
            alignment = alignment.anchorAlignment,
            sideOffset = sideOffset,
            alignmentOffset = alignmentOffset,
            panel = {
                DropdownMenuScope(
                    unstyledScope = this,
                    side = side,
                    alignment = alignment,
                ).panel()
            },
            anchor = anchor,
        )
    }

    if (dropdownMenuWindowInfo == null) {
        dropdownMenuContent()
    } else {
        CompositionLocalProvider(LocalWindowInfo provides dropdownMenuWindowInfo) {
            dropdownMenuContent()
        }
    }
}

@Composable
fun DropdownMenuScope.DropdownMenuPanel(
    modifier: Modifier = Modifier,
    shape: Shape = Theme[shapes][dropdownMenuShape],
    backgroundColor: Color = Theme[colors][panel],
    contentColor: Color = Theme[colors][onPanel],
    shadow: Shadow = Theme[shadows][dropdownMenuShadow],
    minWidth: Dp = 160.dp,
    maxWidth: Dp = 320.dp,
    enter: EnterTransition? = null,
    exit: ExitTransition? = null,
    content: @Composable DropdownMenuPanelContentScope.() -> Unit,
) {
    val transformOrigin = dropdownMenuTransformOrigin(
        side = side,
        alignment = alignment,
    )
    val enterTransition = enter ?: dropdownMenuEnterTransition(transformOrigin)
    val exitTransition = exit ?: dropdownMenuExitTransition(transformOrigin)

    with(unstyledScope) {
        UnstyledDropdownMenuPanel(
            modifier = modifier
                .dropShadow(shape, shadow)
                .width(IntrinsicSize.Max)
                .widthIn(min = minWidth, max = maxWidth)
                .outline(1.dp, Theme[colors][border], shape)
                .clip(shape)
                .background(backgroundColor, shape)
                .padding(6.dp),
            enter = enterTransition,
            exit = exitTransition,
        ) {
            ProvideContentColor(contentColor) {
                ProvideTextStyle(LocalTextStyle.current.merge(DropdownMenuTextStyle)) {
                    val panelScope = this@UnstyledDropdownMenuPanel
                    DropdownMenuPanelContentScope(panelScope).content()
                }
            }
        }
    }
}

private fun dropdownMenuEnterTransition(transformOrigin: TransformOrigin): EnterTransition {
    return scaleIn(
        animationSpec = tween(
            durationMillis = DropdownMenuEnterDurationMillis,
            easing = LinearOutSlowInEasing,
        ),
        initialScale = 0.96f,
        transformOrigin = transformOrigin,
    ) + fadeIn(tween(durationMillis = 30))
}

private fun dropdownMenuExitTransition(transformOrigin: TransformOrigin): ExitTransition {
    return scaleOut(
        animationSpec = tween(durationMillis = 1, delayMillis = DropdownMenuExitDurationMillis),
        targetScale = 1f,
        transformOrigin = transformOrigin,
    ) + fadeOut(tween(durationMillis = DropdownMenuExitDurationMillis))
}

private fun dropdownMenuTransformOrigin(
    side: DropdownMenuSide,
    alignment: DropdownMenuAlignment,
): TransformOrigin {
    return when (side) {
        DropdownMenuSide.Top -> TransformOrigin(
            pivotFractionX = alignment.transformOriginFraction,
            pivotFractionY = 1f,
        )

        DropdownMenuSide.Bottom -> TransformOrigin(
            pivotFractionX = alignment.transformOriginFraction,
            pivotFractionY = 0f,
        )

        DropdownMenuSide.Start -> TransformOrigin(
            pivotFractionX = 1f,
            pivotFractionY = alignment.transformOriginFraction,
        )

        else -> TransformOrigin(
            pivotFractionX = 0f,
            pivotFractionY = alignment.transformOriginFraction,
        )
    }
}

private val DropdownMenuAlignment.transformOriginFraction: Float
    get() = when (this) {
        DropdownMenuAlignment.Start -> 0f
        DropdownMenuAlignment.Center -> 0.5f
        else -> 1f
    }

@Composable
fun DropdownMenuPanelContentScope.DropdownMenuItem(
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
    val itemShape = RoundedCornerShape(12.dp)
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
                        .heightIn(min = Theme[componentSizes][dropdownMenuItemHeight])
                        .padding(horizontal = 12.dp, vertical = 6.dp),
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
fun DropdownMenuPanelContentScope.DropdownMenuLabel(
    modifier: Modifier = Modifier,
    contentColor: Color = Theme[colors][muted],
    content: @Composable RowScope.() -> Unit,
) {
    ProvideContentColor(contentColor) {
        ProvideTextStyle(LocalTextStyle.current.merge(DropdownMenuLabelTextStyle)) {
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

private val DropdownMenuTextStyle = TextStyle()

private val DropdownMenuLabelTextStyle = TextStyle(
    fontWeight = FontWeight.SemiBold,
)

@Composable
fun DropdownMenuPanelContentScope.DropdownMenuSeparator(
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
