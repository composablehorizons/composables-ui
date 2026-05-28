package com.composables.ui.sample.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Dumbbell
import com.composables.icons.lucide.Lucide
import com.composables.ui.components.Icon

@Composable
fun AppIcon() {
    Icon(Lucide.Dumbbell, modifier = Modifier.size(24.dp))
}
