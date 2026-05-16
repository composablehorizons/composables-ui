package com.composables.one.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import com.composables.one.Sample
import com.composeunstyled.LocalContentColor
import com.composeunstyled.UnstyledIcon

@Composable
fun Icon(
    painter: Painter,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current,
) {
    UnstyledIcon(painter, contentDescription, modifier, tint = tint)
}

@Composable
fun Icon(
    imageBitmap: ImageBitmap,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current,
) {
    UnstyledIcon(imageBitmap, contentDescription, modifier, tint)
}

@Sample("IconExample")
@Composable
fun Icon(
    imageVector: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current,
) {
    UnstyledIcon(imageVector, contentDescription, modifier, tint)
}
