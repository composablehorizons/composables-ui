package com.composables.one

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composables.one.styling.bright
import com.composables.one.styling.border
import com.composables.one.styling.buttonLabel
import com.composables.one.styling.buttonShape
import com.composables.one.styling.colors
import com.composables.one.styling.destructive
import com.composables.one.styling.dim
import com.composables.one.styling.indications
import com.composables.one.styling.onBackground
import com.composables.one.styling.onDestructive
import com.composables.one.styling.onPrimary
import com.composables.one.styling.onSecondary
import com.composables.one.styling.primary
import com.composables.one.styling.secondary
import com.composables.one.styling.shapes
import com.composables.one.styling.textStyles
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.ProvideTextStyle
import com.composeunstyled.UnstyledButton
import com.composeunstyled.buildModifier
import com.composeunstyled.theme.Theme

enum class ButtonStyle {
    Default,
    Primary,
    Secondary,
    Outlined,
    Destructive,
    Ghost,
}

enum class ButtonSize {
    Default,
    Small,
    Regular,
    Large,
}

@Composable
fun Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    style: ButtonStyle = ButtonStyle.Default,
    shape: Shape = Theme[shapes][buttonShape],
    buttonSize: ButtonSize = ButtonSize.Default,
    contentPadding: PaddingValues = buttonPaddingFor(buttonSize),
    borderWidth: Dp = 1.dp,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit,
) {
    val styleDefaults = buttonStyleDefaults(style)

    ButtonSkeleton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        backgroundColor = styleDefaults.backgroundColor,
        contentColor = styleDefaults.contentColor,
        shape = shape,
        buttonSize = buttonSize,
        contentPadding = contentPadding,
        borderColor = styleDefaults.borderColor,
        borderWidth = borderWidth,
        interactionSource = interactionSource,
        content = content,
    )
}

@Composable
fun IconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    style: ButtonStyle = ButtonStyle.Default,
    shape: Shape = Theme[shapes][buttonShape],
    buttonSize: ButtonSize = ButtonSize.Default,
    borderWidth: Dp = 1.dp,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit,
) {
    val styleDefaults = buttonStyleDefaults(style)
    val overriddenBackgroundColor = styleDefaults.backgroundColor.mutate(enabled)
    val indication = buttonIndicationFor(styleDefaults.backgroundColor)

    UnstyledButton(
        onClick = onClick,
        enabled = enabled,
        contentPadding = NoButtonPadding,
        modifier = modifier
            .animateContentSize()
            .size(buttonHeightFor(buttonSize))
            .clip(shape)
            .background(overriddenBackgroundColor, shape)
            .then(buildModifier {
                if (styleDefaults.borderColor.isSpecified && styleDefaults.borderColor != Color.Transparent && borderWidth > Dp.Hairline) {
                    add(Modifier.border(borderWidth, styleDefaults.borderColor, shape))
                }
            }),
        interactionSource = interactionSource,
        indication = indication,
    ) {
        ProvideContentColor(styleDefaults.contentColor) {
            content()
        }
    }
}

internal fun buttonPaddingFor(buttonSize: ButtonSize): PaddingValues {
    return PaddingValues(horizontal = buttonHorizontalPaddingFor(buttonSize))
}

private fun buttonHeightFor(buttonSize: ButtonSize): Dp = when (buttonSize) {
    ButtonSize.Default -> buttonHeightFor(ButtonSize.Regular)
    ButtonSize.Small -> 40.dp
    ButtonSize.Regular -> 44.dp
    ButtonSize.Large -> 48.dp
}

private fun buttonHorizontalPaddingFor(buttonSize: ButtonSize): Dp = when (buttonSize) {
    ButtonSize.Default -> buttonHorizontalPaddingFor(ButtonSize.Regular)
    ButtonSize.Small -> 12.dp
    ButtonSize.Regular -> 16.dp
    ButtonSize.Large -> 20.dp
}

private val NoButtonPadding = PaddingValues(0.dp)

private data class ButtonStyleDefaults(
    val backgroundColor: Color,
    val contentColor: Color,
    val borderColor: Color,
)

@Composable
private fun buttonStyleDefaults(style: ButtonStyle): ButtonStyleDefaults {
    return when (style) {
        ButtonStyle.Default -> buttonStyleDefaults(ButtonStyle.Secondary)

        ButtonStyle.Primary -> ButtonStyleDefaults(
            backgroundColor = Theme[colors][primary],
            contentColor = Theme[colors][onPrimary],
            borderColor = Color.Unspecified,
        )

        ButtonStyle.Secondary -> ButtonStyleDefaults(
            backgroundColor = Theme[colors][secondary],
            contentColor = Theme[colors][onSecondary],
            borderColor = Theme[colors][border],
        )

        ButtonStyle.Outlined -> ButtonStyleDefaults(
            backgroundColor = Color.Transparent,
            contentColor = Theme[colors][onBackground],
            borderColor = Theme[colors][border],
        )

        ButtonStyle.Destructive -> ButtonStyleDefaults(
            backgroundColor = Theme[colors][destructive],
            contentColor = Theme[colors][onDestructive],
            borderColor = Color.Unspecified,
        )

        ButtonStyle.Ghost -> ButtonStyleDefaults(
            backgroundColor = Color.Transparent,
            contentColor = Theme[colors][onBackground],
            borderColor = Color.Unspecified,
        )
    }
}

@Composable
private fun ButtonSkeleton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    backgroundColor: Color,
    contentColor: Color,
    shape: Shape,
    buttonSize: ButtonSize,
    contentPadding: PaddingValues,
    borderColor: Color,
    borderWidth: Dp,
    interactionSource: MutableInteractionSource,
    content: @Composable RowScope.() -> Unit,
) {
    val overriddenBackgroundColor = backgroundColor.mutate(enabled)
    val indication = buttonIndicationFor(backgroundColor)

    UnstyledButton(
        onClick = onClick,
        enabled = enabled,
        contentPadding = contentPadding,
        modifier = modifier
            .animateContentSize()
            .heightIn(min = buttonHeightFor(buttonSize))
            .clip(shape)
            .background(overriddenBackgroundColor, shape)
            .then(buildModifier {
                if (borderColor.isSpecified && borderColor != Color.Transparent && borderWidth > Dp.Hairline) {
                    add(Modifier.border(borderWidth, borderColor, shape))
                }
            }),
        interactionSource = interactionSource,
        indication = indication,
    ) {
        ProvideContentColor(contentColor) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ProvideTextStyle(Theme[textStyles][buttonLabel]) {
                    content()
                }
            }
        }
    }
}

@Composable
private fun buttonIndicationFor(backgroundColor: Color) = if (backgroundColor == Color.Transparent || backgroundColor.isBright()) {
    Theme[indications][dim]
} else {
    Theme[indications][bright]
}

private fun Color.isBright(): Boolean = luminance() > 0.5f

private fun Color.mutate(enabled: Boolean): Color {
    if (this == Color.Transparent || this == Color.Unspecified) return this
    return if (enabled) this else copy(alpha = 0.6f)
}
