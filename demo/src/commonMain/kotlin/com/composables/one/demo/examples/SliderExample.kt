package com.composables.one.demo.examples

import androidx.compose.runtime.Composable
import com.composables.one.components.Slider
import com.composables.one.components.rememberSliderState

@Composable
fun SliderExample() {
    val state = rememberSliderState(initialValue = 0.45f)

    Slider(state = state)
}