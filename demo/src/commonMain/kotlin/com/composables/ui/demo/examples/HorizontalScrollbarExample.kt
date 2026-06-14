package com.composables.ui.demo.examples

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.composables.ui.components.HorizontalScrollbar
import com.composables.ui.components.Text
import com.composables.ui.components.rememberVerticalScrollbarState
import com.composables.ui.theme.colors
import com.composables.ui.theme.controlColor
import com.composables.ui.theme.panelColor
import com.composeunstyled.theme.Theme

@Composable
fun HorizontalScrollbarExample() {
    val scrollState = rememberScrollState()
    val scrollbarState = rememberVerticalScrollbarState(scrollState)
    Box(
        modifier = Modifier
            .height(88.dp)
            .widthIn(max = 340.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Theme[colors][controlColor]),
    ) {
        Row(
            modifier = Modifier
                .horizontalScroll(scrollState)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            repeat(10) { index ->
                Box(
                    modifier = Modifier
                        .size(width = 96.dp, height = 40.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Theme[colors][panelColor]),
                    contentAlignment = Alignment.Center,
                ) {
                    Text("Item ${index + 1}")
                }
            }
        }
        HorizontalScrollbar(
            state = scrollbarState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
        )
    }
}
