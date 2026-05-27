package com.composables.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.ui.theme.AppTheme
import com.composables.ui.theme.background
import com.composables.ui.theme.colors
import com.composables.ui.theme.onBackground
import com.composeunstyled.Breakpoint
import com.composeunstyled.FocusVisibilityProvider
import com.composeunstyled.ProvideBreakpoints
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.ScreenBreakpoints
import com.composeunstyled.TooltipHost
import com.composeunstyled.theme.Theme

val Medium = Breakpoint("medium")
val Expanded = Breakpoint("expanded")
val Large = Breakpoint("large")
val ExtraLarge = Breakpoint("extraLarge")

val WidthBreakpoints = ScreenBreakpoints {
    Medium at 600.dp
    Expanded at 840.dp
    Large at 1200.dp
    ExtraLarge at 1600.dp
}

@Composable
fun AppScaffold(content: @Composable () -> Unit) {
    AppTheme {
        ProvideBreakpoints(widthBreakpoints = WidthBreakpoints) {
            TooltipHost {
                FocusVisibilityProvider {
                    ProvideContentColor(Theme[colors][onBackground]) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Theme[colors][background]),
                        ) {
                            content()
                        }
                    }
                }
            }
        }
    }
}
