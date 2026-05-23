package com.composables.ui.demo.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.AtSign
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.User
import com.composables.ui.components.BottomSheet
import com.composables.ui.components.Button
import com.composables.ui.components.ButtonStyle
import com.composables.ui.components.Icon
import com.composables.ui.components.Text
import com.composables.ui.components.TextField
import com.composables.ui.components.rememberBottomSheetState
import com.composables.ui.theme.colors
import com.composables.ui.theme.muted
import com.composeunstyled.SheetDetent
import com.composeunstyled.theme.Theme

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
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Name")
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
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Username")
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
