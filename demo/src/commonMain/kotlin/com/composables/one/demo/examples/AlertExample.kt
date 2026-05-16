package com.composables.one.demo.examples

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.CircleAlert
import com.composables.icons.lucide.Lucide
import com.composables.one.Alert
import com.composables.one.Icon
import com.composables.one.OutlinedButton
import com.composables.one.PrimaryButton
import com.composables.one.Text

@Composable
fun AlertExample() {
    var showAlert by remember { mutableStateOf(false) }
    Alert(
        visible = showAlert,
        icon = {
            Icon(Lucide.CircleAlert, contentDescription = null, modifier = Modifier.size(48.dp))
        },
        title = {
            Text("Are you sure you want to refund this payment?")
        },
        body = {
            Text("The refund will be reflected in the customer’s bank account 2 to 3 business days after processing.")
        },
        positiveButton = {
            PrimaryButton(onClick = { showAlert = false }) {
                Text("Refund")
            }
        },
        negativeButton = {
            OutlinedButton(onClick = { showAlert = false }) {
                Text("Cancel")
            }
        },
        neutralButtons = {
            OutlinedButton(onClick = { showAlert = false }) {
                Text("Help")
            }
        }
    )

    OutlinedButton(onClick = { showAlert = !showAlert }) {
        Text("Refund Customer")
    }
}
