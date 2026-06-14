package com.composables.ui.demo.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.composables.ui.components.Button
import com.composables.ui.components.ButtonStyle
import com.composables.ui.components.Text
import com.composables.ui.components.Tooltip
import com.composables.ui.components.TooltipPanel
import com.composables.ui.components.TooltipSide

@Composable
fun TooltipSideExample() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        TooltipButton(
            side = TooltipSide.Start,
            label = "Start",
            tooltipText = "Placed on the leading side",
        )
        TooltipButton(
            side = TooltipSide.Top,
            label = "Top",
            tooltipText = "Placed above the anchor",
        )
        TooltipButton(
            side = TooltipSide.Bottom,
            label = "Bottom",
            tooltipText = "Placed below the anchor",
        )
        TooltipButton(
            side = TooltipSide.End,
            label = "End",
            tooltipText = "Placed on the trailing side",
        )
    }
}

@Composable
private fun TooltipButton(
    label: String,
    tooltipText: String,
    side: TooltipSide,
) {
    Tooltip(
        side = side,
        panel = { TooltipPanel { Text(text = tooltipText) } },
    ) {
        Button(onClick = {}, style = ButtonStyle.Outlined) {
            Text(text = label)
        }
    }
}
