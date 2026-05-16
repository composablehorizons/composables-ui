package com.composables.one.demo.examples

import androidx.compose.foundation.layout.fillMaxWidth
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
import com.composables.one.components.CenteredAlert
import com.composables.one.components.Icon
import com.composables.one.components.OutlinedButton
import com.composables.one.components.PrimaryButton
import com.composables.one.components.Text

@Composable
fun CenteredAlertExample() {
    var showAlert by remember { mutableStateOf(false) }
    CenteredAlert(
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
            PrimaryButton(onClick = { showAlert = false }, modifier = Modifier.fillMaxWidth()) {
                Text("Refund")
            }
        },
        negativeButton = {
            OutlinedButton(onClick = { showAlert = false }, modifier = Modifier.fillMaxWidth()) {
                Text("Cancel")
            }
        }
    )


    OutlinedButton(onClick = { showAlert = !showAlert }) {
        Text("Refund Customer")
    }
}