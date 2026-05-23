package com.composables.ui.demo.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.ui.components.IndeterminateProgressIndicator
import com.composables.ui.components.ProgressIndicator

@Composable
fun ProgressIndicatorExample() {
    Column(
        modifier = Modifier.widthIn(max = 320.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        ProgressIndicator(progress = 0.64f, modifier = Modifier.fillMaxWidth())
        IndeterminateProgressIndicator(modifier = Modifier.fillMaxWidth())
    }
}
