package com.composables.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.composables.ui.theme.AppTheme
import com.composables.ui.theme.background
import com.composables.ui.theme.body
import com.composables.ui.theme.colors
import com.composables.ui.theme.onBackground
import com.composables.ui.theme.textStyles
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
