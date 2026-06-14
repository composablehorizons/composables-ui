package com.composables.ui.demo.examples

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.composables.ui.components.Text
import com.composables.ui.components.VerticalScrollbar
import com.composables.ui.components.rememberVerticalScrollbarState
import com.composables.ui.theme.colors
import com.composables.ui.theme.controlColor
import com.composeunstyled.theme.Theme

@Composable
fun LazyColumnScrollbarExample() {
    val lazyListState = rememberLazyListState()
    val scrollbarState = rememberVerticalScrollbarState(lazyListState)
    Box(
        modifier = Modifier
            .height(132.dp)
            .width(340.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Theme[colors][controlColor]),
    ) {
        LazyColumn(
            state = lazyListState,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(200) { index ->
                Text("Build artifact ${index + 1}")
            }
        }
        VerticalScrollbar(
            state = scrollbarState,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight(),
        )
    }
}
