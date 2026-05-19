package com.composables.one.demo.examples

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.one.BottomSheet
import com.composables.one.Button
import com.composables.one.Text
import com.composables.one.rememberBottomSheetState
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
