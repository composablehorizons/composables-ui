package com.composables.ui.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Bell
import com.composables.icons.lucide.CircleUser
import com.composables.icons.lucide.House
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Search
import com.composables.icons.lucide.SquarePen
import com.composables.ui.components.ButtonStyle
import com.composables.ui.components.Icon
import com.composables.ui.components.IconButton
import com.composables.ui.theme.background
import com.composables.ui.theme.border
import com.composables.ui.theme.colors
import com.composables.ui.theme.muted
import com.composables.ui.theme.onBackground
import com.composeunstyled.theme.Theme

@Composable
fun SocialBottomBar(
    onProfileClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .border(width = 1.dp, color = Theme[colors][border])
            .background(Theme[colors][background])
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            Lucide.House,
            contentDescription = "Home",
            modifier = Modifier.size(32.dp),
            tint = Theme[colors][onBackground]
        )
        Icon(Lucide.Search, contentDescription = "Search", modifier = Modifier.size(32.dp), tint = Theme[colors][muted])
        Icon(
            Lucide.SquarePen,
            contentDescription = "Compose",
            modifier = Modifier.size(32.dp),
            tint = Theme[colors][muted]
        )
        Icon(
            Lucide.Bell,
            contentDescription = "Notifications",
            modifier = Modifier.size(32.dp),
            tint = Theme[colors][muted]
        )
        IconButton(onClick = onProfileClick, style = ButtonStyle.Ghost) {
            Icon(
                Lucide.CircleUser,
                contentDescription = "Profile",
                modifier = Modifier.size(32.dp),
                tint = Theme[colors][muted]
            )
        }
    }
}
