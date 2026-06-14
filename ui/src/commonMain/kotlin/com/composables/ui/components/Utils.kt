package com.composables.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composables.ui.theme.colors
import com.composables.ui.theme.ringColor
import com.composeunstyled.FocusRingVisibility
import com.composeunstyled.collectIsFocusVisibleAsState
import com.composeunstyled.outline
import com.composeunstyled.theme.Theme

@Composable
fun Modifier.focusRing(
    interactionSource: InteractionSource,
    width: Dp = 2.dp,
    color: Color = Theme[colors][ringColor],
    shape: Shape = RectangleShape,
    offset: Dp = 0.dp,
    visibility: FocusRingVisibility = FocusRingVisibility.FocusVisible,
): Modifier {
    val showFocusRing by if (visibility == FocusRingVisibility.FocusVisible) {
        interactionSource.collectIsFocusVisibleAsState()
    } else {
        interactionSource.collectIsFocusedAsState()
    }
    val animatedWidth by animateDpAsState(
        targetValue = if (showFocusRing) width else 0.dp,
        animationSpec = tween(durationMillis = 120),
        label = "FocusRingWidth",
    )

    return this then Modifier.outline(
        width = animatedWidth,
        color = color,
        shape = shape,
        offset = offset,
    )
}

@Composable
fun Modifier.bouncyPress(
    interactionSource: InteractionSource,
    enabled: Boolean = true,
    pressedScale: Float = 0.98f,
): Modifier {
    val pressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (enabled && pressed) pressedScale else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow,
        ),
        label = "BouncyPressScale",
    )

    return this then Modifier.graphicsLayer {
        scaleX = scale
        scaleY = scale
    }
}
