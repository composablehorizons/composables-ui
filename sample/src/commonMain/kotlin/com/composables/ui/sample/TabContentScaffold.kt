package com.composables.ui.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.composables.ui.sample.components.MobileToolbar
import com.composables.ui.theme.ColorScheme
import com.composables.ui.theme.colors
import com.composables.ui.theme.onPanel
import com.composables.ui.theme.panel
import com.composeunstyled.Breakpoint
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.currentWidthBreakpoint
import com.composeunstyled.theme.Theme

@Composable
fun TabContentScaffold(
    colorScheme: ColorScheme,
    onColorSchemeChange: (ColorScheme) -> Unit,
    content: @Composable () -> Unit
) {
    ProvideContentColor(Theme[colors][onPanel]) {
        Column(
            Modifier
                .fillMaxSize()
                .background(Theme[colors][panel])
        ) {
            val breakpoint = currentWidthBreakpoint()
            if (breakpoint isAt Breakpoint.Base) {
                MobileToolbar(
                    colorScheme = colorScheme,
                    onColorSchemeChange = onColorSchemeChange,
                )
            }
            content()
        }
    }

}
