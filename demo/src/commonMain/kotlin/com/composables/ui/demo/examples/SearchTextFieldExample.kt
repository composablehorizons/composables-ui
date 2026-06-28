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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Search
import com.composables.icons.lucide.X
import com.composables.ui.components.ButtonSize
import com.composables.ui.components.ButtonStyle
import com.composables.ui.components.Icon
import com.composables.ui.components.IconButton
import com.composables.ui.components.Text
import com.composables.ui.components.TextField
import com.composables.ui.theme.colors
import com.composables.ui.theme.mutedColor
import com.composeunstyled.theme.Theme

@Composable
fun SearchTextFieldExample() {
  val state = rememberTextFieldState()

  TextField(
    state = state,
    modifier = Modifier.fillMaxWidth(),
    accessibilityLabel = "Search",
    placeholder = { Text("Search...") },
    leading = {
      Icon(
        imageVector = Lucide.Search,
        contentDescription = null,
        modifier = Modifier.size(16.dp),
        tint = Theme[colors][mutedColor],
      )
    },
    trailing = {
      AnimatedVisibility(
        visible = state.text.isNotEmpty(),
        enter = fadeIn(),
        exit = fadeOut(),
      ) {
        IconButton(
          onClick = { state.clearText() },
          modifier = Modifier.size(32.dp),
          style = ButtonStyle.Ghost,
          buttonSize = ButtonSize.Small,
        ) {
          Icon(
            imageVector = Lucide.X,
            contentDescription = "Clear search",
            modifier = Modifier.size(14.dp),
            tint = Theme[colors][mutedColor],
          )
        }
      }
    },
  )
}
