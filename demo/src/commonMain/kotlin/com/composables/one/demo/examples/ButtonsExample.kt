package com.composables.one.demo.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Plus
import com.composables.one.Button
import com.composables.one.ButtonSize
import com.composables.one.ButtonStyle
import com.composables.one.Icon
import com.composables.one.IconButton
import com.composables.one.Text

@Composable
fun PrimaryButtonExample() {
    ButtonStyleRow(ButtonStyle.Primary)
}

@Composable
fun SecondaryButtonExample() {
    ButtonStyleRow(ButtonStyle.Secondary)
}

@Composable
fun OutlinedButtonExample() {
    ButtonStyleRow(ButtonStyle.Outlined)
}

@Composable
fun DestructiveButtonExample() {
    ButtonStyleRow(ButtonStyle.Destructive)
}

@Composable
fun GhostButtonExample() {
    ButtonStyleRow(ButtonStyle.Ghost)
}

@Composable
fun ButtonSizesExample() {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        ButtonStyleRow(ButtonStyle.Secondary, ButtonSize.Small)
        ButtonStyleRow(ButtonStyle.Secondary, ButtonSize.Regular)
        ButtonStyleRow(ButtonStyle.Secondary, ButtonSize.Large)
    }
}

@Composable
private fun ButtonStyleRow(
    style: ButtonStyle,
    buttonSize: ButtonSize = ButtonSize.Default,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Button(
            onClick = { /* TODO */ },
            style = style,
            buttonSize = buttonSize,
        ) {
            Text("Button")
        }
        IconButton(
            onClick = { /* TODO */ },
            style = style,
            buttonSize = buttonSize,
        ) {
            Icon(
                imageVector = Lucide.Plus,
                contentDescription = "Add",
                modifier = Modifier.size(18.dp),
            )
        }
    }
}
