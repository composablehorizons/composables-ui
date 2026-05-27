package com.composables.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composables.ui.theme.InteractionMode
import com.composables.ui.theme.currentInteractionMode

@Composable
internal fun defaultControlHeight(): Dp {
    return if (currentInteractionMode() == InteractionMode.Touch) 48.dp else 36.dp
}

@Composable
internal fun defaultButtonHorizontalPadding(): Dp {
    return if (currentInteractionMode() == InteractionMode.Touch) 20.dp else 16.dp
}

@Composable
internal fun defaultIconButtonSize(): Dp {
    return if (currentInteractionMode() == InteractionMode.Touch) 48.dp else 36.dp
}
