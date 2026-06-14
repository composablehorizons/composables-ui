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

@Composable
fun TooltipAlignmentExample() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        TooltipButton(
            label = "Start",
            tooltipText = "Aligned to the start edge",
            alignment = TooltipAlignment.Start,
        )
        TooltipButton(
            label = "Center",
            tooltipText = "Aligned to the center",
            alignment = TooltipAlignment.Center,
        )
        TooltipButton(
            label = "End",
            tooltipText = "Aligned to the end edge",
            alignment = TooltipAlignment.End,
        )
    }
}

@Composable
private fun TooltipButton(
    label: String,
    tooltipText: String,
    alignment: TooltipAlignment,
) {
    Tooltip(
        alignment = alignment,
        panel = { TooltipPanel { Text(text = tooltipText) } },
    ) {
        Button(onClick = {}, style = ButtonStyle.Outlined) {
            Text(text = label)
        }
    }
}
