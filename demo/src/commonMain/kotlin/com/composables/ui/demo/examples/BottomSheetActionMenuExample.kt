package com.composables.ui.demo.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Copy
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Pencil
import com.composables.icons.lucide.Share
import com.composables.icons.lucide.Trash2
import com.composables.ui.components.BottomSheet
import com.composables.ui.components.Button
import com.composables.ui.components.ButtonStyle
import com.composables.ui.components.Icon
import com.composables.ui.components.Text
import com.composables.ui.components.rememberBottomSheetState
import com.composables.ui.theme.colors
import com.composables.ui.theme.destructive
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.SheetDetent
import com.composeunstyled.theme.Theme

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
            Button(
                onClick = { sheetState.targetDetent = SheetDetent.Hidden },
                modifier = Modifier.fillMaxWidth(),
                style = ButtonStyle.Secondary,
                contentPadding = PaddingValues(horizontal = 16.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text("Rename")
                    Spacer(Modifier.weight(1f))
                    Icon(
                        imageVector = Lucide.Pencil,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                    )
                }
            }
            Button(
                onClick = { sheetState.targetDetent = SheetDetent.Hidden },
                modifier = Modifier.fillMaxWidth(),
                style = ButtonStyle.Secondary,
                contentPadding = PaddingValues(horizontal = 16.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text("Duplicate")
                    Spacer(Modifier.weight(1f))
                    Icon(
                        imageVector = Lucide.Copy,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                    )
                }
            }
            Button(
                onClick = { sheetState.targetDetent = SheetDetent.Hidden },
                modifier = Modifier.fillMaxWidth(),
                style = ButtonStyle.Secondary,
                contentPadding = PaddingValues(horizontal = 16.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text("Share")
                    Spacer(Modifier.weight(1f))
                    Icon(
                        imageVector = Lucide.Share,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                    )
                }
            }
            Button(
                onClick = { sheetState.targetDetent = SheetDetent.Hidden },
                modifier = Modifier.fillMaxWidth(),
                style = ButtonStyle.Secondary,
                contentPadding = PaddingValues(horizontal = 16.dp),
            ) {
                ProvideContentColor(Theme[colors][destructive]) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text("Delete")
                        Spacer(Modifier.weight(1f))
                        Icon(
                            imageVector = Lucide.Trash2,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                        )
                    }
                }
            }
        }
    }
}
