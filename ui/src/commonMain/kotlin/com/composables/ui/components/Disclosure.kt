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
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composables.ui.theme.InteractionMode
import com.composables.ui.theme.LocalInteractionMode
import com.composables.ui.theme.alphas
import com.composables.ui.theme.colors
import com.composables.ui.theme.defaultIndication
import com.composables.ui.theme.disabledAlpha
import com.composables.ui.theme.indications
import com.composables.ui.theme.mediumShape
import com.composables.ui.theme.ringColor
import com.composables.ui.theme.shapes
import com.composeunstyled.UnstyledDisclosedContent
import com.composeunstyled.UnstyledDisclosure
import com.composeunstyled.UnstyledDisclosureButton
import com.composeunstyled.theme.Theme

/**
 * A container that coordinates disclosure state and content.
 *
 * @param expanded Whether the disclosure content is expanded.
 * @param onExpandedChange Called when the disclosure expanded state changes.
 * @param modifier Modifier applied to the component.
 * @param content Composable content displayed by the component.
 */
@Composable
fun Disclosure(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
  UnstyledDisclosure(
      expanded = expanded,
      onExpandedChange = onExpandedChange,
      modifier = modifier,
  ) {
    CompositionLocalProvider(LocalDisclosureExpanded provides expanded) { Column { content() } }
  }
}

/**
 * A button that toggles the disclosure between expanded and collapsed.
 *
 * @param modifier Modifier applied to the component.
 * @param enabled Whether the disclosure button can be interacted with.
 * @param contentPadding Padding applied inside the component content.
 * @param indicator Optional trailing indicator that receives the expanded state.
 * @param interactionSource Interaction source used for focus and press state.
 * @param content Composable content displayed by the component.
 */
@Composable
fun DisclosureButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
    indicator: (@Composable (expanded: Boolean) -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit,
) {
  val expanded = LocalDisclosureExpanded.current
  val shape = Theme[shapes][mediumShape]
  val alpha = if (enabled) 1f else Theme[alphas][disabledAlpha]

  UnstyledDisclosureButton(
      modifier =
          modifier
              .bouncyPress(
                  interactionSource = interactionSource,
                  enabled = enabled,
              )
              .focusRing(
                  interactionSource = interactionSource,
                  color = Theme[colors][ringColor],
                  shape = shape,
              )
              .alpha(alpha)
              .clip(shape),
      enabled = enabled,
      contentPadding = PaddingValues(0.dp),
      indication = Theme[indications][defaultIndication],
      interactionSource = interactionSource,
      contentAlignment = Alignment.CenterStart,
  ) {
    Row(
        modifier = Modifier.heightIn(min = disclosureButtonMinHeight()).padding(contentPadding),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
      content()
      if (indicator != null) {
        Spacer(Modifier.weight(1f))
        indicator(expanded)
      }
    }
  }
}

/**
 * The expandable content region of a disclosure.
 *
 * @param modifier Modifier applied to the component.
 * @param contentPadding Padding applied inside the component content.
 * @param enter Transition used when the disclosure panel expands.
 * @param exit Transition used when the disclosure panel collapses.
 * @param content Composable content displayed by the component.
 */
@Composable
fun DisclosurePanel(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(12.dp),
    enter: EnterTransition = expandVertically(animationSpec = spring()) + fadeIn(),
    exit: ExitTransition = shrinkVertically(animationSpec = spring()) + fadeOut(),
    content: @Composable () -> Unit,
) {
  UnstyledDisclosedContent(
      modifier = modifier,
      enter = enter,
      exit = exit,
  ) {
    Box(
        Modifier.padding(contentPadding),
    ) {
      content()
    }
  }
}

private val LocalDisclosureExpanded = staticCompositionLocalOf { false }

@Composable
private fun disclosureButtonMinHeight(): Dp {
  return if (LocalInteractionMode.current == InteractionMode.Touch) 48.dp else 36.dp
}
