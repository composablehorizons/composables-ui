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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Bell
import com.composables.icons.lucide.Lucide
import com.composables.ui.components.AlertDialog
import com.composables.ui.components.Button
import com.composables.ui.components.ButtonStyle
import com.composables.ui.components.Icon
import com.composables.ui.components.Text

@Composable
fun AlertDialogWithIconExample() {
  var visible by remember { mutableStateOf(false) }
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
  ) {
    Button(onClick = { visible = true }) {
      Text(text = "Show dialog")
    }

    AlertDialog(
      visible = visible,
      onDismissRequest = { visible = false },
      icon = {
        Icon(
          imageVector = Lucide.Bell,
          contentDescription = null,
          modifier = Modifier.size(24.dp),
        )
      },
      title = { Text("Enable notifications?", textAlign = TextAlign.Center) },
      text = {
        Text(
          "Notifications help you keep up with important updates from this app.",
          textAlign = TextAlign.Center,
        )
      },
      positiveButton = {
        Button(
          onClick = { visible = false },
          modifier = Modifier.fillMaxWidth(),
          style = ButtonStyle.Primary,
        ) {
          Text("Allow")
        }
      },
      negativeButton = {
        Button(
          onClick = { visible = false },
          modifier = Modifier.fillMaxWidth(),
          style = ButtonStyle.Secondary,
        ) {
          Text("Not now")
        }
      },
    )
  }
}
