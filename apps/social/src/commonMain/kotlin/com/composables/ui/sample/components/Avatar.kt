/*
 * Copyright (c) 2026 Composable Horizons
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.composables.ui.sample.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.ui.theme.colors
import com.composables.ui.theme.onSecondaryColor
import com.composables.ui.theme.secondaryColor
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
  ProvideContentColor(Theme[colors][onSecondaryColor]) {
    val avatarSizeDp =
        when (size) {
          AvatarSize.Large -> 40.dp
          AvatarSize.Medium -> 32.dp
          AvatarSize.Small -> 24.dp
          else -> 24.dp
        }
    Box(
        modifier =
            modifier
                .size(
                    avatarSizeDp,
                )
                .background(Theme[colors][secondaryColor], CircleShape),
        contentAlignment = Alignment.Center,
    ) {
      if (fallback != null) {
        Box(
            modifier = Modifier.matchParentSize().clip(CircleShape),
            contentAlignment = Alignment.Center,
        ) {
          ProvideTextStyle(
              LocalTextStyle.current.copy(fontSize = 12.sp),
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
