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

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.composables.ui.theme.alphas
import com.composables.ui.theme.borderColor
import com.composables.ui.theme.colors
import com.composables.ui.theme.controlColor
import com.composables.ui.theme.defaultIndication
import com.composables.ui.theme.disabledAlpha
import com.composables.ui.theme.indications
import com.composables.ui.theme.onPanelColor
import com.composables.ui.theme.primaryColor
import com.composables.ui.theme.ringColor
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.SelectedIndicator
import com.composeunstyled.UnstyledRadioButton
import com.composeunstyled.UnstyledRadioGroup
import com.composeunstyled.buildModifier
import com.composeunstyled.theme.Theme

/**
 * A single-selection group that coordinates radio options.
 * @param value Currently selected value for the radio group or the value represented by a radio.
 * @param onValueChange Called when the selected value changes.
 * @param modifier Modifier applied to the component.
 * @param accessibilityLabel Accessible label announced for the radio group.
 * @param content Composable content displayed by the component.
 */
@Composable
fun <T> RadioGroup(
  value: T?,
  onValueChange: (T) -> Unit,
  modifier: Modifier = Modifier,
  accessibilityLabel: String? = null,
  content: @Composable () -> Unit,
) {
  UnstyledRadioGroup(value, onValueChange, modifier, accessibilityLabel) {
    content()
  }
}

/**
 * A selectable option inside a RadioGroup.
 * @param value Currently selected value for the radio group or the value represented by a radio.
 * @param modifier Modifier applied to the component.
 * @param enabled Whether the radio option can be interacted with.
 * @param interactionSource Interaction source used for focus and press state.
 * @param content Composable content displayed by the component.
 */
@Composable
fun <T> Radio(
  value: T,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  content: (@Composable RowScope.() -> Unit)? = null,
) {
  val rowShape = RoundedCornerShape(8.dp)
  UnstyledRadioButton(
    value = value,
    enabled = enabled,
    interactionSource = interactionSource,
    modifier = modifier
      .clip(rowShape)
      .padding(2.dp)
      .then(buildModifier { if (!enabled) add(Modifier.alpha(Theme[alphas][disabledAlpha])) }),
  ) {
    Row(
      horizontalArrangement = Arrangement.spacedBy(10.dp),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      SelectedIndicator(
        enter = fadeIn(),
        exit = fadeOut(),
        indication = Theme[indications][defaultIndication],
        modifier = Modifier
          .focusRing(
            interactionSource = interactionSource,
            color = Theme[colors][ringColor],
            shape = CircleShape,
          )
          .clip(CircleShape)
          .background(Theme[colors][controlColor], CircleShape)
          .border(1.dp, Theme[colors][borderColor], CircleShape)
          .size(20.dp),
      ) {
        Box(
          modifier = Modifier.size(20.dp),
          contentAlignment = Alignment.Center,
        ) {
          Box(Modifier.size(10.dp).background(Theme[colors][primaryColor], CircleShape))
        }
      }
      if (content != null) {
        ProvideContentColor(Theme[colors][onPanelColor]) {
          content()
        }
      }
    }
  }
}
