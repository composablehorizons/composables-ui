package com.composables.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.composables.ui.theme.alphas
import com.composables.ui.theme.border
import com.composables.ui.theme.colors
import com.composables.ui.theme.componentSizes
import com.composables.ui.theme.control
import com.composables.ui.theme.disabledAlpha
import com.composables.ui.theme.focusRing
import com.composables.ui.theme.focusRingOffset
import com.composables.ui.theme.focusRingWidth
import com.composables.ui.theme.primary
import com.composables.ui.theme.selectedControl
import com.composeunstyled.SliderState
import com.composeunstyled.UnstyledSlider
import com.composeunstyled.buildModifier
import com.composeunstyled.theme.Theme

@Composable
fun Slider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    steps: Int = 0,
    onValueChangeFinished: (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val shape = RoundedCornerShape(999.dp)
    UnstyledSlider(
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        valueRange = valueRange,
        steps = steps,
        onValueChangeFinished = onValueChangeFinished,
        orientation = Orientation.Horizontal,
        interactionSource = interactionSource,
        modifier = modifier
            .heightIn(min = 32.dp)
            .focusRing(interactionSource, Theme[componentSizes][focusRingWidth], Theme[colors][focusRing], shape, Theme[componentSizes][focusRingOffset])
            .then(buildModifier { if (!enabled) add(Modifier.alpha(Theme[alphas][disabledAlpha])) }),
        track = { SliderTrack(it, shape) },
        thumb = { SliderThumb(it) },
    )
}

@Composable
private fun SliderTrack(state: SliderState, shape: Shape) {
    Box(Modifier.fillMaxWidth().height(6.dp).clip(shape).background(Theme[colors][control], shape)) {
        Box(Modifier.fillMaxWidth(state.fraction).height(6.dp).background(Theme[colors][primary], shape))
    }
}

@Composable
private fun SliderThumb(state: SliderState) {
    val thumbColor by animateColorAsState(
        if (state.isDragging || state.isPressed || state.isFocused) Theme[colors][primary] else Theme[colors][selectedControl],
    )
    Box(Modifier.size(28.dp), contentAlignment = Alignment.Center) {
        Box(Modifier.size(18.dp).background(thumbColor, CircleShape).border(1.dp, Theme[colors][border], CircleShape))
    }
}
