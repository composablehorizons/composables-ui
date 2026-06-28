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
package com.composables.ui.demo.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.composables.ui.components.BottomSheet
import com.composables.ui.components.BottomSheetDetent
import com.composables.ui.components.Button
import com.composables.ui.components.ButtonStyle
import com.composables.ui.components.Icon
import com.composables.ui.components.Text
import com.composables.ui.components.TextField
import com.composables.ui.components.rememberBottomSheetState
import com.composables.ui.theme.colors
import com.composables.ui.theme.mutedColor
import com.composeunstyled.theme.Theme

@Composable
fun BottomSheetFormExample() {
  val nameState = rememberTextFieldState("Alex Styl")
  val usernameState = rememberTextFieldState("alexstyl")
  val sheetState = rememberBottomSheetState(
    detents = listOf(BottomSheetDetent.Hidden, BottomSheetDetent.FullyExpanded),
  )

  Button(onClick = { sheetState.targetDetent = BottomSheetDetent.FullyExpanded }) {
    Text("Edit name")
  }

  BottomSheet(
    state = sheetState,
    onDismissRequest = { sheetState.targetDetent = BottomSheetDetent.Hidden },
    toolbar = { Text("Edit name") },
    footer = {
      Button(
        onClick = { sheetState.targetDetent = BottomSheetDetent.Hidden },
        modifier = Modifier.fillMaxWidth(),
        style = ButtonStyle.Primary,
      ) {
        Text("Save")
      }
    },
  ) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
      Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Name")
        TextField(
          state = nameState,
          modifier = Modifier.fillMaxWidth(),
          accessibilityLabel = "Name",
          leading = {
            Icon(
              imageVector = User,
              contentDescription = null,
              modifier = Modifier.size(16.dp),
              tint = Theme[colors][mutedColor],
            )
          },
        )
      }
      Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Username")
        TextField(
          state = usernameState,
          modifier = Modifier.fillMaxWidth(),
          accessibilityLabel = "Username",
          leading = {
            Icon(
              imageVector = AtSign,
              contentDescription = null,
              modifier = Modifier.size(16.dp),
              tint = Theme[colors][mutedColor],
            )
          },
        )
      }
    }
  }
}

val AtSign: ImageVector
  get() {
    if (_AtSign != null) return _AtSign!!

    _AtSign = ImageVector.Builder(
      name = "at-sign",
      defaultWidth = 24.dp,
      defaultHeight = 24.dp,
      viewportWidth = 24f,
      viewportHeight = 24f,
    ).apply {
      path(
        fill = SolidColor(Color.Transparent),
        stroke = SolidColor(Color.Black),
        strokeLineWidth = 2f,
        strokeLineCap = StrokeCap.Round,
        strokeLineJoin = StrokeJoin.Round,
      ) {
        moveTo(16f, 12f)
        arcTo(4f, 4f, 0f, false, true, 12f, 16f)
        arcTo(4f, 4f, 0f, false, true, 8f, 12f)
        arcTo(4f, 4f, 0f, false, true, 16f, 12f)
        close()
      }
      path(
        fill = SolidColor(Color.Transparent),
        stroke = SolidColor(Color.Black),
        strokeLineWidth = 2f,
        strokeLineCap = StrokeCap.Round,
        strokeLineJoin = StrokeJoin.Round,
      ) {
        moveTo(16f, 8f)
        verticalLineToRelative(5f)
        arcToRelative(3f, 3f, 0f, false, false, 6f, 0f)
        verticalLineToRelative(-1f)
        arcToRelative(10f, 10f, 0f, true, false, -4f, 8f)
      }
    }.build()

    return _AtSign!!
  }

private var _AtSign: ImageVector? = null

val User: ImageVector
  get() {
    if (_User != null) return _User!!

    _User = ImageVector.Builder(
      name = "user",
      defaultWidth = 24.dp,
      defaultHeight = 24.dp,
      viewportWidth = 24f,
      viewportHeight = 24f,
    ).apply {
      path(
        fill = SolidColor(Color.Transparent),
        stroke = SolidColor(Color.Black),
        strokeLineWidth = 2f,
        strokeLineCap = StrokeCap.Round,
        strokeLineJoin = StrokeJoin.Round,
      ) {
        moveTo(19f, 21f)
        verticalLineToRelative(-2f)
        arcToRelative(4f, 4f, 0f, false, false, -4f, -4f)
        horizontalLineTo(9f)
        arcToRelative(4f, 4f, 0f, false, false, -4f, 4f)
        verticalLineToRelative(2f)
      }
      path(
        fill = SolidColor(Color.Transparent),
        stroke = SolidColor(Color.Black),
        strokeLineWidth = 2f,
        strokeLineCap = StrokeCap.Round,
        strokeLineJoin = StrokeJoin.Round,
      ) {
        moveTo(16f, 7f)
        arcTo(4f, 4f, 0f, false, true, 12f, 11f)
        arcTo(4f, 4f, 0f, false, true, 8f, 7f)
        arcTo(4f, 4f, 0f, false, true, 16f, 7f)
        close()
      }
    }.build()

    return _User!!
  }

private var _User: ImageVector? = null
