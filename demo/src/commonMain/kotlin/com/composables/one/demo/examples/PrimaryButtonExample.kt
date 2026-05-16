package com.composables.one.demo.examples

import androidx.compose.runtime.Composable
import com.composables.one.components.PrimaryButton
import com.composables.one.components.Text

@Composable
fun PrimaryButtonExample() {
    PrimaryButton(onClick = { /* TODO */ }) {
        Text("Save changes")
    }
}
