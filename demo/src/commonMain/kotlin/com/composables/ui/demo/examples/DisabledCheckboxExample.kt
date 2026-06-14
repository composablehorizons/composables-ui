package com.composables.ui.demo.examples

import androidx.compose.runtime.Composable
import com.composables.ui.components.Checkbox
import com.composables.ui.components.Text

@Composable
fun DisabledCheckboxExample() {
    Checkbox(checked = false, onCheckedChange = {}, enabled = false) {
        Text("Receive updates")
    }
}
