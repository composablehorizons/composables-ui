package com.composables.one.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.isUnspecified
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composables.one.Sample
import com.composables.one.styling.body
import com.composables.one.styling.bright
import com.composables.one.styling.colors
import com.composables.one.styling.destructive
import com.composables.one.styling.dim
import com.composables.one.styling.focusRing
import com.composables.one.styling.indications
import com.composables.one.styling.isBright
import com.composables.one.styling.mutate
import com.composables.one.styling.onDestructive
import com.composables.one.styling.onPrimary
import com.composables.one.styling.onSecondary
import com.composables.one.styling.outline
import com.composables.one.styling.primary
import com.composables.one.styling.secondary
import com.composables.one.styling.shapes
import com.composables.one.styling.small
import com.composables.one.styling.textStyles
import com.composeunstyled.LocalContentColor
import com.composeunstyled.ProvideTextStyle
import com.composeunstyled.focusRing
import com.composeunstyled.minimumInteractiveComponentSize
import com.composeunstyled.theme.Theme
import com.composeunstyled.Button as UnstyledButton

@Sample("OutlinedButtonExample")
@Composable
fun OutlinedButton(
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    shape: Shape = Theme[shapes][small],
    contentColor: Color = LocalContentColor.current,
    backgroundColor: Color = Color.Transparent,
    borderColor: Color = Theme[colors][outline],
    borderWidth: Dp = 1.dp,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    contentPadding: PaddingValues = DefaultButtonPadding,
    content: @Composable RowScope.() -> Unit,
) {
    val overriddenContentColor = contentColor.mutate(enabled)
    val overriddenBackgroundColor = backgroundColor.mutate(enabled)
    val indication = if (isBright(overriddenBackgroundColor)) Theme[indications][bright] else Theme[indications][dim]

    UnstyledButton(
        onClick = onClick,
        shape = shape,
        borderColor = borderColor,
        borderWidth = borderWidth,
        backgroundColor = overriddenBackgroundColor,
        modifier = modifier.minimumInteractiveComponentSize()
            .focusRing(interactionSource, 2.dp, color = Theme[colors][focusRing], shape),
        enabled = enabled,
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        contentColor = overriddenContentColor,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        indication = indication,
    ) {
        ProvideTextStyle(Theme[textStyles][body].copy(fontWeight = FontWeight.Medium)) {
            content()
        }
    }
}
