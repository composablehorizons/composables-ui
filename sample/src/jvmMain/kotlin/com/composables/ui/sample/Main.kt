package com.composables.ui.sample

import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.singleWindowApplication
import java.awt.Dimension

fun main() = singleWindowApplication(title = "Sample App") {
    val density = LocalDensity.current
    val minimumWidth = with(density) { 340.dp.roundToPx() }

    DisposableEffect(window, minimumWidth) {
        val previousMinimumSize = window.minimumSize
        window.minimumSize = Dimension(minimumWidth, previousMinimumSize.height)

        onDispose {
            window.minimumSize = previousMinimumSize
        }
    }

    SocialApp()
}
