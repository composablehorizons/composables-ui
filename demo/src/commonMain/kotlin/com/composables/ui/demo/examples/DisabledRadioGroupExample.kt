package com.composables.ui.demo.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.composables.ui.components.Radio
import com.composables.ui.components.RadioGroup
import com.composables.ui.components.Text

@Composable
fun DisabledRadioGroupExample() {
    val selected = "Daily"
    RadioGroup(value = selected, onValueChange = {}) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Radio("Daily") { Text("Daily") }
            Radio("Weekly", enabled = false) { Text("Weekly") }
            Radio("Monthly", enabled = false) { Text("Monthly") }
        }
    }
}
