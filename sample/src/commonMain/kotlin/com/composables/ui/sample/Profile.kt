package com.composables.ui.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.font.FontWeight
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
import com.composables.ui.components.Tab
import com.composables.ui.components.TabGroup
import com.composables.ui.components.TabList
import com.composables.ui.components.Text
import com.composables.ui.sample.components.Avatar
import com.composables.ui.sample.components.AvatarButton
import com.composables.ui.sample.components.AvatarSize
import com.composables.ui.sample.components.FeedPost
import com.composables.ui.sample.data.Post
import com.composables.ui.sample.data.Posts
import com.composables.ui.sample.data.UserProfile
import com.composables.ui.sample.data.UserProfiles
import com.composables.ui.theme.border
import com.composables.ui.theme.colors
import com.composables.ui.theme.field
import com.composables.ui.theme.muted
import com.composables.ui.theme.onBackground
import com.composables.ui.theme.onField
import com.composables.ui.theme.onPanel
import com.composables.ui.theme.panel
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.theme.Theme

@kotlin.jvm.JvmInline
private value class ProfileFeedTab private constructor(val value: String) {
    companion object {
        val Posts = ProfileFeedTab("posts")
        val Replies = ProfileFeedTab("replies")
        val all = listOf(Posts, Replies)
    }
}

@Composable
fun Profile(
    profileId: String,
    onPostClick: (String) -> Unit,
) {
    val profile = UserProfiles.findWithId(profileId)
    var selectedTab by remember { mutableStateOf(ProfileFeedTab.Posts) }
    val visiblePosts = when (selectedTab) {
        ProfileFeedTab.Replies -> Posts.repliesByProfileId(profile.id)
        else -> Posts.postsByProfileId(profile.id)
    }

    ProvideContentColor(Theme[colors][onPanel]) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(Theme[colors][panel]),
            contentPadding = PaddingValues(vertical = 20.dp),
        ) {
            item {
                ProfileHeader(profile)
                TabGroup(
                    selectedTab = selectedTab,
                    onSelectedTabChange = { it: ProfileFeedTab -> selectedTab = it },
                    tabs = ProfileFeedTab.all,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                ) {
                    this.TabList(
                        modifier = Modifier.fillMaxWidth(),
                        equalTabWidth = true,
                    ) {
                        ProfileFeedTab.all.forEach { tab ->
                            this.Tab(key = tab) {
                                Text(
                                    text = tab.label,
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                        }
                    }
                }
            }
            itemsIndexed(
                items = visiblePosts,
                key = { _, post -> post.id },
            ) { index, post ->
                ProfilePostRow(
                    post = post,
                    onClick = { onPostClick(post.id) },
                )
                if (index < visiblePosts.lastIndex) {
                    HorizontalSeparator()
                }
            }
        }
    }
}

@Composable
private fun ProfileHeader(profile: UserProfile) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        Row(verticalAlignment = Alignment.Top) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(profile.displayName, fontWeight = FontWeight.Bold)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(profile.handle)
                    Text(
                        text = profile.badge,
                        color = Theme[colors][onField],
                        modifier = Modifier
                            .clip(RoundedCornerShape(100))
                            .background(Theme[colors][field])
                            .padding(horizontal = 12.dp, vertical = 5.dp),
                    )
                }
            }
            Avatar(profile.avatarUrl, size = AvatarSize.Large)
        }
        Text(
            text = profile.bio,
        )
        Text(
            text = formatFollowerCount(profile.followerCount),
            color = Theme[colors][muted],
        )
        Button(
            onClick = {},
            style = ButtonStyle.Primary,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Follow")
        }
    }
}

private val ProfileFeedTab.label: String
    get() = when (this) {
        ProfileFeedTab.Replies -> "Replies"
        else -> "Posts"
    }

@Composable
private fun ProfilePostRow(
    post: Post,
    onClick: () -> Unit,
) {
    val author = UserProfiles.findWithId(post.authorId)

    FeedPost(
        onClick = onClick,
        avatar = {
            AvatarButton(
                url = author.avatarUrl,
                onClick = onClick,
            )
        },
        authorName = {
            Text(
                text = author.handle,
                fontWeight = FontWeight.Bold,
            )
        },
        timestamp = {
            Text(post.timestamp)
        },
        overflow = { ProfilePostOverflowMenu() },
        body = {
            Text(
                text = post.body,
                color = Theme[colors][onBackground],
            )
        },
        media = if (post.quoteAuthor != null && post.quoteBody != null && post.quoteReplyCount != null) {
            {
                QuotedPost(
                    avatarUrl = author.avatarUrl,
                    author = post.quoteAuthor,
                    body = post.quoteBody,
                    replyCountLabel = formatReplyCount(post.quoteReplyCount),
                )
            }
        } else {
            null
        },
    ) {
        ProfilePostActions(post)
    }
}

@Composable
private fun ProfilePostOverflowMenu() {
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
                Lucide.Ellipsis,
                contentDescription = "Post options",
                modifier = Modifier.size(22.dp),
                tint = Theme[colors][onBackground],
            )
        }
    }
}

@Composable
private fun QuotedPost(
    avatarUrl: String,
    author: String,
    body: String,
    replyCountLabel: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(width = 1.dp, color = Theme[colors][border], shape = RoundedCornerShape(12.dp))
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Avatar(avatarUrl, size = AvatarSize.Medium)
            Text(author, fontWeight = FontWeight.Bold)
        }
        Text(body)
        Text(replyCountLabel, color = Theme[colors][muted])
    }
}

@Composable
private fun ProfilePostActions(post: Post) {
    val actionColor = Theme[colors][muted]

    ProfileActionButton(count = formatCount(post.likeCount), color = actionColor) { color ->
        Icon(Lucide.Heart, contentDescription = "Like", modifier = Modifier.size(25.dp), tint = color)
    }
    ProfileActionButton(count = formatCount(post.replyCount), color = actionColor) { color ->
        Icon(Lucide.MessageCircle, contentDescription = "Reply", modifier = Modifier.size(25.dp), tint = color)
    }
    Button(onClick = {}, style = ButtonStyle.Ghost) {
        Icon(Lucide.Repeat2, contentDescription = "Repost", modifier = Modifier.size(25.dp), tint = actionColor)
    }
}

@Composable
private fun ProfileActionButton(
    count: String,
    color: Color,
    content: @Composable (Color) -> Unit,
) {
    Button(onClick = {}, style = ButtonStyle.Ghost) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            content(color)
            Text(count, color = color)
        }
    }
}
