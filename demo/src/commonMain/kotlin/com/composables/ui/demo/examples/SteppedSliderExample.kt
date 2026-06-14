package com.composables.ui.demo.examples

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.composables.ui.components.Slider

@Composable
fun SteppedSliderExample() {
    var value by remember { mutableFloatStateOf(50f) }
    Slider(
        value = value,
        onValueChange = { value = it },
        valueRange = 0f..100f,
        steps = 4,
        modifier = Modifier.fillMaxWidth(),
    )
}
