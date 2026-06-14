package com.composables.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composables.ui.theme.alphas
import com.composables.ui.theme.colors
import com.composables.ui.theme.disabledAlpha
import com.composables.ui.theme.ringColor
import com.composables.ui.theme.onPanelColor
import com.composables.ui.theme.switchSelectedTrackColor
import com.composables.ui.theme.switchThumbColor
import com.composables.ui.theme.switchTrackColor
import com.composeunstyled.FocusRingVisibility
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.Thumb
import com.composeunstyled.Track
import com.composeunstyled.UnstyledSwitch
import com.composeunstyled.buildModifier
import com.composeunstyled.theme.Theme

/**
 * A binary switch for settings that change immediately.
 * @param checked Whether the switch is currently on.
 * @param onCheckedChange Called when the checked state changes.
 * @param modifier Modifier applied to the switch row.
 * @param enabled Whether the switch can be interacted with.
 * @param interactionSource Interaction source used for focus and press state.
 * @param content Optional label or supporting content displayed next to the switch.
 */
@Composable
fun Switch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    accessibilityLabel: String? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: (@Composable RowScope.() -> Unit)? = null,
) {
    val trackShape = RoundedCornerShape(999.dp)
    val trackColor by animateColorAsState(
        if (checked) Theme[colors][switchSelectedTrackColor] else Theme[colors][switchTrackColor]
    )

    UnstyledSwitch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        enabled = enabled,
        interactionSource = interactionSource,
        modifier = modifier
            .then(buildModifier {
                accessibilityLabel?.let { label ->
                    add(Modifier.semantics { contentDescription = label })
                }
            })
            .alpha(if (enabled) 1f else Theme[alphas][disabledAlpha]),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Track(
                modifier = Modifier
                    .focusRing(
                        interactionSource = interactionSource,
                        color = Theme[colors][ringColor],
                        shape = trackShape,
                        visibility = FocusRingVisibility.Focused,
                    )
                    .background(trackColor, trackShape)
                    .border(Dp.Hairline, Theme[colors][ringColor], trackShape)
                    .size(width = 44.dp, height = 24.dp)
                    .padding(2.dp),
            ) {
                Thumb(animationSpec = spring()) {
                    Box(
                        Modifier
                            .size(20.dp)
                            .background(Theme[colors][switchThumbColor], CircleShape)
                            .border(Dp.Hairline, Theme[colors][ringColor], CircleShape),
                    )
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
