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
package com.composables.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composeunstyled.LocalContentColor
import com.composeunstyled.LocalTextStyle
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.ProvideTextStyle
import com.composeunstyled.buildModifier
import kotlin.jvm.JvmInline

/**
 * Size variants for the standard Toolbar.
 */
@JvmInline
value class ToolbarSize internal constructor(@Suppress("unused") private val value: Int) {
  companion object {
    /**
     * Uses the standard single-row toolbar height.
     */
    val Medium = ToolbarSize(0)

    /**
     * Uses the large title toolbar layout.
     */
    val Large = ToolbarSize(1)
  }
}

/**
 * A top app bar with medium and large title variants.
 * @param title Composable title content displayed by the toolbar.
 * @param modifier Modifier applied to the toolbar.
 * @param backgroundColor Background color used by the toolbar.
 * @param contentColor Color used for toolbar content.
 * @param windowInsets Insets applied around the toolbar content.
 * @param leading Optional leading content shown before the title.
 * @param trailing Optional trailing content shown after the title.
 * @param size Size variant used by the standard toolbar.
 */
@Composable
fun Toolbar(
  title: @Composable RowScope.() -> Unit,
  modifier: Modifier = Modifier,
  backgroundColor: Color = Color.Transparent,
  contentColor: Color = LocalContentColor.current,
  windowInsets: WindowInsets = toolbarWindowInsets(),
  leading: @Composable (RowScope.() -> Unit)? = null,
  trailing: @Composable (RowScope.() -> Unit)? = null,
  size: ToolbarSize = ToolbarSize.Medium,
) {
  if (size == ToolbarSize.Large) {
    LargeTitleToolbar(
      modifier = modifier,
      backgroundColor = backgroundColor,
      contentColor = contentColor,
      windowInsets = windowInsets,
      title = title,
      size = size,
      leading = leading,
      trailing = trailing,
    )
  } else {
    MediumTitleToolbar(
      modifier = modifier,
      backgroundColor = backgroundColor,
      contentColor = contentColor,
      windowInsets = windowInsets,
      title = title,
      size = size,
      leading = leading,
      trailing = trailing,
    )
  }
}

@Composable
private fun MediumTitleToolbar(
  modifier: Modifier,
  backgroundColor: Color,
  contentColor: Color,
  windowInsets: WindowInsets,
  title: @Composable RowScope.() -> Unit,
  size: ToolbarSize,
  leading: @Composable (RowScope.() -> Unit)?,
  trailing: @Composable (RowScope.() -> Unit)?,
) {
  ToolbarContainer(
    modifier = modifier,
    backgroundColor = backgroundColor,
    contentColor = contentColor,
    windowInsets = windowInsets,
    height = toolbarHeightFor(size),
  ) {
    Row(
      modifier = Modifier.align(Alignment.CenterStart),
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      if (leading != null) {
        leading()
      }
      ProvideTextStyle(LocalTextStyle.current.merge(ToolbarTitleTextStyle)) {
        title()
      }
    }
    if (trailing != null) {
      Row(
        modifier = Modifier.align(Alignment.CenterEnd),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        content = trailing,
      )
    }
  }
}

@Composable
private fun LargeTitleToolbar(
  modifier: Modifier,
  backgroundColor: Color,
  contentColor: Color,
  windowInsets: WindowInsets,
  title: @Composable RowScope.() -> Unit,
  size: ToolbarSize,
  leading: @Composable (RowScope.() -> Unit)?,
  trailing: @Composable (RowScope.() -> Unit)?,
) {
  ToolbarContainer(
    modifier = modifier,
    backgroundColor = backgroundColor,
    contentColor = contentColor,
    windowInsets = windowInsets,
    height = toolbarHeightFor(size),
  ) {
    if (leading != null) {
      Row(
        modifier = Modifier
          .height(64.dp)
          .align(Alignment.TopStart),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        content = leading,
      )
    }
    if (trailing != null) {
      Row(
        modifier = Modifier
          .height(64.dp)
          .align(Alignment.TopEnd),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        content = trailing,
      )
    }
    Row(
      modifier = Modifier
        .align(Alignment.BottomStart)
        .padding(bottom = 12.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      ProvideTextStyle(LocalTextStyle.current.merge(ToolbarHeaderTextStyle)) {
        title()
      }
    }
  }
}

/**
 * A toolbar with centered title content.
 * @param title Composable title content displayed by the toolbar.
 * @param modifier Modifier applied to the toolbar.
 * @param leading Optional leading content shown before the title.
 * @param trailing Optional trailing content shown after the title.
 * @param backgroundColor Background color used by the toolbar.
 * @param contentColor Color used for toolbar content.
 * @param windowInsets Insets applied around the toolbar content.
 */
@Composable
fun CenteredToolbar(
  title: @Composable (RowScope.() -> Unit),
  modifier: Modifier = Modifier,
  leading: @Composable (RowScope.() -> Unit)? = null,
  trailing: @Composable (RowScope.() -> Unit)? = null,
  backgroundColor: Color = Color.Transparent,
  contentColor: Color = LocalContentColor.current,
  windowInsets: WindowInsets = toolbarWindowInsets(),
) {
  ToolbarContainer(
    modifier = modifier,
    backgroundColor = backgroundColor,
    height = 64.dp,
    contentColor = contentColor,
    windowInsets = windowInsets,
  ) {
    if (leading != null) {
      Row(
        modifier = Modifier.align(Alignment.CenterStart),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
      ) {
        ProvideTextStyle(LocalTextStyle.current.merge(ToolbarTitleTextStyle)) {
          leading()
        }
      }
    }
    Row(
      modifier = Modifier.align(Alignment.Center),
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      ProvideTextStyle(LocalTextStyle.current.merge(ToolbarTitleTextStyle)) {
        title()
      }
    }
    if (trailing != null) {
      Row(
        modifier = Modifier.align(Alignment.CenterEnd),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        content = trailing,
      )
    }
  }
}

private fun toolbarHeightFor(size: ToolbarSize): Dp = when (size) {
  ToolbarSize.Large -> 112.dp
  else -> 64.dp
}

private val ToolbarTitleTextStyle = TextStyle(
  fontSize = 20.sp,
  lineHeight = 24.sp,
  fontWeight = FontWeight.Medium,
)

private val ToolbarHeaderTextStyle = TextStyle(
  fontSize = 26.sp,
  lineHeight = 24.sp,
  fontWeight = FontWeight.SemiBold,
)

@Composable
private fun ToolbarContainer(
  backgroundColor: Color,
  height: Dp,
  windowInsets: WindowInsets,
  modifier: Modifier = Modifier,
  contentColor: Color = LocalContentColor.current,
  content: @Composable BoxScope.() -> Unit,
) {
  ProvideContentColor(contentColor) {
    Box(
      modifier = modifier
        .then(
          buildModifier {
            if (backgroundColor != Color.Transparent) {
              add(Modifier.background(backgroundColor))
            }
          },
        )
        .windowInsetsPadding(windowInsets)
        .height(height)
        .padding(ToolbarContentPadding),
      content = content,
    )
  }
}

private val ToolbarContentPadding = PaddingValues(horizontal = 12.dp)

@Composable
private fun toolbarWindowInsets(): WindowInsets {
  return WindowInsets.systemBars.only(
    WindowInsetsSides.Horizontal + WindowInsetsSides.Top,
  )
}
