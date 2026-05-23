package com.composables.ui.components

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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.ui.theme.bright
import com.composables.ui.theme.border
import com.composables.ui.theme.buttonHeight
import com.composables.ui.theme.buttonHorizontalPadding
import com.composables.ui.theme.buttonShape
import com.composables.ui.theme.alphas
import com.composables.ui.theme.componentSizes
import com.composables.ui.theme.colors
import com.composables.ui.theme.destructive
import com.composables.ui.theme.disabledAlpha
import com.composables.ui.theme.dim
import com.composables.ui.theme.focusRing
import com.composables.ui.theme.focusRingOffset
import com.composables.ui.theme.focusRingWidth
import com.composables.ui.theme.iconButtonSize
import com.composables.ui.theme.indications
import com.composables.ui.theme.onBackground
import com.composables.ui.theme.onDestructive
import com.composables.ui.theme.onPrimary
import com.composables.ui.theme.onSecondary
import com.composables.ui.theme.primary
import com.composables.ui.theme.secondary
import com.composables.ui.theme.shapes
import com.composeunstyled.LocalTextStyle
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

class ButtonSize private constructor() {
    companion object {
        val Small = ButtonSize()
        val Regular = ButtonSize()
        val Large = ButtonSize()
        val Default = Regular
    }
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
        sizingModifier = Modifier.heightIn(min = buttonHeightFor(buttonSize)),
        enabled = enabled,
        backgroundColor = styleDefaults.backgroundColor,
        contentColor = styleDefaults.contentColor,
        shape = shape,
        contentPadding = contentPadding,
        borderColor = styleDefaults.borderColor,
        borderWidth = borderWidth,
        interactionSource = interactionSource,
        content = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ProvideTextStyle(LocalTextStyle.current.merge(ButtonLabelTextStyle)) {
                    content()
                }
            }
        },
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

    ButtonSkeleton(
        onClick = onClick,
        modifier = modifier,
        sizingModifier = Modifier.size(iconButtonSizeFor(buttonSize)),
        enabled = enabled,
        backgroundColor = styleDefaults.backgroundColor,
        contentColor = styleDefaults.contentColor,
        shape = shape,
        contentPadding = NoButtonPadding,
        borderColor = styleDefaults.borderColor,
        borderWidth = borderWidth,
        interactionSource = interactionSource,
        content = content,
    )
}

@Composable
internal fun buttonPaddingFor(buttonSize: ButtonSize): PaddingValues {
    return PaddingValues(horizontal = buttonHorizontalPaddingFor(buttonSize))
}

@Composable
private fun buttonHeightFor(buttonSize: ButtonSize): Dp = when (buttonSize) {
    ButtonSize.Small -> Theme[componentSizes][buttonHeight] - 4.dp
    ButtonSize.Large -> Theme[componentSizes][buttonHeight] + 4.dp
    else -> Theme[componentSizes][buttonHeight]
}

@Composable
private fun iconButtonSizeFor(buttonSize: ButtonSize): Dp = when (buttonSize) {
    ButtonSize.Small -> Theme[componentSizes][iconButtonSize] - 4.dp
    ButtonSize.Large -> Theme[componentSizes][iconButtonSize] + 4.dp
    else -> Theme[componentSizes][iconButtonSize]
}

@Composable
private fun buttonHorizontalPaddingFor(buttonSize: ButtonSize): Dp = when (buttonSize) {
    ButtonSize.Small -> Theme[componentSizes][buttonHorizontalPadding] - 4.dp
    ButtonSize.Large -> Theme[componentSizes][buttonHorizontalPadding] + 4.dp
    else -> Theme[componentSizes][buttonHorizontalPadding]
}

private val NoButtonPadding = PaddingValues(0.dp)
private val ButtonLabelTextStyle = TextStyle(
    fontSize = 16.sp,
    lineHeight = 24.sp,
    fontWeight = FontWeight.Medium,
)

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
    sizingModifier: Modifier,
    enabled: Boolean = true,
    backgroundColor: Color,
    contentColor: Color,
    shape: Shape,
    contentPadding: PaddingValues,
    borderColor: Color,
    borderWidth: Dp,
    interactionSource: MutableInteractionSource,
    content: @Composable () -> Unit,
) {
    val indication = buttonIndicationFor(backgroundColor)

    UnstyledButton(
        onClick = onClick,
        enabled = enabled,
        contentPadding = contentPadding,
        modifier = modifier
            .then(sizingModifier)
            .focusRing(
                interactionSource = interactionSource,
                width = Theme[componentSizes][focusRingWidth],
                color = Theme[colors][focusRing],
                shape = shape,
                offset = Theme[componentSizes][focusRingOffset],
            )
            .clip(shape)
            .background(backgroundColor, shape)
            .then(buildModifier {
                if (borderColor.isSpecified && borderColor != Color.Transparent && borderWidth > Dp.Hairline) {
                    add(Modifier.border(borderWidth, borderColor, shape))
                }
                if (!enabled) {
                    add(Modifier.alpha(Theme[alphas][disabledAlpha]))
                }
            }),
        interactionSource = interactionSource,
        indication = indication,
    ) {
        ProvideContentColor(contentColor) {
            content()
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
