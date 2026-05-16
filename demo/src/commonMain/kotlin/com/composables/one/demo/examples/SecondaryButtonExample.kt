package com.composables.one.demo.examples

import androidx.compose.runtime.Composable
import com.composables.one.components.PrimaryButton
import com.composables.one.components.SecondaryButton
import com.composables.one.components.Text

@Composable
fun SecondaryButtonExample() {
    SecondaryButton(onClick = { /* TODO */ }) {
        Text("Save changes")
    }
}
