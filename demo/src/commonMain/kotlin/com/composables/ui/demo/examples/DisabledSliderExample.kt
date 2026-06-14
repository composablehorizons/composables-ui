package com.composables.ui.demo.examples

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.composables.ui.components.Slider

@Composable
fun DisabledSliderExample() {
    Slider(
        value = 0.45f,
        onValueChange = {},
        enabled = false,
        modifier = Modifier.fillMaxWidth(),
    )
}
