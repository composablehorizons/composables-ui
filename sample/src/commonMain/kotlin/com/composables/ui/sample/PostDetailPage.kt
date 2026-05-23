package com.composables.ui.sample

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.composables.ui.components.ScreenScaffold
import com.composables.ui.theme.background
import com.composables.ui.theme.colors
import com.composables.ui.theme.onBackground
import com.composeunstyled.theme.Theme

@Composable
internal fun PostDetailPage() {
    ScreenScaffold(backgroundColor = Theme[colors][background], contentColor = Theme[colors][onBackground]) {
        Box(Modifier.fillMaxSize())
    }
}
