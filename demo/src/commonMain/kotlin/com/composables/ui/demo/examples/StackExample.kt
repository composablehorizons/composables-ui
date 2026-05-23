package com.composables.ui.demo.examples

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.composables.ui.components.Stack
import com.composables.ui.components.StackCrossAxisAlignment
import com.composables.ui.components.StackOrientation
import com.composables.ui.components.Text
import com.composables.ui.theme.colors
import com.composables.ui.theme.control
import com.composeunstyled.theme.Theme

@Composable
fun StackExample() {
    Stack(
        orientation = StackOrientation.Horizontal,
        crossAxisAlignment = StackCrossAxisAlignment.Center,
        spacing = 8.dp,
    ) {
        repeat(3) { index ->
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Theme[colors][control]),
                contentAlignment = Alignment.Center,
            ) {
                Text("${index + 1}")
            }
        }
    }
}
