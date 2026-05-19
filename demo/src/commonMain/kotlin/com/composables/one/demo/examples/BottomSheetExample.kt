package com.composables.one.demo.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.AtSign
import com.composables.icons.lucide.Bell
import com.composables.icons.lucide.Copy
import com.composables.icons.lucide.Pencil
import com.composables.icons.lucide.Share
import com.composables.icons.lucide.Trash2
import com.composables.icons.lucide.User
import com.composables.icons.lucide.Lucide
import com.composables.one.BottomSheet
import com.composables.one.Button
import com.composables.one.ButtonStyle
import com.composables.one.Icon
import com.composables.one.Text
import com.composables.one.TextField
import com.composables.one.rememberBottomSheetState
import com.composables.one.styling.body
import com.composables.one.styling.colors
import com.composables.one.styling.destructive
import com.composables.one.styling.muted
import com.composables.one.styling.textStyles
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.ProvideTextStyle
import com.composeunstyled.theme.Theme
import com.composeunstyled.SheetDetent

@Composable
fun BottomSheetActionMenuExample() {
    val sheetState = rememberBottomSheetState(
        detents = listOf(SheetDetent.Hidden, SheetDetent.FullyExpanded),
    )

    Button(onClick = { sheetState.targetDetent = SheetDetent.FullyExpanded }) {
        Text("Open actions")
    }

    BottomSheet(
        state = sheetState,
        onDismissRequest = { sheetState.targetDetent = SheetDetent.Hidden },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            BottomSheetAction(
                icon = Lucide.Pencil,
                text = "Rename",
                onClick = { sheetState.targetDetent = SheetDetent.Hidden },
            )
            BottomSheetAction(
                icon = Lucide.Copy,
                text = "Duplicate",
                onClick = { sheetState.targetDetent = SheetDetent.Hidden },
            )
            BottomSheetAction(
                icon = Lucide.Share,
                text = "Share",
                onClick = { sheetState.targetDetent = SheetDetent.Hidden },
            )
            BottomSheetAction(
                icon = Lucide.Trash2,
                text = "Delete",
                contentColor = Theme[colors][destructive],
                onClick = { sheetState.targetDetent = SheetDetent.Hidden },
            )
        }
    }
}

@Composable
fun BottomSheetConfirmationExample() {
    val sheetState = rememberBottomSheetState(
        detents = listOf(SheetDetent.Hidden, SheetDetent.FullyExpanded),
    )

    Button(onClick = { sheetState.targetDetent = SheetDetent.FullyExpanded }) {
        Text("Show confirmation")
    }

    BottomSheet(
        state = sheetState,
        onDismissRequest = { sheetState.targetDetent = SheetDetent.Hidden },
        header = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Icon(
                    imageVector = Lucide.Bell,
                    contentDescription = null,
                    modifier = Modifier.size(28.dp),
                )
                Text("Allow notifications?")
            }
        },
        footer = {
            Button(
                onClick = { sheetState.targetDetent = SheetDetent.Hidden },
                modifier = Modifier.fillMaxWidth(),
                style = ButtonStyle.Primary,
            ) {
                Text("Allow")
            }
            Button(
                onClick = { sheetState.targetDetent = SheetDetent.Hidden },
                modifier = Modifier.fillMaxWidth(),
                style = ButtonStyle.Secondary,
            ) {
                Text("Maybe later")
            }
        },
    ) {
        BottomSheetBody("Get notified when projects finish syncing, comments mention you, or billing needs attention.")
    }
}

@Composable
fun BottomSheetFormExample() {
    val nameState = rememberTextFieldState("Alex Styl")
    val usernameState = rememberTextFieldState("alexstyl")
    val sheetState = rememberBottomSheetState(
        detents = listOf(SheetDetent.Hidden, SheetDetent.FullyExpanded),
    )

    Button(onClick = { sheetState.targetDetent = SheetDetent.FullyExpanded }) {
        Text("Edit name")
    }

    BottomSheet(
        state = sheetState,
        onDismissRequest = { sheetState.targetDetent = SheetDetent.Hidden },
        header = { Text("Edit name") },
        footer = {
            Button(
                onClick = { sheetState.targetDetent = SheetDetent.Hidden },
                modifier = Modifier.fillMaxWidth(),
                style = ButtonStyle.Primary,
            ) {
                Text("Save")
            }
        },
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            BottomSheetTextFieldStack(label = "Name") {
                TextField(
                    state = nameState,
                    modifier = Modifier.fillMaxWidth(),
                    accessibilityLabel = "Name",
                    leading = {
                        Icon(
                            imageVector = Lucide.User,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = Theme[colors][muted],
                        )
                    },
                )
            }
            BottomSheetTextFieldStack(label = "Username") {
                TextField(
                    state = usernameState,
                    modifier = Modifier.fillMaxWidth(),
                    accessibilityLabel = "Username",
                    leading = {
                        Icon(
                            imageVector = Lucide.AtSign,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = Theme[colors][muted],
                        )
                    },
                )
            }
        }
    }
}

@Composable
private fun BottomSheetAction(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit,
    style: ButtonStyle = ButtonStyle.Ghost,
    contentColor: Color? = null,
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        style = style,
        contentPadding = PaddingValues(0.dp),
    ) {
        val content = @Composable {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                )
                Text(text)
            }
        }
        if (contentColor != null) {
            ProvideContentColor(contentColor) {
                content()
            }
        } else {
            content()
        }
    }
}

@Composable
private fun BottomSheetTextFieldStack(
    label: String,
    content: @Composable () -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(label)
        content()
    }
}

@Composable
private fun BottomSheetBody(text: String) {
    ProvideContentColor(Theme[colors][muted]) {
        ProvideTextStyle(Theme[textStyles][body]) {
            Text(
                text = text,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
        }
    }
}
