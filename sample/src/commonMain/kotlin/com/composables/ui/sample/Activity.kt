package com.composables.ui.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
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
import com.composables.ui.components.Text
import com.composables.ui.sample.iconography.AtSign
import com.composables.ui.sample.iconography.Heart
import com.composables.ui.sample.iconography.Icons
import com.composables.ui.sample.iconography.Reply
import com.composables.ui.sample.iconography.User
import com.composables.ui.sample.components.Avatar
import com.composables.ui.sample.components.AvatarSize
import com.composables.ui.sample.data.ActivityEvent
import com.composables.ui.sample.data.ActivityEventType
import com.composables.ui.sample.data.ActivityEvents
import com.composables.ui.sample.data.ProfileId
import com.composables.ui.sample.data.UserProfile
import com.composables.ui.sample.data.UserProfiles
import com.composables.ui.theme.colors
import com.composables.ui.theme.destructiveColor
import com.composables.ui.theme.mutedColor
import com.composables.ui.theme.onBackgroundColor
import com.composables.ui.theme.onPanelColor
import com.composables.ui.theme.panelColor
import com.composables.ui.theme.primaryColor
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.theme.Theme

@Composable
fun Activity(
    onProfileClick: (ProfileId) -> Unit,
) {
    ProvideContentColor(Theme[colors][onPanelColor]) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme[colors][panelColor]),
            contentPadding = sampleScreenContentPadding(),
        ) {
            itemsIndexed(
                items = ActivityEvents.recent(),
                key = { _, event -> event.id },
            ) { index, event ->
                val author = UserProfiles.findWithId(event.authorId)
                if (index != 0) {
                    HorizontalSeparator()
                }
                ActivityEventRow(
                    event = event,
                    author = author,
                    onProfileClick = {
                        onProfileClick(author.id)
                    }
                )
            }
        }
    }
}

@Composable
private fun ActivityEventRow(
    event: ActivityEvent,
    author: UserProfile,
    onProfileClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.Top,
    ) {
        BadgedContent(
            badge = { ActivityBadge(event) },
        ) {
            Avatar(
                url = author.avatarUrl,
                size = AvatarSize.Large,
                fallback = {
                    Text(author.displayName.first().uppercase())
                },
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable { onProfileClick() },
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = author.displayName,
                    modifier = Modifier.weight(1f, fill = false),
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = event.timestamp,
                    color = Theme[colors][mutedColor],
                    singleLine = true,
                )
            }
            val context = when (event.type) {
                ActivityEventType.Mention -> "Mentioned you"
                ActivityEventType.Comment -> "Commented"
                ActivityEventType.Follow -> "Followed you"
                else -> event.context
            }
            if (context != null) {
                Text(
                    text = context,
                    color = Theme[colors][mutedColor],
                )
            }

            event.body?.let { body ->
                Text(
                    text = body,
                    color = Theme[colors][onBackgroundColor],
                )
            }
        }
        if (event.type == ActivityEventType.Follow) {
            Button(
                onClick = {},
                style = ButtonStyle.Outlined,
                buttonSize = ButtonSize.Small,
            ) {
                Text("Follow back")
            }
        }
    }
}

@Composable
private fun BadgedContent(
    badge: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(modifier) {
        content()

        Box(
            Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 8.dp, y = 4.dp)
        ) {
            badge()
        }
    }
}

@Composable
private fun ActivityBadge(
    event: ActivityEvent,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = when (event.type) {
        ActivityEventType.Mention -> Theme[colors][primaryColor]
        ActivityEventType.Comment -> Color(0xFF5B21B6)
        ActivityEventType.Follow -> Color(0xFF3B82F6)
        ActivityEventType.Like -> Color(0xFFE11D48)
        else -> Theme[colors][destructiveColor]
    }

    val contentColor = Color.White

    val icon = when (event.type) {
        ActivityEventType.Mention -> Icons.AtSign
        ActivityEventType.Comment -> Icons.Reply
        ActivityEventType.Follow -> Icons.User
        else -> Icons.Heart
    }

    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(Theme[colors][panelColor])
            .padding(2.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .padding(2.dp),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = icon,
            tint = contentColor,
            modifier = Modifier.size(14.dp)
        )
    }
}
