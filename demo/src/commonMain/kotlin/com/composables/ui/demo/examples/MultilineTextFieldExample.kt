package com.composables.ui.demo.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.ui.components.Text
import com.composables.ui.components.TextField

@Composable
fun MultilineTextFieldExample() {
    val state = rememberTextFieldState()

    Column(modifier = Modifier.widthIn(min = 320.dp, max = 360.dp)) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Feedback")
            TextField(
                state = state,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 112.dp),
                accessibilityLabel = "Feedback",
                placeholder = { Text("Tell us what could be better...") },
                contentPadding = PaddingValues(12.dp),
                minHeight = 112.dp,
                lineLimits = TextFieldLineLimits.MultiLine(
                    minHeightInLines = 4,
                    maxHeightInLines = 6,
                ),
            )
        }
    }
}
