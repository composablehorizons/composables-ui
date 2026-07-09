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
package com.composables.ui.theme

import androidx.compose.ui.unit.dp
import com.composeunstyled.HeightBreakpoint
import com.composeunstyled.WidthBreakpoint
import com.composeunstyled.WindowHeightBreakpoints
import com.composeunstyled.WindowWidthBreakpoints

val Compact = WidthBreakpoint("compact")
val Medium = WidthBreakpoint("medium")
val Expanded = WidthBreakpoint("expanded")
val Large = WidthBreakpoint("large")
val ExtraLarge = WidthBreakpoint("extraLarge")

val Short = HeightBreakpoint("short")
val Tall = HeightBreakpoint("tall")

val WidthBreakpoints = WindowWidthBreakpoints {
  Compact startsAt 0.dp
  Medium startsAt 600.dp
  Expanded startsAt 840.dp
  Large startsAt 1200.dp
  ExtraLarge startsAt 1600.dp
}

val HeightBreakpoints = WindowHeightBreakpoints {
  Short startsAt 0.dp
  Tall startsAt 1080.dp
}
