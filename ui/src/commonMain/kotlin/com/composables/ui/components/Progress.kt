package com.composables.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import com.composables.ui.theme.controlColor
import com.composables.ui.theme.primaryColor
import com.composeunstyled.Indicator
import com.composeunstyled.UnstyledProgress
import com.composeunstyled.buildModifier
import com.composeunstyled.theme.Theme

/**
 * A determinate progress indicator for known completion state.
 * @param progress Current progress value clamped between 0 and 1.
 * @param modifier Modifier applied to the indicator.
 * @param shape Shape used for the track and indicator.
 * @param trackColor Color used for the indicator track.
 * @param indicatorColor Color used for the active progress indicator.
 * @param borderColor Optional border color drawn around the track.
 * @param height Height of the progress indicator.
 */
@Composable
fun ProgressIndicator(
    progress: Float,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(999.dp),
    trackColor: Color = Theme[colors][controlColor],
    indicatorColor: Color = Theme[colors][primaryColor],
    borderColor: Color = Color.Unspecified,
    height: Dp = 6.dp,
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 250),
        label = "ProgressIndicatorProgress",
    )

    UnstyledProgress(
        progress = animatedProgress,
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

/**
 * A looping progress indicator for ongoing work without a known end point.
 * @param modifier Modifier applied to the indicator.
 * @param shape Shape used for the track and indicator.
 * @param trackColor Color used for the indicator track.
 * @param indicatorColor Color used for the active progress indicator.
 * @param borderColor Optional border color drawn around the track.
 * @param height Height of the progress indicator.
 */
@Composable
fun IndeterminateProgressIndicator(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(999.dp),
    trackColor: Color = Theme[colors][controlColor],
    indicatorColor: Color = Theme[colors][primaryColor],
    borderColor: Color = Color.Unspecified,
    height: Dp = 6.dp,
) {
    val transition = rememberInfiniteTransition()
    val offset by transition.animateFloat(
        initialValue = 0f,
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
        BoxWithConstraints(Modifier.fillMaxWidth().fillMaxHeight()) {
            val indicatorWidth = maxWidth * 0.35f
            val indicatorOffset = (maxWidth + indicatorWidth) * offset - indicatorWidth
            Box(
                Modifier
                    .fillMaxHeight()
                    .width(indicatorWidth)
                    .offset(x = indicatorOffset)
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
