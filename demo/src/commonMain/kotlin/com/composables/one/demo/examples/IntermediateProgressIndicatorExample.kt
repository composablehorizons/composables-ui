package com.composables.one.demo.examples

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.one.IntermediateProgressIndicator
import com.composables.one.Text

@Composable
fun IntermediateProgressIndicatorExample() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        IntermediateProgressIndicator(Modifier.size(48.dp))
        Spacer(Modifier.height(32.dp))
        Text("Booking your reservation...")
    }
}
