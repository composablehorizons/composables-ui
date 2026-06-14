package com.composables.ui.components

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.ui.theme.borderColor
import com.composables.ui.theme.colors
import com.composables.ui.theme.defaultIndication
import com.composables.ui.theme.ringColor
import com.composables.ui.theme.indications
import com.composables.ui.theme.onPanelColor
import com.composables.ui.theme.overlayShadow
import com.composables.ui.theme.panelColor
import com.composables.ui.theme.scrimColor
import com.composables.ui.theme.sheetShape
import com.composables.ui.theme.shadows
import com.composables.ui.theme.shapes
import com.composeunstyled.DragIndication
import com.composeunstyled.LocalTextStyle
import com.composeunstyled.ModalBottomSheetState
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.ProvideTextStyle
import com.composeunstyled.Scrim
import com.composeunstyled.Sheet
import com.composeunstyled.SheetDetent
import com.composeunstyled.UnstyledModalBottomSheet
import com.composeunstyled.theme.Theme
import kotlin.jvm.JvmInline
import com.composeunstyled.rememberModalBottomSheetState as rememberUnstyledModalBottomSheetState

@Stable
class BottomSheetState internal constructor(
    internal val unstyledState: ModalBottomSheetState,
) {
    var targetDetent: BottomSheetDetent
        get() = BottomSheetDetent.from(unstyledState.targetDetent)
        set(value) {
            unstyledState.targetDetent = value.unstyledDetent
        }

    /**
     * Animates a BottomSheetState to the requested detent.
     * @param detent Target detent to animate the sheet toward.
     */
    suspend fun animateTo(detent: BottomSheetDetent) {
        unstyledState.animateTo(detent.unstyledDetent)
    }

    /**
     * Animates the bottom sheet to its fully expanded state.
     */
    suspend fun show() {
        animateTo(BottomSheetDetent.FullyExpanded)
    }

    /**
     * Animates the bottom sheet to its hidden state.
     */
    suspend fun hide() {
        animateTo(BottomSheetDetent.Hidden)
    }
}

/**
 * Supported resting positions for the bottom sheet.
 */
@JvmInline
value class BottomSheetDetent internal constructor(
    @Suppress("unused") private val value: Int,
) {
    internal val unstyledDetent: SheetDetent
        get() = when (this) {
            FullyExpanded -> SheetDetent.FullyExpanded
            else -> SheetDetent.Hidden
        }

    companion object {
        /**
         * Keeps the bottom sheet off screen.
         */
        val Hidden = BottomSheetDetent(0)
        /**
         * Shows the bottom sheet at its expanded position.
         */
        val FullyExpanded = BottomSheetDetent(1)

        internal fun from(detent: SheetDetent): BottomSheetDetent {
            return when (detent) {
                SheetDetent.Hidden -> Hidden
                SheetDetent.FullyExpanded -> FullyExpanded
                else -> Hidden
            }
        }
    }
}

/**
 * Creates and remembers a [BottomSheetState] to be used in a [BottomSheet].
 * @param initialDetent Detent to be used when the bottom sheet state is first created.
 * @param detents Available detents that the bottom sheet can move between.
 */
@Composable
fun rememberBottomSheetState(
    initialDetent: BottomSheetDetent = BottomSheetDetent.Hidden,
    detents: List<BottomSheetDetent> = listOf(BottomSheetDetent.Hidden, BottomSheetDetent.FullyExpanded),
): BottomSheetState {
    val unstyledState = rememberUnstyledModalBottomSheetState(
        initialDetent = initialDetent.unstyledDetent,
        detents = detents.map { it.unstyledDetent },
    )
    return remember(unstyledState) { BottomSheetState(unstyledState) }
}

/**
 * A modal bottom sheet with optional header and footer content.
 * @param state State object that controls the bottom sheet.
 * @param onDismissRequest Called when the sheet should dismiss.
 * @param modifier Modifier applied to the bottom sheet container.
 * @param toolbar Optional header content shown at the top of the sheet.
 * @param footer Optional footer content shown below the sheet body.
 * @param shape Shape used for the sheet container.
 * @param backgroundColor Background color used for the sheet surface.
 * @param contentColor Color used for sheet content.
 * @param contentPadding Padding applied around the sheet body content.
 * @param shadow Shadow applied to the sheet container.
 * @param body Main content displayed inside the sheet.
 */
@Composable
fun BottomSheet(
    state: BottomSheetState,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    toolbar: (@Composable () -> Unit)? = null,
    footer: (@Composable ColumnScope.() -> Unit)? = null,
    shape: Shape = Theme[shapes][sheetShape],
    backgroundColor: Color = Theme[colors][panelColor],
    contentColor: Color = Theme[colors][onPanelColor],
    contentPadding: PaddingValues = PaddingValues(20.dp),
    shadow: Shadow = Theme[shadows][overlayShadow],
    body: @Composable ColumnScope.() -> Unit,
) {
    val dragIndicationInteractionSource = remember { MutableInteractionSource() }

    UnstyledModalBottomSheet(
        state = state.unstyledState,
        onDismiss = onDismissRequest,
        overlay = {
            Scrim(
                scrimColor = Theme[colors][scrimColor],
                enter = fadeIn(tween(BottomSheetScrimAnimationDurationMillis)),
                exit = fadeOut(tween(BottomSheetScrimAnimationDurationMillis)),
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
                    .dropShadow(shape, shadow)
                    .clip(shape)
                    .background(backgroundColor, shape),
            ) {
                ProvideContentColor(contentColor) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                        ) {
                            if (toolbar != null) {
                                ProvideTextStyle(LocalTextStyle.current.merge(BottomSheetHeaderTextStyle)) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 20.dp),
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        toolbar()
                                    }
                                }
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(contentPadding),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                            ) {
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
                        }
                        DragIndication(
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .padding(top = 8.dp)
                                .size(width = 32.dp, height = 4.dp)
                                .focusRing(
                                    interactionSource = dragIndicationInteractionSource,
                                    color = Theme[colors][ringColor],
                                    shape = RoundedCornerShape(100),
                                )
                                .background(Theme[colors][borderColor], RoundedCornerShape(100)),
                            indication = Theme[indications][defaultIndication],
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

const val BottomSheetScrimAnimationDurationMillis = 300
