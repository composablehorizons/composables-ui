package com.composables.one

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composables.one.styling.bright
import com.composables.one.styling.buttonShape
import com.composables.one.styling.dim
import com.composables.one.styling.indications
import com.composables.one.styling.shapes
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.UnstyledButton
import com.composeunstyled.buildModifier
import com.composeunstyled.theme.Theme

@Composable
fun IconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    style: ButtonStyle = ButtonStyle.Ghost,
    shape: Shape = Theme[shapes][buttonShape],
    buttonSize: ButtonSize = ButtonSize.Default,
    borderWidth: Dp = 1.dp,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit,
) {
    val styleDefaults = buttonStyleDefaults(style)
    val overriddenBackgroundColor = styleDefaults.backgroundColor.mutate(enabled)
    val indication = if (styleDefaults.backgroundColor == Color.Transparent || isBright(styleDefaults.backgroundColor)) {
        Theme[indications][dim]
    } else {
        Theme[indications][bright]
    }

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
