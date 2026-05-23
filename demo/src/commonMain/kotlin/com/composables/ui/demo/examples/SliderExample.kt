package com.composables.ui.demo.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.ui.components.Slider
import com.composables.ui.components.Text

@Composable
fun SliderExample() {
    var value by remember { mutableFloatStateOf(0.45f) }
    Column(
        modifier = Modifier.widthIn(max = 320.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Slider(value = value, onValueChange = { value = it }, modifier = Modifier.fillMaxWidth())
        Text("${(value * 100).toInt()}%")
    }
}
