package com.composables.one.demo.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Plus
import com.composables.one.Icon
import com.composables.one.IconButton
import com.composables.one.PrimaryButton
import com.composables.one.Text
import com.composables.one.styling.colors
import com.composables.one.styling.onPrimary
import com.composables.one.styling.primary
import com.composeunstyled.theme.Theme

@Composable
fun ButtonsExample() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        PrimaryButton(onClick = { /* TODO */ }) {
            Text("Save changes")
        }
        IconButton(
            onClick = { /* TODO */ },
            backgroundColor = Theme[colors][primary],
            contentColor = Theme[colors][onPrimary],
        ) {
            Icon(
                imageVector = Lucide.Plus,
                contentDescription = "Add",
                modifier = Modifier.size(18.dp),
            )
        }
    }
}
