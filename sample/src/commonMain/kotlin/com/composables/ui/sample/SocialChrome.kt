package com.composables.ui.sample

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Bell
import com.composables.icons.lucide.House
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Plus
import com.composables.icons.lucide.Search
import com.composables.icons.lucide.User
import com.composables.ui.components.Icon
import com.composables.ui.components.NavigationBar
import com.composables.ui.components.NavigationBarItem
import com.composables.ui.components.NavigationSidebar
import com.composables.ui.components.NavigationSidebarItem
import com.composables.ui.components.Text
import com.composables.ui.sample.components.Avatar
import com.composables.ui.sample.data.profiles
import com.composables.ui.theme.muted
import com.composables.ui.theme.colors
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.theme.Theme

@Composable
fun SocialBottomBar(
    homeSelected: Boolean,
    searchSelected: Boolean,
    composeSelected: Boolean,
    notificationsSelected: Boolean,
    profileSelected: Boolean,
    onHomeClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onComposeClick: () -> Unit = {},
    onNotificationsClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    NavigationBar(modifier) {
        NavigationBarItem(selected = homeSelected, onClick = onHomeClick) {
            Icon(Lucide.House, contentDescription = "Home")
        }
        NavigationBarItem(selected = searchSelected, onClick = onSearchClick) {
            Icon(Lucide.Search, contentDescription = "Search")
        }
        NavigationBarItem(selected = composeSelected, onClick = onComposeClick) {
            Icon(Lucide.Plus, contentDescription = "New post")
        }
        NavigationBarItem(selected = notificationsSelected, onClick = onNotificationsClick) {
            Icon(Lucide.Bell, contentDescription = "Notifications")
        }
        NavigationBarItem(selected = profileSelected, onClick = onProfileClick) {
            Icon(Lucide.User, contentDescription = "Profile")
        }
    }
}

@Composable
fun SocialSidebar(
    homeSelected: Boolean,
    searchSelected: Boolean,
    composeSelected: Boolean,
    notificationsSelected: Boolean,
    profileSelected: Boolean,
    onHomeClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onComposeClick: () -> Unit = {},
    onNotificationsClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val profile = profiles.first()

    NavigationSidebar(
        modifier = modifier,
        header = {
            Text(
                text = "Leafly",
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        },
        content = {
            SocialSidebarItem(
                selected = homeSelected,
                icon = { Icon(Lucide.House) },
                label = "Home",
                onClick = onHomeClick,
            )
            SocialSidebarItem(
                selected = searchSelected,
                icon = { Icon(Lucide.Search) },
                label = "Search",
                onClick = onSearchClick,
            )
            SocialSidebarItem(
                selected = composeSelected,
                icon = { Icon(Lucide.Plus) },
                label = "New post",
                onClick = onComposeClick,
            )
            SocialSidebarItem(
                selected = notificationsSelected,
                icon = { Icon(Lucide.Bell) },
                label = "Notifications",
                onClick = onNotificationsClick,
            )
            SocialSidebarItem(
                selected = profileSelected,
                icon = { Icon(Lucide.User) },
                label = "Profile",
                onClick = onProfileClick,
            )
        },
        footer = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Avatar(url = profile.avatarUrl, size = 40)
                Column {
                    Text(text = profile.name, fontWeight = FontWeight.Medium, singleLine = true)
                    ProvideContentColor(Theme[colors][muted]) {
                        Text(text = "@${profile.handle}", singleLine = true)
                    }
                }
            }
        },
    )
}

@Composable
private fun ColumnScope.SocialSidebarItem(
    selected: Boolean,
    icon: @Composable () -> Unit,
    label: String,
    onClick: () -> Unit,
) {
    NavigationSidebarItem(
        selected = selected,
        onClick = onClick,
        icon = icon,
    ) {
        Text(text = label, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.weight(1f))
    }
}
