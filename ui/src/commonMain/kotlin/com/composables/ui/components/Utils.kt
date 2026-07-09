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

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composables.ui.theme.colors
import com.composables.ui.theme.ringColor
import com.composeunstyled.FocusRingVisibility
import com.composeunstyled.collectIsFocusVisibleAsState
import com.composeunstyled.outline
import com.composeunstyled.theme.Theme

@Composable
fun Modifier.focusRing(
    interactionSource: InteractionSource,
    width: Dp = 2.dp,
    color: Color = Theme[colors][ringColor],
    shape: Shape = RectangleShape,
    offset: Dp = 0.dp,
    visibility: FocusRingVisibility = FocusRingVisibility.FocusVisible,
): Modifier {
  val showFocusRing by
      if (visibility == FocusRingVisibility.FocusVisible) {
        interactionSource.collectIsFocusVisibleAsState()
      } else {
        interactionSource.collectIsFocusedAsState()
      }
  val animatedWidth by
      animateDpAsState(
          targetValue = if (showFocusRing) width else 0.dp,
          animationSpec = tween(durationMillis = 120),
          label = "FocusRingWidth",
      )

  return this then
      Modifier.outline(
          width = animatedWidth,
          color = color,
          shape = shape,
          offset = offset,
      )
}

@Composable
fun Modifier.bouncyPress(
    interactionSource: InteractionSource,
    enabled: Boolean = true,
    pressedScale: Float = 0.98f,
): Modifier {
  val pressed by interactionSource.collectIsPressedAsState()
  val scale by
      animateFloatAsState(
          targetValue = if (enabled && pressed) pressedScale else 1f,
          animationSpec =
              spring(
                  dampingRatio = Spring.DampingRatioMediumBouncy,
                  stiffness = Spring.StiffnessMediumLow,
              ),
          label = "BouncyPressScale",
      )

  return this then
      Modifier.graphicsLayer {
        scaleX = scale
        scaleY = scale
      }
}
