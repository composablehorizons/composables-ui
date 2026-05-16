package com.composables.one.demo.examples

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.composables.one.components.LinearProgressIndicator
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.delay

@Composable
fun LinearProgressIndicatorExample() {
    var progress by remember { mutableStateOf(0.25f) }

    LaunchedEffect(Unit) {
        while (progress != 1f) {
            delay(1.seconds)
            progress += 0.1f
        }
    }

    LinearProgressIndicator(
        progress = progress,
        modifier = Modifier.fillMaxWidth()
    )
}
