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
package com.composables.ui.sample

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.composables.ui.ComposablesApp
import com.composables.ui.theme.ColorScheme
import com.composables.ui.theme.LocalColorScheme
import kotlin.jvm.JvmInline

@JvmInline
value class Appearance internal constructor(@Suppress("unused") private val value: Int) {
  companion object {
    val System = Appearance(0)
    val Light = Appearance(1)
    val Dark = Appearance(2)
  }
}

@Composable
fun AppScaffold(
  appearance: Appearance = Appearance.System,
  content: @Composable () -> Unit,
) {
  val colorScheme = when (appearance) {
      Appearance.System -> LocalColorScheme.current
      Appearance.Light -> ColorScheme.Light
      else -> ColorScheme.Dark
    }

  CompositionLocalProvider(LocalColorScheme provides colorScheme) {
    ComposablesApp {
      content()
    }
  }
}
