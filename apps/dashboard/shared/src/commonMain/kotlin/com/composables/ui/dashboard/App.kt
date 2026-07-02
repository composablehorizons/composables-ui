package com.composables.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.ChartNoAxesCombined
import com.composables.icons.lucide.Cog
import com.composables.icons.lucide.House
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Package
import com.composables.ui.components.Icon
import com.composables.ui.components.Sidebar
import com.composables.ui.components.SidebarItem
import com.composables.ui.components.Text
import com.composables.ui.theme.colors
import com.composables.ui.theme.ComposablesTheme
import com.composables.ui.theme.backgroundColor
import com.composeunstyled.theme.Theme

@Composable
fun App() {
    data class DashboardDestination(
        val label: String,
        val icon: ImageVector,
    )

    val destinations = listOf(
        DashboardDestination(label = "Home", icon = Lucide.House),
        DashboardDestination(label = "Orders", icon = Lucide.ChartNoAxesCombined),
        DashboardDestination(label = "Products", icon = Lucide.Package),
        DashboardDestination(label = "Settings", icon = Lucide.Cog),
    )
    var selectedIndex by remember { mutableStateOf(0) }

    ComposablesTheme {
        Row(
            modifier = Modifier
                .safeDrawingPadding()
                .fillMaxSize()
                .background(Theme[colors][backgroundColor]),
        ) {
            Sidebar(
                expanded = true,
                modifier = Modifier.fillMaxHeight(),
            ) {
                destinations.forEachIndexed { index, destination ->
                    SidebarItem(
                        selected = selectedIndex == index,
                        onClick = { selectedIndex = index },
                        icon = {
                            Icon(destination.icon, contentDescription = null)
                        },
                        text = {
                            Text(destination.label, singleLine = true)
                        },
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
            ) {
                Text(
                    text = destinations[selectedIndex].label,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}
