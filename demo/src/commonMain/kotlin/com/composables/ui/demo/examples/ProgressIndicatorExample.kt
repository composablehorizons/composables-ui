package com.composables.ui.demo.examples

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.composables.ui.components.ProgressIndicator
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun ProgressIndicatorExample() {
    var progress by remember { mutableFloatStateOf(0.28f) }

    LaunchedEffect(Unit) {
        delay(500.milliseconds)
        progress = 0.64f
    }

    ProgressIndicator(progress = progress, modifier = Modifier.fillMaxWidth())
}
