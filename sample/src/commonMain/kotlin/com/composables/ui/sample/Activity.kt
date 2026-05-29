package com.composables.ui.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.composables.icons.lucide.AtSign
import com.composables.icons.lucide.Heart
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.PersonStanding
import com.composables.icons.lucide.Reply
import com.composables.icons.lucide.User
import com.composables.icons.lucide.UserPlus
import com.composables.ui.components.Button
import com.composables.ui.components.ButtonSize
import com.composables.ui.components.ButtonStyle
import com.composables.ui.components.HorizontalSeparator
import com.composables.ui.components.Icon
import com.composables.ui.components.Text
import com.composables.ui.sample.components.Avatar
import com.composables.ui.sample.data.ActivityEvent
import com.composables.ui.sample.data.ActivityEventType
import com.composables.ui.sample.data.activityEvents
import com.composables.ui.theme.colors
import com.composables.ui.theme.destructive
import com.composables.ui.theme.muted
import com.composables.ui.theme.onBackground
import com.composables.ui.theme.onPanel
import com.composables.ui.theme.panel
import com.composables.ui.theme.primary
import com.composables.ui.theme.selectedControl
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.theme.Theme

@Composable
fun Activity() {
    ProvideContentColor(Theme[colors][onPanel]) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme[colors][panel]),
            contentPadding = PaddingValues(bottom = 96.dp),
        ) {
            itemsIndexed(
                items = activityEvents,
                key = { _, event -> event.id },
            ) { index, event ->
                if (index != 0) {
                    HorizontalSeparator()
                }
                ActivityEventRow(event)
            }
        }
    }
}

@Composable
private fun ActivityEventRow(event: ActivityEvent) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top,
    ) {
        ActivityAvatar(event)
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = event.author.displayName,
                    modifier = Modifier.weight(1f, fill = false),
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = event.timestamp,
                    color = Theme[colors][muted],
                    singleLine = true,
                )
            }
            val context = when (event.type) {
                ActivityEventType.Mention -> "Mentioned you"
                ActivityEventType.Comment -> "Commented"
                ActivityEventType.Follow -> "Followed you"
                else -> null
            }
            if (context != null) {
                Text(
                    text = context,
                    color = Theme[colors][muted],
                )
            }

            event.body?.let { body ->
                Text(
                    text = body,
                    color = Theme[colors][onBackground],
                )
            }
        }
        if (event.type == ActivityEventType.Follow) {
            Button(
                onClick = {},
                style = ButtonStyle.Outlined,
                buttonSize = ButtonSize.Small,
            ) {
                Text("Follow back", color = Theme[colors][muted])
            }
        }
    }
}

@Composable
private fun ActivityAvatar(event: ActivityEvent) {
    Box {
        Avatar(
            url = event.author.avatarUrl,
            fallback = {
                Text(event.author.displayName.first().uppercase())
            },
            badge = {
                ActivityBadge(event = event)
            }
        )
    }
}

@Composable
private fun ActivityBadge(
    event: ActivityEvent,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = when (event.type) {
        ActivityEventType.Mention -> Theme[colors][primary]
        ActivityEventType.Comment -> Color(0xFF5B21B6)
        ActivityEventType.Follow -> Color(0xFF3B82F6)
        ActivityEventType.Like -> Color(0xFFE11D48)
        else -> Theme[colors][destructive]
    }

    val contentColor = Color.White

    val icon = when (event.type) {
        ActivityEventType.Mention -> Lucide.AtSign
        ActivityEventType.Comment -> Lucide.Reply
        ActivityEventType.Follow -> Lucide.User
        else -> Lucide.Heart
    }

    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(Theme[colors][panel])
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
