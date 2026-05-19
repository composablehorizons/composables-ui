package com.composables.one.demo.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.KeyRound
import com.composables.icons.lucide.Lock
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Search
import com.composables.icons.lucide.X
import com.composables.one.ButtonSize
import com.composables.one.ButtonStyle
import com.composables.one.Icon
import com.composables.one.IconButton
import com.composables.one.Text
import com.composables.one.TextField
import com.composables.one.styling.body
import com.composables.one.styling.colors
import com.composables.one.styling.muted
import com.composables.one.styling.textStyles
import com.composeunstyled.theme.Theme

@Composable
fun TextFieldsExample() {
    TextFieldPreviewContainer(
        modifier = Modifier.verticalScroll(rememberScrollState()),
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            DefaultTextFieldExample()
            SearchTextFieldExample()
            MultilineTextFieldExample()
            DisabledTextFieldExample()
            ReadOnlyTextFieldExample()
        }
    }
}

@Composable
fun DefaultTextFieldExample() {
    val state = rememberTextFieldState()

    TextFieldPreviewContainer {
        TextFieldStack(
            label = "Email",
            helperText = "Used for product updates and billing receipts.",
        ) {
            TextField(
                state = state,
                modifier = Modifier.fillMaxWidth(),
                accessibilityLabel = "Email",
                placeholder = { Text("name@example.com") },
            )
        }
    }
}

@Composable
fun SearchTextFieldExample() {
    val state = rememberTextFieldState()

    TextFieldPreviewContainer {
        TextFieldStack(label = "Search") {
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

@Composable
fun MultilineTextFieldExample() {
    val state = rememberTextFieldState()

    TextFieldPreviewContainer {
        TextFieldStack(label = "Feedback") {
            TextField(
                state = state,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 112.dp),
                accessibilityLabel = "Feedback",
                placeholder = { Text("Tell us what could be better...") },
                contentPadding = PaddingValues(12.dp),
                minHeight = 112.dp,
                lineLimits = TextFieldLineLimits.MultiLine(
                    minHeightInLines = 4,
                    maxHeightInLines = 6,
                ),
            )
        }
    }
}

@Composable
fun DisabledTextFieldExample() {
    val state = rememberTextFieldState("alex@example.com")

    TextFieldPreviewContainer {
        TextFieldStack(
            label = "Organization email",
            helperText = "Managed by your organization.",
        ) {
            TextField(
                state = state,
                modifier = Modifier.fillMaxWidth(),
                enabled = false,
                accessibilityLabel = "Organization email",
                leading = {
                    Icon(
                        imageVector = Lucide.Lock,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Theme[colors][muted],
                    )
                },
            )
        }
    }
}

@Composable
fun ReadOnlyTextFieldExample() {
    val state = rememberTextFieldState("proj_1K9xF4vQm2")

    TextFieldPreviewContainer {
        TextFieldStack(
            label = "Project ID",
            helperText = "Select and copy this generated ID.",
        ) {
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
        }
    }
}

@Composable
private fun TextFieldPreviewContainer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier.widthIn(min = 320.dp, max = 360.dp),
    ) {
        content()
    }
}

@Composable
private fun TextFieldStack(
    label: String,
    helperText: String? = null,
    content: @Composable () -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(label)
        content()
        if (helperText != null) {
            Text(
                text = helperText,
                color = Theme[colors][muted],
                style = Theme[textStyles][body],
            )
        }
    }
}
