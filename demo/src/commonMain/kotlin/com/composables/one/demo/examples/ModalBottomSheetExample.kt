package com.composables.one.demo.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.composables.one.Icon
import com.composables.one.GhostButton
import com.composables.one.ModalBottomSheet
import com.composables.one.PrimaryButton
import com.composables.one.SheetDetent
import com.composables.one.styling.destructive
import com.composables.icons.lucide.Link2
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Pencil
import com.composables.icons.lucide.Share2
import com.composables.icons.lucide.Trash2
import com.composables.one.Text
import com.composables.one.rememberModalBottomSheetState
import com.composables.one.styling.colors
import com.composeunstyled.theme.Theme


@Composable
fun ModalBottomSheetExample() {
    val bottomSheetState = rememberModalBottomSheetState(
        initialDetent = SheetDetent.Hidden,
        detents = listOf(SheetDetent.Hidden, SheetDetent.FullyExpanded),
    )
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        PrimaryButton(onClick = { bottomSheetState.targetDetent = SheetDetent.FullyExpanded }) {
            Text("Show options")
        }
        ModalBottomSheet(state = bottomSheetState) {
            Column(Modifier.fillMaxWidth()) {
                GhostButton(
                    onClick = { /* TODO */ },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RectangleShape,
                    horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.Start),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                ) {
                    Icon(Lucide.Pencil, contentDescription = null)
                    Text("Edit")
                }

                GhostButton(
                    onClick = { /* TODO */ },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RectangleShape,
                    horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.Start),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                ) {
                    Icon(Lucide.Link2, contentDescription = null)
                    Text("Copy Link")
                }

                GhostButton(
                    onClick = { /* TODO */ },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RectangleShape,
                    horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.Start),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                ) {
                    Icon(Lucide.Share2, contentDescription = null)
                    Text("Share")
                }

                GhostButton(
                    onClick = { /* TODO */ },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RectangleShape,
                    horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.Start),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                    contentColor = Theme[colors][destructive],
                ) {
                    Icon(Lucide.Trash2, contentDescription = null)
                    Text("Delete")
                }
            }
        }
    }
}
