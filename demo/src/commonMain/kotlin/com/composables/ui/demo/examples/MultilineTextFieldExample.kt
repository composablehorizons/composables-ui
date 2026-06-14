package com.composables.ui.demo.examples

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
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

    TextField(
        state = state,
        modifier = Modifier.fillMaxWidth(),
        accessibilityLabel = "Feedback",
        placeholder = { Text("Tell us what could be better...") },
        contentPadding = PaddingValues(12.dp),
        lineLimits = TextFieldLineLimits.MultiLine(
            minHeightInLines = 4,
            maxHeightInLines = 4,
        ),
    )
}
