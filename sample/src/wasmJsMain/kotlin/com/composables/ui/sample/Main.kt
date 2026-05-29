@file:OptIn(ExperimentalComposeUiApi::class)

package com.composables.ui.sample

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport

fun main() {
    ComposeViewport {
        App()
    }
}
