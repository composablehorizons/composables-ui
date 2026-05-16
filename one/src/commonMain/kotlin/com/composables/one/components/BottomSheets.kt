package com.composables.one.components

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
import androidx.compose.ui.unit.dp
import com.composables.core.BottomSheetState
import com.composables.core.DragIndication
import com.composables.core.ModalBottomSheetState
import com.composables.core.Scrim
import com.composables.core.Sheet
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
import com.composeunstyled.outline
import com.composeunstyled.theme.Theme
import com.composables.core.BottomSheet as UnstyledBottomSheet
import com.composables.core.ModalBottomSheet as UnstyledModalBottomSheet


@Sample("BottomSheetExample")
@Composable
fun BottomSheet(
    state: BottomSheetState,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    UnstyledBottomSheet(
        state = state,
        modifier = modifier
            .widthIn(max = 640.dp)
            .fillMaxWidth()
            .dropShadow(shadow = Theme[shadows][subtle], shape = Theme[shapes][bottomSheet])
            .outline(1.dp, Theme[colors][outline], shape = Theme[shapes][bottomSheet])
            .navigationBarsPadding(),
        backgroundColor = Theme[colors][card],
        shape = Theme[shapes][bottomSheet],
        contentPadding = PaddingValues(bottom = 16.dp),
    ) {
        ProvideContentColor(Theme[colors][onCard]) {
            Column {
                DragIndication(
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 8.dp)
                        .background(Theme[colors][outline], Theme[shapes][small]).height(4.dp).width(32.dp),
                )
                Spacer(Modifier.height(16.dp))
                content()
            }
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
    UnstyledModalBottomSheet(state = state) {
        Scrim(enter = fadeIn(tween(300)), exit = fadeOut(tween(300)), scrimColor = Theme[colors][scrim])
        Sheet(
            modifier
                .widthIn(max = 640.dp)
                .fillMaxWidth()
                .dropShadow(Theme[shapes][bottomSheet], Theme[shadows][subtle])
                .outline(1.dp, Theme[colors][outline], shape = Theme[shapes][bottomSheet])
                .navigationBarsPadding(),
            backgroundColor = Theme[colors][card],
            shape = Theme[shapes][bottomSheet],
            contentPadding = PaddingValues(bottom = 16.dp),

        ) {
            ProvideContentColor(Theme[colors][onCard]) {
                Column {
                    DragIndication(
                        Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 8.dp)
                            .background(Theme[colors][outline], Theme[shapes][small]).height(4.dp)
                            .width(32.dp),
                    )
                    Spacer(Modifier.height(16.dp))
                    content()
                }
            }
        }
    }
}