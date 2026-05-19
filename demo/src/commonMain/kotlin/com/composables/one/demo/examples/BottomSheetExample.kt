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
import androidx.compose.ui.unit.dp
import com.composables.one.BottomSheet
import com.composables.one.Button
import com.composables.one.ButtonStyle
import com.composables.one.Text

@Composable
fun BottomSheetExample() {
    var visible by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Button(onClick = { visible = true }) {
            Text("Show sheet")
        }

        BottomSheet(
            visible = visible,
            onDismissRequest = { visible = false },
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(
                    text = "Before you continue",
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = "Review the details before completing this action.",
                    textAlign = TextAlign.Center,
                )
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Button(
                        onClick = { visible = false },
                        modifier = Modifier.fillMaxWidth(),
                        style = ButtonStyle.Secondary,
                    ) {
                        Text("Cancel")
                    }
                    Button(
                        onClick = { visible = false },
                        modifier = Modifier.fillMaxWidth(),
                        style = ButtonStyle.Primary,
                    ) {
                        Text("Continue")
                    }
                }
            }
        }
    }
}
