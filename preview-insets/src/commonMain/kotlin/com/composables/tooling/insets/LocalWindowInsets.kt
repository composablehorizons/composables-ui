package com.composables.tooling.insets

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.jvm.JvmInline

@Composable
fun Modifier.previewStatusBarPadding(): Modifier {
    val windowInsets = LocalWindowInsets.current ?: return this.statusBarsPadding()

    return this then Modifier.padding(
        top = windowInsets.statusBarSize,
    )
}

@Composable
fun Modifier.previewNavigationBarPadding(): Modifier {
    val windowInsets = LocalWindowInsets.current ?: return this.navigationBarsPadding()
    val navigationBarSize = windowInsets.navigationBarSize
    val softKeyboardSize = windowInsets.softKeyboardSize
    val placeNavigationBarAtEnd = windowInsets.orientation == PreviewInsetsOrientation.Landscape
    val remainingBottomNavigationBarSize = if (softKeyboardSize >= navigationBarSize) {
        0.dp
    } else {
        navigationBarSize - softKeyboardSize
    }

    return this then Modifier.padding(
        bottom = if (placeNavigationBarAtEnd) 0.dp else remainingBottomNavigationBarSize,
        end = if (placeNavigationBarAtEnd) navigationBarSize else 0.dp,
    )
}

@Composable
fun Modifier.previewSoftKeyboardPadding(): Modifier {
    val windowInsets = LocalWindowInsets.current ?: return this.imePadding()

    return this then Modifier.padding(
        bottom = windowInsets.softKeyboardSize,
    )
}

@Composable
fun previewStatusBarPaddingValue(): Dp {
    return LocalWindowInsets.current?.statusBarSize ?: WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
}

@Composable
fun previewNavigationBarPaddingValue(): Dp {
    return LocalWindowInsets.current?.navigationBarSize ?: WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
}


val LocalWindowInsets = staticCompositionLocalOf<PreviewWindowInsets?> { null }

class PreviewWindowInsets(
    val statusBarSize: Dp = 0.dp,
    val navigationBarSize: Dp = 0.dp,
    val softKeyboardSize: Dp = 0.dp,
    val orientation: PreviewInsetsOrientation = PreviewInsetsOrientation.Portrait,
)

@JvmInline
value class PreviewInsetsOrientation internal constructor(private val value: Int) {
    companion object {
        val Portrait = PreviewInsetsOrientation(0)
        val Landscape = PreviewInsetsOrientation(1)
    }
}

@Composable
fun ProvidePreviewWindowInsets(
    windowInsets: PreviewWindowInsets,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(LocalWindowInsets provides windowInsets) {
        content()
    }
}
