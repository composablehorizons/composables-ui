package com.composables.ui.demo.examples

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Heart
import com.composables.icons.lucide.Lucide
import com.composables.ui.components.Icon
import com.composables.ui.theme.colors
import com.composables.ui.theme.destructiveColor
import com.composeunstyled.theme.Theme

@Composable
fun TintedIconExample() {
    Icon(
        imageVector = Lucide.Heart,
        modifier = Modifier.size(24.dp),
        tint = Theme[colors][destructiveColor],
    )
}
