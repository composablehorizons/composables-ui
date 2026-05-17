package com.composables.one.demo.examples

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.House
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.PersonStanding
import com.composables.icons.lucide.Search
import com.composables.one.Icon
import com.composables.one.SecondaryTab
import com.composables.one.TabBar
import com.composables.one.TabGroup
import com.composables.one.TabPanel
import com.composables.one.Text
import com.composables.one.styling.textStyles
import com.composables.one.styling.title
import com.composeunstyled.theme.Theme

@Composable
fun TabBarExample() {
    data class Category(val label: String, val icon: ImageVector)

    val Home = Category("Home", Lucide.House)
    val Search = Category("Search", Lucide.Search)
    val Profile = Category("Profile", Lucide.PersonStanding)

    var selectedTab by remember { mutableStateOf(Home) }
    val categories = listOf(Home, Search, Profile)

    TabGroup(
        selectedTab = selectedTab,
        tabs = categories,
        onSelectedTabChange = { selectedTab = it },
        modifier = Modifier.fillMaxSize(),
    ) {
        TabBar {
            categories.forEach { category ->
                SecondaryTab(
                    key = category,
                    selected = selectedTab == category,
                    onSelected = { selectedTab = category },
                    title = { Text(category.label) },
                    icon = { Icon(category.icon, contentDescription = null, modifier = Modifier.size(16.dp)) },
                )
            }
        }
        categories.forEach { category ->
            TabPanel(category, modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(category.icon, contentDescription = null, modifier = Modifier.size(96.dp).alpha(0.4f))
                    Spacer(Modifier.height(8.dp))
                    Text(category.label, style = Theme[textStyles][title])
                }
            }
        }
    }
}
