package com.composables.ui.sample.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.ui.components.ButtonSize
import com.composables.ui.components.ButtonStyle
import com.composables.ui.components.IconButton
import com.composables.ui.theme.colors
import com.composables.ui.theme.onSecondary
import com.composables.ui.theme.secondary
import com.composables.uripainter.rememberUriPainter
import com.composeunstyled.LocalTextStyle
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.ProvideTextStyle
import com.composeunstyled.theme.Theme
import kotlin.jvm.JvmInline

@Composable
fun Avatar(
    url: String,
    size: AvatarSize = AvatarSize.Default,
    fallback: (@Composable () -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    ProvideContentColor(Theme[colors][onSecondary]) {
        val avatarSizeDp = when (size) {
            AvatarSize.Large -> 40.dp
            AvatarSize.Medium -> 32.dp
            AvatarSize.Small -> 24.dp
            else -> 24.dp
        }
        Box(
            modifier = modifier.size(
                avatarSizeDp
            )
                .background(Theme[colors][secondary], CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (fallback != null) {
                Box(
                    modifier = Modifier.matchParentSize().clip(CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    ProvideTextStyle(
                        LocalTextStyle.current.copy(fontSize = 12.sp)
                    ) {
                        fallback()
                    }
                }
            }
            Image(
                painter = rememberUriPainter(url),
                contentDescription = null,
                modifier = modifier.matchParentSize().clip(CircleShape),
                contentScale = ContentScale.Crop,
            )
        }
    }
}


@JvmInline
value class AvatarSize(val value: Int) {
    companion object {
        val Small = AvatarSize(0)
        val Medium = AvatarSize(1)
        val Large = AvatarSize(2)

        val Default = Medium
    }
}

