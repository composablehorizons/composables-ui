package com.composables.one.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.composables.one.Sample
import com.composables.one.styling.colors
import com.composables.one.styling.medium
import com.composables.one.styling.onSecondary
import com.composables.one.styling.outline
import com.composables.one.styling.secondary
import com.composables.one.styling.shapes
import com.composeunstyled.Text as UnstyledText
import com.composeunstyled.outline
import com.composeunstyled.theme.Theme

@Sample("AvatarExample")
@Composable
fun Avatar(
    painter: Painter,
    initials: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    shape: Shape = Theme[shapes][medium],
    backgroundColor: Color = Theme[colors][secondary],
    contentColor: Color = Theme[colors][onSecondary],
    outlineColor: Color = Theme[colors][outline],
) {
    val text = remember(initials) {
        if (initials.isEmpty()) " " else initials.split(" ").map { it.first() }.joinToString("")
    }
    Box(
        modifier
            .size(48.dp)
            .aspectRatio(1f)
            .outline(1.dp, outlineColor, shape)
            .clip(shape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        UnstyledText(text, color = contentColor)
        Image(
            painter = painter,
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = ContentScale.Crop,
        )
    }
}
