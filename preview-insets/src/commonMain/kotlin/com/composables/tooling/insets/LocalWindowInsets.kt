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
package com.composables.tooling.insets

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.jvm.JvmInline

@Composable
fun Modifier.previewStatusBarPadding(): Modifier {
  val windowInsets = LocalWindowInsets.current ?: return this.statusBarsPadding()

  return this then
      Modifier.padding(
          top = windowInsets.statusBarSize,
      )
}

@Composable
fun Modifier.previewNavigationBarPadding(): Modifier {
  val windowInsets = LocalWindowInsets.current ?: return this.navigationBarsPadding()
  val navigationBarSize = windowInsets.navigationBarSize
  val softKeyboardSize = windowInsets.softKeyboardSize
  val placeNavigationBarAtEnd = windowInsets.orientation == PreviewInsetsOrientation.Landscape
  val remainingBottomNavigationBarSize =
      if (softKeyboardSize >= navigationBarSize) {
        0.dp
      } else {
        navigationBarSize - softKeyboardSize
      }

  return this then
      Modifier.padding(
          bottom = if (placeNavigationBarAtEnd) 0.dp else remainingBottomNavigationBarSize,
          end = if (placeNavigationBarAtEnd) navigationBarSize else 0.dp,
      )
}

@Composable
fun Modifier.previewSoftKeyboardPadding(): Modifier {
  val windowInsets = LocalWindowInsets.current ?: return this.imePadding()

  return this then
      Modifier.padding(
          bottom = windowInsets.softKeyboardSize,
      )
}

@Composable
fun previewStatusBarPaddingValue(): Dp {
  return LocalWindowInsets.current?.statusBarSize
      ?: WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
}

@Composable
fun previewNavigationBarPaddingValue(): Dp {
  return LocalWindowInsets.current?.navigationBarSize
      ?: WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
}

val LocalWindowInsets = staticCompositionLocalOf<PreviewWindowInsets?> { null }

class PreviewWindowInsets(
    val statusBarSize: Dp = 0.dp,
    val navigationBarSize: Dp = 0.dp,
    val softKeyboardSize: Dp = 0.dp,
    val orientation: PreviewInsetsOrientation = PreviewInsetsOrientation.Portrait,
)

@JvmInline
value class PreviewInsetsOrientation internal constructor(private val value: Int) {
  companion object {
    val Portrait = PreviewInsetsOrientation(0)
    val Landscape = PreviewInsetsOrientation(1)
  }
}

@Composable
fun ProvidePreviewWindowInsets(
    windowInsets: PreviewWindowInsets,
    content: @Composable () -> Unit,
) {
  CompositionLocalProvider(LocalWindowInsets provides windowInsets) { content() }
}
