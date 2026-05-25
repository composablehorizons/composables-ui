package com.composables.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.composables.ui.theme.background
import com.composables.ui.theme.colors
import com.composables.ui.theme.onBackground
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.theme.Theme

@Composable
fun ScreenScaffold(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Theme[colors][background],
    contentColor: Color = Theme[colors][onBackground],
    content: @Composable () -> Unit,
) {
    ProvideContentColor(contentColor) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(backgroundColor),
        ) {
            content()
        }
    }
}
