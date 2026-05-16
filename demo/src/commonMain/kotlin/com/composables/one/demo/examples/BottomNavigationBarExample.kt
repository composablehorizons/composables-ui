package com.composables.one.demo.examples

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import com.composables.one.styling.textStyles
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
import com.composables.one.components.BottomNavigationBar
import com.composables.one.components.Icon
import com.composables.one.components.PrimaryTab
import com.composables.one.components.TabGroup
import com.composables.one.components.TabPanel
import com.composables.one.styling.title
import com.composeunstyled.UnstyledText
import com.composeunstyled.theme.Theme

@Composable
fun BottomNavigationBarExample() {
    class Category(val key: String, val icon: ImageVector)

    val Home = Category("Home", Lucide.House)
    val Search = Category("Search", Lucide.Search)
    val Profile = Category("Profile", Lucide.PersonStanding)

    var selectedTab by remember { mutableStateOf(Home.key) }
    val categories = listOf(Home, Search, Profile)

    TabGroup(selectedTab = selectedTab, tabs = categories.map { it.key }, modifier = Modifier.fillMaxWidth()) {
        categories.forEach { category ->
            TabPanel(
                key = category.key,
                modifier = Modifier.weight(1f).fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = category.icon,
                        contentDescription = null,
                        modifier = Modifier.size(96.dp).alpha(0.4f)
                    )
                    Spacer(Modifier.height(8.dp))
                    UnstyledText(category.key, style = Theme[textStyles][title])
                }
            }
        }

        BottomNavigationBar(Modifier.fillMaxWidth()) {
            categories.forEach { category ->
                val selected = selectedTab == category.key
                PrimaryTab(
                    key = category.key,
                    selected = selected,
                    onSelected = { selectedTab = category.key },
                    modifier = Modifier.weight(1f),
                    icon = { Icon(category.icon, contentDescription = null) },
                    title = { UnstyledText(category.key) }
                )
            }
        }
    }
}
