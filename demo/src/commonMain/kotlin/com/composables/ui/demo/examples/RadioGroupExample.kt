package com.composables.ui.demo.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.composables.ui.components.Radio
import com.composables.ui.components.RadioGroup
import com.composables.ui.components.Text

@Composable
fun RadioGroupExample() {
    var selected by remember { mutableStateOf("Weekly") }
    RadioGroup(value = selected, onValueChange = { selected = it }) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Radio("Daily") { Text("Daily") }
            Radio("Weekly") { Text("Weekly") }
            Radio("Monthly") { Text("Monthly") }
        }
    }
}
