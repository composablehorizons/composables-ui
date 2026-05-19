package com.composables.one.demo.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Copy
import com.composables.icons.lucide.Pencil
import com.composables.icons.lucide.Share
import com.composables.icons.lucide.Trash2
import com.composables.icons.lucide.Lucide
import com.composables.one.BottomSheet
import com.composables.one.Button
import com.composables.one.ButtonStyle
import com.composables.one.Icon
import com.composables.one.Text
import com.composables.one.rememberBottomSheetState
import com.composables.one.styling.colors
import com.composables.one.styling.destructive
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.theme.Theme
import com.composeunstyled.SheetDetent

@Composable
fun BottomSheetExample() {
    val peek = SheetDetent("peek") { containerHeight, _ ->
        containerHeight * 0.6f
    }
    val sheetState = rememberBottomSheetState(
        detents = listOf(SheetDetent.Hidden, SheetDetent.FullyExpanded),
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(onClick = { sheetState.targetDetent = SheetDetent.FullyExpanded }) {
            Text("Show sheet")
        }

        BottomSheet(
            state = sheetState,
            onDismissRequest = { sheetState.targetDetent = SheetDetent.Hidden },
        ) {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun BottomSheetActionMenuExample() {
    val sheetState = rememberBottomSheetState(
        detents = listOf(SheetDetent.Hidden, SheetDetent.FullyExpanded),
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
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
}

@Composable
private fun BottomSheetAction(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit,
    style: ButtonStyle = ButtonStyle.Ghost,
    contentColor: androidx.compose.ui.graphics.Color? = null,
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        style = style,
        contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp),
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
