package com.composables.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.composables.ui.styling.AppTheme
import com.composables.ui.styling.background
import com.composables.ui.styling.body
import com.composables.ui.styling.colors
import com.composables.ui.styling.onBackground
import com.composables.ui.styling.textStyles
import com.composeunstyled.FocusVisibilityProvider
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.ProvideTextStyle
import com.composeunstyled.theme.Theme

@Composable
fun AppScaffold(content: @Composable () -> Unit) {
    AppTheme {
        FocusVisibilityProvider {
            ProvideContentColor(Theme[colors][onBackground]) {
                ProvideTextStyle(Theme[textStyles][body]) {
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
