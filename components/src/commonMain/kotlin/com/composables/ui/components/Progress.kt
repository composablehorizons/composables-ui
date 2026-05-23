package com.composables.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composables.ui.theme.colors
import com.composables.ui.theme.control
import com.composables.ui.theme.primary
import com.composeunstyled.Indicator
import com.composeunstyled.UnstyledProgress
import com.composeunstyled.buildModifier
import com.composeunstyled.theme.Theme

@Composable
fun ProgressIndicator(
    progress: Float,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(999.dp),
    trackColor: Color = Theme[colors][control],
    indicatorColor: Color = Theme[colors][primary],
    borderColor: Color = Color.Unspecified,
    height: Dp = 6.dp,
) {
    UnstyledProgress(
        progress = progress.coerceIn(0f, 1f),
        modifier = modifier.progressIndicatorContainer(
            shape = shape,
            trackColor = trackColor,
            borderColor = borderColor,
            height = height,
        ),
    ) {
        Indicator(
            modifier = Modifier
                .clip(shape)
                .background(indicatorColor, shape),
        )
    }
}

@Composable
fun IndeterminateProgressIndicator(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(999.dp),
    trackColor: Color = Theme[colors][control],
    indicatorColor: Color = Theme[colors][primary],
    borderColor: Color = Color.Unspecified,
    height: Dp = 6.dp,
) {
    val transition = rememberInfiniteTransition()
    val offset by transition.animateFloat(
        initialValue = -0.35f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1100, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
    )
    UnstyledProgress(
        modifier = modifier.progressIndicatorContainer(
            shape = shape,
            trackColor = trackColor,
            borderColor = borderColor,
            height = height,
        ),
    ) {
        Box(Modifier.fillMaxWidth().fillMaxHeight()) {
            Box(
                Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.35f)
                    .offset(x = (offset * 240).dp)
                    .clip(shape)
                    .background(indicatorColor, shape),
            )
        }
    }
}

private fun Modifier.progressIndicatorContainer(
    shape: Shape,
    trackColor: Color,
    borderColor: Color,
    height: Dp,
): Modifier {
    return this
        .height(height)
        .clip(shape)
        .background(trackColor, shape)
        .then(buildModifier {
            if (borderColor.isSpecified && borderColor != Color.Transparent) {
                add(Modifier.border(1.dp, borderColor, shape))
            }
        })
}
