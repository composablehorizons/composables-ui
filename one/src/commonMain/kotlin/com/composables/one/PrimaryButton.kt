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
import com.composables.one.styling.buttonShape
import com.composables.one.styling.colors
import com.composables.one.styling.onPrimary
import com.composables.one.styling.primary
import com.composables.one.styling.shapes
import com.composeunstyled.theme.Theme

@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    backgroundColor: Color = Theme[colors][primary],
    contentColor: Color = Theme[colors][onPrimary],
    shape: Shape = Theme[shapes][buttonShape],
    buttonSize: ButtonSize = ButtonSize.Default,
    contentPadding: PaddingValues = buttonPaddingFor(buttonSize),
    borderColor: Color = Color.Unspecified,
    borderWidth: Dp = 1.dp,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit,
) {
    ButtonSkeleton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        backgroundColor = backgroundColor,
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
