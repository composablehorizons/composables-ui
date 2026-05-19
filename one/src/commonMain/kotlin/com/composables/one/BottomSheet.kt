package com.composables.one

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.composables.one.styling.bottomSheetShape
import com.composables.one.styling.border
import com.composables.one.styling.colors
import com.composables.one.styling.dim
import com.composables.one.styling.indications
import com.composables.one.styling.onPanel
import com.composables.one.styling.panel
import com.composables.one.styling.scrim
import com.composables.one.styling.shapes
import com.composeunstyled.DragIndication
import com.composeunstyled.ModalBottomSheetState
import com.composeunstyled.ModalBottomSheetProperties
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.Scrim
import com.composeunstyled.Sheet
import com.composeunstyled.SheetDetent
import com.composeunstyled.UnstyledModalBottomSheet
import com.composeunstyled.outline
import com.composeunstyled.theme.Theme
import com.composeunstyled.rememberModalBottomSheetState as rememberUnstyledModalBottomSheetState

private val BottomSheetOutlineColor = Color.Black.copy(alpha = 0.1f)

@Composable
fun rememberBottomSheetState(
    initialDetent: SheetDetent = SheetDetent.Hidden,
    detents: List<SheetDetent> = listOf(SheetDetent.Hidden, SheetDetent.FullyExpanded),
): ModalBottomSheetState {
    return rememberUnstyledModalBottomSheetState(
        initialDetent = initialDetent,
        detents = detents,
    )
}

@Composable
fun BottomSheet(
    state: ModalBottomSheetState,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = Theme[shapes][bottomSheetShape],
    backgroundColor: Color = Theme[colors][panel],
    contentColor: Color = Theme[colors][onPanel],
    properties: ModalBottomSheetProperties = ModalBottomSheetProperties(),
    content: @Composable ColumnScope.() -> Unit,
) {
    UnstyledModalBottomSheet(
        state = state,
        properties = properties,
        onDismiss = onDismissRequest,
        overlay = {
            Scrim(
                scrimColor = Theme[colors][scrim],
                enter = fadeIn(),
                exit = fadeOut(),
            )
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
                    .background(backgroundColor, shape),
            ) {
                ProvideContentColor(contentColor) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            content = content,
                        )
                        DragIndication(
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .padding(top = 22.dp)
                                .background(Theme[colors][border], RoundedCornerShape(100))
                                .size(width = 32.dp, height = 4.dp),
                            indication = Theme[indications][dim],
                        )
                    }
                }
            }
        }
    }
}
