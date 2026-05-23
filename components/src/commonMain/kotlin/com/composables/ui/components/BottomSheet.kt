package com.composables.ui.components

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.ui.theme.bottomSheetShape
import com.composables.ui.theme.border
import com.composables.ui.theme.componentSizes
import com.composables.ui.theme.colors
import com.composables.ui.theme.dim
import com.composables.ui.theme.focusRing
import com.composables.ui.theme.focusRingOffset
import com.composables.ui.theme.focusRingWidth
import com.composables.ui.theme.indications
import com.composables.ui.theme.onPanel
import com.composables.ui.theme.panel
import com.composables.ui.theme.scrim
import com.composables.ui.theme.shapes
import com.composeunstyled.DragIndication
import com.composeunstyled.LocalTextStyle
import com.composeunstyled.ModalBottomSheetState
import com.composeunstyled.ModalBottomSheetProperties
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.ProvideTextStyle
import com.composeunstyled.Scrim
import com.composeunstyled.Sheet
import com.composeunstyled.SheetDetent
import com.composeunstyled.UnstyledModalBottomSheet
import com.composeunstyled.theme.Theme
import com.composeunstyled.rememberModalBottomSheetState as rememberUnstyledModalBottomSheetState

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
    header: (@Composable () -> Unit)? = null,
    footer: (@Composable ColumnScope.() -> Unit)? = null,
    shape: Shape = Theme[shapes][bottomSheetShape],
    backgroundColor: Color = Theme[colors][panel],
    contentColor: Color = Theme[colors][onPanel],
    properties: ModalBottomSheetProperties = ModalBottomSheetProperties(),
    body: @Composable ColumnScope.() -> Unit,
) {
    val dragIndicationInteractionSource = remember { MutableInteractionSource() }
    val dragIndicationShape = RoundedCornerShape(100)

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
                .padding(top = 16.dp),
            contentAlignment = Alignment.TopCenter,
        ) {
                Sheet(
                    modifier = modifier
                        .widthIn(max = 640.dp)
                        .fillMaxWidth()
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
                                .padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                        ) {
                            if (header != null) {
                                ProvideTextStyle(LocalTextStyle.current.merge(BottomSheetHeaderTextStyle)) {
                                    Box(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        header()
                                    }
                                }
                            }
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                content = body,
                            )
                            if (footer != null) {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalArrangement = Arrangement.spacedBy(8.dp),
                                    content = footer,
                                )
                            }
                        }
                        DragIndication(
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .padding(top = 8.dp)
                                .size(width = 32.dp, height = 4.dp)
                                .focusRing(
                                    interactionSource = dragIndicationInteractionSource,
                                    width = Theme[componentSizes][focusRingWidth],
                                    color = Theme[colors][focusRing],
                                    shape = dragIndicationShape,
                                    offset = Theme[componentSizes][focusRingOffset],
                                )
                                .background(Theme[colors][border], dragIndicationShape),
                            indication = Theme[indications][dim],
                            interactionSource = dragIndicationInteractionSource,
                        )
                    }
                }
            }
        }
    }
}

private val BottomSheetHeaderTextStyle = TextStyle(
    fontSize = 20.sp,
    lineHeight = 24.sp,
    fontWeight = FontWeight.Medium,
)
