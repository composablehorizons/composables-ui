package com.composables.ui.demo.examples

import androidx.compose.runtime.Composable
import com.composables.ui.components.Button
import com.composables.ui.components.Text
import com.composables.ui.components.Tooltip
import com.composables.ui.components.TooltipPanel

@Composable
fun TooltipExample() {
    Tooltip(
        panel = {
            TooltipPanel {
                Text("Tooltip")
            }
        },
    ) {
        Button(onClick = {}) {
            Text("Hover or focus")
        }
    }
}
