package com.composables.one

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.composables.one.styling.border
import com.composables.one.styling.colors
import com.composables.one.styling.componentSizes
import com.composables.one.styling.focusRing
import com.composables.one.styling.focusRingOffset
import com.composables.one.styling.focusRingWidth
import com.composables.one.styling.input
import com.composables.one.styling.inputDisabled
import com.composables.one.styling.inputPlaceholder
import com.composables.one.styling.onInput
import com.composables.one.styling.shapes
import com.composables.one.styling.textFieldHeight
import com.composables.one.styling.textFieldHorizontalPadding
import com.composables.one.styling.textFieldInput
import com.composables.one.styling.textFieldShape
import com.composables.one.styling.textSelectionBackground
import com.composables.one.styling.textSelectionHandle
import com.composables.one.styling.textStyles
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.ProvideTextStyle
import com.composeunstyled.TextInput
import com.composeunstyled.UnstyledTextField
import com.composeunstyled.buildModifier
import com.composeunstyled.focusRing
import com.composeunstyled.theme.Theme
import com.composeunstyled.TextFieldScope as UnstyledTextFieldScope

@Composable
fun TextField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    accessibilityLabel: String? = null,
    placeholder: (@Composable () -> Unit)? = null,
    leading: (@Composable () -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null,
    shape: Shape = Theme[shapes][textFieldShape],
    backgroundColor: Color = if (enabled) Theme[colors][input] else Theme[colors][inputDisabled],
    contentColor: Color = Theme[colors][onInput],
    placeholderColor: Color = Theme[colors][inputPlaceholder],
    borderColor: Color = Theme[colors][border],
    contentPadding: PaddingValues = PaddingValues(horizontal = Theme[componentSizes][textFieldHorizontalPadding]),
    minHeight: Dp = Theme[componentSizes][textFieldHeight],
    cursorBrush: Brush = SolidColor(contentColor),
    selectionColors: TextSelectionColors = TextSelectionColors(
        handleColor = Theme[colors][textSelectionHandle],
        backgroundColor = Theme[colors][textSelectionBackground],
    ),
    textStyle: TextStyle = Theme[textStyles][textFieldInput],
    textAlign: TextAlign = TextAlign.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,
    fontSize: TextUnit = textStyle.fontSize,
    letterSpacing: TextUnit = textStyle.letterSpacing,
    fontWeight: FontWeight? = textStyle.fontWeight,
    fontFamily: FontFamily? = textStyle.fontFamily,
    textDecoration: TextDecoration? = textStyle.textDecoration,
    lineLimits: TextFieldLineLimits = TextFieldLineLimits.SingleLine,
    inputTransformation: InputTransformation? = null,
    outputTransformation: OutputTransformation? = null,
    onTextLayout: (Density.(getResult: () -> TextLayoutResult?) -> Unit)? = null,
    onKeyboardAction: KeyboardActionHandler? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    scrollState: ScrollState = rememberScrollState(),
) {
    CompositionLocalProvider(LocalTextSelectionColors provides selectionColors) {
        UnstyledTextField(
            state = state,
            enabled = enabled,
            readOnly = readOnly,
            accessibilityLabel = accessibilityLabel,
            cursorBrush = cursorBrush,
            textStyle = textStyle,
            textAlign = textAlign,
            lineHeight = lineHeight,
            fontSize = fontSize,
            letterSpacing = letterSpacing,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            textDecoration = textDecoration,
            lineLimits = lineLimits,
            inputTransformation = inputTransformation,
            outputTransformation = outputTransformation,
            onTextLayout = onTextLayout,
            onKeyboardAction = onKeyboardAction,
            keyboardOptions = keyboardOptions,
            interactionSource = interactionSource,
            textColor = contentColor,
            scrollState = scrollState,
            modifier = modifier
                .focusRing(
                    interactionSource = interactionSource,
                    width = Theme[componentSizes][focusRingWidth],
                    color = Theme[colors][focusRing],
                    shape = shape,
                    offset = Theme[componentSizes][focusRingOffset],
                )
                .clip(shape)
                .background(backgroundColor, shape)
                .border(1.dp, borderColor, shape)
                .then(buildModifier {
                    if (!enabled) {
                        add(Modifier.alpha(0.65f))
                    }
                }),
        ) {
            TextFieldContent(
                placeholder = placeholder,
                placeholderColor = placeholderColor,
                contentColor = contentColor,
                textStyle = textStyle,
                lineLimits = lineLimits,
                minHeight = minHeight,
                contentPadding = contentPadding,
                leading = leading,
                trailing = trailing,
            )
        }
    }
}

@Composable
private fun UnstyledTextFieldScope.TextFieldContent(
    placeholder: (@Composable () -> Unit)?,
    placeholderColor: Color,
    contentColor: Color,
    textStyle: TextStyle,
    lineLimits: TextFieldLineLimits,
    minHeight: Dp,
    contentPadding: PaddingValues,
    leading: (@Composable () -> Unit)?,
    trailing: (@Composable () -> Unit)?,
) {
    ProvideContentColor(contentColor) {
        ProvideTextStyle(textStyle) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = minHeight)
                    .padding(contentPadding),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = if (lineLimits == TextFieldLineLimits.SingleLine) {
                    Alignment.CenterVertically
                } else {
                    Alignment.Top
                },
            ) {
                if (leading != null) {
                    Box(
                        modifier = Modifier.sizeIn(minWidth = 16.dp, minHeight = 16.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        leading()
                    }
                }
                TextInput(
                    modifier = Modifier.weight(1f),
                    placeholder = placeholder?.let {
                        {
                            ProvideContentColor(placeholderColor) {
                                it()
                            }
                        }
                    },
                )
                if (trailing != null) {
                    Box(
                        modifier = Modifier.sizeIn(minWidth = 16.dp, minHeight = 16.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        trailing()
                    }
                }
            }
        }
    }
}
