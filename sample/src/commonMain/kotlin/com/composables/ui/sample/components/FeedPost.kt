package com.composables.ui.sample.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.composables.ui.components.focusRing
import com.composables.ui.theme.borderColor
import com.composables.ui.theme.colors
import com.composables.ui.theme.ringColor
import com.composables.ui.theme.mediumShape
import com.composables.ui.theme.mutedColor
import com.composables.ui.theme.shapes
import com.composables.uripainter.rememberUriPainter
import com.composeunstyled.FocusRingVisibility
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.theme.Theme

private val FeedPostHeaderControlSize = 20.dp

@Composable
fun FeedPost(
    onClick: () -> Unit,
    avatar: @Composable () -> Unit,
    authorName: @Composable () -> Unit,
    timestamp: @Composable () -> Unit,
    overflow: @Composable () -> Unit,
    body: @Composable () -> Unit,
    attachment: (@Composable () -> Unit)? = null,
    actions: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .focusRing(
                interactionSource = interactionSource,
                color = Theme[colors][ringColor],
                visibility = FocusRingVisibility.Focused,
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            )
            .padding(vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Box(Modifier.size(44.dp)) {
                avatar()
            }
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Box(Modifier.weight(1f, fill = false)) {
                            authorName()
                        }
                        ProvideContentColor(Theme[colors][mutedColor]) {
                            timestamp()
                        }
                    }
                    Box(
                        modifier = Modifier.size(FeedPostHeaderControlSize),
                        contentAlignment = Alignment.Center,
                    ) {
                        overflow()
                    }
                }
                body()
            }
        }
        if (attachment != null) {
            attachment()
        }
        Row(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(start = 56.dp)
                .offset(x = (-16).dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            actions()
        }
    }
}

@Composable
fun MediaAttachment(
    contentPadding: PaddingValues = PaddingValues(start = 80.dp, end = 24.dp),
    content: @Composable () -> Unit,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(contentPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        content()
    }
}

@Composable
fun PortraitMediaItem(url: String) {
    Image(
        painter = rememberUriPainter(url),
        contentDescription = null,
        modifier = Modifier
            .size(220.dp, 280.dp)
            .clip(Theme[shapes][mediumShape])
            .background(Theme[colors][borderColor], Theme[shapes][mediumShape])
            .border(1.dp, Theme[colors][borderColor], Theme[shapes][mediumShape]),
        contentScale = ContentScale.Crop,
    )
}

@Composable
fun LandscapeMediaItem(url: String) {

    Image(
        painter = rememberUriPainter(url),
        contentDescription = null,
        modifier = Modifier
            .size(250.dp, 180.dp)
            .clip(Theme[shapes][mediumShape])
            .background(Theme[colors][borderColor], Theme[shapes][mediumShape])
            .border(1.dp, Theme[colors][borderColor], Theme[shapes][mediumShape]),
        contentScale = ContentScale.Crop,
    )
}
