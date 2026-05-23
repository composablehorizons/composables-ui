package com.composables.ui.demo.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.icons.lucide.KeyRound
import com.composables.icons.lucide.Lucide
import com.composables.ui.components.Icon
import com.composables.ui.components.Text
import com.composables.ui.components.TextField
import com.composables.ui.theme.colors
import com.composables.ui.theme.muted
import com.composeunstyled.LocalTextStyle
import com.composeunstyled.theme.Theme

@Composable
fun ReadOnlyTextFieldExample() {
    val state = rememberTextFieldState("proj_1K9xF4vQm2")

    Column(modifier = Modifier.widthIn(min = 320.dp, max = 360.dp)) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Project ID")
            TextField(
                state = state,
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                accessibilityLabel = "Project ID",
                leading = {
                    Icon(
                        imageVector = Lucide.KeyRound,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Theme[colors][muted],
                    )
                },
            )
            Text(
                text = "Select and copy this generated ID.",
                color = Theme[colors][muted],
                style = LocalTextStyle.current.merge(TextStyle(fontSize = 16.sp, lineHeight = 24.sp)),
            )
        }
    }
}
