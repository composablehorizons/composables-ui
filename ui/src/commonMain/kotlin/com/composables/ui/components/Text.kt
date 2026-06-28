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

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import com.composeunstyled.LocalContentColor
import com.composeunstyled.LocalTextStyle
import com.composeunstyled.Text as UnstyledText

/**
 * A styled text primitive that inherits local typography and content color.
 * @param text String content displayed by the text component.
 * @param modifier Modifier applied to the text.
 * @param style Base text style used when drawing the text.
 * @param textAlign Alignment used for the text within its bounds.
 * @param lineHeight Line height used for the text.
 * @param fontSize Font size used for the text.
 * @param letterSpacing Letter spacing used for the text.
 * @param fontWeight Font weight used for the text.
 * @param color Color used to draw the text.
 * @param fontFamily Font family used for the text.
 * @param singleLine Whether the text is forced onto a single line.
 * @param minLines Minimum number of lines reserved for the text.
 * @param maxLines Maximum number of lines allowed for the text.
 * @param overflow How overflowing text should be handled.
 */
@Composable
fun Text(
  text: String,
  modifier: Modifier = Modifier,
  style: TextStyle = LocalTextStyle.current,
  textAlign: TextAlign = TextAlign.Unspecified,
  lineHeight: TextUnit = TextUnit.Unspecified,
  fontSize: TextUnit = style.fontSize,
  letterSpacing: TextUnit = style.letterSpacing,
  fontWeight: FontWeight? = style.fontWeight,
  color: Color = if (style.color.isSpecified) style.color else LocalContentColor.current,
  fontFamily: FontFamily? = style.fontFamily,
  singleLine: Boolean = false,
  minLines: Int = 1,
  maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
  overflow: TextOverflow = TextOverflow.Clip,
) {
  UnstyledText(
    text = text,
    modifier = modifier,
    style = style,
    textAlign = textAlign,
    lineHeight = lineHeight,
    fontSize = fontSize,
    letterSpacing = letterSpacing,
    fontWeight = fontWeight,
    color = color,
    fontFamily = fontFamily,
    singleLine = singleLine,
    minLines = minLines,
    maxLines = maxLines,
    overflow = overflow,
  )
}

/**
 * A styled text primitive that inherits local typography and content color.
 * @param text String content displayed by the text component.
 * @param modifier Modifier applied to the text.
 * @param style Base text style used when drawing the text.
 * @param textAlign Alignment used for the text within its bounds.
 * @param lineHeight Line height used for the text.
 * @param fontSize Font size used for the text.
 * @param letterSpacing Letter spacing used for the text.
 * @param fontWeight Font weight used for the text.
 * @param color Color used to draw the text.
 * @param fontFamily Font family used for the text.
 * @param singleLine Whether the text is forced onto a single line.
 * @param minLines Minimum number of lines reserved for the text.
 * @param maxLines Maximum number of lines allowed for the text.
 * @param overflow How overflowing text should be handled.
 */
@Composable
fun Text(
  text: AnnotatedString,
  modifier: Modifier = Modifier,
  style: TextStyle = LocalTextStyle.current,
  textAlign: TextAlign = TextAlign.Unspecified,
  lineHeight: TextUnit = TextUnit.Unspecified,
  fontSize: TextUnit = style.fontSize,
  letterSpacing: TextUnit = style.letterSpacing,
  fontWeight: FontWeight? = style.fontWeight,
  color: Color = if (style.color.isSpecified) style.color else LocalContentColor.current,
  fontFamily: FontFamily? = style.fontFamily,
  singleLine: Boolean = false,
  minLines: Int = 1,
  maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
  overflow: TextOverflow = TextOverflow.Clip,
) {
  UnstyledText(
    text = text,
    modifier = modifier,
    style = style,
    textAlign = textAlign,
    lineHeight = lineHeight,
    fontSize = fontSize,
    letterSpacing = letterSpacing,
    fontWeight = fontWeight,
    color = color,
    fontFamily = fontFamily,
    singleLine = singleLine,
    minLines = minLines,
    maxLines = maxLines,
    overflow = overflow,
  )
}
