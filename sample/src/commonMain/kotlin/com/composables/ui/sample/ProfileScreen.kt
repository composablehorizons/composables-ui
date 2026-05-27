package com.composables.ui.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.ArrowLeft
import com.composables.icons.lucide.Bell
import com.composables.icons.lucide.CircleEllipsis
import com.composables.icons.lucide.Ellipsis
import com.composables.icons.lucide.Heart
import com.composables.icons.lucide.Instagram
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
import com.composables.ui.components.Tab
import com.composables.ui.components.TabGroup
import com.composables.ui.components.TabList
import com.composables.ui.components.Text
import com.composables.ui.components.Toolbar
import com.composables.ui.sample.components.Avatar
import com.composables.ui.sample.components.AvatarButton
import com.composables.ui.sample.components.FeedPost
import com.composables.ui.sample.data.SocialProfile
import com.composables.ui.sample.data.ProfilePost
import com.composables.ui.sample.data.profiles
import com.composables.ui.theme.background
import com.composables.ui.theme.border
import com.composables.ui.theme.colors
import com.composables.ui.theme.field
import com.composables.ui.theme.muted
import com.composables.ui.theme.onBackground
import com.composables.ui.theme.onField
import com.composeunstyled.currentWidthBreakpoint
import com.composeunstyled.outline
import com.composeunstyled.theme.Theme

private val ProfileMaxWidth = 700.dp

@kotlin.jvm.JvmInline
private value class ProfileFeedTab private constructor(val value: String) {
    companion object {
        val Posts = ProfileFeedTab("posts")
        val Replies = ProfileFeedTab("replies")
        val all = listOf(Posts, Replies)
    }
}

@Composable
fun ProfileScreen(
    profileId: String,
    onBack: (() -> Unit)?,
    onPostClick: (String) -> Unit,
    onProfileClick: () -> Unit,
) {
    val profile = profiles.firstOrNull { it.id == profileId } ?: profiles.first()
    var selectedTab by remember { mutableStateOf(ProfileFeedTab.Posts) }
    val visiblePosts = when (selectedTab) {
        ProfileFeedTab.Replies -> profile.replies
        else -> profile.posts
    }
    val widthBreakpoint = currentWidthBreakpoint()
    val showProfileOutline = widthBreakpoint isAtLeast ExpandedWidthBreakpoint
    val profileShape = RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp)

    ScreenScaffold {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier
                        .widthIn(max = ProfileMaxWidth)
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .align(Alignment.TopCenter),
                ) {
                    ProfileToolbar(onBack)
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .then(
                                if (showProfileOutline) {
                                    Modifier
                                        .outline(
                                            width = 1.dp,
                                            color = Theme[colors][border],
                                            shape = profileShape,
                                            offset = (-1).dp,
                                        )
                                        .clip(profileShape)
                                } else {
                                    Modifier
                                }
                            ),
                    ) {
                        item {
                            ProfileHeader(profile)
                            ProfileTabs(
                                selectedTab = selectedTab,
                                onSelectedTabChange = { selectedTab = it },
                            )
                        }
                        itemsIndexed(
                            items = visiblePosts,
                            key = { _, post -> post.id },
                        ) { index, post ->
                            ProfilePostRow(
                                profile = profile,
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
        }
    }
}

@Composable
private fun ProfileToolbar(onBack: (() -> Unit)?) {
    Toolbar(
        backgroundColor = Theme[colors][background],
        leading = onBack?.let { onBackClick ->
            {
                IconButton(onClick = onBackClick, style = ButtonStyle.Ghost) {
                    Icon(
                        Lucide.ArrowLeft,
                        contentDescription = "Back",
                        modifier = Modifier.size(28.dp),
                        tint = Theme[colors][onBackground]
                    )
                }
                Text("Back")
            }
        },
        trailing = {
            IconButton(onClick = {}, style = ButtonStyle.Ghost) {
                Icon(
                    Lucide.Instagram,
                    contentDescription = "Instagram",
                    modifier = Modifier.size(28.dp),
                    tint = Theme[colors][onBackground]
                )
            }
            IconButton(onClick = {}, style = ButtonStyle.Ghost) {
                Icon(
                    Lucide.Bell,
                    contentDescription = "Notifications",
                    modifier = Modifier.size(28.dp),
                    tint = Theme[colors][onBackground]
                )
            }
            IconButton(onClick = {}, style = ButtonStyle.Ghost) {
                Icon(
                    Lucide.CircleEllipsis,
                    contentDescription = "More",
                    modifier = Modifier.size(28.dp),
                    tint = Theme[colors][onBackground]
                )
            }
        }
    )
}

@Composable
private fun ProfileHeader(profile: SocialProfile) {
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
                Text(profile.name, fontWeight = FontWeight.Bold)
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
            Avatar(profile.avatarUrl, size = 82)
        }
        Text(
            text = profile.bio,
        )
        Text(
            text = profile.followerCount,
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

@Composable
private fun ProfileTabs(
    selectedTab: ProfileFeedTab,
    onSelectedTabChange: (ProfileFeedTab) -> Unit,
) {
    TabGroup(
        selectedTab = selectedTab,
        onSelectedTabChange = onSelectedTabChange,
        tabs = ProfileFeedTab.all,
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
    ) {
        TabList(
            modifier = Modifier.fillMaxWidth(),
            equalTabWidth = true,
        ) {
            ProfileFeedTab.all.forEach { tab ->
                Tab(key = tab) {
                    Text(
                        text = tab.label,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
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
    profile: SocialProfile,
    post: ProfilePost,
    onClick: () -> Unit,
) {
    FeedPost(
        onClick = onClick,
        avatar = {
            AvatarButton(
                url = profile.avatarUrl,
                onClick = onClick,
            )
        },
        authorName = {
            Text(
                text = profile.handle,
                fontWeight = FontWeight.Bold,
            )
        },
        timestamp = {
            Text(post.age)
        },
        overflow = { ProfilePostOverflowMenu() },
        body = {
            Text(
                text = post.body,
                color = Theme[colors][onBackground],
            )
        },
        media = if (post.quoteAuthor != null && post.quoteBody != null && post.quoteReplies != null) {
            {
                QuotedPost(
                    avatarUrl = profile.avatarUrl,
                    author = post.quoteAuthor,
                    body = post.quoteBody,
                    replies = post.quoteReplies,
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
    replies: String,
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
            Avatar(avatarUrl, size = 28)
            Text(author, fontWeight = FontWeight.Bold)
        }
        Text(body)
        Text(replies, color = Theme[colors][muted])
    }
}

@Composable
private fun ProfilePostActions(post: ProfilePost) {
    val actionColor = Theme[colors][muted]

    ProfileActionButton(count = post.likes, color = actionColor) { color ->
        Icon(Lucide.Heart, contentDescription = "Like", modifier = Modifier.size(25.dp), tint = color)
    }
    ProfileActionButton(count = post.replies, color = actionColor) { color ->
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
