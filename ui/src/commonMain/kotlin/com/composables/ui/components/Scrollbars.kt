package com.composables.ui.components

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.composables.ui.theme.colors
import com.composables.ui.theme.mutedColor
import com.composeunstyled.Thumb
import com.composeunstyled.ThumbVisibility
import com.composeunstyled.UnstyledHorizontalScrollbar
import com.composeunstyled.UnstyledVerticalScrollbar
import com.composeunstyled.theme.Theme
import com.composeunstyled.ScrollbarState as UnstyledScrollbarState
import com.composeunstyled.rememberScrollbarState as rememberUnstyledScrollbarState
import kotlin.time.Duration.Companion.milliseconds

@Stable
class ScrollbarState internal constructor(
    internal val unstyledState: UnstyledScrollbarState,
)

/**
 * Creates and remembers scrollbar state for a scrollable container.
 * @param scrollState ScrollState used to drive the scrollbar.
 */
@Composable
fun rememberVerticalScrollbarState(scrollState: ScrollState): ScrollbarState {
    return rememberScrollbarState(rememberUnstyledScrollbarState(scrollState))
}

/**
 * Creates and remembers scrollbar state for a scrollable container.
 * @param lazyListState LazyListState used to drive the scrollbar.
 */
@Composable
fun rememberVerticalScrollbarState(lazyListState: LazyListState): ScrollbarState {
    return rememberScrollbarState(rememberUnstyledScrollbarState(lazyListState))
}

/**
 * Creates and remembers scrollbar state for a scrollable container.
 * @param lazyGridState LazyGridState used to drive the scrollbar.
 */
@Composable
fun rememberVerticalScrollbarState(lazyGridState: LazyGridState): ScrollbarState {
    return rememberScrollbarState(rememberUnstyledScrollbarState(lazyGridState))
}

@Composable
private fun rememberScrollbarState(unstyledState: UnstyledScrollbarState): ScrollbarState {
    return remember(unstyledState) { ScrollbarState(unstyledState) }
}

/**
 * A vertical scrollbar tied to a ScrollbarState.
 * @param state Scrollbar state used by the scrollbar component.
 * @param modifier Modifier applied to the scrollbar.
 * @param enabled Whether the scrollbar can be interacted with.
 * @param reverseLayout Whether the underlying layout scroll direction is reversed.
 * @param interactionSource Interaction source used for dragging and hover state.
 * @param thumbColor Color used for the scrollbar thumb.
 * @param thumbShape Shape used for the scrollbar thumb.
 * @param autoHide Whether the scrollbar thumb fades out while idle.
 */
@Composable
fun VerticalScrollbar(
    state: ScrollbarState,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    reverseLayout: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    thumbColor: Color = Theme[colors][mutedColor],
    thumbShape: Shape = RoundedCornerShape(999.dp),
    autoHide: Boolean = true,
) {
    UnstyledVerticalScrollbar(
        scrollbarState = state.unstyledState,
        modifier = modifier.width(10.dp),
        enabled = enabled,
        interactionSource = interactionSource,
        reverseLayout = reverseLayout
    ) {
        Thumb(
            thumbVisibility = scrollbarThumbVisibility(autoHide),
            enabled = enabled,
            modifier = Modifier.clip(thumbShape).background(thumbColor.copy(alpha = 0.5f), thumbShape).width(6.dp),
        )
    }
}

/**
 * A horizontal scrollbar tied to a ScrollbarState.
 * @param state Scrollbar state used by the scrollbar component.
 * @param modifier Modifier applied to the scrollbar.
 * @param enabled Whether the scrollbar can be interacted with.
 * @param reverseLayout Whether the underlying layout scroll direction is reversed.
 * @param interactionSource Interaction source used for dragging and hover state.
 * @param thumbColor Color used for the scrollbar thumb.
 * @param thumbShape Shape used for the scrollbar thumb.
 * @param autoHide Whether the scrollbar thumb fades out while idle.
 */
@Composable
fun HorizontalScrollbar(
    state: ScrollbarState,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    reverseLayout: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    thumbColor: Color = Theme[colors][mutedColor],
    thumbShape: Shape = RoundedCornerShape(999.dp),
    autoHide: Boolean = true,
) {
    UnstyledHorizontalScrollbar(state.unstyledState, modifier.height(10.dp), enabled, interactionSource, reverseLayout) {
        Thumb(
            thumbVisibility = scrollbarThumbVisibility(autoHide),
            enabled = enabled,
            modifier = Modifier.clip(thumbShape).background(thumbColor.copy(alpha = 0.45f), thumbShape).height(6.dp),
        )
    }
}

private fun scrollbarThumbVisibility(autoHide: Boolean): ThumbVisibility {
    return if (autoHide) {
        ThumbVisibility.HideWhileIdle(fadeIn(), fadeOut(), 700.milliseconds)
    } else {
        ThumbVisibility.AlwaysVisible
    }
}
