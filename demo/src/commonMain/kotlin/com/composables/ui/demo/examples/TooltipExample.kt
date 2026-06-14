package com.composables.ui.demo.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.composables.ui.components.Button
import com.composables.ui.components.ButtonStyle
import com.composables.ui.components.Text
import com.composables.ui.components.Tooltip
import com.composables.ui.components.TooltipAlignment
import com.composables.ui.components.TooltipPanel
import com.composables.ui.components.TooltipSide

@Composable
fun TooltipExample() {
    Tooltip(
        panel = {
            TooltipPanel {
                Text("Tooltip")
            }
        },
    ) {
        Button(onClick = {}, style = ButtonStyle.Outlined) {
            Text("Hover me")
        }
    }
}
