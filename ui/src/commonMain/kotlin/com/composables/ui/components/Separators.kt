package com.composables.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composables.ui.theme.borderColor
import com.composables.ui.theme.colors
import com.composeunstyled.UnstyledHorizontalSeparator
import com.composeunstyled.UnstyledVerticalSeparator
import com.composeunstyled.theme.Theme

/**
 * A horizontal divider line.
 * @param modifier Modifier applied to the separator.
 * @param color Color used for the separator line.
 * @param thickness Thickness of the separator line.
 */
@Composable
fun HorizontalSeparator(
    modifier: Modifier = Modifier,
    color: Color = Theme[colors][borderColor],
    thickness: Dp = 1.dp,
) {
    UnstyledHorizontalSeparator(
        color = color,
        modifier = modifier.clip(RoundedCornerShape(100)),
        thickness = thickness,
    )
}

/**
 * A vertical divider line.
 * @param modifier Modifier applied to the separator.
 * @param color Color used for the separator line.
 * @param thickness Thickness of the separator line.
 */
@Composable
fun VerticalSeparator(
    modifier: Modifier = Modifier,
    color: Color = Theme[colors][borderColor],
    thickness: Dp = 1.dp,
) {
    UnstyledVerticalSeparator(
        color = color,
        modifier = modifier.clip(RoundedCornerShape(100)),
        thickness = thickness,
    )
}
