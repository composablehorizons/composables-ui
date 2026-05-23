package com.composables.ui.demo.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.ui.components.AccordionPanel
import com.composables.ui.components.Tab
import com.composables.ui.components.TabGroup
import com.composables.ui.components.TabList
import com.composables.ui.components.TabPanel
import com.composables.ui.components.Text

@Composable
fun TabGroupExample() {
    val tabs = listOf("Preview", "Code", "Docs")
    var selected by remember { mutableStateOf(tabs.first()) }
    TabGroup(
        selectedTab = selected,
        onSelectedTabChange = { selected = it },
        tabs = tabs,
    ) {
        Column(
            modifier = Modifier.widthIn(max = 360.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            TabList {
                tabs.forEach { tab ->
                    Tab(tab) { Text(tab) }
                }
            }
            tabs.forEach { tab ->
                TabPanel(tab) {
                    AccordionPanel {
                        Text("$tab content")
                    }
                }
            }
        }
    }
}
