package com.composables.ui.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.isSpecified
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
import com.composables.ui.theme.InteractionMode
import com.composables.ui.theme.LocalInteractionMode
import com.composables.ui.theme.alphas
import com.composables.ui.theme.colors
import com.composables.ui.theme.defaultTextSelectionColors
import com.composables.ui.theme.fieldColor
import com.composables.ui.theme.fieldShape
import com.composables.ui.theme.ringColor
import com.composables.ui.theme.disabledAlpha
import com.composables.ui.theme.mutedColor
import com.composables.ui.theme.onFieldColor
import com.composables.ui.theme.shapes
import com.composables.ui.theme.textSelectionColors
import com.composeunstyled.LocalTextStyle
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.ProvideTextStyle
import com.composeunstyled.TextInput
import com.composeunstyled.UnstyledTextField
import com.composeunstyled.buildModifier
import com.composeunstyled.focusRing as unstyledFocusRing
import com.composeunstyled.theme.Theme
import com.composeunstyled.TextFieldScope as UnstyledTextFieldScope

/**
 * Visual style variants for TextField.
 */
@kotlin.jvm.JvmInline
value class TextFieldStyle internal constructor(@Suppress("unused") private val value: Int) {
    companion object {
        /**
         * Uses the filled text field container.
         */
        val Filled = TextFieldStyle(0)
        /**
         * Uses a transparent text field container.
         */
        val Ghost = TextFieldStyle(1)
        /**
         * Uses the default text field style.
         */
        val Default = Filled
    }
}

/**
 * A styled text input field.
 * @param state State object that holds and edits the text field value.
 * @param modifier Modifier applied to the text field.
 * @param enabled Whether the text field can be interacted with.
 * @param readOnly Whether the text field can receive focus without allowing text edits.
 * @param accessibilityLabel Accessible label announced for the text field.
 * @param placeholder Content displayed when the text field is empty.
 * @param leading Content displayed before the editable text.
 * @param trailing Content displayed after the editable text.
 * @param style Visual style used by the text field.
 * @param shape Shape used for the text field container and focus ring.
 * @param backgroundColor Background color used by the text field container.
 * @param contentColor Color used for input text and field content.
 * @param placeholderColor Color used for placeholder content.
 * @param borderColor Border color drawn around the text field when specified.
 * @param contentPadding Padding applied around the text field content.
 * @param cursorBrush Brush used to draw the text cursor.
 * @param selectionColors Colors used for text selection handles and highlight.
 * @param textStyle Text style used by the editable text.
 * @param textAlign Text alignment used by the editable text.
 * @param lineHeight Line height used by the editable text.
 * @param fontSize Font size used by the editable text.
 * @param letterSpacing Letter spacing used by the editable text.
 * @param fontWeight Font weight used by the editable text.
 * @param fontFamily Font family used by the editable text.
 * @param textDecoration Text decoration used by the editable text.
 * @param lineLimits Single-line or multiline limits used by the text field.
 * @param inputTransformation Transformation applied to input edits before they are committed.
 * @param outputTransformation Transformation applied to displayed text.
 * @param onTextLayout Called with the latest text layout result.
 * @param onKeyboardAction Called when the keyboard action is triggered.
 * @param keyboardOptions Keyboard configuration for the text field.
 * @param interactionSource Interaction source used for focus state.
 * @param scrollState Scroll state used by the editable text.
 */
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
    style: TextFieldStyle = TextFieldStyle.Default,
    shape: Shape = Theme[shapes][fieldShape],
    backgroundColor: Color = textFieldBackgroundColorFor(style),
    contentColor: Color = Theme[colors][onFieldColor],
    placeholderColor: Color = Theme[colors][mutedColor],
    borderColor: Color = textFieldBorderColorFor(style),
    contentPadding: PaddingValues = PaddingValues(horizontal = 12.dp),
    cursorBrush: Brush = SolidColor(contentColor),
    selectionColors: TextSelectionColors = Theme[textSelectionColors][defaultTextSelectionColors],
    textStyle: TextStyle = LocalTextStyle.current.merge(TextFieldInputTextStyle),
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
    UnstyledTextField(
        state = state,
        enabled = enabled,
        readOnly = readOnly,
        accessibilityLabel = accessibilityLabel,
        cursorBrush = cursorBrush,
        selectionColors = selectionColors,
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
            .unstyledFocusRing(
                interactionSource = interactionSource,
                width = 2.dp,
                color = Theme[colors][ringColor],
                shape = shape,
                offset = 2.dp,
            )
            .clip(shape)
            .background(backgroundColor, shape)
            .then(buildModifier {
                if (borderColor.isSpecified && borderColor != Color.Transparent) {
                    add(Modifier.border(1.dp, borderColor, shape))
                }
                if (!enabled) {
                    add(Modifier.alpha(Theme[alphas][disabledAlpha]))
                }
            }),
    ) {
        TextFieldContent(
            placeholder = placeholder,
            placeholderColor = placeholderColor,
            contentColor = contentColor,
            textStyle = textStyle,
            lineLimits = lineLimits,
            contentPadding = contentPadding,
            leading = leading,
            trailing = trailing,
        )
    }
}

@Composable
private fun textFieldBackgroundColorFor(style: TextFieldStyle): Color {
    return when (style) {
        TextFieldStyle.Ghost -> Color.Transparent
        else -> Theme[colors][fieldColor]
    }
}

@Composable
private fun textFieldBorderColorFor(style: TextFieldStyle): Color {
    return when (style) {
        else -> Color.Unspecified
    }
}

private val TextFieldInputTextStyle = TextStyle()

@Composable
private fun textFieldMinHeight(): Dp {
    return if (LocalInteractionMode.current == InteractionMode.Touch) 48.dp else 40.dp
}

@Composable
private fun UnstyledTextFieldScope.TextFieldContent(
    placeholder: (@Composable () -> Unit)?,
    placeholderColor: Color,
    contentColor: Color,
    textStyle: TextStyle,
    lineLimits: TextFieldLineLimits,
    contentPadding: PaddingValues,
    leading: (@Composable () -> Unit)?,
    trailing: (@Composable () -> Unit)?,
) {
    ProvideContentColor(contentColor) {
        ProvideTextStyle(textStyle) {
            Row(
                modifier = Modifier
                    .heightIn(min = textFieldMinHeight())
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
