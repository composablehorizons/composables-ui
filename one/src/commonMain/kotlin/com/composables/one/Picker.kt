package com.composables.one

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.overscroll
import androidx.compose.foundation.rememberOverscrollEffect
import androidx.compose.foundation.withoutVisualEffect
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.composables.one.Sample
import com.composables.one.styling.colors
import com.composables.one.styling.focusRing
import com.composables.one.styling.medium
import com.composables.one.styling.shapes
import com.composables.one.styling.textStyles
import com.composables.one.styling.title
import com.composeunstyled.Text as UnstyledText
import com.composeunstyled.focusRing
import com.composeunstyled.theme.Theme
import kotlinx.coroutines.flow.distinctUntilChanged

@Sample("PickerExample")
@Composable
fun Picker(
    value: String,
    values: List<String>,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val numberOfVisibleItems = 5

    val textStyle = Theme[textStyles][title].copy(fontWeight = FontWeight.Normal)
    val itemHeight = with(LocalDensity.current) {
        (textStyle.fontSize.toDp() + 16.dp).coerceAtLeast(32.dp)
    }
    val listHeight = itemHeight * numberOfVisibleItems

    val currentValueChange by rememberUpdatedState(onValueChange)

    val lazyListState = rememberLazyListState()
    val itemHeightPx = with(LocalDensity.current) { itemHeight.roundToPx() }
    val flingBehavior = rememberSnapFlingBehavior(lazyListState)

    var focusedItemIndex by remember { mutableStateOf(-1) }

    val centeredFocusRequester = remember { FocusRequester() }

    val currentIndex by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex +
                    if (lazyListState.firstVisibleItemScrollOffset > itemHeightPx / 2) 1 else 0
        }
    }

    LaunchedEffect(Unit) {
        val index = values.indexOf(value).coerceAtLeast(0)
        lazyListState.scrollToItem(index)
        focusedItemIndex = index
    }

    LaunchedEffect(Unit) {
        snapshotFlow { currentIndex }
            .distinctUntilChanged()
            .collect { selectedIndex ->
                if (selectedIndex in values.indices) {
                    currentValueChange(values[selectedIndex])
                }
            }
    }

    LaunchedEffect(focusedItemIndex) {
        if (focusedItemIndex in values.indices) {
            lazyListState.animateScrollToItem(focusedItemIndex)
        }
    }

    val overscrollEffect = rememberOverscrollEffect()

    LazyColumn(
        overscrollEffect = overscrollEffect?.withoutVisualEffect(),
        modifier = modifier
            .focusable(interactionSource = interactionSource)
            .overscroll(overscrollEffect)
            .focusProperties {
                onEnter = {
                    // move focus to centered item
                    centeredFocusRequester.requestFocus()
                }
            }
            .onKeyEvent { event ->
                when (event.key) {
                    Key.DirectionUp -> {
                        if (event.type == KeyEventType.KeyDown && focusedItemIndex > 0) {
                            val prevIndex = (focusedItemIndex - 1).coerceAtLeast(0)
                            focusedItemIndex = prevIndex
                        }
                        true
                    }

                    Key.DirectionDown -> {
                        if (event.type == KeyEventType.KeyDown && focusedItemIndex < values.lastIndex) {
                            val nextIndex = (focusedItemIndex + 1).coerceAtMost(values.size - 1)
                            focusedItemIndex = nextIndex
                        }
                        true
                    }


                    else -> false
                }
            }

            .focusRing(interactionSource, 2.dp, color = Theme[colors][focusRing], Theme[shapes][medium])
            .fadingEdge(Brush.verticalGradient(0f to Color.Transparent, 0.3f to Color.Black))
            .fadingEdge(Brush.verticalGradient(0.7f to Color.Black, 1f to Color.Transparent))
            .height(listHeight),
        state = lazyListState,
        flingBehavior = flingBehavior,
        contentPadding = PaddingValues(vertical = listHeight / 2 - itemHeight / 2),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(values.size) { index ->
            val alpha by animateFloatAsState(targetValue = if (index == currentIndex) 1f else 0.6f)
            Box(
                modifier = Modifier
                    .widthIn(min = 48.dp)
                    .alpha(alpha)
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center,
            ) {
                UnstyledText(text = values[index], style = textStyle)
            }
        }
    }
}

private fun Modifier.fadingEdge(brush: Brush) = this
    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    .drawWithContent {
        drawContent()
        drawRect(brush = brush, blendMode = BlendMode.DstIn)
    }
