package com.composables.one

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.input.InputMode
import androidx.compose.ui.platform.LocalInputModeManager
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composables.one.styling.bright
import com.composables.one.styling.colors
import com.composables.one.styling.componentSizes
import com.composables.one.styling.dim
import com.composables.one.styling.iconButtonSize
import com.composables.one.styling.iconButtonTouchSize
import com.composables.one.styling.indications
import com.composables.one.styling.isBright
import com.composables.one.styling.mutate
import com.composables.one.styling.onBackground
import com.composables.one.styling.shapes
import com.composables.one.styling.small
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.UnstyledButton
import com.composeunstyled.buildModifier
import com.composeunstyled.minimumInteractiveComponentSize
import com.composeunstyled.outline
import com.composeunstyled.theme.Theme

@Composable
fun IconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    backgroundColor: Color = Color.Transparent,
    contentColor: Color = Theme[colors][onBackground],
    shape: Shape = Theme[shapes][small],
    borderColor: Color = Color.Unspecified,
    borderWidth: Dp = 1.dp,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit,
) {
    val overriddenBackgroundColor = backgroundColor.mutate(enabled)
    val indication = if (isBright(backgroundColor)) Theme[indications][dim] else Theme[indications][bright]
    val inputModeManager = LocalInputModeManager.current
    val size = if (inputModeManager.inputMode == InputMode.Touch) {
        Theme[componentSizes][iconButtonTouchSize]
    } else {
        Theme[componentSizes][iconButtonSize]
    }

    UnstyledButton(
        onClick = onClick,
        enabled = enabled,
        contentPadding = NoButtonPadding,
        modifier = modifier
            .size(size)
            .minimumInteractiveComponentSize()
            .clip(shape)
            .background(overriddenBackgroundColor, shape)
            .then(buildModifier {
                if (borderColor.isSpecified && borderColor != Color.Transparent && borderWidth > Dp.Hairline) {
                    add(Modifier.outline(borderWidth, borderColor, shape))
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
