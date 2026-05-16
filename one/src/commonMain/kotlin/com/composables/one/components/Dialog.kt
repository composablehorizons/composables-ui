package com.composables.one.components

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.composables.one.Sample
import com.composables.one.styling.card
import com.composables.one.styling.colors
import com.composables.one.styling.medium
import com.composables.one.styling.modal
import com.composables.one.styling.onCard
import com.composables.one.styling.outline
import com.composables.one.styling.scrim
import com.composables.one.styling.shadows
import com.composables.one.styling.shapes
import com.composeunstyled.UnstyledDialog
import com.composeunstyled.UnstyledDialogPanel
import com.composeunstyled.UnstyledScrim
import com.composeunstyled.outline
import com.composeunstyled.rememberDialogState
import com.composeunstyled.theme.Theme

@Sample("DialogExample")
@Composable
fun Dialog(visible: Boolean, content: @Composable () -> Unit) {
    val state = rememberDialogState(visible)

    SideEffect { state.visible = visible }

    UnstyledDialog(state) {
        UnstyledScrim(enter = fadeIn(tween(300)), exit = fadeOut(tween(300)), scrimColor = Theme[colors][scrim])

        UnstyledDialogPanel(
            modifier = Modifier
                .zIndex(10f)
                .padding(16.dp)
                .dropShadow(Theme[shapes][medium],Theme[shadows][modal])
                .outline(Dp.Hairline, Theme[colors][outline], Theme[shapes][medium])
                .widthIn(max = 560.dp)
                .fillMaxWidth(),
            contentColor = Theme[colors][onCard],
            backgroundColor = Theme[colors][card],
            contentPadding = PaddingValues(24.dp),
            shape = Theme[shapes][medium],
            enter = scaleIn(initialScale = 0.9f, animationSpec = tween(150)) + fadeIn(tween(durationMillis = 150)),
            exit = scaleOut(targetScale = 0.8f, animationSpec = tween(250)) + fadeOut(tween(durationMillis = 250)),
        ) {
            Column {
                content()
            }
        }
    }
}
