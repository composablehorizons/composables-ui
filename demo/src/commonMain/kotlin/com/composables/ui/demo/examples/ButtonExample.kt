package com.composables.ui.demo.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Plus
import com.composables.ui.components.Button
import com.composables.ui.components.Icon
import com.composables.ui.components.IconButton
import com.composables.ui.components.Text

@Composable
fun ButtonExample() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Button(onClick = { /* TODO */ }) {
            Text("Button")
        }
        IconButton(onClick = { /* TODO */ }) {
            Icon(
                imageVector = Lucide.Plus,
                contentDescription = "Add",
                modifier = Modifier.size(18.dp),
            )
        }
    }
}
