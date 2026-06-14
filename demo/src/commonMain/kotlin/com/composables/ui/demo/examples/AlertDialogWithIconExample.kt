package com.composables.ui.demo.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Bell
import com.composables.icons.lucide.Lucide
import com.composables.ui.components.AlertDialog
import com.composables.ui.components.Button
import com.composables.ui.components.ButtonStyle
import com.composables.ui.components.Icon
import com.composables.ui.components.Text

@Composable
fun AlertDialogWithIconExample() {
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
            icon = {
                Icon(
                    imageVector = Lucide.Bell,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                )
            },
            title = { Text("Enable notifications?", textAlign = TextAlign.Center) },
            text = {
                Text(
                    "Notifications help you keep up with important updates from this app.",
                    textAlign = TextAlign.Center,
                )
            },
            positiveButton = {
                Button(
                    onClick = { visible = false },
                    modifier = Modifier.fillMaxWidth(),
                    style = ButtonStyle.Primary,
                ) {
                    Text("Allow")
                }
            },
            negativeButton = {
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
