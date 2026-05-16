package com.composables.one.components

import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import com.composables.one.Sample
import com.composeunstyled.LocalTextStyle
import com.composeunstyled.UnstyledText

@Sample("TextExample")
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
    color: Color = Color.Unspecified,
    selectable: Boolean = false,
    fontFamily: FontFamily? = style.fontFamily,
    singleLine: Boolean = false,
    minLines: Int = 1,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
) {
    val text = @Composable {
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
            overflow = overflow,
            maxLines = maxLines,
            minLines = minLines,
        )
    }

    val movableContent = remember {
        movableContentOf {
            text()
        }
    }
    if (selectable) {
        SelectionContainer {
            movableContent()
        }
    } else {
        movableContent()
    }
}
