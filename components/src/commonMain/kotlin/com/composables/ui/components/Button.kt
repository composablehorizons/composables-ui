package com.composables.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composables.ui.theme.alphas
import com.composables.ui.theme.bright
import com.composables.ui.theme.border
import com.composables.ui.theme.buttonShape
import com.composables.ui.theme.componentSizes
import com.composables.ui.theme.colors
import com.composables.ui.theme.destructive
import com.composables.ui.theme.disabledAlpha
import com.composables.ui.theme.dim
import com.composables.ui.theme.focusRing
import com.composables.ui.theme.focusRingOffset
import com.composables.ui.theme.focusRingWidth
import com.composables.ui.theme.indications
import com.composables.ui.theme.onBackground
import com.composables.ui.theme.onDestructive
import com.composables.ui.theme.onPrimary
import com.composables.ui.theme.onSecondary
import com.composables.ui.theme.primary
import com.composables.ui.theme.secondary
import com.composables.ui.theme.shapes
import com.composables.ui.theme.smallShape
import com.composeunstyled.LocalTextStyle
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.ProvideTextStyle
import com.composeunstyled.UnstyledButton
import com.composeunstyled.buildModifier
import com.composeunstyled.theme.Theme

@kotlin.jvm.JvmInline
value class ButtonStyle internal constructor(@Suppress("unused") private val value: Int) {
    companion object {
        val Primary = ButtonStyle(0)
        val Secondary = ButtonStyle(1)
        val Outlined = ButtonStyle(2)
        val Destructive = ButtonStyle(3)
        val Ghost = ButtonStyle(4)
        val Link = ButtonStyle(5)
        val Default = Secondary
    }
}

@kotlin.jvm.JvmInline
value class ButtonSize internal constructor(@Suppress("unused") private val value: Int) {
    companion object {
        val Small = ButtonSize(0)
        val Regular = ButtonSize(1)
        val Large = ButtonSize(2)
        val Default = Regular
    }
}

@Composable
fun Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    style: ButtonStyle = ButtonStyle.Default,
    shape: Shape = buttonShapeFor(style),
    buttonSize: ButtonSize = ButtonSize.Default,
    contentPadding: PaddingValues = buttonPaddingFor(buttonSize, style),
    borderWidth: Dp = 1.dp,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    indication: Indication? = buttonIndicationFor(style, buttonBackgroundColorFor(style)),
    content: @Composable RowScope.() -> Unit,
) {
    val hovered by interactionSource.collectIsHoveredAsState()
    val backgroundColor = buttonBackgroundColorFor(style)

    BaseButton(
        onClick = onClick,
        modifier = modifier.size(buttonSize, style),
        enabled = enabled,
        style = style,
        backgroundColor = backgroundColor,
        contentColor = buttonContentColorFor(style),
        shape = shape,
        contentPadding = contentPadding,
        borderColor = buttonBorderColorFor(style),
        borderWidth = borderWidth,
        interactionSource = interactionSource,
        indication = indication,
        content = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ProvideTextStyle(LocalTextStyle.current.merge(buttonLabelTextStyleFor(style, hovered))) {
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
    indication: Indication? = buttonIndicationFor(style, buttonBackgroundColorFor(style)),
    content: @Composable () -> Unit,
) {
    val backgroundColor = buttonBackgroundColorFor(style)

    BaseButton(
        onClick = onClick,
        modifier = modifier.size(iconButtonSizeFor(buttonSize)),
        enabled = enabled,
        style = style,
        backgroundColor = backgroundColor,
        contentColor = buttonContentColorFor(style),
        shape = shape,
        contentPadding = NoButtonPadding,
        borderColor = buttonBorderColorFor(style),
        borderWidth = borderWidth,
        interactionSource = interactionSource,
        indication = indication,
        content = content,
    )
}

@Composable
private fun buttonPaddingFor(buttonSize: ButtonSize, style: ButtonStyle): PaddingValues {
    if (style == ButtonStyle.Link) {
        return NoButtonPadding
    }

    return PaddingValues(horizontal = buttonHorizontalPaddingFor(buttonSize))
}

@Composable
private fun buttonShapeFor(style: ButtonStyle): Shape {
    return if (style == ButtonStyle.Link) Theme[shapes][smallShape] else Theme[shapes][buttonShape]
}

@Composable
private fun Modifier.size(buttonSize: ButtonSize, style: ButtonStyle): Modifier {
    return if (style == ButtonStyle.Link) this else heightIn(min = buttonHeightFor(buttonSize))
}

@Composable
private fun buttonHeightFor(buttonSize: ButtonSize): Dp = when (buttonSize) {
    ButtonSize.Small -> defaultControlHeight() - 4.dp
    ButtonSize.Large -> defaultControlHeight() + 4.dp
    else -> defaultControlHeight()
}

@Composable
private fun iconButtonSizeFor(buttonSize: ButtonSize): Dp = when (buttonSize) {
    ButtonSize.Small -> defaultIconButtonSize() - 4.dp
    ButtonSize.Large -> defaultIconButtonSize() + 4.dp
    else -> defaultIconButtonSize()
}

@Composable
private fun buttonHorizontalPaddingFor(buttonSize: ButtonSize): Dp = when (buttonSize) {
    ButtonSize.Small -> defaultButtonHorizontalPadding() - 4.dp
    ButtonSize.Large -> defaultButtonHorizontalPadding() + 4.dp
    else -> defaultButtonHorizontalPadding()
}

private val NoButtonPadding = PaddingValues(0.dp)
private val ButtonLabelTextStyle = TextStyle(
    fontWeight = FontWeight.Medium,
)

private fun buttonLabelTextStyleFor(style: ButtonStyle, hovered: Boolean): TextStyle {
    return if (style == ButtonStyle.Link && hovered) {
        ButtonLabelTextStyle.copy(textDecoration = TextDecoration.Underline)
    } else {
        ButtonLabelTextStyle
    }
}

@Composable
private fun buttonBackgroundColorFor(style: ButtonStyle): Color {
    return when (style) {
        ButtonStyle.Primary -> Theme[colors][primary]
        ButtonStyle.Secondary -> Theme[colors][secondary]
        ButtonStyle.Outlined -> Color.Transparent
        ButtonStyle.Destructive -> Theme[colors][destructive]
        else -> Color.Transparent
    }
}

@Composable
private fun buttonContentColorFor(style: ButtonStyle): Color {
    return when (style) {
        ButtonStyle.Primary -> Theme[colors][onPrimary]
        ButtonStyle.Secondary -> Theme[colors][onSecondary]
        ButtonStyle.Destructive -> Theme[colors][onDestructive]
        else -> Theme[colors][onBackground]
    }
}

@Composable
private fun buttonBorderColorFor(style: ButtonStyle): Color {
    return when (style) {
        ButtonStyle.Outlined -> Theme[colors][border]
        else -> Color.Unspecified
    }
}

@Composable
private fun BaseButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    style: ButtonStyle,
    backgroundColor: Color,
    contentColor: Color,
    shape: Shape,
    contentPadding: PaddingValues,
    borderColor: Color,
    borderWidth: Dp,
    interactionSource: MutableInteractionSource,
    indication: Indication?,
    content: @Composable () -> Unit,
) {
    UnstyledButton(
        onClick = onClick,
        enabled = enabled,
        contentPadding = contentPadding,
        modifier = modifier
            .focusRing(
                interactionSource = interactionSource,
                width = Theme[componentSizes][focusRingWidth],
                color = Theme[colors][focusRing],
                shape = shape,
                offset = Theme[componentSizes][focusRingOffset],
            )
            .bouncyPress(enabled = enabled && style != ButtonStyle.Link, interactionSource = interactionSource)
            .then(buildModifier {
                if (style != ButtonStyle.Link) {
                    add(Modifier.clip(shape))
                    add(Modifier.background(backgroundColor, shape))
                    if (borderColor.isSpecified && borderColor != Color.Transparent && borderWidth > Dp.Hairline) {
                        add(Modifier.border(borderWidth, borderColor, shape))
                    }
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
private fun buttonIndicationFor(style: ButtonStyle, backgroundColor: Color) = if (style == ButtonStyle.Link) {
    null
} else if (backgroundColor == Color.Transparent || backgroundColor.isBright()) {
    Theme[indications][dim]
} else {
    Theme[indications][bright]
}

private fun Color.isBright(): Boolean = luminance() > 0.5f

@Composable
private fun Modifier.bouncyPress(
    enabled: Boolean,
    interactionSource: InteractionSource,
): Modifier {
    if (!enabled) {
        return this
    }

    val pressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed) ButtonPressedScale else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow,
        ),
        label = "ButtonPressedScale",
    )

    return graphicsLayer {
        scaleX = scale
        scaleY = scale
    }
}

private const val ButtonPressedScale = 0.96f
