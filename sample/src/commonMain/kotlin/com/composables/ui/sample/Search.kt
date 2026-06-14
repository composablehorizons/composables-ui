package com.composables.ui.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.composables.ui.components.Button
import com.composables.ui.components.ButtonSize
import com.composables.ui.components.ButtonStyle
import com.composables.ui.components.HorizontalSeparator
import com.composables.ui.components.Icon
import com.composables.ui.components.IconButton
import com.composables.ui.components.Text
import com.composables.ui.components.TextField
import com.composables.ui.sample.iconography.Icons
import com.composables.ui.sample.iconography.Search
import com.composables.ui.sample.iconography.X
import com.composables.ui.sample.components.Avatar
import com.composables.ui.sample.components.AvatarSize
import com.composables.ui.sample.data.ProfileId
import com.composables.ui.sample.data.UserProfile
import com.composables.ui.sample.data.UserProfiles
import com.composables.ui.theme.colors
import com.composables.ui.theme.mutedColor
import com.composables.ui.theme.onBackgroundColor
import com.composables.ui.theme.onPanelColor
import com.composables.ui.theme.panelColor
import com.composables.ui.theme.shapes
import com.composables.ui.theme.smallShape
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.theme.Theme

@Composable
fun Search(onProfileClick: (ProfileId) -> Unit) {
    val queryState = rememberTextFieldState()
    val query = queryState.text.toString()

    ProvideContentColor(Theme[colors][onPanelColor]) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme[colors][panelColor]),
            contentPadding = sampleScreenContentPadding(),
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
                    leading = {
                        Icon(
                            imageVector = Icons.Search,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp),
                            tint = Theme[colors][mutedColor],
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
                                    imageVector = Icons.X,
                                    contentDescription = "Clear search",
                                    modifier = Modifier.size(15.dp),
                                    tint = Theme[colors][mutedColor],
                                )
                            }
                        }
                    },
                )
            }

            UserProfiles.searchResults().forEachIndexed { index, profile ->
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
private fun SearchProfileRow(profile: UserProfile, onProfileClick: (ProfileId) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(Theme[shapes][smallShape])
                .clickable { onProfileClick(profile.id) }
                .weight(1f)
        ) {
            Avatar(
                url = profile.avatarUrl,
                size = AvatarSize.Large,
                fallback = {
                    Text(profile.displayName.first().uppercase())
                },
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
                    color = Theme[colors][mutedColor],
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = formatFollowerCount(profile.followerCount),
                    color = Theme[colors][onBackgroundColor],
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
        Spacer(Modifier.width(8.dp))
        Button(
            onClick = { },
            modifier = Modifier.width(104.dp),
            style = ButtonStyle.Primary,
            buttonSize = ButtonSize.Small,
            shape = RoundedCornerShape(12.dp),
        ) {
            Text("Follow")
        }
    }
}
