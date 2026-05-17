package com.composables.one.demo.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import com.composables.one.AlertDialog
import com.composables.one.Button
import com.composables.one.ButtonStyle
import com.composables.one.Text

@Composable
fun AlertDialogExample() {
    var visible by remember { mutableStateOf(false) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Button(onClick = { visible = true }) {
            Text(text = "Show dialog")
        }

        AlertDialog(
            visible = visible,
            onDismissRequest = { visible = false },
            title = { Text("Enable notifications?") },
            text = {
                Text("Notifications help you keep up with important updates from this app.")
            },
            confirmButton = {
                Button(onClick = { visible = false }, style = ButtonStyle.Primary) {
                    Text("Allow")
                }
            },
            dismissButton = {
                Button(onClick = { visible = false }, style = ButtonStyle.Ghost) {
                    Text("Not now")
                }
            },
        )

    }
}

