package com.composables.ui.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.composables.ui.theme.background
import com.composables.ui.theme.colors
import com.composables.ui.theme.onBackground
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.theme.Theme

val ScreenContentMaxWidth = 700.dp

@Composable
fun ScreenScaffold(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Theme[colors][background],
    contentColor: Color = Theme[colors][onBackground],
    toolbar: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    ProvideContentColor(contentColor) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(backgroundColor),
        ) {
            content()
            if (toolbar != null) {
                Box(
                    modifier = Modifier
                        .widthIn(max = ScreenContentMaxWidth)
                        .fillMaxWidth()
                        .align(Alignment.TopCenter),
                ) {
                    toolbar()
                }
            }
        }
    }
}
