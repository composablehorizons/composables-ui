package com.composables.one.demo.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.one.Text
import com.composables.one.styling.body
import com.composables.one.styling.buttonLabel
import com.composables.one.styling.header
import com.composables.one.styling.textStyles
import com.composables.one.styling.title
import com.composeunstyled.theme.Theme

@Composable
fun TypographyExample() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = "Header",
            style = Theme[textStyles][header],
        )
        Text(
            text = "Title",
            style = Theme[textStyles][title],
        )
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
