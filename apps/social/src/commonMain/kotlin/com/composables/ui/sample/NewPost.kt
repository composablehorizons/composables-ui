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
package com.composables.ui.sample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.tooling.insets.previewNavigationBarPadding
import com.composables.tooling.insets.previewSoftKeyboardPadding
import com.composables.ui.components.Button
import com.composables.ui.components.ButtonStyle
import com.composables.ui.components.CenteredToolbar
import com.composables.ui.components.Icon
import com.composables.ui.components.IconButton
import com.composables.ui.components.Text
import com.composables.ui.components.TextField
import com.composables.ui.components.TextFieldStyle
import com.composables.ui.sample.components.Avatar
import com.composables.ui.sample.iconography.Film
import com.composables.ui.sample.iconography.Icons
import com.composables.ui.sample.iconography.Image
import com.composables.ui.sample.iconography.MapPin
import com.composables.ui.sample.iconography.Smile
import com.composables.ui.sample.iconography.Vote
import com.composables.ui.sample.iconography.X
import com.composables.ui.theme.Large
import com.composables.ui.theme.Tall
import com.composables.ui.theme.colors
import com.composables.ui.theme.mutedColor
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.buildModifier
import com.composeunstyled.currentWindowHeightBreakpoint
import com.composeunstyled.currentWindowWidthBreakpoint
import com.composeunstyled.theme.Theme

@Composable
fun NewPost(onBackClick: () -> Unit) {
  val textFieldState = rememberTextFieldState()
  val canPost by derivedStateOf { textFieldState.text.isNotBlank() }
  val widthBreakpoint = currentWindowWidthBreakpoint()
  val heightBreakpoint = currentWindowHeightBreakpoint()
  val useDialog = widthBreakpoint isAtLeast Large || heightBreakpoint isAtLeast Tall

  Column(
      Modifier.padding(vertical = 8.dp)
          .previewSoftKeyboardPadding()
          .then(
              buildModifier {
                if (!useDialog) {
                  add(Modifier.previewNavigationBarPadding())
                }
              },
          ),
  ) {
    CenteredToolbar(
        modifier = Modifier.fillMaxWidth(),
        leading = {
          IconButton(
              onClick = onBackClick,
              style = ButtonStyle.Ghost,
          ) {
            Icon(Icons.X, contentDescription = "Dismiss")
          }
        },
        title = { Text("New Post") },
    )

    Column(Modifier.padding(horizontal = 12.dp)) {
      Row(
          horizontalArrangement = Arrangement.spacedBy(12.dp),
          verticalAlignment = Alignment.Top,
      ) {
        Avatar(
            url = signedInProfile.avatarUrl,
        )
        TextField(
            state = textFieldState,
            modifier = Modifier.weight(1f),
            style = TextFieldStyle.Ghost,
            placeholder = { Text("What's up?") },
            lineLimits = TextFieldLineLimits.MultiLine(),
        )
      }

      Spacer(Modifier.height(16.dp))
      Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        ProvideContentColor(Theme[colors][mutedColor]) {
          IconButton(
              onClick = { /* TODO */},
              style = ButtonStyle.Ghost,
          ) {
            Icon(Icons.Image, contentDescription = "Pick image")
          }
          IconButton(
              onClick = { /* TODO */},
              style = ButtonStyle.Ghost,
          ) {
            Icon(Icons.Smile, contentDescription = "Add emoji")
          }
          IconButton(
              onClick = { /* TODO */},
              style = ButtonStyle.Ghost,
          ) {
            Icon(Icons.Film, contentDescription = "Add GIF")
          }
          IconButton(
              onClick = { /* TODO */},
              style = ButtonStyle.Ghost,
          ) {
            Icon(Icons.Vote, contentDescription = "Create poll")
          }
          IconButton(
              onClick = { /* TODO */},
              style = ButtonStyle.Ghost,
          ) {
            Icon(Icons.MapPin, contentDescription = "Add location")
          }
        }

        Spacer(Modifier.weight(1f))
        Button(onClick = { /* TODO */}, enabled = canPost, style = ButtonStyle.Primary) {
          Text("Post")
        }
      }
    }
  }
}
