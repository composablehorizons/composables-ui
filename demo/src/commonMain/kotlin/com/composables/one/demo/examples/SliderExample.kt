package com.composables.one.demo.examples

import androidx.compose.runtime.Composable
import com.composables.one.Slider
import com.composables.one.rememberSliderState

@Composable
fun SliderExample() {
    val state = rememberSliderState(initialValue = 0.45f)

    Slider(state = state)
}