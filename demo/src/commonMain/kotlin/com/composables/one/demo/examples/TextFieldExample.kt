package com.composables.one.demo.examples

import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.one.TextField
import com.composables.one.Text

@Composable
fun TextFieldExample() {
    var text by remember { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Email") },
        modifier = Modifier.width(300.dp),
        placeholder = {
            Text("jones@examples.com")
        },
        error = true,
        singleLine = true,
        supportive = {
            Text("Email is taken")
        }
    )
}