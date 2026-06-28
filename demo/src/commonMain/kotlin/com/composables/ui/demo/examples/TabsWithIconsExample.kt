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
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.BookOpen
import com.composables.icons.lucide.Code
import com.composables.icons.lucide.Eye
import com.composables.icons.lucide.Lucide
import com.composables.ui.components.Icon
import com.composables.ui.components.Tab
import com.composables.ui.components.TabList
import com.composables.ui.components.TabPanel
import com.composables.ui.components.Tabs
import com.composables.ui.components.Text

@Composable
fun TabsWithIconsExample() {
  val tabs = listOf(
    TabsExampleTab("Preview", Lucide.Eye),
    TabsExampleTab("Code", Lucide.Code),
    TabsExampleTab("Docs", Lucide.BookOpen),
  )
  var selected by remember { mutableStateOf(tabs.first()) }
  Tabs(
    selectedTab = selected,
    onSelectedTabChange = { selected = it },
    orderedTabs = tabs,
  ) {
    Column(
      verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
      TabList {
        tabs.forEach { tab ->
          Tab(
            key = tab,
            icon = {
              Icon(
                imageVector = tab.icon,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
              )
            },
            text = {
              Text(tab.label)
            },
          )
        }
      }
      tabs.forEach { tab ->
        TabPanel(tab) {
          Text("${tab.label} content")
        }
      }
    }
  }
}

private data class TabsExampleTab(
  val label: String,
  val icon: ImageVector,
)
