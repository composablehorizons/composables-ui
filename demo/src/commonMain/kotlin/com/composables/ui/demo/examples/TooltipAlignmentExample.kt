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
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.composables.ui.components.Button
import com.composables.ui.components.ButtonStyle
import com.composables.ui.components.Text
import com.composables.ui.components.Tooltip
import com.composables.ui.components.TooltipAlignment
import com.composables.ui.components.TooltipPanel

@Composable
fun TooltipAlignmentExample() {
  Row(
      horizontalArrangement = Arrangement.spacedBy(12.dp),
  ) {
    TooltipButton(
        label = "Start",
        tooltipText = "Aligned to the start edge",
        alignment = TooltipAlignment.Start,
    )
    TooltipButton(
        label = "Center",
        tooltipText = "Aligned to the center",
        alignment = TooltipAlignment.Center,
    )
    TooltipButton(
        label = "End",
        tooltipText = "Aligned to the end edge",
        alignment = TooltipAlignment.End,
    )
  }
}

@Composable
private fun TooltipButton(
    label: String,
    tooltipText: String,
    alignment: TooltipAlignment,
) {
  Tooltip(
      alignment = alignment,
      panel = { TooltipPanel { Text(text = tooltipText) } },
  ) {
    Button(onClick = {}, style = ButtonStyle.Outlined) { Text(text = label) }
  }
}
