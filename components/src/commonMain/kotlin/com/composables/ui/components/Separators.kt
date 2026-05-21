package com.composables.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composables.ui.theme.border
import com.composables.ui.theme.colors
import com.composeunstyled.UnstyledHorizontalSeparator
import com.composeunstyled.UnstyledVerticalSeparator
import com.composeunstyled.theme.Theme

@Composable
fun HorizontalSeparator(
    modifier: Modifier = Modifier,
    color: Color = Theme[colors][border],
    thickness: Dp = 1.dp,
) {
    UnstyledHorizontalSeparator(
        color = color,
        modifier = modifier,
        thickness = thickness,
    )
}

@Composable
fun VerticalSeparator(
    modifier: Modifier = Modifier,
    color: Color = Theme[colors][border],
    thickness: Dp = 1.dp,
) {
    UnstyledVerticalSeparator(
        color = color,
        modifier = modifier,
        thickness = thickness,
    )
}
