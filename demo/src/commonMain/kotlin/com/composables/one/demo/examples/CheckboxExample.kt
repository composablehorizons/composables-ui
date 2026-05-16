package com.composables.one.demo.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.composables.one.Checkbox

@Composable
fun CheckboxExample() {
    var checkedA by remember { mutableStateOf(true) }
    var checkedB by remember { mutableStateOf(true) }

    Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
        Checkbox(
            checked = checkedA,
            onCheckedChange = { checkedA = it },
        )
        Checkbox(
            checked = checkedB.not(),
            onCheckedChange = { checkedB = it.not() },
        )
    }
}