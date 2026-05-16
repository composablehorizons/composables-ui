package com.composables.one.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.unit.Dp
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.UnstyledButton
import com.composeunstyled.outline

@Composable
internal fun OneButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape,
    backgroundColor: Color,
    contentColor: Color,
    contentPadding: PaddingValues,
    borderColor: Color = Color.Transparent,
    borderWidth: Dp,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Center,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    interactionSource: MutableInteractionSource,
    indication: androidx.compose.foundation.Indication?,
    content: @Composable RowScope.() -> Unit,
) {
    UnstyledButton(
        onClick = onClick,
        enabled = enabled,
        contentPadding = contentPadding,
        modifier = modifier
            .clip(shape)
            .background(backgroundColor, shape)
            .then(
                if (borderColor.isSpecified && borderColor != Color.Transparent && borderWidth > Dp.Hairline) {
                    Modifier.outline(borderWidth, borderColor, shape)
                } else {
                    Modifier
                }
            ),
        interactionSource = interactionSource,
        indication = indication,
    ) {
        ProvideContentColor(contentColor) {
            Row(
                horizontalArrangement = horizontalArrangement,
                verticalAlignment = verticalAlignment,
                content = content,
            )
        }
    }
}
