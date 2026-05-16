package com.composables.one.scaffolds

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.composables.one.Sample
import com.composables.one.styling.background
import com.composables.one.styling.colors
import com.composables.one.styling.onBackground
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.currentWindowContainerSize
import com.composeunstyled.theme.Theme

@Sample("ScreenScaffoldExample")
@Composable
fun ScreenScaffold(
    modifier: Modifier = Modifier,
    appBar: (@Composable () -> Unit)? = null,
    sideBar: (@Composable () -> Unit)? = null,
    bottomBar: (@Composable () -> Unit)? = null,
    backgroundColor: Color = Theme[colors][background],
    contentColor: Color = Theme[colors][onBackground],
    content: @Composable BoxScope.() -> Unit,
) {
    val isWide = currentWindowContainerSize().width > 600.dp
    Row(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        ProvideContentColor(contentColor) {
            if (sideBar != null && isWide) {
                sideBar()
            }
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal).asPaddingValues())
                    .consumeWindowInsets(WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal)),
            ) {
                if (appBar != null) {
                    appBar()
                }
                content()
                if (isWide.not() && bottomBar != null) {
                    Box(Modifier.align(Alignment.BottomCenter)) {
                        bottomBar()
                    }
                }
            }
        }
    }
}
