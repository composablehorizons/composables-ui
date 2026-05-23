package com.composables.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.composables.ui.theme.AppTheme
import com.composables.ui.theme.background
import com.composables.ui.theme.colors
import com.composables.ui.theme.onBackground
import com.composeunstyled.FocusVisibilityProvider
import com.composeunstyled.LocalTextStyle
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.ProvideTextStyle
import com.composeunstyled.theme.Theme

@Composable
fun AppScaffold(content: @Composable () -> Unit) {
    AppTheme {
        FocusVisibilityProvider {
            ProvideContentColor(Theme[colors][onBackground]) {
                ProvideTextStyle(LocalTextStyle.current.merge(AppScaffoldTextStyle)) {
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

private val AppScaffoldTextStyle = TextStyle(
    fontSize = 16.sp,
    lineHeight = 24.sp,
)
