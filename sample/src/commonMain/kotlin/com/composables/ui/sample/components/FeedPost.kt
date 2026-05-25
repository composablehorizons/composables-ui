package com.composables.ui.sample.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.composables.ui.theme.largeShape
import com.composables.ui.theme.shapes
import com.composeunstyled.theme.Theme

@Composable
internal fun FeedPost(
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
    val shape = Theme[shapes][largeShape]

    Row(
        modifier = modifier
            .fillMaxWidth()
            .focusRing(
                interactionSource = interactionSource,
                width = Theme[componentSizes][focusRingWidth],
                color = Theme[colors][focusRing],
                shape = shape,
                offset = Theme[componentSizes][focusRingOffset],
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            )
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(Modifier.size(44.dp)) {
            avatar()
        }
        Column(Modifier.weight(1f)) {
            Row(Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    authorName()
                    timestamp()
                }
                Box(
                    modifier = Modifier.offset(x = 10.dp, y = (-10).dp),
                ) {
                    overflow()
                }
            }
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                body()
                media?.invoke()
                Row(
                    modifier = Modifier.offset(x = (-20).dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    actions()
                }
            }
        }
    }
}
