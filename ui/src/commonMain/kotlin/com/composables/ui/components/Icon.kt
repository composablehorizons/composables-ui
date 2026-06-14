package com.composables.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.composeunstyled.LocalContentColor
import com.composeunstyled.UnstyledIcon

/**
 * An ImageVector-based icon that tints from the current content color by default.
 * @param imageVector Vector asset drawn by the icon.
 * @param modifier Modifier applied to the icon.
 * @param contentDescription Accessible description announced for the icon.
 * @param tint Tint color applied to the icon.
 */
@Composable
fun Icon(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    tint: Color = LocalContentColor.current,
) {
    UnstyledIcon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = modifier,
        tint = tint,
    )
}
