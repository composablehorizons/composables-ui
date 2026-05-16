package com.composables.one.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.composables.one.Sample
import com.composables.one.styling.accent
import com.composables.one.styling.colors
import com.composables.one.styling.focusRing
import com.composables.one.styling.medium
import com.composables.one.styling.mutate
import com.composables.one.styling.onAccent
import com.composables.one.styling.outline
import com.composables.one.styling.primary
import com.composables.one.styling.shadows
import com.composables.one.styling.shapes
import com.composables.one.styling.subtle
import com.composeunstyled.focusRing
import com.composeunstyled.outline
import com.composeunstyled.theme.Theme
import com.composeunstyled.SwitchThumb
import com.composeunstyled.UnstyledSwitch

@Sample("ToggleSwitchExample")
@Composable
fun ToggleSwitch(
    toggled: Boolean,
    onToggled: ((Boolean) -> Unit)? = null,
    modifier: Modifier = Modifier,
    activatedBackgroundColor: Color = Theme[colors][accent],
    backgroundColor: Color = Color.Black.copy(alpha = 0.1f),
    shape: Shape = RoundedCornerShape(100),
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val toggleBackgroundColor by animateColorAsState(
        targetValue = if (toggled) activatedBackgroundColor.mutate(enabled) else backgroundColor
    )

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val thumbSize = 24.dp
        UnstyledSwitch(
            enabled = enabled,
            checked = toggled,
            onCheckedChange = onToggled,
            interactionSource = interactionSource,
            modifier = Modifier.width(thumbSize * 2)
                .background(toggleBackgroundColor, shape)
                .padding(2.dp)
                .focusRing(interactionSource, 2.dp, color = Theme[colors][focusRing], shape)
                .outline(1.dp, Theme[colors][outline], shape),
        ) {
            SwitchThumb {
                Box(
                    Modifier
                        .size(thumbSize)
                        .dropShadow(shape = CircleShape, shadow = Theme[shadows][subtle])
                        .background(Theme[colors][onAccent], CircleShape),
                )
            }
        }
    }
}

@Composable
fun ToggleSwitch(
    toggled: Boolean,
    onToggled: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    toggleActivatedColor: Color = Theme[colors][primary],
    toggleBackgroundColor: Color = Color.Black.copy(alpha = 0.1f),
    enabled: Boolean = true,
    shape: Shape = Theme[shapes][medium],
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    contentPadding: PaddingValues = PaddingValues(0.dp),
    label: (@Composable () -> Unit),
) {
    Row(
        modifier = modifier
            .clip(shape)
            .toggleable(
                value = toggled,
                interactionSource = interactionSource,
                enabled = enabled,
                role = Role.Switch,
                onValueChange = onToggled,
                indication = LocalIndication.current
            )
            .padding(contentPadding)
            // additional padding so that the toggles' outline is not clipped
            .padding(1.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        label()
        ToggleSwitch(
            interactionSource = interactionSource,
            enabled = enabled,
            toggled = toggled,
            backgroundColor = if (toggled) toggleActivatedColor.mutate(enabled) else toggleBackgroundColor,
        )
    }
}
