package com.composables.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import com.composeunstyled.LocalContentColor
import com.composeunstyled.LocalTextStyle
import com.composeunstyled.Text as UnstyledText

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
