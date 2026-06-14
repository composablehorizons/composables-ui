package com.composables.ui.components

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.composables.ui.theme.alphas
import com.composables.ui.theme.borderColor
import com.composables.ui.theme.defaultIndication
import com.composables.ui.theme.colors
import com.composables.ui.theme.controlColor
import com.composables.ui.theme.inverseIndication
import com.composables.ui.theme.disabledAlpha
import com.composables.ui.theme.ringColor
import com.composables.ui.theme.indications
import com.composables.ui.theme.onPanelColor
import com.composables.ui.theme.onPrimaryColor
import com.composables.ui.theme.primaryColor
import com.composeunstyled.CheckedIndicator
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.UnstyledCheckbox
import com.composeunstyled.buildModifier
import com.composeunstyled.theme.Theme

/**
 * A checkbox for independent binary selections.
 *
 * @param checked Whether the checkbox is currently checked.
 * @param onCheckedChange Called when the checked state changes.
 * @param modifier Modifier applied to the checkbox row.
 * @param enabled Whether the checkbox can be interacted with.
 * @param accessibilityLabel Accessible label announced for the checkbox.
 * @param interactionSource Interaction source used for focus and press state.
 * @param content Optional label or supporting content displayed next to the checkbox.
 */
@Composable
fun Checkbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    accessibilityLabel: String? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: (@Composable RowScope.() -> Unit)? = null,
) {
    val backgroundColor = if (checked) Theme[colors][primaryColor] else Theme[colors][controlColor]
    val contentColor = if (checked) Theme[colors][onPrimaryColor] else Color.Transparent
    val borderColor = if (checked) Theme[colors][primaryColor] else Theme[colors][borderColor]
    val activeIndication = if (checked) Theme[indications][inverseIndication] else Theme[indications][defaultIndication]
    UnstyledCheckbox(
        checked = checked,
        onCheckedChange = onCheckedChange,
        enabled = enabled,
        accessibilityLabel = accessibilityLabel,
        interactionSource = interactionSource,
        indication = null,
        modifier = modifier.then(buildModifier { if (!enabled) add(Modifier.alpha(Theme[alphas][disabledAlpha])) }),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CheckedIndicator(
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier
                    .focusRing(
                        interactionSource = interactionSource,
                        color = Theme[colors][ringColor],
                        shape = RoundedCornerShape(5.dp),
                    )
                    .clip(RoundedCornerShape(5.dp))
                    .background(backgroundColor, RoundedCornerShape(5.dp))
                    .border(1.dp, borderColor, RoundedCornerShape(5.dp))
                    .size(20.dp),
                indication = activeIndication
            ) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CheckMark(contentColor)
                }
            }

            if (content != null) {
                ProvideContentColor(Theme[colors][onPanelColor]) {
                    content()
                }
            }
        }
    }
}

@Composable
private fun CheckMark(color: Color) {
    Canvas(Modifier.size(14.dp)) {
        val strokeWidth = 2.dp.toPx()
        drawLine(
            color,
            Offset(size.width * 0.2f, size.height * 0.52f),
            Offset(size.width * 0.42f, size.height * 0.74f),
            strokeWidth,
            cap = StrokeCap.Round
        )
        drawLine(
            color,
            Offset(size.width * 0.42f, size.height * 0.74f),
            Offset(size.width * 0.8f, size.height * 0.28f),
            strokeWidth,
            cap = StrokeCap.Round
        )
    }
}
