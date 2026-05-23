package com.composables.ui.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.TextStyle
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
import com.composables.ui.components.ButtonStyle
import com.composables.ui.components.DropdownMenu
import com.composables.ui.components.DropdownMenuAlignment
import com.composables.ui.components.DropdownMenuItem
import com.composables.ui.components.DropdownMenuItemStyle
import com.composables.ui.components.DropdownMenuPanel
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
import com.composables.ui.theme.background
import com.composables.ui.theme.border
import com.composables.ui.theme.colors
import com.composables.ui.theme.field
import com.composables.ui.theme.muted
import com.composables.ui.theme.onBackground
import com.composables.ui.theme.onField
import com.composeunstyled.theme.Theme

private val ProfileMaxWidth = 700.dp
private const val ProfileAvatarUrl = "https://images.unsplash.com/photo-1520412099551-62b6bafeb5bb?q=80&w=240"

private val profilePosts = listOf(
    ProfilePost(
        id = "botany-follow",
        age = "4h",
        body = "Here's a post you should follow if you love botany @jane_mobbin",
        replies = "2",
        likes = "11",
    ),
    ProfilePost(
        id = "morocco-dream",
        age = "4h",
        body = "Always a dream to see the Medina in Morocco!",
        quoteAuthor = "earthpix",
        quoteBody = "What is one place you're absolutely traveling to by next year?",
        quoteReplies = "237 replies",
        replies = "9",
        likes = "42",
    ),
    ProfilePost(
        id = "plant-light",
        age = "7h",
        body = "Soft morning light makes every plant look like it has a personal photographer.",
        replies = "4",
        likes = "28",
    ),
    ProfilePost(
        id = "tiny-garden",
        age = "1d",
        body = "Tiny balconies count as gardens if you care enough.",
        replies = "5",
        likes = "51",
    ),
)

private val profileReplies = listOf(
    ProfilePost(
        id = "reply-greenhouse",
        age = "2h",
        body = "This is exactly why small greenhouses are dangerous. One shelf becomes a whole weekend project.",
        replies = "1",
        likes = "14",
    ),
    ProfilePost(
        id = "reply-travel",
        age = "6h",
        body = "Adding this to the travel list. The light in that courtyard looks unreal.",
        replies = "3",
        likes = "22",
        quoteAuthor = "jane_mobbin",
        quoteBody = "The botanical garden in Palermo might be my favorite quiet place in the city.",
        quoteReplies = "19 replies",
    ),
    ProfilePost(
        id = "reply-balcony",
        age = "1d",
        body = "A balcony garden counts. The plants do not care about square footage.",
        replies = "2",
        likes = "31",
    ),
)

@kotlin.jvm.JvmInline
private value class ProfileFeedTab private constructor(val value: String) {
    companion object {
        val Posts = ProfileFeedTab("posts")
        val Replies = ProfileFeedTab("replies")
        val all = listOf(Posts, Replies)
    }
}

private data class ProfilePost(
    val id: String,
    val age: String,
    val body: String,
    val replies: String,
    val likes: String,
    val quoteAuthor: String? = null,
    val quoteBody: String? = null,
    val quoteReplies: String? = null,
)

@Composable
internal fun ProfilePage(
    onBack: () -> Unit,
    onProfileClick: () -> Unit,
) {
    var selectedTab by remember { mutableStateOf(ProfileFeedTab.Posts) }
    val visiblePosts = when (selectedTab) {
        ProfileFeedTab.Replies -> profileReplies
        else -> profilePosts
    }

    ScreenScaffold(backgroundColor = Theme[colors][background], contentColor = Theme[colors][onBackground]) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme[colors][background]),
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
            ) {
                LazyColumn(
                    modifier = Modifier
                        .widthIn(max = ProfileMaxWidth)
                        .fillMaxWidth()
                        .align(Alignment.TopCenter),
                ) {
                    item {
                        ProfileToolbar(onBack)
                        ProfileHeader()
                        ProfileTabs(
                            selectedTab = selectedTab,
                            onSelectedTabChange = { selectedTab = it },
                        )
                    }
                    itemsIndexed(
                        items = visiblePosts,
                        key = { _, post -> post.id },
                    ) { index, post ->
                        ProfilePostRow(post)
                        if (index < visiblePosts.lastIndex) {
                            HorizontalSeparator()
                        }
                    }
                }
            }
            SocialBottomBar(onProfileClick = onProfileClick)
        }
    }
}

@Composable
private fun ProfileToolbar(onBack: () -> Unit) {
    Toolbar(
        backgroundColor = Theme[colors][background],
        leading = {
            IconButton(onClick = onBack, style = ButtonStyle.Ghost) {
                Icon(Lucide.ArrowLeft, contentDescription = "Back", modifier = Modifier.size(28.dp), tint = Theme[colors][onBackground])
            }
            Text("Back")
        },
        trailing = {
            IconButton(onClick = {}, style = ButtonStyle.Ghost) {
                Icon(Lucide.Instagram, contentDescription = "Instagram", modifier = Modifier.size(28.dp), tint = Theme[colors][onBackground])
            }
            IconButton(onClick = {}, style = ButtonStyle.Ghost) {
                Icon(Lucide.Bell, contentDescription = "Notifications", modifier = Modifier.size(28.dp), tint = Theme[colors][onBackground])
            }
            IconButton(onClick = {}, style = ButtonStyle.Ghost) {
                Icon(Lucide.CircleEllipsis, contentDescription = "More", modifier = Modifier.size(28.dp), tint = Theme[colors][onBackground])
            }
        }
    )
}

@Composable
private fun ProfileHeader() {
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
                Text("John", style = TextStyle(fontWeight = FontWeight.Bold))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text("john_mobbin")
                    Text(
                        text = "social.app",
                        color = Theme[colors][onField],
                        modifier = Modifier
                            .clip(RoundedCornerShape(100))
                            .background(Theme[colors][field])
                            .padding(horizontal = 12.dp, vertical = 5.dp),
                    )
                }
            }
            Avatar(ProfileAvatarUrl, size = 82)
        }
        Text(
            text = "I love to travel, and hope to see more of the world each day",
        )
        Text(
            text = "1 follower",
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
                        style = TextStyle(fontWeight = FontWeight.Bold),
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
private fun ProfilePostRow(post: ProfilePost) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Avatar(ProfileAvatarUrl, size = 44)
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            ProfilePostHeader(post)
            Text(
                text = post.body,
                color = Theme[colors][onBackground],
            )
            if (post.quoteAuthor != null && post.quoteBody != null && post.quoteReplies != null) {
                QuotedPost(post.quoteAuthor, post.quoteBody, post.quoteReplies)
            }
            ProfilePostActions(post)
        }
    }
}

@Composable
private fun ProfilePostHeader(post: ProfilePost) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
    ) {
        Text(
            text = "john_mobbin",
            style = TextStyle(fontWeight = FontWeight.Bold),
        )
        Spacer(Modifier.weight(1f))
        Text(
            text = post.age,
            color = Theme[colors][muted],
        )
        Spacer(Modifier.width(12.dp))
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
            ) {
                Icon(Lucide.Ellipsis, contentDescription = "Post options", modifier = Modifier.size(22.dp), tint = Theme[colors][onBackground])
            }
        }
    }
}

@Composable
private fun QuotedPost(
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
            Avatar(ProfileAvatarUrl, size = 28)
            Text(author, style = TextStyle(fontWeight = FontWeight.Bold))
        }
        Text(body)
        Text(replies, color = Theme[colors][muted])
    }
}

@Composable
private fun ProfilePostActions(post: ProfilePost) {
    val actionColor = Theme[colors][muted]

    Row(
        modifier = Modifier.offset(x = (-20).dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
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
