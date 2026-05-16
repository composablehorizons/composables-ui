package com.composables.one.demo.examples

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.one.Card
import com.composables.one.OutlinedButton
import com.composables.one.Text

@Composable
fun CardWithTitleExample() {
    Card(
        title = { Text("Explore the Mountains") },
        modifier = Modifier.width(380.dp)
    ) {
        Text("Discover breathtaking trails and scenic views for your next adventure.")
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedButton(onClick = {}) {
            Text("Learn More")
        }
    }
}
