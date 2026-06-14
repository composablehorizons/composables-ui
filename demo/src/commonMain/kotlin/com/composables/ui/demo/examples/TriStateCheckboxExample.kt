package com.composables.ui.demo.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp
import com.composables.ui.components.Checkbox
import com.composables.ui.components.Text
import com.composables.ui.components.TriStateCheckbox

@Composable
fun TriStateCheckboxExample() {
    var marketing by remember { mutableStateOf(true) }
    var product by remember { mutableStateOf(false) }
    var security by remember { mutableStateOf(true) }

    val allSelected = marketing && product && security
    val noneSelected = !marketing && !product && !security
    val state = when {
        allSelected -> ToggleableState.On
        noneSelected -> ToggleableState.Off
        else -> ToggleableState.Indeterminate
    }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        TriStateCheckbox(
            state = state,
            onStateChange = { nextState ->
                val selected = nextState == ToggleableState.On
                marketing = selected
                product = selected
                security = selected
            },
        ) {
            Text("Select all")
        }

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Checkbox(checked = marketing, onCheckedChange = { marketing = it }) {
                Text("Marketing updates")
            }
            Checkbox(checked = product, onCheckedChange = { product = it }) {
                Text("Product announcements")
            }
            Checkbox(checked = security, onCheckedChange = { security = it }) {
                Text("Security alerts")
            }
        }
    }
}
