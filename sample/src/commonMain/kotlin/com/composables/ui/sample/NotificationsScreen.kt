package com.composables.ui.sample

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.composables.ui.components.Text
import com.composables.ui.components.Toolbar

@Composable
fun NotificationsScreen() {
    ScreenScaffold(
        toolbar = {
            Toolbar(
                title = {
                    Text("Activity")
                },
            )
        },
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = "Notifications", fontWeight = FontWeight.SemiBold)
        }
    }
}
