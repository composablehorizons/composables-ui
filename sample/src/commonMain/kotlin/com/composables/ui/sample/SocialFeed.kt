package com.composables.ui.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Ellipsis
import com.composables.icons.lucide.Heart
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.MessageCircle
import com.composables.icons.lucide.Repeat2
import com.composables.ui.components.Button
import com.composables.ui.components.ButtonSize
import com.composables.ui.components.ButtonStyle
import com.composables.ui.components.DropdownMenu
import com.composables.ui.components.DropdownMenuAlignment
import com.composables.ui.components.DropdownMenuItem
import com.composables.ui.components.DropdownMenuItemStyle
import com.composables.ui.components.DropdownMenuPanel
import com.composables.ui.components.HorizontalSeparator
import com.composables.ui.components.Icon
import com.composables.ui.components.IconButton
import com.composables.ui.components.Text
import com.composables.ui.sample.components.AvatarButton
import com.composables.ui.sample.components.FeedPost
import com.composables.ui.sample.components.LandscapeMediaItem
import com.composables.ui.sample.components.PortraitMediaItem
import com.composables.ui.sample.data.SocialPost
import com.composables.ui.sample.data.feedPosts
import com.composables.ui.sample.data.profiles
import com.composables.ui.theme.Medium
import com.composables.ui.theme.border
import com.composables.ui.theme.colors
import com.composables.ui.theme.muted
import com.composables.ui.theme.onBackground
import com.composables.ui.theme.panel
import com.composeunstyled.currentWidthBreakpoint
import com.composeunstyled.outline
import com.composeunstyled.theme.Theme

private const val LoggedInProfileId = "john_mobbin"

@Composable
fun SocialFeed(
    onPostClick: (SocialPost) -> Unit,
    onProfileClick: (String) -> Unit,
    onNewPostClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val widthBreakpoint = currentWidthBreakpoint()
    val feedShape = RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp)
    Box(modifier = modifier.padding(horizontal = if (widthBreakpoint isAtLeast Medium) 80.dp else 0.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .then(
                    if (widthBreakpoint isAtLeast Medium) {
                        Modifier
                            .background(Theme[colors][panel], feedShape)
                            .outline(
                                width = 1.dp,
                                color = Theme[colors][border],
                                shape = feedShape,
                            )
                            .clip(feedShape)
                    } else {
                        Modifier.background(Theme[colors][panel])
                    }
                )
                .align(Alignment.TopCenter),
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 96.dp),
            ) {
                item(key = "composer") {
                    FeedComposer(
                        onProfileClick = { onProfileClick(LoggedInProfileId) },
                        onNewPostClick = onNewPostClick,
                    )
                    HorizontalSeparator()
                }
                itemsIndexed(
                    items = feedPosts,
                    key = { _, post -> post.id },
                ) { index, post ->
                    val onProfileClick1 = { onProfileClick(post.profileId) }
                    FeedPost(
                        onClick = { onPostClick(post) },
                        avatar = {
                            AvatarButton(
                                url = post.avatarUrl,
                                onClick = onProfileClick1,
                            )
                        },
                        authorName = {
                            Button(onClick = onProfileClick1, style = ButtonStyle.Link) {
                                Text(
                                    text = post.author,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                )
                            }
                        },
                        timestamp = {
                            Text(post.age)
                        },
                        overflow = { PostOverflowMenu() },
                        body = {
                            Text(
                                text = post.body,
                                color = Theme[colors][onBackground],
                            )
                        },
                        media = run {
                            if (post.media.isNotEmpty()) {
                                {
                                    post.media.forEach { item ->
                                        if (post.portraitMedia) {
                                            PortraitMediaItem(item.url)
                                        } else {
                                            LandscapeMediaItem(item.url)
                                        }
                                    }
                                }
                            } else null
                        },
                    ) {
                        PostActions(post = post)
                    }
                    if (index < feedPosts.lastIndex) {
                        HorizontalSeparator()
                    }
                }
            }
        }
    }
}

@Composable
private fun FeedComposer(
    onProfileClick: () -> Unit,
    onNewPostClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val loggedInProfile = profiles.first { it.id == LoggedInProfileId }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        AvatarButton(
            url = loggedInProfile.avatarUrl,
            onClick = onProfileClick,
        )
        Text(
            text = "What's up?",
            modifier = Modifier
                .weight(1f)
                .pointerHoverIcon(PointerIcon.Text)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onNewPostClick,
                ),
            color = Theme[colors][muted],
        )
        Button(
            onClick = onNewPostClick,
            style = ButtonStyle.Primary
        ) {
            Text("Post")
        }
    }
}

@Composable
private fun PostOverflowMenu() {
    var expanded by remember { mutableStateOf(false) }

    DropdownMenu(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        alignment = DropdownMenuAlignment.End,
        panel = {
            DropdownMenuPanel {
                DropdownMenuItem(onClick = { }) {
                    Text("Save")
                }
                DropdownMenuItem(onClick = { }) {
                    Text("Copy link")
                }
                DropdownMenuItem(onClick = { }) {
                    Text("Mute")
                }
                DropdownMenuItem(onClick = { }) {
                    Text("Not interested")
                }
                DropdownMenuItem(
                    onClick = { },
                    style = DropdownMenuItemStyle.Destructive,
                ) {
                    Text("Report")
                }
            }
        },
    ) {
        IconButton(
            onClick = { expanded = expanded.not() },
            style = ButtonStyle.Ghost,
            buttonSize = ButtonSize.Small,
            indication = null,
        ) {
            Icon(
                imageVector = Lucide.Ellipsis,
                contentDescription = "Post options",
                tint = Theme[colors][onBackground],
            )
        }
    }
}

@Composable
private fun PostActions(post: SocialPost) {
    val actionColor = Theme[colors][muted]

    CountedActionButton(
        count = post.likes,
        color = actionColor,
        onClick = {},
    ) { color ->
        Icon(Lucide.Heart, contentDescription = "Like", modifier = Modifier.size(25.dp), tint = color)
    }
    CountedActionButton(
        count = post.replies,
        color = actionColor,
        onClick = {},
    ) { color ->
        Icon(
            Lucide.MessageCircle,
            contentDescription = "Reply",
            modifier = Modifier.size(25.dp),
            tint = color,
        )
    }
    ActionButton(
        color = actionColor,
        onClick = {},
    ) { color ->
        Icon(Lucide.Repeat2, contentDescription = "Repost", modifier = Modifier.size(25.dp), tint = color)
    }
}

@Composable
private fun CountedActionButton(
    count: String,
    color: Color,
    onClick: () -> Unit,
    content: @Composable (Color) -> Unit,
) {
    Button(
        onClick = onClick,
        style = ButtonStyle.Ghost,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            content(color)
            Text(
                text = count,
                color = color,
            )
        }
    }
}

@Composable
private fun ActionButton(
    color: Color,
    onClick: () -> Unit,
    content: @Composable (Color) -> Unit,
) {
    Button(
        onClick = onClick,
        style = ButtonStyle.Ghost,
    ) {
        content(color)
    }
}
