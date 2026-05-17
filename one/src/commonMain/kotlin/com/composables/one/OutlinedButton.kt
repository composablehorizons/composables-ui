package com.composables.one

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composables.one.styling.border
import com.composables.one.styling.colors
import com.composables.one.styling.onBackground
import com.composables.one.styling.shapes
import com.composables.one.styling.buttonShape
import com.composeunstyled.theme.Theme

@Composable
fun OutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentColor: Color = Theme[colors][onBackground],
    shape: Shape = Theme[shapes][buttonShape],
    buttonSize: ButtonSize = ButtonSize.Default,
    contentPadding: PaddingValues = buttonPaddingFor(buttonSize),
    borderColor: Color = Theme[colors][border],
    borderWidth: Dp = 1.dp,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit,
) {
    ButtonSkeleton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        backgroundColor = Color.Transparent,
        contentColor = contentColor,
        shape = shape,
        buttonSize = buttonSize,
        contentPadding = contentPadding,
        borderColor = borderColor,
        borderWidth = borderWidth,
        interactionSource = interactionSource,
        content = content,
    )
}
