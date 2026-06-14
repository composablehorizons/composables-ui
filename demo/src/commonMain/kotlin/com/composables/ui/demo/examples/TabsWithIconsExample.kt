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
