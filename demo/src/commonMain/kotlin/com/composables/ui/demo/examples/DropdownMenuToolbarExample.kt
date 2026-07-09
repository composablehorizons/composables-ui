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

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.EllipsisVertical
import com.composables.icons.lucide.Heart
import com.composables.icons.lucide.Link
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Share
import com.composables.icons.lucide.Trash2
import com.composables.ui.components.ButtonStyle
import com.composables.ui.components.DropdownMenu
import com.composables.ui.components.DropdownMenuAlignment
import com.composables.ui.components.DropdownMenuItem
import com.composables.ui.components.DropdownMenuItemStyle
import com.composables.ui.components.DropdownMenuPanel
import com.composables.ui.components.Icon
import com.composables.ui.components.IconButton
import com.composables.ui.components.Text
import com.composables.ui.components.Toolbar

@Composable
fun DropdownMenuToolbarExample() {
  var expanded by remember { mutableStateOf(false) }

  Toolbar(
      modifier = Modifier.fillMaxWidth(),
      title = { Text("Details") },
      trailing = {
        DropdownMenu(
            expanded = expanded,
            onExpandedChange = { expanded = it },
            alignment = DropdownMenuAlignment.End,
            panel = {
              DropdownMenuPanel {
                DropdownMenuItem(
                    onClick = { expanded = false },
                    leading = {
                      Icon(
                          imageVector = Lucide.Share,
                          contentDescription = null,
                          modifier = Modifier.size(16.dp),
                      )
                    },
                ) {
                  Text("Share")
                }
                DropdownMenuItem(
                    onClick = { expanded = false },
                    leading = {
                      Icon(
                          imageVector = Lucide.Heart,
                          contentDescription = null,
                          modifier = Modifier.size(16.dp),
                      )
                    },
                ) {
                  Text("Add to favorites")
                }
                DropdownMenuItem(
                    onClick = { expanded = false },
                    leading = {
                      Icon(
                          imageVector = Lucide.Link,
                          contentDescription = null,
                          modifier = Modifier.size(16.dp),
                      )
                    },
                ) {
                  Text("Copy link")
                }
                DropdownMenuItem(
                    onClick = { expanded = false },
                    style = DropdownMenuItemStyle.Destructive,
                    leading = {
                      Icon(
                          imageVector = Lucide.Trash2,
                          contentDescription = null,
                          modifier = Modifier.size(16.dp),
                      )
                    },
                ) {
                  Text("Delete")
                }
              }
            },
        ) {
          IconButton(
              onClick = { expanded = expanded.not() },
              style = ButtonStyle.Ghost,
          ) {
            Icon(
                imageVector = Lucide.EllipsisVertical,
                contentDescription = "More options",
                modifier = Modifier.size(18.dp),
            )
          }
        }
      },
  )
}
