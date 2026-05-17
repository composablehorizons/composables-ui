package com.composables.one.demo.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.one.Text
import com.composables.one.styling.body
import com.composables.one.styling.buttonLabel
import com.composables.one.styling.textStyles
import com.composeunstyled.theme.Theme

@Composable
fun TypographyExample() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = "Body",
            style = Theme[textStyles][body],
        )
        Text(
            text = "Button Label",
            style = Theme[textStyles][buttonLabel],
        )
    }
}
