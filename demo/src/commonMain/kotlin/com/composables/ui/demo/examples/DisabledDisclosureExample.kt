package com.composables.ui.demo.examples

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.composables.ui.components.Disclosure
import com.composables.ui.components.DisclosureButton
import com.composables.ui.components.DisclosurePanel
import com.composables.ui.components.Text

@Composable
fun DisabledDisclosureExample() {
    var expanded by remember { mutableStateOf(false) }
    Disclosure(
        expanded = expanded,
        onExpandedChange = { expanded = it },
    ) {
        DisclosureButton(enabled = false, modifier = Modifier.fillMaxWidth()) {
            Text("Show installation notes")
        }
        DisclosurePanel {
            Text("Wall mounting requires a solid surface, a level, and two anchor points spaced 40 cm apart.")
        }
    }
}
