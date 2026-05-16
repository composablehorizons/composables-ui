package com.composables.one

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.composables.one.Sample
import com.composables.one.styling.background
import com.composables.one.styling.colors
import com.composables.one.styling.onBackground
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.theme.Theme

@Sample("AppScaffoldExample")
@Composable
fun AppScaffold(
    backgroundColor: Color = Theme[colors][background],
    contentColor: Color = Theme[colors][onBackground],
    contentPadding: PaddingValues = PaddingValues(0.dp),
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(Modifier.fillMaxSize().background(backgroundColor).padding(contentPadding)) {
        ProvideContentColor(contentColor) {
            content()
        }
    }
}