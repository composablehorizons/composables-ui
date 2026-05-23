package com.composables.ui.demo.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TextFieldsExample() {
    Column(
        modifier = Modifier
            .widthIn(min = 320.dp, max = 360.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            DefaultTextFieldExample()
            SearchTextFieldExample()
            MultilineTextFieldExample()
            DisabledTextFieldExample()
            ReadOnlyTextFieldExample()
        }
    }
}
