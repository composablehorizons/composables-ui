package com.composables.one.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.composables.one.Sample
import com.composables.one.styling.colors
import com.composables.one.styling.primary
import com.composables.one.styling.secondary
import com.composables.one.styling.shapes
import com.composables.one.styling.small
import com.composeunstyled.Indicator
import com.composeunstyled.UnstyledProgress
import com.composeunstyled.theme.Theme

@Sample("LinearProgressIndicatorExample")
@Composable
fun LinearProgressIndicator(
    progress: Float,
    modifier: Modifier = Modifier,
    fillColor: Color = Theme[colors][primary],
    trackColor: Color = Theme[colors][secondary],
) {
    val animateFloat by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 750, easing = LinearEasing)
    )

    UnstyledProgress(
        progress = animateFloat,
        modifier = modifier
            .height(4.dp)
            .background(trackColor, Theme[shapes][small])
    ) {
        Indicator(Modifier.background(fillColor, Theme[shapes][small]))
    }
}

@Sample("IntermediateProgressIndicatorExample")
@Composable
fun IntermediateProgressIndicator(
    modifier: Modifier = Modifier,
    fillColor: Color = Theme[colors][primary],
    ringColor: Color = Theme[colors][secondary],
) {
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(animation = tween(durationMillis = 750, easing = LinearEasing))
    )
    val strokeWidth = 4.dp

    Canvas(modifier = modifier.size(32.dp)) {
        drawCircle(
            color = ringColor,
            style = Stroke(width = strokeWidth.toPx())
        )
        drawArc(
            color = fillColor,
            startAngle = angle,
            sweepAngle = 270f,
            useCenter = false,
            style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
        )
    }
}
