package com.composables.ui.demo.examples

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.composables.ui.components.CheckboxState
import com.composables.ui.components.Text
import com.composables.ui.components.TriStateCheckbox

@Composable
fun TriStateCheckboxExample() {
    var state by remember { mutableStateOf(CheckboxState.Indeterminate) }
    TriStateCheckbox(state = state, onStateChange = { state = it }) {
        Text("Select all")
    }
}
