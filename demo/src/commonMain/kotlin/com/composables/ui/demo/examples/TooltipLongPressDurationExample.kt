package com.composables.ui.demo.examples

import androidx.compose.runtime.Composable
import com.composables.ui.components.Button
import com.composables.ui.components.ButtonStyle
import com.composables.ui.components.Text
import com.composables.ui.components.Tooltip
import com.composables.ui.components.TooltipPanel

@Composable
fun TooltipLongPressDurationExample() {
    Tooltip(
        longPressShowDurationMillis = 3_000L,
        panel = {
            TooltipPanel {
                Text("Tooltip")
            }
        },
    ) {
        Button(onClick = {}, style = ButtonStyle.Outlined) {
            Text("Long press me")
        }
    }
}
