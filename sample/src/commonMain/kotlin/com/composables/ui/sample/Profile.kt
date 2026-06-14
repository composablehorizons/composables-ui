package com.composables.ui.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
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
import com.composables.ui.components.TabList
import com.composables.ui.components.Text
import com.composables.ui.components.Tabs
import com.composables.ui.sample.iconography.Ellipsis
import com.composables.ui.sample.iconography.Heart
import com.composables.ui.sample.iconography.Icons
import com.composables.ui.sample.iconography.MessageCircle
import com.composables.ui.sample.iconography.Repeat2
import com.composables.ui.sample.components.Avatar
import com.composables.ui.sample.components.AvatarSize
import com.composables.ui.sample.components.FeedPost
import com.composables.ui.sample.data.Post
import com.composables.ui.sample.data.Posts
import com.composables.ui.sample.data.UserProfile
import com.composables.ui.sample.data.UserProfiles
import com.composables.ui.theme.borderColor
import com.composables.ui.theme.colors
import com.composables.ui.theme.mutedColor
import com.composables.ui.theme.onBackgroundColor
import com.composables.ui.theme.onPanelColor
import com.composables.ui.theme.panelColor
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.theme.Theme

private sealed interface ProfileFeedTab {
    data object Posts : ProfileFeedTab
    data object Replies : ProfileFeedTab
}

private val profileFeedTabs = listOf(ProfileFeedTab.Posts, ProfileFeedTab.Replies)

@Composable
fun Profile(
    profileId: String,
    signedInProfileId: String,
    onPostClick: (Post) -> Unit,
) {
    val profile = UserProfiles.findWithId(profileId)
    val isSignedInProfile = profile.id == signedInProfileId
    var selectedTab by remember { mutableStateOf<ProfileFeedTab>(ProfileFeedTab.Posts) }
    val visiblePosts = when (selectedTab) {
        ProfileFeedTab.Posts -> Posts.postsByProfileId(profile.id)
        ProfileFeedTab.Replies -> Posts.repliesByProfileId(profile.id)
    }
    ProvideContentColor(Theme[colors][onPanelColor]) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(Theme[colors][panelColor]),
            contentPadding = sampleScreenContentPadding(extraTop = 20.dp, extraBottom = 20.dp),
        ) {
            item {
                ProfileHeader(
                    profile = profile,
                    isSignedInProfile = isSignedInProfile,
                )
                Tabs(
                    selectedTab = selectedTab,
                    onSelectedTabChange = { it: ProfileFeedTab -> selectedTab = it },
                    orderedTabs = profileFeedTabs,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                ) {
                    this.TabList(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        profileFeedTabs.forEach { tab ->
                            Tab(
                                key = tab,
                                modifier = Modifier.weight(1f),
                                text = {
                                    Text(
                                        text = tab.label,
                                        fontWeight = FontWeight.Bold,
                                    )
                                },
                            )
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
                    onClick = { onPostClick(post) },
                )
                if (index < visiblePosts.lastIndex) {
                    HorizontalSeparator()
                }
            }
        }
    }
}

@Composable
private fun ProfileHeader(
    profile: UserProfile,
    isSignedInProfile: Boolean,
) {
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
                Text(profile.handle)
            }
            Avatar(profile.avatarUrl, size = AvatarSize.Large)
        }
        Text(
            text = profile.bio,
        )
        Text(
            text = formatFollowerCount(profile.followerCount),
            color = Theme[colors][mutedColor],
        )
        ProfileActionButton(isSignedInProfile)
    }
}

@Composable
private fun ProfileActionButton(isSignedInProfile: Boolean) {
    if (isSignedInProfile) {
        Button(
            onClick = {},
            style = ButtonStyle.Secondary,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Edit profile")
        }
    } else {
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
        ProfileFeedTab.Posts -> "Posts"
        ProfileFeedTab.Replies -> "Replies"
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
            Avatar(
                url = author.avatarUrl,
                modifier = Modifier.clip(CircleShape).clickable { onClick() },
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
                color = Theme[colors][onBackgroundColor],
            )
        },
        attachment = {
            if (post.quoteAuthor != null && post.quoteBody != null && post.quoteReplyCount != null) {
                QuotedPost(
                    avatarUrl = author.avatarUrl,
                    author = post.quoteAuthor,
                    body = post.quoteBody,
                    replyCountLabel = formatReplyCount(post.quoteReplyCount),
                )
            }
        },
        actions = {
            ProfilePostActions(post)
        }
    )
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
                Icons.Ellipsis,
                contentDescription = "Post options",
                modifier = Modifier.size(22.dp),
                tint = Theme[colors][onBackgroundColor],
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
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 80.dp, end = 24.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .border(width = 1.dp, color = Theme[colors][borderColor], shape = RoundedCornerShape(12.dp))
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
            Text(replyCountLabel, color = Theme[colors][mutedColor])
        }
    }
}

@Composable
private fun ProfilePostActions(post: Post) {
    val actionColor = Theme[colors][mutedColor]

    ProfileActionButton(count = formatCount(post.likeCount), color = actionColor) { color ->
        Icon(Icons.Heart, contentDescription = "Like", modifier = Modifier.size(25.dp), tint = color)
    }
    ProfileActionButton(count = formatCount(post.replyCount), color = actionColor) { color ->
        Icon(Icons.MessageCircle, contentDescription = "Reply", modifier = Modifier.size(25.dp), tint = color)
    }
    Button(onClick = {}, style = ButtonStyle.Ghost) {
        Icon(Icons.Repeat2, contentDescription = "Repost", modifier = Modifier.size(25.dp), tint = actionColor)
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
