package com.composables.ui.demo.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.icons.lucide.Bell
import com.composables.icons.lucide.Lucide
import com.composables.ui.components.BottomSheet
import com.composables.ui.components.Button
import com.composables.ui.components.ButtonStyle
import com.composables.ui.components.Icon
import com.composables.ui.components.Text
import com.composables.ui.components.rememberBottomSheetState
import com.composables.ui.theme.colors
import com.composables.ui.theme.muted
import com.composeunstyled.LocalTextStyle
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.ProvideTextStyle
import com.composeunstyled.SheetDetent
import com.composeunstyled.theme.Theme

@Composable
fun BottomSheetConfirmationExample() {
    val sheetState = rememberBottomSheetState(
        detents = listOf(SheetDetent.Hidden, SheetDetent.FullyExpanded),
    )

    Button(onClick = { sheetState.targetDetent = SheetDetent.FullyExpanded }) {
        Text("Show confirmation")
    }

    BottomSheet(
        state = sheetState,
        onDismissRequest = { sheetState.targetDetent = SheetDetent.Hidden },
        header = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Icon(
                    imageVector = Lucide.Bell,
                    contentDescription = null,
                    modifier = Modifier.size(28.dp),
                )
                Text("Allow notifications?")
            }
        },
        footer = {
            Button(
                onClick = { sheetState.targetDetent = SheetDetent.Hidden },
                modifier = Modifier.fillMaxWidth(),
                style = ButtonStyle.Primary,
            ) {
                Text("Allow")
            }
            Button(
                onClick = { sheetState.targetDetent = SheetDetent.Hidden },
                modifier = Modifier.fillMaxWidth(),
                style = ButtonStyle.Secondary,
            ) {
                Text("Maybe later")
            }
        },
    ) {
        ProvideContentColor(Theme[colors][muted]) {
            ProvideTextStyle(LocalTextStyle.current.merge(TextStyle(fontSize = 16.sp, lineHeight = 24.sp))) {
                Text(
                    text = "Get notified when projects finish syncing, comments mention you, or billing needs attention.",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}
