package com.composables.ui.demo.examples

import androidx.compose.runtime.Composable
import com.composables.ui.components.Switch
import com.composables.ui.components.Text

@Composable
fun DisabledSwitchExample() {
    Switch(checked = true, onCheckedChange = {}, enabled = false) {
        Text("Notifications")
    }
}
