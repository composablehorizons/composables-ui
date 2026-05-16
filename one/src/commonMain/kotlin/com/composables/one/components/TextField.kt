package com.composables.one.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.composables.one.Sample
import com.composables.one.ValidationState
import com.composables.one.styling.accent
import com.composables.one.styling.body
import com.composables.one.styling.caption
import com.composables.one.styling.card
import com.composables.one.styling.colors
import com.composables.one.styling.destructive
import com.composables.one.styling.focusRing
import com.composables.one.styling.medium
import com.composables.one.styling.mutate
import com.composables.one.styling.onCard
import com.composables.one.styling.outline
import com.composables.one.styling.shapes
import com.composables.one.styling.textStyles
import com.composeunstyled.LocalContentColor
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.ProvideTextStyle
import com.composeunstyled.TextInput
import com.composeunstyled.minimumInteractiveComponentSize
import com.composeunstyled.outline
import com.composeunstyled.theme.Theme
import com.composeunstyled.UnstyledTextField

private val ButtonDefaultPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)

@Sample("TextFieldExample")
@Composable
fun TextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: (@Composable () -> Unit)? = null,
    leading: (@Composable () -> Unit)? = null,
    placeholder: (@Composable () -> Unit)? = null,
    singleLine: Boolean = false,
    shape: Shape = Theme[shapes][medium],
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    supportive: @Composable (() -> Unit)? = null,
    error: Boolean = false,
    editable: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    validationState: ValidationState? = null,
) {
    UnstyledTextField(
        modifier = modifier,
        value = value,
        onValueChange = {
            onValueChange(it)
            validationState?.validate(it)
        },
        keyboardOptions = keyboardOptions,
        editable = editable,
        interactionSource = interactionSource,
        singleLine = singleLine,
        visualTransformation = visualTransformation,
        cursorBrush = SolidColor(Theme[colors][accent]),
    ) {
        ProvideTextStyle(Theme[textStyles][body]) {
            if (label != null) {
                label()
                Spacer(Modifier.height(16.dp))
            }
        }

        val focused by interactionSource.collectIsFocusedAsState()
        val outlineColor = when {
            error -> Theme[colors][destructive]
            focused -> Theme[colors][focusRing]
            else -> Theme[colors][outline]
        }
        val outlineThickness = if (focused) 2.dp else 1.dp
        TextInput(
            leading = leading,
            backgroundColor = Theme[colors][card].mutate(editable),
            contentColor = Theme[colors][onCard].mutate(editable),
            shape = shape,
            contentPadding = ButtonDefaultPadding,
            modifier = Modifier
                .minimumInteractiveComponentSize()
                .outline(outlineThickness, outlineColor, shape),
            placeholder = placeholder?.let { content ->
                {
                    ProvideContentColor(LocalContentColor.current.copy(alpha = 0.50f)) {
                        content()
                    }
                }
            }
        )
        if (supportive != null) {
            Spacer(Modifier.height(8.dp))
            val supportiveColor = if (error) Theme[colors][destructive] else LocalContentColor.current
            ProvideContentColor(supportiveColor) {
                ProvideTextStyle(Theme[textStyles][caption]) {
                    supportive()
                }
            }
        }
    }
}