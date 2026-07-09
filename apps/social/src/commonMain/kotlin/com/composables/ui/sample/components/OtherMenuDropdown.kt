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
package com.composables.ui.sample.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.composables.ui.components.DropdownMenu
import com.composables.ui.components.DropdownMenuAlignment
import com.composables.ui.components.DropdownMenuItem
import com.composables.ui.components.DropdownMenuItemStyle
import com.composables.ui.components.DropdownMenuPanel
import com.composables.ui.components.DropdownMenuSeparator
import com.composables.ui.components.DropdownMenuSide
import com.composables.ui.components.Text
import com.composables.ui.sample.Appearance

@Composable
fun OtherMenuDropdown(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    appearance: Appearance,
    onAppearanceChange: (Appearance) -> Unit,
    side: DropdownMenuSide = DropdownMenuSide.Bottom,
    alignment: DropdownMenuAlignment = DropdownMenuAlignment.Start,
    anchor: @Composable () -> Unit,
) {
  DropdownMenu(
      expanded = expanded,
      onExpandedChange = onExpandedChange,
      side = side,
      alignment = alignment,
      panel = {
        DropdownMenuPanel(minWidth = 280.dp) {
          AppearanceSelector(
              selectedAppearance = appearance,
              onSelectedAppearanceChange = onAppearanceChange,
          )
          DropdownMenuSeparator()
          DropdownMenuItem(
              onClick = {},
              style = DropdownMenuItemStyle.Destructive,
          ) {
            Text("Log out")
          }
        }
      },
  ) {
    anchor()
  }
}
