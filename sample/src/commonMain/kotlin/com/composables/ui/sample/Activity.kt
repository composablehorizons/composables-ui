package com.composables.ui.sample

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.AtSign
import com.composables.icons.lucide.Heart
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.MessageCircleReply
import com.composables.icons.lucide.UserPlus
import com.composables.ui.components.Button
import com.composables.ui.components.ButtonSize
import com.composables.ui.components.ButtonStyle
import com.composables.ui.components.HorizontalSeparator
import com.composables.ui.components.Icon
import com.composables.ui.components.Text
import com.composables.ui.sample.data.ActivityEvent
import com.composables.ui.sample.data.ActivityEventType
import com.composables.ui.sample.data.activityEvents
import com.composables.ui.theme.border
import com.composables.ui.theme.colors
import com.composables.ui.theme.componentSizes
import com.composables.ui.theme.destructive
import com.composables.ui.theme.dropdownMenuItemHeight
import com.composables.ui.theme.focusRingOffset
import com.composables.ui.theme.muted
import com.composables.ui.theme.onBackground
import com.composables.ui.theme.onPanel
import com.composables.ui.theme.onPrimary
import com.composables.ui.theme.panel
import com.composables.ui.theme.primary
import com.composables.ui.theme.secondary
import com.composables.ui.theme.selectedControl
import com.composables.ui.theme.textFieldHorizontalPadding
import com.composables.uripainter.rememberUriPainter
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.theme.Theme

@Composable
fun Activity() {
    ProvideContentColor(Theme[colors][onPanel]) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme[colors][panel]),
            contentPadding = PaddingValues(bottom = 80.dp),
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
            .padding(
                horizontal = 24.dp,
                vertical = 16.dp,
            ),
        horizontalArrangement = Arrangement.spacedBy(Theme[componentSizes][textFieldHorizontalPadding]),
        verticalAlignment = Alignment.Top,
    ) {
        ActivityAvatar(event)
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(Theme[componentSizes][textFieldHorizontalPadding] / 2),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(Theme[componentSizes][textFieldHorizontalPadding] / 2),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = event.author,
                    modifier = Modifier.weight(1f, fill = false),
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = event.age,
                    color = Theme[colors][muted],
                    singleLine = true,
                )
            }
            event.context?.let { context ->
                Text(
                    text = context,
                    color = Theme[colors][muted],
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            event.body?.let { body ->
                Text(
                    text = body,
                    color = Theme[colors][onBackground],
                )
            }
        }
        if (event.showFollowing) {
            Button(
                onClick = {},
                style = ButtonStyle.Outlined,
                buttonSize = ButtonSize.Small,
            ) {
                Text("Following", color = Theme[colors][muted])
            }
        }
    }
}

@Composable
private fun ActivityAvatar(event: ActivityEvent) {
    Box {
        Image(
            painter = rememberUriPainter(event.avatarUrl),
            contentDescription = null,
            modifier = Modifier
                .size(Theme[componentSizes][dropdownMenuItemHeight])
                .clip(CircleShape)
                .background(Theme[colors][border]),
            contentScale = ContentScale.Crop,
        )
        ActivityBadge(
            event = event,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(
                    x = Theme[componentSizes][focusRingOffset],
                    y = Theme[componentSizes][focusRingOffset],
                )
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
        ActivityEventType.Reply -> Theme[colors][secondary]
        ActivityEventType.Follow -> Theme[colors][selectedControl]
        else -> Theme[colors][destructive]
    }
    Box(
        modifier = modifier
            .size(20.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .border(1.dp, backgroundColor, CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        when (event.type) {
            ActivityEventType.Mention -> Icon(
                imageVector = Lucide.AtSign,
                contentDescription = "Mention",
                tint = Theme[colors][onPrimary]
            )

            ActivityEventType.Reply -> Icon(
                imageVector = Lucide.MessageCircleReply,
                contentDescription = "Reply",
                tint = Theme[colors][onPrimary]
            )

            ActivityEventType.Follow -> Icon(
                imageVector = Lucide.UserPlus,
                contentDescription = "Follow",
                tint = Theme[colors][onPrimary]
            )

            else -> Icon(
                imageVector = Lucide.Heart,
                contentDescription = "Like",
                tint = Theme[colors][onPrimary]
            )
        }
    }
}

