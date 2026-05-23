package com.composables.ui.demo.examples

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.ArrowLeft
import com.composables.icons.lucide.EllipsisVertical
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Share
import com.composables.ui.components.ButtonStyle
import com.composables.ui.components.Icon
import com.composables.ui.components.IconButton
import com.composables.ui.components.Text
import com.composables.ui.components.Toolbar

@Composable
fun ToolbarWithActionsExample() {
    Toolbar(
        title = { Text("Title") },
        leading = {
            IconButton(
                onClick = { /* TODO */ },
                style = ButtonStyle.Ghost,
            ) {
                Icon(Lucide.ArrowLeft, contentDescription = "Go back")
            }
        },
        trailing = {
            IconButton(
                onClick = { /* TODO */ },
                style = ButtonStyle.Ghost,
            ) {
                Icon(
                    imageVector = Lucide.Share,
                    contentDescription = "Share",
                    modifier = Modifier.size(18.dp),
                )
            }
            IconButton(
                onClick = { /* TODO */ },
                style = ButtonStyle.Ghost,
            ) {
                Icon(
                    imageVector = Lucide.EllipsisVertical,
                    contentDescription = "More options",
                    modifier = Modifier.size(18.dp),
                )
            }
        },
    )
}
