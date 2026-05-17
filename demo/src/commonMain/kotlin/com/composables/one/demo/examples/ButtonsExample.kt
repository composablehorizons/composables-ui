package com.composables.one.demo.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.composeunstyled.CrossAxisAlignment
import com.composeunstyled.MainAxisArrangement
import com.composeunstyled.Stack
import com.composeunstyled.StackOrientation
import com.composeunstyled.currentWindowContainerSize

@Composable
fun ButtonsExample() {
    val windowSize = currentWindowContainerSize()
    val isCompact = windowSize.width < 600.dp
    val stackOrientation = if (isCompact) {
        StackOrientation.Vertical
    } else {
        StackOrientation.Horizontal
    }

    val mainAxisArrangement = if (isCompact) {
        MainAxisArrangement.Start
    } else
        MainAxisArrangement.Center

    val crossAxisAlignment = if (isCompact) {
        CrossAxisAlignment.Start
    } else
        CrossAxisAlignment.Center

    Stack(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        orientation = stackOrientation,
        mainAxisArrangement = mainAxisArrangement,
        crossAxisAlignment = crossAxisAlignment,
        spacing = 32.dp,
    ) {
        ButtonSizeColumn("Small", ButtonSize.Small)
        ButtonSizeColumn("Regular", ButtonSize.Regular)
        ButtonSizeColumn("Large", ButtonSize.Large)
    }
}

@Composable
private fun ButtonSizeColumn(
    label: String,
    buttonSize: ButtonSize,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(label)
        ButtonStyleRow(ButtonStyle.Primary, buttonSize)
        ButtonStyleRow(ButtonStyle.Secondary, buttonSize)
        ButtonStyleRow(ButtonStyle.Outlined, buttonSize)
        ButtonStyleRow(ButtonStyle.Destructive, buttonSize)
        ButtonStyleRow(ButtonStyle.Ghost, buttonSize)
    }
}

@Composable
private fun ButtonStyleRow(
    style: ButtonStyle,
    buttonSize: ButtonSize,
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
