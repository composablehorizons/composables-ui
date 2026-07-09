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
package com.composables.ui.demo.examples

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.composables.ui.components.HorizontalScrollbar
import com.composables.ui.components.Text
import com.composables.ui.components.rememberVerticalScrollbarState
import com.composables.ui.theme.colors
import com.composables.ui.theme.controlColor
import com.composables.ui.theme.panelColor
import com.composeunstyled.theme.Theme

@Composable
fun DisabledHorizontalScrollbarExample() {
  val scrollState = rememberScrollState()
  val scrollbarState = rememberVerticalScrollbarState(scrollState)
  Box(
      modifier =
          Modifier.height(88.dp)
              .widthIn(max = 340.dp)
              .clip(RoundedCornerShape(8.dp))
              .background(Theme[colors][controlColor]),
  ) {
    Row(
        modifier = Modifier.horizontalScroll(scrollState).padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
      repeat(10) { index ->
        Box(
            modifier =
                Modifier.size(width = 96.dp, height = 40.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Theme[colors][panelColor]),
            contentAlignment = Alignment.Center,
        ) {
          Text("Item ${index + 1}")
        }
      }
    }
    HorizontalScrollbar(
        state = scrollbarState,
        enabled = false,
        modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth(),
    )
  }
}
