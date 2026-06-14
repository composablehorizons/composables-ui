package com.composables.ui.demo.examples

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.composables.ui.components.IndeterminateProgressIndicator

@Composable
fun IndeterminateProgressIndicatorExample() {
    IndeterminateProgressIndicator(modifier = Modifier.fillMaxWidth())
}
