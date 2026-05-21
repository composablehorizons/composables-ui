package com.composables.ui.demo

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import kotlinx.browser.window

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val composeTarget = document.getElementById("ComposeTarget") ?: error("No ComposeTarget")
    val demoId = window.location.search
        .removePrefix("?")
        .split("&")
        .firstNotNullOfOrNull { parameter ->
            val parts = parameter.split("=", limit = 2)
            if (parts.getOrNull(0) == "id") {
                parts.getOrNull(1)?.takeIf { it.isNotBlank() }
            } else {
                null
            }
        }
    ComposeViewport(composeTarget) {
        Demo(initialDemoId = demoId)
    }
}
