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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp
import com.composables.ui.components.Checkbox
import com.composables.ui.components.Text
import com.composables.ui.components.TriStateCheckbox

@Composable
fun TriStateCheckboxExample() {
  var marketing by remember { mutableStateOf(true) }
  var product by remember { mutableStateOf(false) }
  var security by remember { mutableStateOf(true) }

  val allSelected = marketing && product && security
  val noneSelected = !marketing && !product && !security
  val state =
      when {
        allSelected -> ToggleableState.On
        noneSelected -> ToggleableState.Off
        else -> ToggleableState.Indeterminate
      }

  Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
    TriStateCheckbox(
        state = state,
        onStateChange = { nextState ->
          val selected = nextState == ToggleableState.On
          marketing = selected
          product = selected
          security = selected
        },
    ) {
      Text("Select all")
    }

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
      Checkbox(checked = marketing, onCheckedChange = { marketing = it }) {
        Text("Marketing updates")
      }
      Checkbox(checked = product, onCheckedChange = { product = it }) {
        Text("Product announcements")
      }
      Checkbox(checked = security, onCheckedChange = { security = it }) { Text("Security alerts") }
    }
  }
}
