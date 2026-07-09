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

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composables.ui.theme.colors
import com.composables.ui.theme.controlColor
import com.composables.ui.theme.primaryColor
import com.composeunstyled.Indicator
import com.composeunstyled.UnstyledProgress
import com.composeunstyled.buildModifier
import com.composeunstyled.theme.Theme

/**
 * A determinate progress indicator for known completion state.
 *
 * @param progress Current progress value clamped between 0 and 1.
 * @param modifier Modifier applied to the indicator.
 * @param shape Shape used for the track and indicator.
 * @param trackColor Color used for the indicator track.
 * @param indicatorColor Color used for the active progress indicator.
 * @param borderColor Optional border color drawn around the track.
 * @param height Height of the progress indicator.
 */
@Composable
fun ProgressIndicator(
    progress: Float,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(999.dp),
    trackColor: Color = Theme[colors][controlColor],
    indicatorColor: Color = Theme[colors][primaryColor],
    borderColor: Color = Color.Unspecified,
    height: Dp = 6.dp,
) {
  val animatedProgress by
      animateFloatAsState(
          targetValue = progress.coerceIn(0f, 1f),
          animationSpec = tween(durationMillis = 250),
          label = "ProgressIndicatorProgress",
      )

  UnstyledProgress(
      progress = animatedProgress,
      modifier =
          modifier.progressIndicatorContainer(
              shape = shape,
              trackColor = trackColor,
              borderColor = borderColor,
              height = height,
          ),
  ) {
    Indicator(
        modifier = Modifier.clip(shape).background(indicatorColor, shape),
    )
  }
}

/**
 * A looping progress indicator for ongoing work without a known end point.
 *
 * @param modifier Modifier applied to the indicator.
 * @param shape Shape used for the track and indicator.
 * @param trackColor Color used for the indicator track.
 * @param indicatorColor Color used for the active progress indicator.
 * @param borderColor Optional border color drawn around the track.
 * @param height Height of the progress indicator.
 */
@Composable
fun IndeterminateProgressIndicator(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(999.dp),
    trackColor: Color = Theme[colors][controlColor],
    indicatorColor: Color = Theme[colors][primaryColor],
    borderColor: Color = Color.Unspecified,
    height: Dp = 6.dp,
) {
  val transition = rememberInfiniteTransition()
  val offset by
      transition.animateFloat(
          initialValue = 0f,
          targetValue = 1f,
          animationSpec =
              infiniteRepeatable(
                  animation = tween(durationMillis = 1100, easing = LinearEasing),
                  repeatMode = RepeatMode.Restart,
              ),
      )
  UnstyledProgress(
      modifier =
          modifier.progressIndicatorContainer(
              shape = shape,
              trackColor = trackColor,
              borderColor = borderColor,
              height = height,
          ),
  ) {
    BoxWithConstraints(Modifier.fillMaxWidth().fillMaxHeight()) {
      val indicatorWidth = maxWidth * 0.35f
      val indicatorOffset = (maxWidth + indicatorWidth) * offset - indicatorWidth
      Box(
          Modifier.fillMaxHeight()
              .width(indicatorWidth)
              .offset(x = indicatorOffset)
              .clip(shape)
              .background(indicatorColor, shape),
      )
    }
  }
}

private fun Modifier.progressIndicatorContainer(
    shape: Shape,
    trackColor: Color,
    borderColor: Color,
    height: Dp,
): Modifier {
  return this.height(height)
      .clip(shape)
      .background(trackColor, shape)
      .then(
          buildModifier {
            if (borderColor.isSpecified && borderColor != Color.Transparent) {
              add(Modifier.border(1.dp, borderColor, shape))
            }
          },
      )
}
