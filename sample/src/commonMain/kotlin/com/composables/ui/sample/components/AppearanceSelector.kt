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
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Moon
import com.composables.icons.lucide.Sun
import com.composables.ui.components.Button
import com.composables.ui.components.ButtonStyle
import com.composables.ui.components.Icon
import com.composables.ui.components.Text
import com.composables.ui.theme.ColorScheme
import com.composables.ui.theme.colors
import com.composables.ui.theme.control
import com.composables.ui.theme.onControl
import com.composables.ui.theme.onSelectedControl
import com.composables.ui.theme.selectedControl
import com.composeunstyled.buildModifier
import com.composeunstyled.theme.Theme

@Composable
fun AppearanceSelector(
    selectedColorScheme: ColorScheme,
    onSelectedColorSchemeChange: (ColorScheme) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(Theme[colors][control])
            .padding(3.dp),
    ) {
        AppearanceSegment(
            selected = selectedColorScheme == ColorScheme.Light,
            onClick = { onSelectedColorSchemeChange(ColorScheme.Light) },
        ) {
            Icon(
                imageVector = Lucide.Sun,
                contentDescription = "Light appearance",
                tint = it,
            )
        }
        AppearanceSegment(
            selected = selectedColorScheme == ColorScheme.Dark,
            onClick = { onSelectedColorSchemeChange(ColorScheme.Dark) },
        ) {
            Icon(
                imageVector = Lucide.Moon,
                contentDescription = "Dark appearance",
                tint = it,
            )
        }
        AppearanceSegment(
            selected = selectedColorScheme == ColorScheme.System,
            onClick = { onSelectedColorSchemeChange(ColorScheme.System) },
        ) {
            Text("Auto", color = it)
        }
    }
}

@Composable
private fun RowScope.AppearanceSegment(
    selected: Boolean,
    onClick: () -> Unit,
    content: @Composable (androidx.compose.ui.graphics.Color) -> Unit,
) {
    val shape = RoundedCornerShape(12.dp)
    val contentColor = if (selected) Theme[colors][onSelectedControl] else Theme[colors][onControl]

    Button(
        onClick = onClick,
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .then(buildModifier {
                if (selected) {
                    add(Modifier.background(Theme[colors][selectedControl], shape))
                }
            }),
        style = ButtonStyle.Ghost,
        shape = shape,
        contentPadding = PaddingValues(0.dp),
    ) {
        content(contentColor)
    }
}
