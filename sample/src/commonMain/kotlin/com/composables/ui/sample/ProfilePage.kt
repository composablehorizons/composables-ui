package com.composables.ui.sample

import androidx.compose.animation.core.animateDpAsState
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
private val WideProfileVerticalInset = 70.dp

private val fakeProfiles = listOf(
    Profile(
        id = "john_mobbin",
        name = "John",
        handle = "john_mobbin",
        badge = "social.app",
        bio = "I love to travel, and hope to see more of the world each day",
        followerCount = "1 follower",
        avatarUrl = "https://images.unsplash.com/photo-1520412099551-62b6bafeb5bb?q=80&w=240",
        posts = listOf(
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
        ),
        replies = listOf(
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
        ),
    ),
    Profile(
        id = "iwetmyyplants",
        name = "Rachel",
        handle = "iwetmyyplants",
        badge = "plant log",
        bio = "Overwatering emotions, underwatering actual plants.",
        followerCount = "18.4k followers",
        avatarUrl = "https://images.unsplash.com/photo-1494790108377-be9c29b29330?q=80&w=240",
        posts = listOf(
            ProfilePost(
                "rachel-tea",
                "7m",
                "There is about to be some piping hot tea spillage in the plant group chat.",
                "2",
                "4"
            ),
            ProfilePost("rachel-fern", "1h", "My fern has entered its dramatic era again.", "6", "33"),
            ProfilePost(
                "rachel-cuttings",
                "5h",
                "Cuttings update: everyone has roots except the one I cared about most.",
                "8",
                "91"
            ),
        ),
        replies = listOf(
            ProfilePost("rachel-reply-pot", "2h", "That pot is doing a lot of emotional support work.", "1", "12"),
            ProfilePost(
                "rachel-reply-light",
                "6h",
                "Move it three inches left and pretend that was always the plan.",
                "3",
                "26"
            ),
        ),
    ),
    Profile(
        id = "ashtonofplants",
        name = "Ashton",
        handle = "ashtonofplants",
        badge = "social.app",
        bio = "Plants, coffee, and saying I only need one more shelf.",
        followerCount = "4,208 followers",
        avatarUrl = "https://images.unsplash.com/photo-1438761681033-6461ffad8d80?q=80&w=240",
        posts = listOf(
            ProfilePost("ashton-rachel", "5m", "oh my god rachel, thank goodness, you're here", "1", "18"),
            ProfilePost("ashton-shelf", "3h", "The new shelf is full. I have learned nothing.", "9", "77"),
            ProfilePost("ashton-moss", "9h", "Moss poles are furniture now and I will not hear otherwise.", "5", "48"),
        ),
        replies = listOf(
            ProfilePost(
                "ashton-reply-water",
                "1h",
                "I use reminders and still manage to freestyle the watering schedule.",
                "4",
                "19"
            ),
            ProfilePost("ashton-reply-window", "7h", "North window plants deserve more respect.", "2", "14"),
        ),
    ),
    Profile(
        id = "jungle_dudes",
        name = "Jungle Dudes",
        handle = "jungle_dudes",
        badge = "duo",
        bio = "Two friends slowly turning an apartment into a conservatory.",
        followerCount = "9,812 followers",
        avatarUrl = "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?q=80&w=240",
        posts = listOf(
            ProfilePost(
                "jungle-chat",
                "1h",
                "Plant parent social app posts are about to turn into group chat energy and I am ready.",
                "7",
                "56"
            ),
            ProfilePost(
                "jungle-humidifier",
                "4h",
                "Bought a humidifier and accidentally created a microclimate.",
                "11",
                "84"
            ),
            ProfilePost(
                "jungle-trail",
                "1d",
                "Weekend plan: nursery, coffee, pretend we have self-control.",
                "6",
                "63"
            ),
        ),
        replies = listOf(
            ProfilePost("jungle-reply-shelf", "3h", "Second shelf is not optional. It is infrastructure.", "2", "31"),
            ProfilePost("jungle-reply-soil", "8h", "Chunky soil mix changed everything.", "1", "17"),
        ),
    ),
    Profile(
        id = "rootbound",
        name = "Maya",
        handle = "rootbound",
        badge = "repotting",
        bio = "I repot for fun and then complain about the mess.",
        followerCount = "2,341 followers",
        avatarUrl = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?q=80&w=240",
        posts = listOf(
            ProfilePost(
                "rootbound-balcony",
                "2h",
                "Repotted one monstera and somehow ended up cleaning the entire balcony.",
                "9",
                "83"
            ),
            ProfilePost("rootbound-mix", "5h", "Today’s soil mix: bark, perlite, compost, confidence.", "4", "36"),
            ProfilePost("rootbound-roots", "1d", "Healthy roots are the plant version of good gossip.", "7", "58"),
        ),
        replies = listOf(
            ProfilePost("rootbound-reply-fern", "4h", "Ferns do not negotiate. They issue demands.", "5", "44"),
            ProfilePost("rootbound-reply-pot", "11h", "Drainage holes are not a suggestion.", "2", "29"),
        ),
    ),
)

private data class Profile(
    val id: String,
    val name: String,
    val handle: String,
    val badge: String,
    val bio: String,
    val followerCount: String,
    val avatarUrl: String,
    val posts: List<ProfilePost>,
    val replies: List<ProfilePost>,
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
fun ProfilePage(
    profileId: String,
    onBack: () -> Unit,
    onPostClick: (String) -> Unit,
    onProfileClick: () -> Unit,
) {
    val profile = fakeProfiles.firstOrNull { it.id == profileId } ?: fakeProfiles.first()
    var selectedTab by remember { mutableStateOf(ProfileFeedTab.Posts) }
    val visiblePosts = when (selectedTab) {
        ProfileFeedTab.Replies -> profile.replies
        else -> profile.posts
    }
    val widthBreakpoint = currentWidthBreakpoint()
    val showProfileOutline = widthBreakpoint isAtLeast ExpandedWidthBreakpoint
    val profileShape = RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp)
    val profileVerticalInset by animateDpAsState(
        targetValue = if (showProfileOutline) WideProfileVerticalInset else 0.dp,
        label = "ProfileVerticalInset",
    )

    ScreenScaffold {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .weight(1f)
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
                            .padding(bottom = profileVerticalInset)
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
                Icon(
                    Lucide.ArrowLeft,
                    contentDescription = "Back",
                    modifier = Modifier.size(28.dp),
                    tint = Theme[colors][onBackground]
                )
            }
            Text("Back")
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
private fun ProfileHeader(profile: Profile) {
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
                Text(profile.name, style = TextStyle(fontWeight = FontWeight.Bold))
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
private fun ProfilePostRow(
    profile: Profile,
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
                style = TextStyle(fontWeight = FontWeight.Bold),
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
            Text(author, style = TextStyle(fontWeight = FontWeight.Bold))
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
