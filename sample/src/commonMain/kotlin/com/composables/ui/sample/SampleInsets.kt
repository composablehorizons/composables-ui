package com.composables.ui.sample

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composables.tooling.insets.previewNavigationBarPaddingValue
import com.composables.tooling.insets.previewStatusBarPaddingValue
import com.composeunstyled.currentWindowWidthBreakpoint

@Composable
fun sampleScreenContentPadding(
    extraTop: Dp = 0.dp,
    extraBottom: Dp = 0.dp,
): PaddingValues {
    val widthBreakpoint = currentWindowWidthBreakpoint()
    val statusBarPadding = previewStatusBarPaddingValue()
    val navigationBarPadding = previewNavigationBarPaddingValue()
    val useMobileChrome = !(widthBreakpoint isAtLeast Medium)

    return PaddingValues(
        top = extraTop + if (useMobileChrome) statusBarPadding else 0.dp,
        bottom = extraBottom + if (useMobileChrome) {
            SampleMobileNavigationBarHeight + navigationBarPadding
        } else {
            0.dp
        },
    )
}

val SampleMobileNavigationBarHeight = 64.dp
