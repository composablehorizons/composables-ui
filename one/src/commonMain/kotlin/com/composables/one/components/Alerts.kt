package com.composables.one.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.composables.core.rememberDialogState
import com.composables.one.Sample
import com.composables.one.styling.textStyles
import com.composeunstyled.LocalTextStyle
import com.composeunstyled.ProvideTextStyle
import com.composeunstyled.theme.Theme

@Sample("AlertExample")
@Composable
fun Alert(
    visible: Boolean,
    title: @Composable () -> Unit,
    body: @Composable () -> Unit,
    icon: (@Composable () -> Unit)? = null,
    positiveButton: @Composable () -> Unit,
    negativeButton: (@Composable () -> Unit)? = null,
    neutralButtons: (@Composable () -> Unit)? = null,
) {
    val dialogState = rememberDialogState()
    SideEffect {
        dialogState.visible = visible
    }

    Dialog(visible = visible) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            if (icon != null) {
                icon()
            }
            Column {
                ProvideTextStyle(Theme[textStyles][com.composables.one.styling.title]) {
                    title()
                }
                Spacer(Modifier.height(16.dp))
                ProvideTextStyle(Theme[textStyles][com.composables.one.styling.body]) {
                    body()
                }
                Spacer(Modifier.height(24.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (neutralButtons != null) {
                        neutralButtons()
                        Spacer(Modifier.weight(1f))
                    }
                    if (negativeButton != null) {
                        negativeButton()
                    }
                    val focusRequester = remember { FocusRequester() }
                    LaunchedEffect(Unit) {
                        focusRequester.requestFocus()
                    }
                    Box(Modifier.focusRequester(focusRequester)) {
                        positiveButton()
                    }
                }
            }
        }
    }
}

@Sample("CenteredAlertExample")
@Composable
fun CenteredAlert(
    visible: Boolean,
    title: @Composable () -> Unit,
    body: @Composable () -> Unit,
    icon: (@Composable () -> Unit)? = null,
    positiveButton: @Composable () -> Unit,
    negativeButton: (@Composable () -> Unit)? = null,
    neutralButtons: (@Composable () -> Unit)? = null,
) {
    val dialogState = rememberDialogState()
    SideEffect {
        dialogState.visible = visible
    }

    Dialog(visible = visible) {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            if (icon != null) {
                icon()
                Spacer(Modifier.height(16.dp))
            }

            ProvideTextStyle(
                Theme[textStyles][com.composables.one.styling.title]
                    .copy(textAlign = TextAlign.Center)
            ) {
                title()
            }
            Spacer(Modifier.height(16.dp))
            ProvideTextStyle(LocalTextStyle.current.copy(textAlign = TextAlign.Center)) {
                body()
            }
            Spacer(Modifier.height(24.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                positiveButton()
                if (neutralButtons != null) {
                    neutralButtons()
                }
                if (negativeButton != null) {
                    negativeButton()
                }
            }
        }
    }
}
