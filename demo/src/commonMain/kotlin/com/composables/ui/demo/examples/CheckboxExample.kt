package com.composables.ui.demo.examples

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.composables.ui.components.Checkbox
import com.composables.ui.components.Text

@Composable
fun CheckboxExample() {
    var checked by remember { mutableStateOf(true) }
    Checkbox(checked = checked, onCheckedChange = { checked = it }) {
        Text("Receive updates")
    }
}
