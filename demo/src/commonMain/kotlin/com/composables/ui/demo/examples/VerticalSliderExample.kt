package com.composables.ui.demo.examples

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.ui.components.Slider

@Composable
fun VerticalSliderExample() {
    var value by remember { mutableFloatStateOf(0.45f) }
    Slider(
        value = value,
        onValueChange = { value = it },
        orientation = Orientation.Vertical,
        modifier = Modifier.height(180.dp),
    )
}
