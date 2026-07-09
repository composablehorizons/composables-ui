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

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composables.ui.theme.borderColor
import com.composables.ui.theme.colors
import com.composeunstyled.UnstyledHorizontalSeparator
import com.composeunstyled.UnstyledVerticalSeparator
import com.composeunstyled.theme.Theme

/**
 * A horizontal divider line.
 *
 * @param modifier Modifier applied to the separator.
 * @param color Color used for the separator line.
 * @param thickness Thickness of the separator line.
 */
@Composable
fun HorizontalSeparator(
    modifier: Modifier = Modifier,
    color: Color = Theme[colors][borderColor],
    thickness: Dp = 1.dp,
) {
  UnstyledHorizontalSeparator(
      color = color,
      modifier = modifier.clip(RoundedCornerShape(100)),
      thickness = thickness,
  )
}

/**
 * A vertical divider line.
 *
 * @param modifier Modifier applied to the separator.
 * @param color Color used for the separator line.
 * @param thickness Thickness of the separator line.
 */
@Composable
fun VerticalSeparator(
    modifier: Modifier = Modifier,
    color: Color = Theme[colors][borderColor],
    thickness: Dp = 1.dp,
) {
  UnstyledVerticalSeparator(
      color = color,
      modifier = modifier.clip(RoundedCornerShape(100)),
      thickness = thickness,
  )
}
