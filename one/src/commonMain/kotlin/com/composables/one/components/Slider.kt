package com.composables.one.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.composables.core.androidx.annotation.IntRange
import com.composables.one.Sample
import com.composables.one.styling.card
import com.composables.one.styling.colors
import com.composables.one.styling.primary
import com.composables.one.styling.shadows
import com.composables.one.styling.subtle
import com.composeunstyled.UnstyledThumb
import com.composeunstyled.minimumInteractiveComponentSize
import com.composeunstyled.theme.Theme
import com.composeunstyled.UnstyledSlider
import com.composeunstyled.SliderState as UnstyledSliderState

@Composable
fun rememberSliderState(
    initialValue: Float = 0.0f,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    @IntRange(from = 0) steps: Int = 0,
): SliderState {
    return remember {
        SliderState(initialValue, valueRange, steps)
    }
}

class SliderState internal constructor(initialValue: Float, valueRange: ClosedFloatingPointRange<Float>, steps: Int) {
    val unstyledSliderState = UnstyledSliderState(
        initialValue = initialValue, valueRange = valueRange, steps = steps
    )

    val value: Float
        get() = unstyledSliderState.value
}

@Sample("SliderExample")
@Composable
fun Slider(
    state: SliderState,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    thumbColor: Color = Theme[colors][card],
    filledTrackColor: Color = Theme[colors][primary],
    trackColor: Color = filledTrackColor.copy(alpha = 0.4f),
) {
    val isFocused by interactionSource.collectIsFocusedAsState()
    val isPressed by interactionSource.collectIsPressedAsState()

    UnstyledSlider(
        modifier = modifier,
        state = state.unstyledSliderState,
        interactionSource = interactionSource,
        track = {
            Box(
                Modifier.fillMaxWidth().height(8.dp).padding(horizontal = 16.dp).clip(RoundedCornerShape(100.dp))
            ) {
                // the 'not yet completed' part of the track
                Box(
                    Modifier.fillMaxHeight().fillMaxWidth().background(trackColor)
                )
                // the 'completed' part of the track
                Box(
                    Modifier.fillMaxHeight().fillMaxWidth(state.value).background(filledTrackColor)
                )
            }
        },
        thumb = {
            val thumbSize by animateDpAsState(targetValue = if (isPressed) 22.dp else 18.dp)

            val thumbInteractionSource = remember { MutableInteractionSource() }
            val isHovered by thumbInteractionSource.collectIsHoveredAsState()
            val glowColor by animateColorAsState(
                if (isFocused || isHovered) Color.White.copy(0.33f) else Color.Transparent
            )
            // keep the size fixed to ensure that the resizing animation is always centered
            Box(
                modifier = Modifier.size(36.dp).clip(CircleShape).background(glowColor),
                contentAlignment = Alignment.Center
            ) {
                UnstyledThumb(
                    color = thumbColor,
                    modifier = Modifier.size(thumbSize)
                        .dropShadow(CircleShape, Theme[shadows][subtle])
                        .hoverable(thumbInteractionSource),
                    shape = CircleShape,
                )
            }
        }
    )
}