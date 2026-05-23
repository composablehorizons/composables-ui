package com.composables.ui.demo.examples

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.EllipsisVertical
import com.composables.icons.lucide.Lucide
import com.composables.ui.components.ButtonStyle
import com.composables.ui.components.Icon
import com.composables.ui.components.IconButton
import com.composables.ui.components.Text
import com.composables.ui.components.Toolbar
import com.composables.ui.components.ToolbarSize

@Composable
fun LargeToolbarExample() {
    Toolbar(
        title = { Text("Large title") },
        size = ToolbarSize.Large,
        trailing = {
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
