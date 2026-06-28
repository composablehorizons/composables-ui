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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Check
import com.composables.icons.lucide.ChevronsUpDown
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Monitor
import com.composables.icons.lucide.Moon
import com.composables.icons.lucide.Palette
import com.composables.icons.lucide.Sun
import com.composables.ui.components.Button
import com.composables.ui.components.ButtonStyle
import com.composables.ui.components.DropdownMenu
import com.composables.ui.components.DropdownMenuAlignment
import com.composables.ui.components.DropdownMenuItem
import com.composables.ui.components.DropdownMenuPanel
import com.composables.ui.components.Icon
import com.composables.ui.components.Text

@Composable
fun DropdownMenuExample() {
  var expanded by remember { mutableStateOf(false) }
  var selectedColorScheme by remember { mutableStateOf("Light") }

  DropdownMenu(
    expanded = expanded,
    onExpandedChange = { expanded = it },
    alignment = DropdownMenuAlignment.End,
    panel = {
      DropdownMenuPanel {
        DropdownMenuItem(
          onClick = {
            selectedColorScheme = "System"
            expanded = false
          },
          leading = {
            if (selectedColorScheme == "System") {
              Icon(
                imageVector = Lucide.Check,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
              )
            } else {
              Spacer(Modifier.width(16.dp))
            }
          },
          trailing = {
            Icon(
              imageVector = Lucide.Monitor,
              contentDescription = null,
              modifier = Modifier.size(16.dp),
            )
          },
        ) {
          Text("System")
        }
        DropdownMenuItem(
          onClick = {
            selectedColorScheme = "Dark"
            expanded = false
          },
          leading = {
            if (selectedColorScheme == "Dark") {
              Icon(
                imageVector = Lucide.Check,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
              )
            } else {
              Spacer(Modifier.width(16.dp))
            }
          },
          trailing = {
            Icon(
              imageVector = Lucide.Moon,
              contentDescription = null,
              modifier = Modifier.size(16.dp),
            )
          },
        ) {
          Text("Dark")
        }
        DropdownMenuItem(
          onClick = {
            selectedColorScheme = "Light"
            expanded = false
          },
          leading = {
            if (selectedColorScheme == "Light") {
              Icon(
                imageVector = Lucide.Check,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
              )
            } else {
              Spacer(Modifier.width(16.dp))
            }
          },
          trailing = {
            Icon(
              imageVector = Lucide.Sun,
              contentDescription = null,
              modifier = Modifier.size(16.dp),
            )
          },
        ) {
          Text("Light")
        }
      }
    },
  ) {
    Button(
      onClick = { expanded = expanded.not() },
      modifier = Modifier.fillMaxWidth(),
      style = ButtonStyle.Ghost,
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Icon(
          imageVector = Lucide.Palette,
          contentDescription = null,
          modifier = Modifier.size(18.dp),
        )
        Text("Color scheme")
        Spacer(Modifier.weight(1f))
        Text(selectedColorScheme)
        Icon(
          imageVector = Lucide.ChevronsUpDown,
          contentDescription = null,
          modifier = Modifier.size(16.dp),
        )
      }
    }
  }
}
