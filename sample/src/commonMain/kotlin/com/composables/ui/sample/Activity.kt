package com.composables.ui.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.composables.ui.components.Text
import com.composables.ui.theme.colors
import com.composables.ui.theme.onPanel
import com.composables.ui.theme.panel
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.theme.Theme

@Composable
fun Activity() {

    ProvideContentColor(Theme[colors][onPanel]) {
        Box(
            modifier = Modifier.fillMaxSize().background(Theme[colors][panel]),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = "Notifications", fontWeight = FontWeight.SemiBold)
        }
    }
}
