package com.composables.one

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composables.one.styling.bright
import com.composables.one.styling.buttonLabel
import com.composables.one.styling.buttonHeight
import com.composables.one.styling.buttonShape
import com.composables.one.styling.componentSizes
import com.composables.one.styling.colors
import com.composables.one.styling.dim
import com.composables.one.styling.indications
import com.composables.one.styling.isBright
import com.composables.one.styling.mutate
import com.composables.one.styling.onPrimary
import com.composables.one.styling.primary
import com.composables.one.styling.shapes
import com.composables.one.styling.textStyles
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.ProvideTextStyle
import com.composeunstyled.UnstyledButton
import com.composeunstyled.buildModifier
import com.composeunstyled.minimumInteractiveComponentSize
import com.composeunstyled.theme.Theme

internal val DefaultButtonPadding = PaddingValues(horizontal = 12.dp)
internal val NoButtonPadding = PaddingValues(0.dp)

@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    backgroundColor: Color = Theme[colors][primary],
    contentColor: Color = Theme[colors][onPrimary],
    shape: Shape = Theme[shapes][buttonShape],
    contentPadding: PaddingValues = DefaultButtonPadding,
    borderColor: Color = Color.Unspecified,
    borderWidth: Dp = 1.dp,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit,
) {
    val overriddenBackgroundColor = backgroundColor.mutate(enabled)
    val indication = if (isBright(backgroundColor)) Theme[indications][dim] else Theme[indications][bright]

    UnstyledButton(
        onClick = onClick,
        enabled = enabled,
        contentPadding = contentPadding,
        modifier = modifier
            .heightIn(min = Theme[componentSizes][buttonHeight])
            .minimumInteractiveComponentSize()
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
