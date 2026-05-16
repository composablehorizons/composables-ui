package com.composables.one.demo.examples

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.composables.one.components.Picker

@Composable
fun PickerExample() {
    val values = listOf(
        "Cupcake",
        "Donut",
        "Eclair",
        "Froyo",
        "Gingerbread",
        "Honeycomb",
        "Ice Cream Sandwich",
    )

    var value by remember { mutableStateOf(values.first()) }

    Picker(
        value = value,
        onValueChange = { value = it },
        values = values,
    )
}
