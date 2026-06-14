package com.composables.ui.demo.examples

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.composables.ui.components.Text
import com.composables.ui.components.TextField
import com.composables.ui.components.TextFieldStyle

@Composable
fun GhostTextFieldExample() {
    val state = rememberTextFieldState()

    TextField(
        state = state,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text("Type your name...") },
        style = TextFieldStyle.Ghost,
    )
}
