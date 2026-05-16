package com.composables.one.demo.examples

import androidx.compose.runtime.Composable
import com.composables.one.components.DestructiveButton
import com.composables.one.components.Text

@Composable
fun DestructiveButtonExample() {
    DestructiveButton(onClick = { /* TODO */ }) {
        Text("Delete")
    }
}
