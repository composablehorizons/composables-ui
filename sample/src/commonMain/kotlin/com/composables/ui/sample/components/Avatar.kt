package com.composables.ui.sample.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.composables.ui.components.ButtonSize
import com.composables.ui.components.ButtonStyle
import com.composables.ui.components.IconButton
import com.composables.ui.theme.border
import com.composables.ui.theme.colors
import com.composables.uripainter.rememberUriPainter
import com.composeunstyled.theme.Theme

@Composable
internal fun Avatar(
    url: String,
    size: Int,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = rememberUriPainter(url),
        contentDescription = null,
        modifier = modifier
            .size(size.dp)
            .clip(CircleShape)
            .background(Theme[colors][border]),
        contentScale = ContentScale.Crop,
    )
}

@Composable
internal fun AvatarButton(
    url: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
        style = ButtonStyle.Ghost,
        shape = CircleShape,
        buttonSize = ButtonSize.Small,
    ) {
        Avatar(
            url = url,
            size = 44,
            modifier = Modifier.clip(CircleShape),
        )
    }
}
