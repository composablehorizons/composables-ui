package com.composables.ui.sample

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.composables.ui.sample.components.AvatarButton
import com.composables.ui.sample.components.FeedPost
import com.composables.ui.sample.data.SocialPost
import com.composables.ui.sample.data.feedPosts
import com.composables.ui.theme.background
import com.composables.ui.theme.border
import com.composables.ui.theme.colors
import com.composables.ui.theme.muted
import com.composables.ui.theme.onBackground
import com.composables.uripainter.rememberUriPainter
import com.composeunstyled.currentWidthBreakpoint
import com.composeunstyled.outline
import com.composeunstyled.theme.Theme

private val FeedMaxWidth = 700.dp
private val WideFeedVerticalInset = 70.dp

@Composable
fun HomePage(
    onPostClick: (SocialPost) -> Unit,
    onProfileClick: (String) -> Unit,
) {
    ScreenScaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme[colors][background]),
        ) {
            SocialTimeline(
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxWidth(),
                onPostClick = onPostClick,
                onProfileClick = onProfileClick,
            )
            SocialBottomBar(
                onProfileClick = { onProfileClick("john_mobbin") },
                modifier = Modifier.align(Alignment.BottomCenter),
            )
        }
    }
}

@Composable
private fun SocialTimeline(
    onPostClick: (SocialPost) -> Unit,
    onProfileClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val widthBreakpoint = currentWidthBreakpoint()
    val showFeedOutline = widthBreakpoint isAtLeast ExpandedWidthBreakpoint
    val feedShape = RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp)
    val feedVerticalInset by animateDpAsState(
        targetValue = if (showFeedOutline) WideFeedVerticalInset else 0.dp,
        label = "FeedVerticalInset",
    )

    Box(
        modifier = modifier.padding(vertical = feedVerticalInset)
    ) {
        Box(
            modifier = Modifier
                .widthIn(max = FeedMaxWidth)
                .fillMaxWidth()
                .fillMaxHeight()
                .then(
                    if (showFeedOutline) {
                        Modifier
                            .outline(
                                width = 1.dp,
                                color = Theme[colors][border],
                                shape = feedShape,
                                offset = (-1).dp,
                            )
                            .clip(feedShape)
                    } else {
                        Modifier
                    }
                )
                .align(Alignment.TopCenter),
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 96.dp),
            ) {
                itemsIndexed(
                    items = feedPosts,
                    key = { _, post -> post.id },
                ) { index, post ->
                    SocialPostRow(
                        post = post,
                        onClick = { onPostClick(post) },
                        onProfileClick = { onProfileClick(post.profileId) },
                    )
                    if (index < feedPosts.lastIndex) {
                        HorizontalSeparator()
                    }
                }
            }
        }
    }
}

@Composable
private fun SocialPostRow(
    post: SocialPost,
    onClick: () -> Unit,
    onProfileClick: () -> Unit,
) {
    FeedPost(
        onClick = onClick,
        avatar = {
            AvatarButton(
                url = post.avatarUrl,
                onClick = onProfileClick,
            )
        },
        authorName = {
            Button(onClick = onProfileClick, style = ButtonStyle.Link) {
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
        media = post.imageUrl?.let { imageUrl ->
            { PostImage(imageUrl) }
        },
    ) {
        PostActions(post)
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
