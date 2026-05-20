package com.composables.one.demo

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val composeTarget = document.getElementById("ComposeTarget") ?: error("No ComposeTarget")
    ComposeViewport(composeTarget) {
        Demo()
    }
}
