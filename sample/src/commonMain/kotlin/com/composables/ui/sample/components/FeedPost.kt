package com.composables.ui.sample.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.ui.components.focusRing
import com.composables.ui.theme.colors
import com.composables.ui.theme.componentSizes
import com.composables.ui.theme.focusRing
import com.composables.ui.theme.focusRingOffset
import com.composables.ui.theme.focusRingWidth
import com.composables.ui.theme.muted
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.theme.Theme

@Composable
fun FeedPost(
    onClick: () -> Unit,
    avatar: @Composable () -> Unit,
    authorName: @Composable () -> Unit,
    timestamp: @Composable () -> Unit,
    overflow: @Composable () -> Unit,
    body: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    media: (@Composable () -> Unit)? = null,
    actions: @Composable () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .focusRing(
                interactionSource = interactionSource,
                width = Theme[componentSizes][focusRingWidth],
                color = Theme[colors][focusRing],
                offset = Theme[componentSizes][focusRingOffset],
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
                        ProvideContentColor(Theme[colors][muted]) {
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
        if (media != null) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .horizontalScroll(rememberScrollState())
                    .padding(start = 80.dp, end = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                media()
            }
        }
        Row(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(start = 56.dp)
                .offset(x = (-20).dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            actions()
        }
    }
}

private val FeedPostHeaderControlSize = 20.dp
