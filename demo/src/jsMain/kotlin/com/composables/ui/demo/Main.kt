package com.composables.ui.demo

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import org.jetbrains.skiko.wasm.onWasmReady

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    onWasmReady {
        val composeTarget = document.getElementById("ComposeTarget") ?: error("No ComposeTarget")
        ComposeViewport(composeTarget) {
            Demo()
        }
    }
}
