package com.composables.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.composables.ui.theme.AppTheme
import com.composables.ui.theme.background
import com.composables.ui.theme.colors
import com.composables.ui.theme.onBackground
import com.composeunstyled.FocusVisibilityProvider
import com.composeunstyled.ProvideBreakpoints
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.TooltipHost
import com.composeunstyled.theme.Theme

@Composable
fun AppScaffold(content: @Composable () -> Unit) {
    AppTheme {
        ProvideBreakpoints(
            widthBreakpoints = WidthBreakpoints,
            heightBreakpoints = HeightBreakpoints,
        ) {
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
