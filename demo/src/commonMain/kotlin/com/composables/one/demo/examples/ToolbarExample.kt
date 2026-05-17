package com.composables.one.demo.examples

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.ArrowLeft
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.EllipsisVertical
import com.composables.icons.lucide.Share
import com.composables.one.ButtonStyle
import com.composables.one.Icon
import com.composables.one.IconButton
import com.composables.one.Text
import com.composables.one.Toolbar
import com.composables.one.ToolbarSize

@Composable
fun LargeToolbarExample() {
    Toolbar(
        title = { Text("Large title") },
        size = ToolbarSize.Large,
        trailing = { OverflowButton() },
    )
}

@Composable
fun ToolbarWithActionsExample() {
    Toolbar(
        title = { Text("Title") },
        leading = { BackButton() },
        trailing = { ToolbarActions() },
    )
}

@Composable
fun CenteredToolbarExample() {
    Toolbar(
        leading = { BackButton() },
        centered = { Text("Centered") },
        trailing = { OverflowButton() },
    )
}

@Composable
private fun BackButton() {
    IconButton(
        onClick = { /* TODO */ },
        style = ButtonStyle.Ghost,
    ) {
        Icon(Lucide.ArrowLeft, contentDescription = "Go back")
    }
}

@Composable
private fun RowScope.ToolbarActions() {
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
    OverflowButton()
}

@Composable
private fun OverflowButton() {
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
}
