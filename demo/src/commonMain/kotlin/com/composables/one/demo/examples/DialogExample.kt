package com.composables.one.demo.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.one.components.Dialog
import com.composables.one.components.OutlinedButton
import com.composables.one.components.PrimaryButton
import com.composables.one.components.TextField
import com.composables.one.styling.title
import com.composables.one.components.Text
import com.composeunstyled.theme.Theme
import com.composables.one.styling.textStyles

@Composable
fun DialogExample() {
    var visible by remember { mutableStateOf(true) }

    Dialog(visible = visible) {
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        Text("Welcome Back", style = Theme[textStyles][title])
        Spacer(Modifier.height(24.dp))

        TextField(username, onValueChange = { username = it }, label = { Text("Username") }, singleLine = true)
        Spacer(Modifier.height(12.dp))
        TextField(password, onValueChange = { password = it }, label = { Text("Password") }, singleLine = true)
        Spacer(Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
        ) {
            OutlinedButton(onClick = { visible = false }) {
                Text("Cancel")
            }
            PrimaryButton(onClick = { visible = false }) {
                Text("Enter")
            }
        }
    }

    PrimaryButton(onClick = { visible = true }) {
        Text("Show")
    }
}