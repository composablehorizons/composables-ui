package com.composables.ui.sample

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Bell
import com.composables.icons.lucide.CircleUser
import com.composables.icons.lucide.House
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Search
import com.composables.icons.lucide.SquarePen
import com.composables.ui.components.Icon
import com.composables.ui.components.NavigationBar
import com.composables.ui.components.NavigationBarItem

@Composable
fun SocialBottomBar(
    onProfileClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    NavigationBar(modifier) {
        NavigationBarItem(selected = true, onClick = {}) {
            Icon(Lucide.House, contentDescription = "Home", modifier = Modifier.size(32.dp))
        }
        NavigationBarItem(selected = false, onClick = {}) {
            Icon(Lucide.Search, contentDescription = "Search", modifier = Modifier.size(32.dp))
        }
        NavigationBarItem(selected = false, onClick = {}) {
            Icon(Lucide.SquarePen, contentDescription = "Compose", modifier = Modifier.size(32.dp))
        }
        NavigationBarItem(selected = false, onClick = {}) {
            Icon(Lucide.Bell, contentDescription = "Notifications", modifier = Modifier.size(32.dp))
        }
        NavigationBarItem(selected = false, onClick = onProfileClick) {
            Icon(Lucide.CircleUser, contentDescription = "Profile", modifier = Modifier.size(32.dp))
        }
    }
}
