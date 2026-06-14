package com.composables.ui.sample.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.ui.components.Icon
import com.composables.ui.sample.iconography.Aperture
import com.composables.ui.sample.iconography.Icons

@Composable
fun AppIcon() {
    Icon(Icons.Aperture, modifier = Modifier.size(32.dp))
}
