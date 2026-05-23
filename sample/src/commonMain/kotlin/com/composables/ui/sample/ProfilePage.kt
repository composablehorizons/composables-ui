package com.composables.ui.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.sp
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
import com.composables.ui.components.Text
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
internal fun ProfilePage(onBack: () -> Unit) {
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
                        ProfileTabs()
                    }
                    itemsIndexed(
                        items = profilePosts,
                        key = { _, post -> post.id },
                    ) { index, post ->
                        ProfilePostRow(post)
                        if (index < profilePosts.lastIndex) {
                            HorizontalSeparator()
                        }
                    }
                }
            }
            SocialBottomBar()
        }
    }
}

@Composable
private fun ProfileToolbar(onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(horizontal = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier.clickable(onClick = onBack),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Icon(Lucide.ArrowLeft, contentDescription = "Back", modifier = Modifier.size(28.dp), tint = Theme[colors][onBackground])
            Text("Back", style = TextStyle(fontSize = 22.sp, lineHeight = 28.sp))
        }
        Spacer(Modifier.weight(1f))
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
                Text("John", style = TextStyle(fontSize = 32.sp, lineHeight = 38.sp, fontWeight = FontWeight.Bold))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text("john_mobbin", style = TextStyle(fontSize = 20.sp, lineHeight = 26.sp))
                    Text(
                        text = "social.app",
                        color = Theme[colors][onField],
                        style = TextStyle(fontSize = 16.sp, lineHeight = 22.sp),
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
            style = TextStyle(fontSize = 22.sp, lineHeight = 32.sp),
        )
        Text(
            text = "1 follower",
            color = Theme[colors][muted],
            style = TextStyle(fontSize = 20.sp, lineHeight = 26.sp),
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
private fun ProfileTabs() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .border(width = 1.dp, color = Theme[colors][border]),
        verticalAlignment = Alignment.Bottom,
    ) {
        ProfileTab("Posts", selected = true, modifier = Modifier.weight(1f))
        ProfileTab("Replies", selected = false, modifier = Modifier.weight(1f))
    }
}

@Composable
private fun ProfileTab(
    label: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = label,
            color = if (selected) Theme[colors][onBackground] else Theme[colors][muted],
            style = TextStyle(fontSize = 20.sp, lineHeight = 28.sp, fontWeight = FontWeight.Bold),
        )
        Box(
            modifier = Modifier
                .padding(top = 18.dp)
                .height(2.dp)
                .fillMaxWidth()
                .background(if (selected) Theme[colors][onBackground] else Theme[colors][background]),
        )
    }
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
                style = TextStyle(fontSize = 19.sp, lineHeight = 27.sp),
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
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
    ) {
        Text(
            text = "john_mobbin",
            style = TextStyle(fontSize = 18.sp, lineHeight = 24.sp, fontWeight = FontWeight.Bold),
        )
        Spacer(Modifier.weight(1f))
        Text(
            text = post.age,
            color = Theme[colors][muted],
            style = TextStyle(fontSize = 18.sp, lineHeight = 24.sp),
        )
        Spacer(Modifier.width(12.dp))
        ProfileOverflowMenu()
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
            Text(author, style = TextStyle(fontSize = 18.sp, lineHeight = 24.sp, fontWeight = FontWeight.Bold))
        }
        Text(body, style = TextStyle(fontSize = 20.sp, lineHeight = 28.sp))
        Text(replies, color = Theme[colors][muted], style = TextStyle(fontSize = 18.sp, lineHeight = 24.sp))
    }
}

@Composable
private fun ProfileOverflowMenu() {
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
        ) {
            Icon(Lucide.Ellipsis, contentDescription = "Post options", modifier = Modifier.size(22.dp), tint = Theme[colors][onBackground])
        }
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
            Text(count, color = color, style = TextStyle(fontSize = 16.sp))
        }
    }
}
