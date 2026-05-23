package com.composables.ui.demo.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Search
import com.composables.icons.lucide.X
import com.composables.ui.components.ButtonSize
import com.composables.ui.components.ButtonStyle
import com.composables.ui.components.Icon
import com.composables.ui.components.IconButton
import com.composables.ui.components.Text
import com.composables.ui.components.TextField
import com.composables.ui.theme.colors
import com.composables.ui.theme.muted
import com.composeunstyled.theme.Theme

@Composable
fun SearchTextFieldExample() {
    val state = rememberTextFieldState()

    Column(modifier = Modifier.widthIn(min = 320.dp, max = 360.dp)) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Search")
            TextField(
                state = state,
                modifier = Modifier.fillMaxWidth(),
                accessibilityLabel = "Search",
                placeholder = { Text("Search...") },
                leading = {
                    Icon(
                        imageVector = Lucide.Search,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Theme[colors][muted],
                    )
                },
                trailing = {
                    if (state.text.isNotEmpty()) {
                        IconButton(
                            onClick = { state.clearText() },
                            modifier = Modifier.size(32.dp),
                            style = ButtonStyle.Ghost,
                            buttonSize = ButtonSize.Small,
                        ) {
                            Icon(
                                imageVector = Lucide.X,
                                contentDescription = "Clear search",
                                modifier = Modifier.size(14.dp),
                                tint = Theme[colors][muted],
                            )
                        }
                    }
                },
            )
        }
    }
}
