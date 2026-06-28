/*
 * Copyright (c) 2026 Composable Horizons
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.composables.ui.theme.borderColor
import com.composables.ui.theme.colors
import com.composables.ui.theme.onPanelColor
import com.composables.ui.theme.panelColor
import com.composeunstyled.AnchorAlignment
import com.composeunstyled.AnchorSide
import com.composeunstyled.LocalTextStyle
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.ProvideTextStyle
import com.composeunstyled.UnstyledTooltip
import com.composeunstyled.theme.Theme
import kotlin.jvm.JvmInline
import com.composeunstyled.TooltipPanel as UnstyledTooltipPanel
import com.composeunstyled.TooltipScope as UnstyledTooltipScope

private const val TooltipEnterDurationMillis = 300
private const val TooltipExitDurationMillis = 250

class TooltipScope internal constructor(
  internal val unstyledScope: UnstyledTooltipScope,
  internal val side: TooltipSide,
  internal val alignment: TooltipAlignment,
)

/**
 * Side options for tooltip placement relative to its anchor.
 */
@JvmInline
value class TooltipSide internal constructor(private val value: Int) {
  internal val anchorSide: AnchorSide
    get() = when (this) {
      Bottom -> AnchorSide.Bottom
      Start -> AnchorSide.Start
      End -> AnchorSide.End
      else -> AnchorSide.Top
    }

  companion object {
    /**
     * Places the tooltip above its anchor.
     */
    val Top = TooltipSide(0)

    /**
     * Places the tooltip below its anchor.
     */
    val Bottom = TooltipSide(1)

    /**
     * Places the tooltip before its anchor in the layout direction.
     */
    val Start = TooltipSide(2)

    /**
     * Places the tooltip after its anchor in the layout direction.
     */
    val End = TooltipSide(3)
  }
}

/**
 * Alignment options for tooltip placement along the anchor edge.
 */
@JvmInline
value class TooltipAlignment internal constructor(private val value: Int) {
  internal val anchorAlignment: AnchorAlignment
    get() = when (this) {
      Start -> AnchorAlignment.Start
      End -> AnchorAlignment.End
      else -> AnchorAlignment.Center
    }

  companion object {
    /**
     * Aligns the tooltip to the start edge of the anchor.
     */
    val Start = TooltipAlignment(0)

    /**
     * Centers the tooltip against the anchor.
     */
    val Center = TooltipAlignment(1)

    /**
     * Aligns the tooltip to the end edge of the anchor.
     */
    val End = TooltipAlignment(2)
  }
}

/**
 * An anchored tooltip with configurable placement and timing.
 * @param enabled Whether the tooltip can be shown.
 * @param side Side of the anchor where the tooltip should appear.
 * @param alignment Alignment of the tooltip along the chosen side.
 * @param sideOffset Distance between the tooltip and its anchor.
 * @param alignmentOffset Offset applied along the anchor edge.
 * @param longPressShowDurationMillis Duration to keep the tooltip visible after a long press.
 * @param hoverDelayMillis Delay before showing the tooltip on hover.
 * @param panel Composable tooltip panel content.
 * @param anchor Composable anchor that triggers the tooltip.
 */
@Composable
fun Tooltip(
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

/**
 * A styled tooltip surface drawn near the anchor.
 * @param modifier Modifier applied to the component.
 * @param shape Shape used for the tooltip panel.
 * @param backgroundColor Background color used for the tooltip panel.
 * @param contentColor Color used for tooltip content.
 * @param contentPadding Padding applied inside the tooltip panel.
 * @param enter Transition used when the tooltip appears.
 * @param exit Transition used when the tooltip disappears.
 * @param content Composable content displayed by the component.
 */
@Composable
fun TooltipScope.TooltipPanel(
  modifier: Modifier = Modifier,
  shape: Shape = RoundedCornerShape(6.dp),
  backgroundColor: Color = Theme[colors][panelColor],
  contentColor: Color = Theme[colors][onPanelColor],
  outlineColor: Color = Theme[colors][borderColor],
  contentPadding: PaddingValues = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
  enter: EnterTransition? = null,
  exit: ExitTransition? = null,
  content: @Composable () -> Unit,
) {
  val transformOrigin = tooltipTransformOrigin(side, alignment)

  with(unstyledScope) {
    UnstyledTooltipPanel(
      modifier = modifier.zIndex(15f),
      enter = enter ?: tooltipEnterTransition(transformOrigin),
      exit = exit ?: tooltipExitTransition(transformOrigin),
    ) {
      Box(
        modifier = Modifier
          .border(1.dp, outlineColor, shape)
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
  }
}

private val TooltipTextStyle = TextStyle()

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
    animationSpec = tween(durationMillis = TooltipExitDurationMillis),
    targetScale = 0.96f,
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
