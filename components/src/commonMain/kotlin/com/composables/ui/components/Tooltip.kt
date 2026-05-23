package com.composables.ui.components

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.composables.ui.theme.colors
import com.composables.ui.theme.onPrimary
import com.composables.ui.theme.primary
import com.composeunstyled.AnchorAlignment
import com.composeunstyled.AnchorSide
import com.composeunstyled.LocalTextStyle
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.ProvideTextStyle
import com.composeunstyled.TooltipPlacement as UnstyledTooltipPlacement
import com.composeunstyled.TooltipScope as UnstyledTooltipScope
import com.composeunstyled.TooltipPanel as UnstyledTooltipPanel
import com.composeunstyled.UnstyledTooltip
import com.composeunstyled.theme.Theme

private const val TooltipEnterDurationMillis = 120
private const val TooltipExitDurationMillis = 75

class TooltipScope internal constructor(
    internal val unstyledScope: UnstyledTooltipScope,
    internal val side: TooltipSide,
    internal val alignment: TooltipAlignment,
)

@kotlin.jvm.JvmInline
value class TooltipSide internal constructor(private val value: Int) {
    internal val anchorSide: AnchorSide
        get() = when (this) {
            Bottom -> AnchorSide.Bottom
            Start -> AnchorSide.Start
            End -> AnchorSide.End
            else -> AnchorSide.Top
        }

    companion object {
        val Top = TooltipSide(0)
        val Bottom = TooltipSide(1)
        val Start = TooltipSide(2)
        val End = TooltipSide(3)
    }
}

@kotlin.jvm.JvmInline
value class TooltipAlignment internal constructor(private val value: Int) {
    internal val anchorAlignment: AnchorAlignment
        get() = when (this) {
            Start -> AnchorAlignment.Start
            End -> AnchorAlignment.End
            else -> AnchorAlignment.Center
        }

    companion object {
        val Start = TooltipAlignment(0)
        val Center = TooltipAlignment(1)
        val End = TooltipAlignment(2)
    }
}

@kotlin.jvm.JvmInline
value class TooltipArrowDirection internal constructor(private val value: Int) {
    companion object {
        val Up = TooltipArrowDirection(0)
        val Down = TooltipArrowDirection(1)
        val Left = TooltipArrowDirection(2)
        val Right = TooltipArrowDirection(3)
    }
}

@Composable
fun Tooltip(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    side: TooltipSide = TooltipSide.Top,
    alignment: TooltipAlignment = TooltipAlignment.Center,
    sideOffset: Dp = 8.dp,
    alignmentOffset: Dp = 0.dp,
    longPressShowDurationMillis: Long = 1500L,
    hoverDelayMillis: Long = 0L,
    panel: @Composable TooltipScope.() -> Unit,
    anchor: @Composable () -> Unit,
) {
    Box(modifier) {
        UnstyledTooltip(
            enabled = enabled,
            panel = {
                TooltipScope(
                    unstyledScope = this,
                    side = side,
                    alignment = alignment,
                ).panel()
            },
            side = side.anchorSide,
            alignment = alignment.anchorAlignment,
            sideOffset = sideOffset,
            alignmentOffset = alignmentOffset,
            longPressShowDurationMillis = longPressShowDurationMillis,
            hoverDelayMillis = hoverDelayMillis,
            anchor = anchor,
        )
    }
}

@Composable
fun TooltipScope.TooltipPanel(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(6.dp),
    backgroundColor: Color = Theme[colors][primary],
    contentColor: Color = Theme[colors][onPrimary],
    contentPadding: PaddingValues = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
    enter: EnterTransition? = null,
    exit: ExitTransition? = null,
    arrow: (@Composable (TooltipArrowDirection, Color) -> Unit)? = { direction, color ->
        TooltipArrow(direction = direction, color = color)
    },
    content: @Composable () -> Unit,
) {
    val transformOrigin = tooltipTransformOrigin(side, alignment)

    with(unstyledScope) {
        UnstyledTooltipPanel(
            modifier = modifier.zIndex(15f),
            enter = enter ?: tooltipEnterTransition(transformOrigin),
            exit = exit ?: tooltipExitTransition(transformOrigin),
        ) { placement ->
            TooltipPanelContent(
                placement = placement,
                shape = shape,
                backgroundColor = backgroundColor,
                contentColor = contentColor,
                contentPadding = contentPadding,
                arrow = arrow,
                content = content,
            )
        }
    }
}

@Composable
private fun TooltipPanelContent(
    placement: UnstyledTooltipPlacement,
    shape: Shape,
    backgroundColor: Color,
    contentColor: Color,
    contentPadding: PaddingValues,
    arrow: (@Composable (TooltipArrowDirection, Color) -> Unit)?,
    content: @Composable () -> Unit,
) {
    val direction = placement.side.arrowDirection

    when (placement.side) {
        AnchorSide.Bottom -> {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                arrow?.invoke(direction, backgroundColor)
                TooltipSurface(shape, backgroundColor, contentColor, contentPadding, content)
            }
        }

        AnchorSide.Start -> {
            Row(verticalAlignment = Alignment.CenterVertically) {
                TooltipSurface(shape, backgroundColor, contentColor, contentPadding, content)
                arrow?.invoke(direction, backgroundColor)
            }
        }

        AnchorSide.End -> {
            Row(verticalAlignment = Alignment.CenterVertically) {
                arrow?.invoke(direction, backgroundColor)
                TooltipSurface(shape, backgroundColor, contentColor, contentPadding, content)
            }
        }

        else -> {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                TooltipSurface(shape, backgroundColor, contentColor, contentPadding, content)
                arrow?.invoke(direction, backgroundColor)
            }
        }
    }
}

@Composable
private fun TooltipSurface(
    shape: Shape,
    backgroundColor: Color,
    contentColor: Color,
    contentPadding: PaddingValues,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier
            .clip(shape)
            .background(backgroundColor, shape)
            .padding(contentPadding),
    ) {
        ProvideContentColor(contentColor) {
            ProvideTextStyle(LocalTextStyle.current.merge(TooltipTextStyle)) {
                content()
            }
        }
    }
}

private val TooltipTextStyle = TextStyle()

@Composable
private fun TooltipArrow(
    direction: TooltipArrowDirection,
    color: Color,
    modifier: Modifier = Modifier,
) {
    val rotation = when (direction) {
        TooltipArrowDirection.Down -> 180f
        TooltipArrowDirection.Left -> 270f
        TooltipArrowDirection.Right -> 90f
        else -> 0f
    }

    Canvas(
        modifier = modifier
            .size(width = 10.dp, height = 5.dp)
            .rotate(rotation),
    ) {
        val path = Path().apply {
            moveTo(size.width / 2f, 0f)
            lineTo(0f, size.height)
            lineTo(size.width, size.height)
            close()
        }
        drawPath(path = path, color = color)
    }
}

private val AnchorSide.arrowDirection: TooltipArrowDirection
    get() = when (this) {
        AnchorSide.Bottom -> TooltipArrowDirection.Up
        AnchorSide.Start -> TooltipArrowDirection.Right
        AnchorSide.End -> TooltipArrowDirection.Left
        else -> TooltipArrowDirection.Down
    }

private fun tooltipEnterTransition(transformOrigin: TransformOrigin): EnterTransition {
    return scaleIn(
        animationSpec = tween(
            durationMillis = TooltipEnterDurationMillis,
            easing = LinearOutSlowInEasing,
        ),
        initialScale = 0.96f,
        transformOrigin = transformOrigin,
    ) + fadeIn(tween(durationMillis = TooltipEnterDurationMillis))
}

private fun tooltipExitTransition(transformOrigin: TransformOrigin): ExitTransition {
    return scaleOut(
        animationSpec = tween(durationMillis = 1, delayMillis = TooltipExitDurationMillis),
        targetScale = 1f,
        transformOrigin = transformOrigin,
    ) + fadeOut(tween(durationMillis = TooltipExitDurationMillis))
}

private fun tooltipTransformOrigin(
    side: TooltipSide,
    alignment: TooltipAlignment,
): TransformOrigin {
    return when (side) {
        TooltipSide.Top -> TransformOrigin(
            pivotFractionX = alignment.transformOriginFraction,
            pivotFractionY = 1f,
        )

        TooltipSide.Bottom -> TransformOrigin(
            pivotFractionX = alignment.transformOriginFraction,
            pivotFractionY = 0f,
        )

        TooltipSide.Start -> TransformOrigin(
            pivotFractionX = 1f,
            pivotFractionY = alignment.transformOriginFraction,
        )

        else -> TransformOrigin(
            pivotFractionX = 0f,
            pivotFractionY = alignment.transformOriginFraction,
        )
    }
}

private val TooltipAlignment.transformOriginFraction: Float
    get() = when (this) {
        TooltipAlignment.Start -> 0f
        TooltipAlignment.Center -> 0.5f
        else -> 1f
    }
