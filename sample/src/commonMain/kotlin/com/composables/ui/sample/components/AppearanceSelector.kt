package com.composables.ui.sample.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.composables.ui.components.Button
import com.composables.ui.components.ButtonStyle
import com.composables.ui.components.Icon
import com.composables.ui.components.Text
import com.composables.ui.sample.iconography.Icons
import com.composables.ui.sample.iconography.Moon
import com.composables.ui.sample.iconography.Sun
import com.composables.ui.sample.Appearance
import com.composables.ui.theme.colors
import com.composables.ui.theme.controlColor
import com.composables.ui.theme.onControlColor
import com.composables.ui.theme.onPanelColor
import com.composables.ui.theme.panelColor
import com.composables.ui.theme.shapes
import com.composables.ui.theme.smallShape
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.buildModifier
import com.composeunstyled.theme.Theme

@Composable
fun AppearanceSelector(
    selectedAppearance: Appearance,
    onSelectedAppearanceChange: (Appearance) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(Theme[colors][controlColor])
            .padding(3.dp),
    ) {
        AppearanceSegment(
            selected = selectedAppearance == Appearance.Light,
            onClick = { onSelectedAppearanceChange(Appearance.Light) },
        ) {
            Icon(
                imageVector = Icons.Sun,
                contentDescription = "Light appearance",
            )
        }
        AppearanceSegment(
            selected = selectedAppearance == Appearance.Dark,
            onClick = { onSelectedAppearanceChange(Appearance.Dark) },
        ) {
            Icon(
                imageVector = Icons.Moon,
                contentDescription = "Dark appearance",
            )
        }
        AppearanceSegment(
            selected = selectedAppearance == Appearance.System,
            onClick = { onSelectedAppearanceChange(Appearance.System) },
        ) {
            Text("Auto")
        }
    }
}

@Composable
private fun RowScope.AppearanceSegment(
    selected: Boolean,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    val shape = Theme[shapes][smallShape]
    val contentColor = if (selected) Theme[colors][onPanelColor] else Theme[colors][onControlColor]

    Button(
        onClick = onClick,
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .then(buildModifier {
                if (selected) {
                    add(Modifier.background(Theme[colors][panelColor], shape))
                }
            }),
        style = ButtonStyle.Ghost,
        shape = shape,
        contentPadding = PaddingValues(0.dp),
    ) {
        ProvideContentColor(contentColor) {
            content()
        }
    }
}
