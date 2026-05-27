package com.composables.ui.sample

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.icons.lucide.ArrowLeft
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
import com.composables.ui.components.ExpandedWidthBreakpoint
import com.composables.ui.components.HorizontalSeparator
import com.composables.ui.components.Icon
import com.composables.ui.components.IconButton
import com.composables.ui.components.ScreenScaffold
import com.composables.ui.components.Text
import com.composables.ui.components.Toolbar
import com.composables.ui.sample.components.AvatarButton
import com.composables.ui.sample.components.FeedPost
import com.composables.ui.sample.data.SocialPost
import com.composables.ui.sample.data.SocialReply
import com.composables.ui.sample.data.feedPosts
import com.composables.ui.sample.data.postReplies
import com.composables.ui.theme.background
import com.composables.ui.theme.border
import com.composables.ui.theme.colors
import com.composables.ui.theme.muted
import com.composables.ui.theme.onBackground
import com.composables.uripainter.rememberUriPainter
import com.composeunstyled.currentWidthBreakpoint
import com.composeunstyled.outline
import com.composeunstyled.theme.Theme

private val ThreadMaxWidth = 700.dp
private val WideThreadVerticalInset = 70.dp

@Composable
fun PostDetailScreen(
    postId: String,
    onBack: () -> Unit,
    onProfileClick: (String) -> Unit,
) {
    val post = feedPosts.firstOrNull { it.id == postId } ?: feedPosts.first()
    val replies = postReplies[post.id].orEmpty()
    val widthBreakpoint = currentWidthBreakpoint()
    val showThreadOutline = widthBreakpoint isAtLeast ExpandedWidthBreakpoint
    val threadShape = RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp)
    val threadVerticalInset by animateDpAsState(
        targetValue = if (showThreadOutline) WideThreadVerticalInset else 0.dp,
        label = "ThreadVerticalInset",
    )

    ScreenScaffold(backgroundColor = Theme[colors][background], contentColor = Theme[colors][onBackground]) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = threadVerticalInset),
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = ThreadMaxWidth)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .align(Alignment.TopCenter),
            ) {
                ThreadToolbar(onBack = onBack)
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .then(
                            if (showThreadOutline) {
                                Modifier
                                    .outline(
                                        width = 1.dp,
                                        color = Theme[colors][border],
                                        shape = threadShape,
                                        offset = (-1).dp,
                                    )
                                    .clip(threadShape)
                            } else {
                                Modifier
                            }
                        ),
                    contentPadding = PaddingValues(bottom = 96.dp),
                ) {
                    item(key = "post-${post.id}") {
                        ThreadPost(
                            post = post,
                            onProfileClick = onProfileClick,
                        )
                        HorizontalSeparator()
                    }
                    item(key = "replies-header") {
                        RepliesHeader(replyCount = replies.size)
                    }
                    if (replies.isEmpty()) {
                        item(key = "empty-replies") {
                            EmptyReplies()
                        }
                    } else {
                        itemsIndexed(
                            items = replies,
                            key = { _, reply -> reply.id },
                        ) { index, reply ->
                            ReplyPost(
                                reply = reply,
                                onProfileClick = onProfileClick,
                            )
                            if (index < replies.lastIndex) {
                                HorizontalSeparator()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ThreadToolbar(onBack: () -> Unit) {
    Toolbar(
        backgroundColor = Theme[colors][background],
        leading = {
            IconButton(onClick = onBack, style = ButtonStyle.Ghost) {
                Icon(
                    Lucide.ArrowLeft,
                    contentDescription = "Back",
                    modifier = Modifier.size(28.dp),
                    tint = Theme[colors][onBackground],
                )
            }
        },
        centered = {
            Text(
                text = "Thread",
                style = TextStyle(fontWeight = FontWeight.SemiBold),
            )
        },
        trailing = {
            PostOverflowMenu()
        },
    )
}

@Composable
private fun ThreadPost(
    post: SocialPost,
    onProfileClick: (String) -> Unit,
) {
    val onProfileClick1 = { onProfileClick(post.profileId) }
    FeedPost(
        onClick = {},
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
                style = TextStyle(fontSize = 19.sp, lineHeight = 27.sp),
                color = Theme[colors][onBackground],
            )
        },
        media = post.imageUrl?.let<String, @Composable (() -> Unit)?> { imageUrl ->
            { PostImage(imageUrl) }
        },
    ) {
        PostActions(
            replies = post.replies,
            likes = post.likes,
        )
    }
}

@Composable
private fun ReplyPost(
    reply: SocialReply,
    onProfileClick: (String) -> Unit,
) {
    val onProfileClick1 = { onProfileClick(reply.profileId) }
    FeedPost(
        onClick = {},
        avatar = {
            AvatarButton(
                url = reply.avatarUrl,
                onClick = onProfileClick1,
            )
        },
        authorName = {
            Button(onClick = onProfileClick1, style = ButtonStyle.Link) {
                Text(
                    text = reply.author,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        },
        timestamp = {
            Text(reply.age)
        },
        overflow = { PostOverflowMenu() },
        body = {
            Text(
                text = reply.body,
                color = Theme[colors][onBackground],
            )
        },
    ) {
        PostActions(
            replies = reply.replies,
            likes = reply.likes,
        )
    }
}

@Composable
private fun RepliesHeader(replyCount: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "Replies",
            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.SemiBold),
        )
        Text(
            text = "$replyCount ${if (replyCount == 1) "reply" else "replies"}",
            color = Theme[colors][muted],
        )
    }
}

@Composable
private fun EmptyReplies() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 32.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "No replies yet",
            color = Theme[colors][muted],
        )
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
                DropdownMenuItem(onClick = { expanded = false }) {
                    Text("Save")
                }
                DropdownMenuItem(onClick = { expanded = false }) {
                    Text("Copy link")
                }
                DropdownMenuItem(onClick = { expanded = false }) {
                    Text("Mute")
                }
                DropdownMenuItem(onClick = { expanded = false }) {
                    Text("Not interested")
                }
                DropdownMenuItem(
                    onClick = { expanded = false },
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
private fun PostImage(url: String) {
    Image(
        painter = rememberUriPainter(url),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.35f)
            .clip(RoundedCornerShape(10.dp))
            .background(Theme[colors][border]),
        contentScale = ContentScale.Crop,
    )
}

@Composable
private fun PostActions(
    replies: String,
    likes: String,
) {
    val actionColor = Theme[colors][muted]

    CountedActionButton(
        count = likes,
        color = actionColor,
        onClick = {},
    ) { color ->
        Icon(Lucide.Heart, contentDescription = "Like", modifier = Modifier.size(25.dp), tint = color)
    }
    CountedActionButton(
        count = replies,
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
                style = TextStyle(fontSize = 16.sp),
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
