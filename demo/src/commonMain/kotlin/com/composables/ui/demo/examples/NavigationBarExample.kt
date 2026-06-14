package com.composables.ui.demo.examples

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Bell
import com.composables.icons.lucide.Heart
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Plus
import com.composables.icons.lucide.Search
import com.composables.ui.components.Icon
import com.composables.ui.components.NavigationBar
import com.composables.ui.components.NavigationBarItem

@Composable
fun NavigationBarExample() {
    var selectedItem by remember { mutableStateOf("Feed") }
    NavigationBar(modifier = Modifier.fillMaxWidth()) {
        NavigationBarItem(
            selected = selectedItem == "Feed",
            onClick = { selectedItem = "Feed" },
            modifier = Modifier.weight(1f),
        ) {
            Icon(Lucide.Heart, contentDescription = "Feed", modifier = Modifier.size(20.dp))
        }
        NavigationBarItem(
            selected = selectedItem == "Search",
            onClick = { selectedItem = "Search" },
            modifier = Modifier.weight(1f),
        ) {
            Icon(Lucide.Search, contentDescription = "Search", modifier = Modifier.size(20.dp))
        }
        NavigationBarItem(
            selected = selectedItem == "Create",
            onClick = { selectedItem = "Create" },
            modifier = Modifier.weight(1f),
        ) {
            Icon(Lucide.Plus, contentDescription = "Create", modifier = Modifier.size(20.dp))
        }
        NavigationBarItem(
            selected = selectedItem == "Activity",
            onClick = { selectedItem = "Activity" },
            modifier = Modifier.weight(1f),
        ) {
            Icon(Lucide.Bell, contentDescription = "Activity", modifier = Modifier.size(20.dp))
        }
    }
}
