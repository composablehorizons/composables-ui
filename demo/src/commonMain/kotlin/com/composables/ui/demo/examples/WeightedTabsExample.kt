package com.composables.ui.demo.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.ui.components.Tab
import com.composables.ui.components.TabList
import com.composables.ui.components.TabPanel
import com.composables.ui.components.Tabs
import com.composables.ui.components.Text

@Composable
fun WeightedTabsExample() {
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
            TabList(modifier = Modifier.fillMaxWidth()) {
                tabs.forEach { tab ->
                    Tab(
                        key = tab,
                        modifier = Modifier.weight(1f),
                        text = {
                            Text(tab)
                        },
                    )
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
