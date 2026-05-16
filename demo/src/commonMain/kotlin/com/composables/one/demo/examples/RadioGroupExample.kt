package com.composables.one.demo.examples

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.composables.one.PrimaryButton
import com.composables.one.RadioButton
import com.composables.one.RadioGroup
import com.composables.one.Text

@Composable
fun RadioGroupExample() {
    class Option(val id: String, val title: String, val subtitle: String)

    val options = listOf(
        Option("plan-1", "Starter", "Perfect for side projects"),
        Option("plan-2", "Pro", "For growing businesses"),
        Option("plan-3", "Enterprise", "Custom solutions for large teams"),
    )
    var selectedOption by remember { mutableStateOf<String?>(null) }

    Column(Modifier.widthIn(max = 600.dp).fillMaxWidth()) {
        RadioGroup(
            value = selectedOption,
            onValueChange = { selectedOption = it },
            contentDescription = "Plans",
            modifier = Modifier.fillMaxWidth(),
            title = { Text("Our Plans") },
        ) {
            options.forEach {
                RadioButton(
                    option = it.id,
                    selected = it.id == selectedOption,
                ) {
                    Column {
                        Text(it.title, fontWeight = FontWeight.Medium)
                        Spacer(Modifier.height(8.dp))
                        Text(it.subtitle)
                    }
                }
            }
        }
        Spacer(Modifier.height(24.dp))
        PrimaryButton(
            onClick = { /* TODO */ },
            enabled = selectedOption != null,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Continue", modifier = Modifier.padding(vertical = 8.dp))
        }
    }
}
