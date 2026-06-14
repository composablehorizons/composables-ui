package com.composables.ui.demo.examples

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.composables.ui.components.Text
import com.composeunstyled.ProvideTextStyle

@Composable
fun ThemedTextExample() {
    ProvideTextStyle(
        TextStyle(
            fontSize = 18.sp,
            lineHeight = 28.sp,
            fontWeight = FontWeight.Medium,
        ),
    ) {
        Column {
            Text("This text is themed")
            Text("And so is this one")
        }
    }
}
