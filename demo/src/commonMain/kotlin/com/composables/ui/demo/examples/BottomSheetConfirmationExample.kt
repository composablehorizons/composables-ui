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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.icons.lucide.Bell
import com.composables.icons.lucide.Lucide
import com.composables.ui.components.BottomSheet
import com.composables.ui.components.BottomSheetDetent
import com.composables.ui.components.Button
import com.composables.ui.components.ButtonStyle
import com.composables.ui.components.Icon
import com.composables.ui.components.Text
import com.composables.ui.components.rememberBottomSheetState
import com.composables.ui.theme.colors
import com.composables.ui.theme.mutedColor
import com.composeunstyled.LocalTextStyle
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.ProvideTextStyle
import com.composeunstyled.theme.Theme

@Composable
fun BottomSheetConfirmationExample() {
  val sheetState =
      rememberBottomSheetState(
          detents = listOf(BottomSheetDetent.Hidden, BottomSheetDetent.FullyExpanded),
      )

  Button(onClick = { sheetState.targetDetent = BottomSheetDetent.FullyExpanded }) {
    Text("Show confirmation")
  }

  BottomSheet(
      state = sheetState,
      onDismissRequest = { sheetState.targetDetent = BottomSheetDetent.Hidden },
      toolbar = {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
          Icon(
              imageVector = Lucide.Bell,
              contentDescription = null,
              modifier = Modifier.size(28.dp),
          )
          Text("Allow notifications?")
        }
      },
      footer = {
        Button(
            onClick = { sheetState.targetDetent = BottomSheetDetent.Hidden },
            modifier = Modifier.fillMaxWidth(),
            style = ButtonStyle.Primary,
        ) {
          Text("Allow")
        }
        Button(
            onClick = { sheetState.targetDetent = BottomSheetDetent.Hidden },
            modifier = Modifier.fillMaxWidth(),
            style = ButtonStyle.Secondary,
        ) {
          Text("Maybe later")
        }
      },
  ) {
    ProvideContentColor(Theme[colors][mutedColor]) {
      ProvideTextStyle(
          LocalTextStyle.current.merge(TextStyle(fontSize = 16.sp, lineHeight = 24.sp))) {
            Text(
                text =
                    "Get notified when projects finish syncing, comments mention you, or billing needs attention.",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
          }
    }
  }
}
