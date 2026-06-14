package com.composables.ui.demo.examples

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.composables.ui.components.Text

@Composable
fun StyledTextExample() {
    Text(
        text = "Styled headline",
        fontSize = 24.sp,
        lineHeight = 32.sp,
        fontWeight = FontWeight.SemiBold,
    )
}
