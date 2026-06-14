package com.composables.ui.demo.examples

import androidx.compose.runtime.Composable
import androidx.compose.ui.state.ToggleableState
import com.composables.ui.components.Text
import com.composables.ui.components.TriStateCheckbox

@Composable
fun DisabledTriStateCheckboxExample() {
    TriStateCheckbox(
        state = ToggleableState.Off,
        onStateChange = {},
        enabled = false,
    ) {
        Text("Select all")
    }
}
