package com.composables.ui.demo.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
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
import com.composables.ui.components.TabOrientation
import com.composables.ui.components.TabPanel
import com.composables.ui.components.Text
import com.composables.ui.components.Tabs

@Composable
fun TabsExample() {
    val tabs = listOf("Preview", "Code", "Docs")
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
                    Tab(key = tab, text = { Text(tab) })
                }
            }
            tabs.forEach { tab ->
                TabPanel(tab) {
                    Text("$tab content")
                }
            }
        }
    }
}
