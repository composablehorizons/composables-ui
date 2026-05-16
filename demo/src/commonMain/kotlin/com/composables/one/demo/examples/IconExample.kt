package com.composables.one.demo.examples

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.House
import com.composables.icons.lucide.Lucide
import com.composables.one.Icon

@Composable
fun IconExample() {
    Icon(Lucide.House, contentDescription = "A house", Modifier.size(48.dp))
}
