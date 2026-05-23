package com.composables.ui.sample

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.icons.lucide.Ellipsis
import com.composables.icons.lucide.Heart
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
import com.composables.ui.sample.components.Avatar
import com.composables.ui.theme.background
import com.composables.ui.theme.border
import com.composables.ui.theme.colors
import com.composables.ui.theme.muted
import com.composables.ui.theme.onBackground
import com.composeunstyled.currentWindowContainerSize
import com.composeunstyled.outline
import com.composeunstyled.theme.Theme
import com.composables.uripainter.rememberUriPainter

internal data class SocialPost(
    val id: String,
    val profileId: String,
    val author: String,
    val age: String,
    val body: String,
    val replies: String,
    val likes: String,
    val avatarUrl: String,
    val imageUrl: String? = null,
)

private val timelinePosts = listOf(
    SocialPost(
        id = "tea-spillage",
        profileId = "iwetmyyplants",
        author = "iwetmyyplants",
        age = "7m",
        body = "I'm just going to say what we are all thinking and knowing is about to go downity down:\nThere is about to be some piping hot tea spillage on here daily that people will be posting and we are all going to be sitting back like:",
        replies = "2",
        likes = "4",
        avatarUrl = "https://images.unsplash.com/photo-1494790108377-be9c29b29330?q=80&w=240",
        imageUrl = "https://images.unsplash.com/photo-1501004318641-b39e6451bec6?q=80&w=1200",
    ),
    SocialPost(
        id = "rachel",
        profileId = "ashtonofplants",
        author = "ashtonofplants",
        age = "5m",
        body = "oh my god rachel, thank goodness, you're here",
        replies = "1",
        likes = "18",
        avatarUrl = "https://images.unsplash.com/photo-1438761681033-6461ffad8d80?q=80&w=240",
    ),
    SocialPost(
        id = "plant-chat",
        profileId = "jungle_dudes",
        author = "jungle_dudes",
        age = "1h",
        body = "Plant parent social app posts are about to turn into group chat energy and I am ready.",
        replies = "7",
        likes = "56",
        avatarUrl = "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?q=80&w=240",
    ),
    SocialPost(
        id = "soil-report",
        profileId = "rootbound",
        author = "rootbound",
        age = "2h",
        body = "Repotted one monstera and somehow ended up cleaning the entire balcony. This app needs a support group for people who say \"quick plant chore\" and vanish for three hours.",
        replies = "9",
        likes = "83",
        avatarUrl = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?q=80&w=240",
    ),
    SocialPost(
        id = "sunlight-map",
        profileId = "window_seat",
        author = "window_seat",
        age = "3h",
        body = "The west window is officially premium real estate. Applications now open. Snake plants need not apply; you know you can survive anywhere.",
        replies = "4",
        likes = "41",
        avatarUrl = "https://images.unsplash.com/photo-1527980965255-d3b416303d12?q=80&w=240",
        imageUrl = "https://images.unsplash.com/photo-1495908333425-29a1e0918c5f?q=80&w=1200",
    ),
    SocialPost(
        id = "watering-day",
        profileId = "leafledger",
        author = "leafledger",
        age = "4h",
        body = "Watering day is less of a task and more of a tiny audit where every plant explains what I did wrong last week.",
        replies = "12",
        likes = "104",
        avatarUrl = "https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?q=80&w=240",
    ),
    SocialPost(
        id = "cutting-club",
        profileId = "propagation_station",
        author = "propagation_station",
        age = "5h",
        body = "The pothos cutting has roots. I am now emotionally attached to a glass jar.",
        replies = "6",
        likes = "67",
        avatarUrl = "https://images.unsplash.com/photo-1517841905240-472988babdf9?q=80&w=240",
        imageUrl = "https://images.unsplash.com/photo-1463936575829-25148e1db1b8?q=80&w=1200",
    ),
    SocialPost(
        id = "fern-drama",
        profileId = "humid_habits",
        author = "humid_habits",
        age = "6h",
        body = "Boston fern update: still dramatic. Mist level increased. Negotiations ongoing.",
        replies = "3",
        likes = "29",
        avatarUrl = "https://images.unsplash.com/photo-1508214751196-bcfd4ca60f91?q=80&w=240",
    ),
    SocialPost(
        id = "new-shelf",
        profileId = "shelf_control",
        author = "shelf_control",
        age = "8h",
        body = "Bought a shelf for plants. Plants filled shelf. Need shelf for shelf.",
        replies = "15",
        likes = "138",
        avatarUrl = "https://images.unsplash.com/photo-1524504388940-b1c1722653e1?q=80&w=240",
    ),
    SocialPost(
        id = "morning-check",
        profileId = "photosynthesquad",
        author = "photosynthesquad",
        age = "10h",
        body = "Morning check-in: everyone alive, one suspicious yellow leaf, and a calathea staring at me like rent is due.",
        replies = "8",
        likes = "92",
        avatarUrl = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?q=80&w=240",
    ),
    SocialPost(
        id = "ceramic-find",
        profileId = "potluck",
        author = "potluck",
        age = "12h",
        body = "Found the perfect ceramic pot and now I need a plant worthy of it. This is how they get you.",
        replies = "2",
        likes = "37",
        avatarUrl = "https://images.unsplash.com/photo-1544005313-94ddf0286df2?q=80&w=240",
        imageUrl = "https://images.unsplash.com/photo-1485955900006-10f4d324d411?q=80&w=1200",
    ),
    SocialPost(
        id = "leaf-cleaning",
        profileId = "dusty_leaves",
        author = "dusty_leaves",
        age = "14h",
        body = "Wiping leaves is basically skincare for plants and I will not be taking questions.",
        replies = "11",
        likes = "76",
        avatarUrl = "https://images.unsplash.com/photo-1547425260-76bcadfb4f2c?q=80&w=240",
    ),
)

private val FeedMaxWidth = 700.dp
private val WideFeedVerticalInset = 70.dp

@Composable
internal fun HomePage(
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
    val windowSize = currentWindowContainerSize()
    val showFeedOutline = windowSize.width > FeedMaxWidth
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
                    items = timelinePosts,
                    key = { _, post -> post.id },
                ) { index, post ->
                    SocialPostRow(
                        post = post,
                        onClick = { onPostClick(post) },
                        onProfileClick = { onProfileClick(post.profileId) },
                    )
                    if (index < timelinePosts.lastIndex) {
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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        SocialTimelineRail(
            avatarUrl = post.avatarUrl,
            onProfileClick = onProfileClick,
            modifier = Modifier.width(44.dp),
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            PostHeader(post, onProfileClick)
            Text(
                text = post.body,
                style = TextStyle(fontSize = 19.sp, lineHeight = 27.sp),
                color = Theme[colors][onBackground],
            )
            if (post.imageUrl != null) {
                PostImage(post.imageUrl)
            }
            PostActions(post)
        }
    }
}

@Composable
private fun SocialTimelineRail(
    avatarUrl: String,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Avatar(
            url = avatarUrl,
            size = 44,
            modifier = Modifier
                .clip(CircleShape)
                .clickable(onClick = onProfileClick),
        )
    }
}

@Composable
private fun PostHeader(
    post: SocialPost,
    onProfileClick: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
    ) {
        Text(
            text = post.author,
            modifier = Modifier.clickable(onClick = onProfileClick),
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(Modifier.weight(1f))
        Text(
            text = post.age,
            color = Theme[colors][muted],
            style = TextStyle(fontSize = 18.sp, lineHeight = 24.sp),
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
            ) {
                Icon(
                    imageVector = Lucide.Ellipsis,
                    contentDescription = "Post options",
                    modifier = Modifier.size(22.dp),
                    tint = Theme[colors][onBackground],
                )
            }
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

    Row(
        modifier = Modifier.offset(x = (-20).dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
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
