package com.composables.one

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.composables.one.styling.bottomSheetShape
import com.composables.one.styling.colors
import com.composables.one.styling.onPanel
import com.composables.one.styling.panel
import com.composables.one.styling.shapes
import com.composeunstyled.ModalBottomSheetProperties
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.Sheet
import com.composeunstyled.SheetDetent
import com.composeunstyled.UnstyledModalBottomSheet
import com.composeunstyled.outline
import com.composeunstyled.rememberModalBottomSheetState
import com.composeunstyled.theme.Theme

private val BottomSheetOutlineColor = Color.Black.copy(alpha = 0.1f)

@Composable
fun BottomSheet(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = Theme[shapes][bottomSheetShape],
    backgroundColor: Color = Theme[colors][panel],
    contentColor: Color = Theme[colors][onPanel],
    properties: ModalBottomSheetProperties = ModalBottomSheetProperties(),
    content: @Composable ColumnScope.() -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(
        initialDetent = if (visible) SheetDetent.FullyExpanded else SheetDetent.Hidden,
        detents = listOf(SheetDetent.Hidden, SheetDetent.FullyExpanded),
    )

    LaunchedEffect(visible) {
        sheetState.targetDetent = if (visible) SheetDetent.FullyExpanded else SheetDetent.Hidden
    }

    UnstyledModalBottomSheet(
        state = sheetState,
        properties = properties,
        onDismiss = onDismissRequest,
        overlay = {
            Box(Modifier.fillMaxSize().background(Color.Black.copy(0.12f)))
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            contentAlignment = Alignment.TopCenter,
        ) {
            Sheet(
                modifier = modifier
                    .widthIn(max = 640.dp)
                    .fillMaxWidth()
                    .outline(1.dp, BottomSheetOutlineColor, shape)
                    .clip(shape)
                    .background(backgroundColor, shape)
                    .padding(24.dp),
            ) {
                ProvideContentColor(contentColor) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        content = content,
                    )
                }
            }
        }
    }
}
