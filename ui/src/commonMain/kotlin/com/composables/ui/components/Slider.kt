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

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.composables.ui.theme.alphas
import com.composables.ui.theme.colors
import com.composables.ui.theme.disabledAlpha
import com.composables.ui.theme.primaryColor
import com.composables.ui.theme.ringColor
import com.composables.ui.theme.secondaryColor
import com.composables.ui.theme.thumbColor
import com.composeunstyled.FocusRingVisibility
import com.composeunstyled.SliderState
import com.composeunstyled.UnstyledSlider
import com.composeunstyled.buildModifier
import com.composeunstyled.theme.Theme

/**
 * A slider for selecting a value from a bounded range.
 * @param value Current value shown by the slider.
 * @param onValueChange Called when the slider value changes.
 * @param modifier Modifier applied to the slider.
 * @param enabled Whether the slider can be interacted with.
 * @param valueRange Minimum and maximum values allowed by the slider.
 * @param steps Number of discrete steps between the range endpoints.
 * @param orientation Whether the slider is laid out horizontally or vertically.
 * @param onValueChangeFinished Called when a drag or input interaction finishes.
 * @param interactionSource Interaction source used for focus, drag, and press state.
 */
@Composable
fun Slider(
  value: Float,
  onValueChange: (Float) -> Unit,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
  steps: Int = 0,
  orientation: Orientation = Orientation.Horizontal,
  onValueChangeFinished: (() -> Unit)? = null,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
  val shape = RoundedCornerShape(999.dp)
  UnstyledSlider(
    value = value,
    onValueChange = onValueChange,
    enabled = enabled,
    valueRange = valueRange,
    steps = steps,
    onValueChangeFinished = onValueChangeFinished,
    orientation = orientation,
    interactionSource = interactionSource,
    modifier = modifier
      .heightIn(min = 32.dp)
      .then(buildModifier { if (!enabled) add(Modifier.alpha(Theme[alphas][disabledAlpha])) }),
    track = { SliderTrack(it, shape, filledColor = Theme[colors][primaryColor]) },
    thumb = { SliderThumb(interactionSource) },
  )
}

@Composable
private fun SliderTrack(
  state: SliderState,
  shape: Shape,
  backgroundColor: Color = Theme[colors][secondaryColor],
  filledColor: Color,
) {
  if (state.orientation == Orientation.Vertical) {
    Box(
      modifier = Modifier
        .width(SliderThumbSize)
        .fillMaxHeight(),
      contentAlignment = Alignment.Center,
    ) {
      Box(
        Modifier
          .width(SliderTrackHeight)
          .fillMaxHeight()
          .padding(vertical = SliderThumbSize / 2)
          .clip(shape)
          .background(backgroundColor, shape),
        contentAlignment = Alignment.BottomCenter,
      ) {
        Box(
          Modifier
            .fillMaxWidth()
            .fillMaxHeight(state.fraction)
            .background(filledColor),
        )
      }
    }
  } else {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(SliderThumbSize),
      contentAlignment = Alignment.Center,
    ) {
      Box(
        Modifier
          .fillMaxWidth()
          .padding(horizontal = SliderThumbSize / 2)
          .height(SliderTrackHeight)
          .clip(shape)
          .background(backgroundColor, shape),
      ) {
        Box(
          Modifier.fillMaxWidth(state.fraction).height(SliderTrackHeight)
            .background(filledColor),
        )
      }
    }
  }
}

@Composable
private fun SliderThumb(interactionSource: MutableInteractionSource) {
  Box(
    Modifier
      .focusRing(
        interactionSource = interactionSource,
        color = Theme[colors][ringColor],
        shape = CircleShape,
        visibility = FocusRingVisibility.Focused,
      )
      .border(1.dp, Theme[colors][ringColor], CircleShape)
      .size(SliderThumbSize)
      .background(Theme[colors][thumbColor], CircleShape),
  )
}

private val SliderTrackHeight = 6.dp
private val SliderThumbSize = 18.dp
