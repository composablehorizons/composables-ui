package com.composables.one.demo.examples

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.composables.icons.lucide.ChartNoAxesCombined
import com.composables.icons.lucide.Cog
import com.composables.icons.lucide.House
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Package
import com.composables.one.components.Icon
import com.composables.one.components.SideBar
import com.composables.one.components.SideBarItem
import com.composables.one.components.Text

@Composable
fun SideBarExample() {
    var selectedIndex by remember { mutableStateOf(0) }

    SideBar(
        navigationItems = {
            SideBarItem(
                onClick = { selectedIndex = 0 },
                selected = selectedIndex == 0,
                modifier = Modifier.fillMaxWidth(),
                icon = { Icon(Lucide.House, contentDescription = null) },
                title = { Text("Home") }
            )

            SideBarItem(
                onClick = { selectedIndex = 1 },
                selected = selectedIndex == 1,
                modifier = Modifier.fillMaxWidth(),
                icon = { Icon(Lucide.ChartNoAxesCombined, contentDescription = null) },
                title = { Text("Orders") }
            )

            SideBarItem(
                onClick = { selectedIndex = 2 },
                selected = selectedIndex == 2,
                modifier = Modifier.fillMaxWidth(),
                icon = { Icon(Lucide.Package, contentDescription = null) },
                title = { Text("Products") }
            )

            SideBarItem(
                onClick = { selectedIndex = 3 },
                selected = selectedIndex == 3,
                modifier = Modifier.fillMaxWidth(),
                icon = { Icon(Lucide.Cog, contentDescription = null) },
                title = { Text("Settings") }
            )
        }
    )
}