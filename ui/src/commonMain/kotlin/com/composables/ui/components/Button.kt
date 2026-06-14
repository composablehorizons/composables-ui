package com.composables.ui.components

import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composables.ui.theme.InteractionMode
import com.composables.ui.theme.LocalInteractionMode
import com.composables.ui.theme.alphas
import com.composables.ui.theme.borderColor
import com.composables.ui.theme.buttonShape
import com.composables.ui.theme.colors
import com.composables.ui.theme.defaultIndication
import com.composables.ui.theme.destructiveColor
import com.composables.ui.theme.disabledAlpha
import com.composables.ui.theme.indications
import com.composables.ui.theme.inverseIndication
import com.composables.ui.theme.onDestructiveColor
import com.composables.ui.theme.onPanelColor
import com.composables.ui.theme.onPrimaryColor
import com.composables.ui.theme.onSecondaryColor
import com.composables.ui.theme.panelColor
import com.composables.ui.theme.primaryColor
import com.composables.ui.theme.ringColor
import com.composables.ui.theme.secondaryColor
import com.composables.ui.theme.shapes
import com.composables.ui.theme.smallShape
import com.composeunstyled.LocalContentColor
import com.composeunstyled.LocalTextStyle
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.ProvideTextStyle
import com.composeunstyled.UnstyledButton
import com.composeunstyled.buildModifier
import com.composeunstyled.theme.Theme
import kotlin.jvm.JvmInline

/**
 * Visual style variants for Button and IconButton.
 */
@JvmInline
value class ButtonStyle internal constructor(@Suppress("unused") private val value: Int) {
    companion object {
        /**
         * Use for the primary action on a screen.
         */
        val Primary = ButtonStyle(0)

        /**
         * Use for secondary actions.
         */
        val Secondary = ButtonStyle(1)

        /**
         * Use for lower-emphasis actions with an outline.
         */
        val Outlined = ButtonStyle(2)

        /**
         * Use for destructive actions.
         */
        val Destructive = ButtonStyle(3)

        /**
         * Use for low-emphasis actions without a container.
         */
        val Ghost = ButtonStyle(4)

        /**
         * Style for clickable text links.
         */
        val Link = ButtonStyle(5)

        /**
         * The default button style.
         */
        val Default = Secondary
    }
}

/**
 * Size variants for Button and IconButton.
 */
@JvmInline
value class ButtonSize internal constructor(@Suppress("unused") private val value: Int) {
    companion object {
        /**
         * Small button size.
         */
        val Small = ButtonSize(0)

        /**
         * Regular button size.
         */
        val Regular = ButtonSize(1)

        /**
         * Large button size.
         */
        val Large = ButtonSize(2)

        /**
         * The default button size.
         */
        val Default = Regular
    }
}

/**
 * A component that can be clicked.
 * @param onClick Called when the button is activated.
 * @param modifier Modifier applied to the button.
 * @param enabled Whether the button can be interacted with.
 * @param style Visual style used by the button.
 * @param shape Shape used for the button container, border, and focus ring.
 * @param buttonSize Size used for button height, padding, and icon-only button dimensions.
 * @param contentPadding Padding applied inside the button bounds, around the button content.
 * @param borderWidth Width of the button border when the selected style draws one.
 * @param interactionSource Interaction source used for focus, press, hover, and indication state.
 * @param content The content to display within the button.
 */
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
    indication: Indication? = buttonIndicationFor(style),
    contentColor: Color = buttonContentColorFor(style),
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
        contentColor = contentColor,
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

/**
 * Variant of [Button] meant for square targets, such as icons.
 *
 * @param onClick Called when the button is activated.
 * @param modifier Modifier applied to the button.
 * @param enabled Whether the button can be interacted with.
 * @param style Visual style used by the button.
 * @param shape Shape used for the button container, border, and focus ring.
 * @param buttonSize Size used for button height, padding, and icon-only button dimensions.
 * @param borderWidth Width of the button border when the selected style draws one.
 * @param interactionSource Interaction source used for focus, press, hover, and indication state.
 * @param content Composable content displayed inside the button.
 */
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
    indication: Indication? = buttonIndicationFor(style),
    contentColor: Color = buttonContentColorFor(style),
    content: @Composable () -> Unit,
) {
    val backgroundColor = buttonBackgroundColorFor(style)

    BaseButton(
        onClick = onClick,
        modifier = modifier.size(buttonHeightFor(buttonSize)),
        enabled = enabled,
        style = style,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        shape = shape,
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
        return NoPadding
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
private fun buttonHeightFor(buttonSize: ButtonSize): Dp {
    val baseHeight = if (LocalInteractionMode.current == InteractionMode.Touch) 48.dp else 36.dp
    return when (buttonSize) {
        ButtonSize.Small -> baseHeight - 4.dp
        ButtonSize.Large -> baseHeight + 4.dp
        else -> baseHeight
    }
}

@Composable
private fun buttonHorizontalPaddingFor(buttonSize: ButtonSize): Dp {
    val basePadding = if (LocalInteractionMode.current == InteractionMode.Touch) 20.dp else 16.dp
    return when (buttonSize) {
        ButtonSize.Small -> basePadding - 4.dp
        ButtonSize.Large -> basePadding + 4.dp
        else -> basePadding
    }
}

private val NoPadding = PaddingValues(0.dp)
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
        ButtonStyle.Primary -> Theme[colors][primaryColor]
        ButtonStyle.Secondary -> Theme[colors][secondaryColor]
        ButtonStyle.Outlined -> Theme[colors][panelColor]
        ButtonStyle.Destructive -> Theme[colors][destructiveColor]
        else -> Color.Transparent
    }
}

@Composable
private fun buttonContentColorFor(style: ButtonStyle): Color {
    return when (style) {
        ButtonStyle.Primary -> Theme[colors][onPrimaryColor]
        ButtonStyle.Secondary -> Theme[colors][onSecondaryColor]
        ButtonStyle.Destructive -> Theme[colors][onDestructiveColor]
        ButtonStyle.Outlined -> Theme[colors][onPanelColor]
        else -> LocalContentColor.current
    }
}

@Composable
private fun buttonBorderColorFor(style: ButtonStyle): Color {
    return when (style) {
        ButtonStyle.Outlined -> Theme[colors][borderColor]
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
    contentPadding: PaddingValues = NoPadding,
    borderColor: Color,
    borderWidth: Dp,
    interactionSource: MutableInteractionSource,
    indication: Indication?,
    content: @Composable () -> Unit,
) {
    val alpha = if (enabled) 1f else Theme[alphas][disabledAlpha]

    UnstyledButton(
        onClick = onClick,
        enabled = enabled,
        contentPadding = contentPadding,
        modifier = modifier
            .focusRing(
                interactionSource = interactionSource,
                color = Theme[colors][ringColor],
                shape = shape,
            )
            .bouncyPress(
                interactionSource = interactionSource,
                enabled = enabled && style != ButtonStyle.Link,
            )
            .graphicsLayer { this.alpha = alpha }
            .then(buildModifier {
                if (style != ButtonStyle.Link) {
                    add(Modifier.clip(shape))
                    add(Modifier.background(backgroundColor, shape))
                    if (borderColor.isSpecified && borderColor != Color.Transparent && borderWidth > 0.dp) {
                        add(Modifier.border(borderWidth, borderColor, shape))
                    }
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
private fun buttonIndicationFor(style: ButtonStyle) = when (style) {
    ButtonStyle.Primary, ButtonStyle.Destructive -> Theme[indications][inverseIndication]
    ButtonStyle.Link -> null
    else -> Theme[indications][defaultIndication]
}
