package com.composables.one.demo.examples

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.one.DropdownMenu
import com.composables.one.DropdownMenuItem
import com.composables.one.OutlinedButton
import com.composables.one.Text

@Composable
fun DropdownMenuExample() {
    var expanded by remember { mutableStateOf(false) }

    val options = listOf("Refresh", "Settings", "Help", "Delete")

    DropdownMenu(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        panelModifier = Modifier.width(140.dp),
        panelContent = {
            options.forEach { option ->
                DropdownMenuItem(onClick = { expanded = false }, modifier = Modifier.fillMaxWidth()) {
                    Text(option)
                }
            }
        },
    ) {
        OutlinedButton(onClick = { expanded = true }) {
            Text("Show Options")
        }
    }
}
