package com.composables.one.demo.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
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
            title = { Text("Enable notifications?", textAlign = TextAlign.Center) },
            text = {
                Text(
                    "Notifications help you keep up with important updates from this app.",
                    textAlign = TextAlign.Center,
                )
            },
            confirmButton = {
                Button(
                    onClick = { visible = false },
                    modifier = Modifier.fillMaxWidth(),
                    style = ButtonStyle.Primary,
                ) {
                    Text("Allow")
                }
            },
            dismissButton = {
                Button(
                    onClick = { visible = false },
                    modifier = Modifier.fillMaxWidth(),
                    style = ButtonStyle.Secondary,
                ) {
                    Text("Not now")
                }
            },
        )

    }
}
