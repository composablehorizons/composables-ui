package com.composables.ui.components

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.composables.ui.theme.colors
import com.composables.ui.theme.muted
import com.composeunstyled.ScrollbarState
import com.composeunstyled.Thumb
import com.composeunstyled.ThumbVisibility
import com.composeunstyled.UnstyledHorizontalScrollbar
import com.composeunstyled.UnstyledVerticalScrollbar
import com.composeunstyled.rememberScrollbarState
import com.composeunstyled.theme.Theme
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun rememberVerticalScrollbarState(scrollState: ScrollState): ScrollbarState = rememberScrollbarState(scrollState)

@Composable
fun rememberVerticalScrollbarState(lazyListState: LazyListState): ScrollbarState = rememberScrollbarState(lazyListState)

@Composable
fun rememberVerticalScrollbarState(lazyGridState: LazyGridState): ScrollbarState = rememberScrollbarState(lazyGridState)

@Composable
fun VerticalScrollbar(
    state: ScrollbarState,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    reverseLayout: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    thumbColor: Color = Theme[colors][muted],
    thumbShape: Shape = RoundedCornerShape(999.dp),
) {
    UnstyledVerticalScrollbar(state, modifier.width(10.dp).fillMaxHeight(), enabled, interactionSource, reverseLayout) {
        Thumb(
            thumbVisibility = ThumbVisibility.HideWhileIdle(fadeIn(), fadeOut(), 700.milliseconds),
            modifier = Modifier.clip(thumbShape).background(thumbColor.copy(alpha = 0.45f), thumbShape).width(6.dp),
        )
    }
}

@Composable
fun HorizontalScrollbar(
    state: ScrollbarState,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    reverseLayout: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    thumbColor: Color = Theme[colors][muted],
    thumbShape: Shape = RoundedCornerShape(999.dp),
) {
    UnstyledHorizontalScrollbar(state, modifier.height(10.dp).fillMaxWidth(), enabled, interactionSource, reverseLayout) {
        Thumb(
            thumbVisibility = ThumbVisibility.HideWhileIdle(fadeIn(), fadeOut(), 700.milliseconds),
            modifier = Modifier.clip(thumbShape).background(thumbColor.copy(alpha = 0.45f), thumbShape).height(6.dp),
        )
    }
}
