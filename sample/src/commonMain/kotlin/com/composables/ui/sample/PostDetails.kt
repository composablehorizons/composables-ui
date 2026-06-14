package com.composables.ui.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.composables.tooling.insets.previewStatusBarPaddingValue
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
import com.composables.ui.components.CenteredToolbar
import com.composables.ui.sample.iconography.ArrowLeft
import com.composables.ui.sample.iconography.Ellipsis
import com.composables.ui.sample.iconography.Icons
import com.composables.ui.sample.components.Avatar
import com.composables.ui.sample.components.FeedPost
import com.composables.ui.sample.components.LandscapeMediaItem
import com.composables.ui.sample.components.MediaAttachment
import com.composables.ui.sample.components.PortraitMediaItem
import com.composables.ui.sample.data.Post
import com.composables.ui.sample.data.PostId
import com.composables.ui.sample.data.Posts
import com.composables.ui.sample.data.UserProfiles
import com.composables.ui.theme.colors
import com.composables.ui.theme.mutedColor
import com.composables.ui.theme.onBackgroundColor
import com.composables.ui.theme.onPanelColor
import com.composables.ui.theme.panelColor
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.currentWindowWidthBreakpoint
import com.composeunstyled.theme.Theme

@Composable
fun PostDetails(
    onBackClick: () -> Unit,
    onProfileClick: (String) -> Unit,
    postId: PostId,
) {
    val post = Posts.findWithId(postId)
    val replies = Posts.repliesByPostId(post.id)

    val breakpoint = currentWindowWidthBreakpoint()
    ProvideContentColor(Theme[colors][onPanelColor]) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme[colors][panelColor]),
            contentPadding = sampleScreenContentPadding(),
        ) {
            if (breakpoint isAt Compact) {
                stickyHeader {
                    ThreadToolbar(onBackClick)
                }
            }
            item(key = post.id) {
                MainPost(
                    post = post,
                    onProfileClick = onProfileClick,
                )
                HorizontalSeparator()
            }
            itemsIndexed(
                items = replies,
                key = { _, reply -> reply.id },
            ) { index, reply ->
                if (index != 0) {
                    HorizontalSeparator()
                }
                ThreadReplyPost(
                    post = reply,
                    onProfileClick = onProfileClick,
                )
            }
        }
    }
}

@Composable
private fun ThreadToolbar(onBackClick: () -> Unit) {
    CenteredToolbar(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = Theme[colors][panelColor],
        contentColor = Theme[colors][onPanelColor],
        windowInsets = WindowInsets(top = previewStatusBarPaddingValue()),
        leading = {
            IconButton(
                onClick = onBackClick,
                style = ButtonStyle.Ghost,
            ) {
                Icon(Icons.ArrowLeft, contentDescription = "Go back")
            }

        },
        title = {
            Text("Post", fontWeight = FontWeight.Bold)
        },
    )
}

@Composable
private fun MainPost(
    onProfileClick: (String) -> Unit,
    post: Post,
) {
    val author = UserProfiles.findWithId(post.authorId)
    val openAuthor = { onProfileClick(author.id) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        ThreadPostHeader(
            post = post,
            authorHandle = author.handle,
            avatarUrl = author.avatarUrl,
            onAuthorClick = openAuthor,
            modifier = Modifier.padding(horizontal = 24.dp),
        )
        Text(
            text = post.body,
            modifier = Modifier.padding(horizontal = 24.dp),
            color = Theme[colors][onBackgroundColor],
        )
        if (post.media.isNotEmpty()) {
            MediaAttachment(
                contentPadding = PaddingValues(horizontal = 24.dp),
            ) {
                post.media.forEach { item ->
                    if (post.portraitMedia) {
                        PortraitMediaItem(item.url)
                    } else {
                        LandscapeMediaItem(item.url)
                    }
                }
            }
        }
        PostActions(post, modifier = Modifier.padding(horizontal = 4.dp))
    }
}

@Composable
private fun ThreadPostHeader(
    post: Post,
    authorHandle: String,
    avatarUrl: String,
    onAuthorClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Avatar(
            url = avatarUrl,
            modifier = Modifier.clip(CircleShape).clickable { onAuthorClick() },
        )
        Button(
            onClick = onAuthorClick,
            style = ButtonStyle.Link,
        ) {
            Text(
                text = authorHandle,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Spacer(Modifier.weight(1f))
        Text(
            text = post.timestamp,
            color = Theme[colors][mutedColor],
        )
        ThreadPostOverflowMenu()
    }
}

@Composable
private fun ThreadReplyPost(
    post: Post,
    onProfileClick: (String) -> Unit,
) {
    val author = UserProfiles.findWithId(post.authorId)
    val openAuthor = { onProfileClick(author.id) }

    FeedPost(
        onClick = {},
        avatar = {
            Avatar(
                url = author.avatarUrl,
                modifier = Modifier.clip(CircleShape).clickable { openAuthor() },
            )
        },
        authorName = {
            Button(onClick = openAuthor, style = ButtonStyle.Link) {
                Text(
                    text = author.handle,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        },
        timestamp = {
            Text(post.timestamp)
        },
        overflow = { ThreadPostOverflowMenu() },
        body = {
            Text(
                text = post.body,
                color = Theme[colors][onBackgroundColor],
            )
        },
        attachment = {
            if (post.media.isNotEmpty()) {
                MediaAttachment {
                    post.media.forEach { item ->
                        if (post.portraitMedia) {
                            PortraitMediaItem(item.url)
                        } else {
                            LandscapeMediaItem(item.url)
                        }
                    }
                }
            }
        },
        actions = {
            PostActions(post)
        },
    )
}

@Composable
private fun ThreadPostOverflowMenu() {
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
                imageVector = Icons.Ellipsis,
                contentDescription = "Post options",
                tint = Theme[colors][onBackgroundColor],
            )
        }
    }
}

@Composable
private fun ThreadActionButton(
    color: Color,
    content: @Composable (Color) -> Unit,
) {
    IconButton(onClick = {}, style = ButtonStyle.Ghost) {
        content(color)
    }
}
