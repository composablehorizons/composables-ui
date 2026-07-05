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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.composables.ui.theme.colors
import com.composables.ui.theme.mediumShape
import com.composables.ui.theme.selectedControlColor
import com.composables.ui.theme.shapes
import com.composeunstyled.LocalTextStyle
import com.composeunstyled.ProvideTextStyle
import com.composeunstyled.buildModifier
import com.composeunstyled.theme.Theme

class SidebarScope internal constructor(
  val expanded: Boolean,
)

@Composable
fun Sidebar(
  expanded: Boolean,
  modifier: Modifier = Modifier,
  header: @Composable SidebarScope.() -> Unit = {},
  footer: @Composable SidebarScope.() -> Unit = {},
  content: @Composable SidebarScope.() -> Unit,
) {
  val sidebarScope = SidebarScope(expanded)
  val horizontalPadding = if (expanded) 20.dp else 0.dp

  Column(
    modifier = modifier
      .fillMaxHeight()
      .then(
        buildModifier {
          if (expanded) add(Modifier.width(SidebarExpandedWidth)) else add(Modifier.width(SidebarCompactWidth))
        },
      )
      .pointerInput(Unit) {}
      .padding(
        start = horizontalPadding,
        top = 40.dp,
        end = horizontalPadding,
        bottom = 24.dp,
      ),
  ) {
    sidebarScope.header()
    Column(
      modifier = Modifier
        .weight(1f)
        .fillMaxWidth()
        .padding(vertical = 24.dp),
      verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
      sidebarScope.content()
    }
    sidebarScope.footer()
  }
}

@Composable
fun SidebarScope.SidebarItem(
  selected: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  shape: Shape = Theme[shapes][mediumShape],
  contentPadding: PaddingValues = PaddingValues(horizontal = if (expanded) 10.dp else 0.dp),
  icon: @Composable () -> Unit,
  text: @Composable () -> Unit,
) {
  if (!expanded) {
    Box(
      modifier = modifier.fillMaxWidth(),
      contentAlignment = Alignment.Center,
    ) {
      IconButton(
        onClick = onClick,
        modifier = Modifier.then(
          buildModifier {
            if (selected) add(Modifier.background(Theme[colors][selectedControlColor], shape))
          },
        ),
        enabled = enabled,
        style = ButtonStyle.Ghost,
        shape = shape,
      ) {
        Box(Modifier.size(NavigationSidebarItemIconSize)) {
          icon()
        }
      }
    }
    return
  }

  Button(
    onClick = onClick,
    modifier = modifier
      .fillMaxWidth()
      .then(
        buildModifier {
          if (selected) add(Modifier.background(Theme[colors][selectedControlColor], shape))
        },
      ),
    enabled = enabled,
    style = ButtonStyle.Ghost,
    shape = shape,
    contentPadding = contentPadding,
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(12.dp),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Box(Modifier.size(NavigationSidebarItemIconSize)) {
        icon()
      }
      ProvideTextStyle(LocalTextStyle.current.merge(SidebarItemTextStyle)) {
        text()
      }
    }
  }
}

private val SidebarItemTextStyle = TextStyle(
  fontWeight = FontWeight.Medium,
)

private val NavigationSidebarItemIconSize = 18.dp
private val SidebarExpandedWidth = 230.dp
private val SidebarCompactWidth = 78.dp
