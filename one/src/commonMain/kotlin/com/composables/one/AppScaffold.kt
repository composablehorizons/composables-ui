package com.composables.one

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import com.composables.one.styling.OneTheme
import com.composables.one.styling.background
import com.composables.one.styling.body
import com.composables.one.styling.colors
import com.composables.one.styling.onBackground
import com.composables.one.styling.textStyles
import com.composeunstyled.FocusVisibilityProvider
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.ProvideTextStyle
import com.composeunstyled.theme.Theme

@Composable
fun AppScaffold(content: @Composable BoxScope.() -> Unit) {
    OneTheme {
        FocusVisibilityProvider {
            ProvideContentColor(Theme[colors][onBackground]) {
                ProvideTextStyle(Theme[textStyles][body]) {
                    Box(
                        modifier = androidx.compose.ui.Modifier
                            .fillMaxSize()
                            .background(Theme[colors][background]),
                        content = content,
                    )
                }
            }
        }
    }
}
