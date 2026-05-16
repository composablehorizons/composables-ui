package com.composables.one

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.sensitiveContent
import androidx.compose.ui.unit.dp
import com.composables.one.Sample
import com.composables.one.styling.card
import com.composables.one.styling.colors
import com.composables.one.styling.onCard
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.theme.Theme

@Sample("SignInExample")
@Sample("SignUpExample")
@Composable
fun AuthScreenScaffold(
    contentPadding: PaddingValues = PaddingValues(0.dp),
    content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme[colors][card])
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 40.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        ProvideContentColor(Theme[colors][onCard]) {
            Column(Modifier.widthIn(max = 400.dp).padding(contentPadding)) {
                Column(
                    modifier = Modifier.sensitiveContent(),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    content()
                }
            }
        }
    }
}
