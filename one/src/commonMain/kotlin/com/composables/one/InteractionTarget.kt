package com.composables.one

import androidx.compose.runtime.compositionLocalOf

enum class InteractionTarget {
    NonTouch,
    Touch,
}

val LocalInteractionTarget = compositionLocalOf { InteractionTarget.NonTouch }
