package com.composables.ui.demo.examples

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.composables.ui.components.LabeledSwitch
import com.composables.ui.components.Text

@Composable
fun ToggleSwitchExample() {
    var checked by remember { mutableStateOf(true) }
    LabeledSwitch(checked = checked, onCheckedChange = { checked = it }) {
        Text("Notifications")
    }
}
