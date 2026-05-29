package com.composables.ui.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Search
import com.composables.icons.lucide.X
import com.composables.ui.components.Button
import com.composables.ui.components.ButtonSize
import com.composables.ui.components.ButtonStyle
import com.composables.ui.components.HorizontalSeparator
import com.composables.ui.components.Icon
import com.composables.ui.components.IconButton
import com.composables.ui.components.Text
import com.composables.ui.components.TextField
import com.composables.ui.sample.components.Avatar
import com.composables.ui.sample.components.AvatarSize
import com.composables.ui.sample.data.ProfileId
import com.composables.ui.sample.data.SocialProfile
import com.composables.ui.sample.data.profiles
import com.composables.ui.theme.colors
import com.composables.ui.theme.muted
import com.composables.ui.theme.onBackground
import com.composables.ui.theme.onPanel
import com.composables.ui.theme.panel
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.theme.Theme

@Composable
fun Search(onProfileClick: (ProfileId) -> Unit) {
    val queryState = rememberTextFieldState()
    val query = queryState.text.toString()

    ProvideContentColor(Theme[colors][onPanel]) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme[colors][panel]),
            contentPadding = PaddingValues(bottom = 96.dp),
        ) {
            item {
                TextField(
                    state = queryState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 12.dp),
                    accessibilityLabel = "Search",
                    placeholder = { Text("Search") },
                    shape = CircleShape,
                    minHeight = 48.dp,
                    leading = {
                        Icon(
                            imageVector = Lucide.Search,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp),
                            tint = Theme[colors][muted],
                        )
                    },
                    trailing = {
                        if (query.isNotEmpty()) {
                            IconButton(
                                onClick = { queryState.clearText() },
                                modifier = Modifier.size(32.dp),
                                style = ButtonStyle.Ghost,
                                buttonSize = ButtonSize.Small,
                            ) {
                                Icon(
                                    imageVector = Lucide.X,
                                    contentDescription = "Clear search",
                                    modifier = Modifier.size(15.dp),
                                    tint = Theme[colors][muted],
                                )
                            }
                        }
                    },
                )
            }

            profiles.take(5).forEachIndexed { index, profile ->
                item(key = profile.handle) {
                    if (index != 0) {
                        HorizontalSeparator()
                    }
                    SearchProfileRow(profile, onProfileClick = onProfileClick)
                }
            }
        }
    }
}

@Composable
private fun SearchProfileRow(profile: SocialProfile, onProfileClick: (ProfileId) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Avatar(
            url = profile.avatarUrl,
            size = AvatarSize.Large,
            fallback = {
                Text(profile.displayName.first().uppercase())
            },
            modifier = Modifier
                .clip(CircleShape)
                .clickable { onProfileClick(profile.id) },
        )

        Spacer(Modifier.width(16.dp))
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = profile.handle,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = profile.displayName,
                color = Theme[colors][muted],
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = profile.followerCount,
                color = Theme[colors][onBackground],
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Spacer(Modifier.width(16.dp))
        Button(
            onClick = { },
            modifier = Modifier.width(104.dp),
            style = ButtonStyle.Outlined,
            buttonSize = ButtonSize.Small,
            shape = RoundedCornerShape(12.dp),
        ) {
            Text("Follow")
        }
    }
}
