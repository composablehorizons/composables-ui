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
import com.composables.one.styling.dim
import com.composables.one.styling.focusRing
import com.composables.one.styling.indications
import com.composables.one.styling.isBright
import com.composables.one.styling.mutate
import com.composables.one.styling.shapes
import com.composables.one.styling.small
import com.composables.one.styling.textStyles
import com.composeunstyled.LocalContentColor
import com.composeunstyled.ProvideTextStyle
import com.composeunstyled.focusRing
import com.composeunstyled.minimumInteractiveComponentSize
import com.composeunstyled.theme.Theme
import com.composeunstyled.UnstyledButton

@Sample("GhostButtonExample")
@Composable
fun GhostButton(
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    shape: Shape = Theme[shapes][small],
    contentColor: Color = LocalContentColor.current,
    backgroundColor: Color = Color.Transparent,
    borderColor: Color = Color.Transparent,
    borderWidth: Dp = 1.dp,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    contentPadding: PaddingValues = DefaultButtonPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit,
) {
    val overriddenContentColor = contentColor.mutate(enabled)
    val overriddenBackgroundColor = backgroundColor.mutate(enabled)
    val borderColor = when {
        borderColor.isUnspecified || borderColor == Color.Transparent -> Color.Transparent
        enabled -> borderColor
        else -> borderColor.copy(alpha = 0.50f)
    }
    val indication = if (isBright(overriddenBackgroundColor)) Theme[indications][bright] else Theme[indications][dim]

    UnstyledButton(
        onClick = onClick,
        shape = shape,
        backgroundColor = overriddenBackgroundColor,
        modifier = modifier.minimumInteractiveComponentSize()
            .focusRing(interactionSource, 2.dp, color = Theme[colors][focusRing], shape),
        enabled = enabled,
        borderColor = borderColor,
        horizontalArrangement = horizontalArrangement,
        borderWidth = borderWidth,
        contentColor = overriddenContentColor,
        contentPadding = contentPadding,
        verticalAlignment = verticalAlignment,
        interactionSource = interactionSource,
        indication = indication,
    ) {
        ProvideTextStyle(Theme[textStyles][body].copy(fontWeight = FontWeight.Medium)) {
            content()
        }
    }
}

