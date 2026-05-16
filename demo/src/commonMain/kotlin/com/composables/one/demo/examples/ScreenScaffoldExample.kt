package com.composables.one.demo.examples

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.ChartNoAxesCombined
import com.composables.icons.lucide.Cog
import com.composables.icons.lucide.House
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Package
import com.composables.one.BottomNavigationBar
import com.composables.one.Icon
import com.composables.one.PrimaryTab
import com.composables.one.SideBar
import com.composables.one.SideBarItem
import com.composables.one.Text
import com.composables.one.ScreenScaffold

@Composable
fun ScreenScaffoldExample() {
    class TopLevelDestination(val route: String, val name: String, val icon: ImageVector)

    val destinations = listOf(
        TopLevelDestination("home", "Home", Lucide.House),
        TopLevelDestination("orders", "Orders", Lucide.ChartNoAxesCombined),
        TopLevelDestination("products", "Products", Lucide.Package),
        TopLevelDestination("settings", "Settings", Lucide.Cog)
    )

    var currentRoute by remember { mutableStateOf(destinations.first()) }

    ScreenScaffold(
        sideBar = {
            SideBar(
                modifier = Modifier.fillMaxHeight(),
                navigationItems = {
                    destinations.forEach { destination ->
                        val selected = currentRoute == destination
                        SideBarItem(
                            onClick = { currentRoute = destination },
                            selected = selected,
                            modifier = Modifier.fillMaxWidth(),
                            icon = { Icon(destination.icon, contentDescription = null) },
                            title = { Text(destination.name) }
                        )
                    }
                },
            )
        },
        bottomBar = {
            BottomNavigationBar {
                destinations.forEach { destination ->
                    val selected = currentRoute == destination
                    PrimaryTab(
                        selected = selected,
                        onSelected = { currentRoute = destination },
                        modifier = Modifier.weight(1f),
                        icon = {
                            Icon(destination.icon, contentDescription = null)
                        },
                        title = {
                            Text(
                                text = destination.name,
                                overflow = TextOverflow.Ellipsis,
                                singleLine = true
                            )
                        },
                    )
                }
            }
        }
    ) {
        // screen content
    }
}
