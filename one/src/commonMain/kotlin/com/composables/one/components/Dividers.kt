package com.composables.one.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composables.core.HorizontalSeparator
import com.composables.core.Separator
import com.composables.core.VerticalSeparator
import com.composables.one.Sample
import com.composables.one.styling.colors
import com.composables.one.styling.outline
import com.composeunstyled.theme.Theme

@Sample("DividerExample")
@Composable
fun ColumnScope.Divider(modifier: Modifier = Modifier, color: Color = Theme[colors][outline], thickness: Dp = 1.dp) {
    Separator(color = color, thickness = thickness)
}

@Composable
fun RowScope.Divider(modifier: Modifier = Modifier, color: Color = Theme[colors][outline], thickness: Dp = 1.dp) {
    Separator(color = color, thickness = thickness)
}

@Sample("HorizontalDividerExample")
@Composable
fun HorizontalDivider(modifier: Modifier = Modifier, color: Color = Theme[colors][outline], thickness: Dp = 1.dp) {
    HorizontalSeparator(color = color, thickness = thickness)
}

@Sample("VerticalDividerExample")
@Composable
fun VerticalDivider(modifier: Modifier = Modifier, color: Color = Theme[colors][outline], thickness: Dp = 1.dp) {
    VerticalSeparator(color = color, thickness = thickness)
}