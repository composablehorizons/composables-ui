@file:OptIn(ExperimentalComposeUiApi::class)

package com.composables.ui.sample

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.composables.ui.theme.ColorScheme
import com.composables.ui.theme.InteractionMode
import com.composables.ui.theme.LocalColorScheme
import com.composables.ui.theme.LocalInteractionMode
import kotlinx.browser.window

fun main() {
    ComposeViewport {
        val colorScheme = systemColorScheme()
        val interactionMode = interactionModeFromUrl()
        if (interactionMode == null) {
            CompositionLocalProvider(LocalColorScheme provides colorScheme) {
                App()
            }
        } else {
            CompositionLocalProvider(
                LocalColorScheme provides colorScheme,
                LocalInteractionMode provides interactionMode,
            ) {
                App()
            }
        }
    }
}

private fun systemColorScheme(): ColorScheme {
    return if (window.matchMedia("(prefers-color-scheme: dark)").matches) {
        ColorScheme.Dark
    } else {
        ColorScheme.Light
    }
}

private fun interactionModeFromUrl(): InteractionMode? {
    return window.location.search
        .removePrefix("?")
        .split("&")
        .firstNotNullOfOrNull { parameter ->
            val separatorIndex = parameter.indexOf("=")
            if (separatorIndex < 0) return@firstNotNullOfOrNull null

            val key = parameter.take(separatorIndex)
            if (key != "interaction") return@firstNotNullOfOrNull null

            when (parameter.drop(separatorIndex + 1).lowercase()) {
                "touch" -> InteractionMode.Touch
                "pointer" -> InteractionMode.Pointer
                else -> null
            }
        }
}
