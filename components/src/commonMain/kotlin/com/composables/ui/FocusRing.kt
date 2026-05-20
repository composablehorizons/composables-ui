package com.composables.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composeunstyled.buildModifier
import com.composeunstyled.collectIsFocusVisibleAsState
import com.composeunstyled.outline

@Composable
internal fun Modifier.focusRing(
    interactionSource: InteractionSource,
    width: Dp,
    color: Color,
    shape: Shape,
    offset: Dp,
): Modifier {
    val showFocusRing by interactionSource.collectIsFocusVisibleAsState()
    val animatedWidth by animateDpAsState(
        targetValue = if (showFocusRing) width else 0.dp,
        animationSpec = tween(durationMillis = 120),
        label = "FocusRingWidth",
    )

    return this.then(buildModifier {
        if (animatedWidth > 0.dp) {
            add(Modifier.outline(width = animatedWidth, color = color, shape = shape, offset = offset))
        }
    })
}
