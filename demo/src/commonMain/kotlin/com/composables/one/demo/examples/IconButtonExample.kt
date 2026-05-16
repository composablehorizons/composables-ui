package com.composables.one.demo.examples

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Pencil
import com.composables.one.Icon
import com.composables.one.IconButton

@Composable
fun IconButtonExample() {
    IconButton(onClick = { /* TODO */ }) {
        Icon(Lucide.Pencil, contentDescription = "Compose", modifier = Modifier.size(16.dp))
    }
}
