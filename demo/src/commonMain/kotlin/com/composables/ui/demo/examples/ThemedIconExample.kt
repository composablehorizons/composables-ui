package com.composables.ui.demo.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Bell
import com.composables.icons.lucide.Lucide
import com.composables.ui.components.Icon
import com.composables.ui.components.Text
import com.composables.ui.theme.colors
import com.composables.ui.theme.mutedColor
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.theme.Theme

@Composable
fun ThemedIconExample() {
    ProvideContentColor(Theme[colors][mutedColor]) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Icon(
                imageVector = Lucide.Bell,
                modifier = Modifier.size(24.dp),
            )
            Text("3 Notifications")
        }
    }
}
