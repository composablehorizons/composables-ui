package com.composables.one

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composables.one.Sample
import com.composables.one.styling.bottomSheet
import com.composables.one.styling.card
import com.composables.one.styling.colors
import com.composables.one.styling.onCard
import com.composables.one.styling.outline
import com.composables.one.styling.scrim
import com.composables.one.styling.shadows
import com.composables.one.styling.shapes
import com.composables.one.styling.small
import com.composables.one.styling.subtle
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.Scrim
import com.composeunstyled.Sheet
import com.composeunstyled.UnstyledBottomSheet
import com.composeunstyled.UnstyledModalBottomSheet
import com.composeunstyled.outline
import com.composeunstyled.rememberBottomSheetState as rememberUnstyledBottomSheetState
import com.composeunstyled.rememberModalBottomSheetState as rememberUnstyledModalBottomSheetState
import com.composeunstyled.theme.Theme
import com.composeunstyled.BottomSheetState as UnstyledBottomSheetState
import com.composeunstyled.ModalBottomSheetState as UnstyledModalBottomSheetState
import com.composeunstyled.SheetDetent as UnstyledSheetDetent

class SheetDetent(
    val identifier: String,
    val calculateDetentHeight: (containerHeight: Dp, sheetHeight: Dp) -> Dp,
) {
    companion object {
        val FullyExpanded: SheetDetent = SheetDetent("fully-expanded") { _, sheetHeight -> sheetHeight }
        val Hidden: SheetDetent = SheetDetent("hidden") { _, _ -> 0.dp }
    }

    override fun equals(other: Any?): Boolean = other is SheetDetent && identifier == other.identifier

    override fun hashCode(): Int = identifier.hashCode()
}

class BottomSheetState internal constructor(
    internal val unstyledState: UnstyledBottomSheetState,
    private val detents: List<SheetDetent>,
) {
    var targetDetent: SheetDetent
        get() = detents.firstOrNull { it.identifier == unstyledState.targetDetent.identifier } ?: SheetDetent.Hidden
        set(value) {
            unstyledState.targetDetent = value.toUnstyled()
        }
}

class ModalBottomSheetState internal constructor(
    internal val unstyledState: UnstyledModalBottomSheetState,
    private val detents: List<SheetDetent>,
) {
    var targetDetent: SheetDetent
        get() = detents.firstOrNull { it.identifier == unstyledState.targetDetent.identifier } ?: SheetDetent.Hidden
        set(value) {
            unstyledState.targetDetent = value.toUnstyled()
        }
}

@Composable
fun rememberBottomSheetState(
    initialDetent: SheetDetent,
    detents: List<SheetDetent> = listOf(SheetDetent.Hidden, SheetDetent.FullyExpanded),
): BottomSheetState {
    return BottomSheetState(
        unstyledState = rememberUnstyledBottomSheetState(
            initialDetent = initialDetent.toUnstyled(),
            detents = detents.map { it.toUnstyled() },
        ),
        detents = detents,
    )
}

@Composable
fun rememberModalBottomSheetState(
    initialDetent: SheetDetent,
    detents: List<SheetDetent> = listOf(SheetDetent.Hidden, SheetDetent.FullyExpanded),
): ModalBottomSheetState {
    return ModalBottomSheetState(
        unstyledState = rememberUnstyledModalBottomSheetState(
            initialDetent = initialDetent.toUnstyled(),
            detents = detents.map { it.toUnstyled() },
        ),
        detents = detents,
    )
}

private fun SheetDetent.toUnstyled(): UnstyledSheetDetent = UnstyledSheetDetent(identifier, calculateDetentHeight)

@Sample("BottomSheetExample")
@Composable
fun BottomSheet(
    state: BottomSheetState,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    UnstyledBottomSheet(
        state = state.unstyledState,
        modifier = modifier
            .widthIn(max = 640.dp)
            .fillMaxWidth()
            .dropShadow(shadow = Theme[shadows][subtle], shape = Theme[shapes][bottomSheet])
            .outline(1.dp, Theme[colors][outline], shape = Theme[shapes][bottomSheet])
            .navigationBarsPadding(),
    ) {
        Sheet(
            Modifier
                .background(Theme[colors][card], Theme[shapes][bottomSheet])
                .padding(PaddingValues(bottom = 16.dp)),
        ) {
            SheetContent(content)
        }
    }
}

@Sample("ModalBottomSheetExample")
@Composable
fun ModalBottomSheet(
    state: ModalBottomSheetState,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    UnstyledModalBottomSheet(
        state = state.unstyledState,
        overlay = {
            Scrim(enter = fadeIn(tween(300)), exit = fadeOut(tween(300)), scrimColor = Theme[colors][scrim])
        },
    ) {
        Sheet(
            modifier
                .widthIn(max = 640.dp)
                .fillMaxWidth()
                .dropShadow(Theme[shapes][bottomSheet], Theme[shadows][subtle])
                .outline(1.dp, Theme[colors][outline], shape = Theme[shapes][bottomSheet])
                .navigationBarsPadding()
                .background(Theme[colors][card], Theme[shapes][bottomSheet])
                .padding(PaddingValues(bottom = 16.dp)),
        ) {
            SheetContent(content)
        }
    }
}

@Composable
private fun SheetContent(content: @Composable () -> Unit) {
    ProvideContentColor(Theme[colors][onCard]) {
        Column {
            androidx.compose.foundation.layout.Box(
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 8.dp)
                    .background(Theme[colors][outline], Theme[shapes][small])
                    .height(4.dp)
                    .width(32.dp),
            )
            Spacer(Modifier.height(16.dp))
            content()
        }
    }
}
