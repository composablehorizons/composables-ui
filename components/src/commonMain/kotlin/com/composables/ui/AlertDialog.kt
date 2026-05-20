package com.composables.ui

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.unit.dp
import com.composables.ui.styling.alertDialogShape
import com.composables.ui.styling.body
import com.composables.ui.styling.colors
import com.composables.ui.styling.muted
import com.composables.ui.styling.onPanel
import com.composables.ui.styling.panel
import com.composables.ui.styling.scrim
import com.composables.ui.styling.shapes
import com.composables.ui.styling.textStyles
import com.composeunstyled.DialogPanel
import com.composeunstyled.DialogProperties
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.ProvideTextStyle
import com.composeunstyled.Scrim
import com.composeunstyled.UnstyledDialog
import com.composeunstyled.outline
import com.composeunstyled.theme.Theme
import com.composables.ui.styling.title as titleTextStyle

private const val DialogEnterDurationMillis = 400
private const val DialogExitDurationMillis = 200
private const val DialogEnterFadeDurationMillis = 150
private const val DialogExitFadeDurationMillis = 100

private val EmphasizedDecelerateEasing = CubicBezierEasing(0.05f, 0.7f, 0.1f, 1f)
private val EmphasizedAccelerateEasing = CubicBezierEasing(0.3f, 0f, 0.8f, 0.15f)
private val DialogOutlineColor = Color.Black.copy(alpha = 0.1f)

@Composable
fun AlertDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    title: (@Composable () -> Unit)? = null,
    text: @Composable () -> Unit,
    positiveButton: @Composable () -> Unit,
    neutralButton: (@Composable () -> Unit)? = null,
    negativeButton: (@Composable () -> Unit)? = null,
    shape: Shape = Theme[shapes][alertDialogShape],
    backgroundColor: Color = Theme[colors][panel],
    contentColor: Color = Theme[colors][onPanel],
    supportingTextColor: Color = Theme[colors][muted],
    properties: DialogProperties = DialogProperties(),
) {
    UnstyledDialog(
        visible = visible,
        onDismissRequest = onDismissRequest,
        properties = properties,
        overlay = {
            Scrim(
                scrimColor = Theme[colors][scrim],
                enter = fadeIn(
                    animationSpec = tween(
                        durationMillis = DialogEnterFadeDurationMillis,
                        easing = EmphasizedDecelerateEasing,
                    ),
                ),
                exit = fadeOut(
                    animationSpec = tween(
                        durationMillis = DialogExitFadeDurationMillis,
                        easing = EmphasizedAccelerateEasing,
                    ),
                ),
            )
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(vertical = 24.dp),
            contentAlignment = Alignment.Center,
        ) {
            DialogPanel(
                modifier = modifier
                    .widthIn(min = 280.dp, max = 560.dp)
                    .fillMaxWidth()
                    .heightIn(max = 560.dp)
                    .outline(1.dp, DialogOutlineColor, shape)
                    .clip(shape)
                    .background(backgroundColor, shape)
                    .padding(24.dp),
                paneTitle = "Alert dialog",
                enter = scaleIn(
                    initialScale = 0.92f,
                    transformOrigin = TransformOrigin.Center,
                    animationSpec = tween(
                        durationMillis = DialogEnterDurationMillis,
                        easing = EmphasizedDecelerateEasing,
                    ),
                ) + fadeIn(
                    animationSpec = tween(
                        durationMillis = DialogEnterFadeDurationMillis,
                        easing = EmphasizedDecelerateEasing,
                    ),
                ),
                exit = scaleOut(
                    targetScale = 0.92f,
                    transformOrigin = TransformOrigin.Center,
                    animationSpec = tween(
                        durationMillis = DialogExitDurationMillis,
                        easing = EmphasizedAccelerateEasing,
                    ),
                ) + fadeOut(
                    animationSpec = tween(
                        durationMillis = DialogExitFadeDurationMillis,
                        easing = EmphasizedAccelerateEasing,
                    ),
                ),
            ) {
                ProvideContentColor(contentColor) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        if (icon != null) {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center,
                            ) {
                                Box(Modifier.size(24.dp)) {
                                    icon()
                                }
                            }
                        }

                        if (title != null) {
                            ProvideTextStyle(Theme[textStyles][titleTextStyle]) {
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    title()
                                }
                            }
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f, fill = false)
                                .verticalScroll(rememberScrollState()),
                            contentAlignment = Alignment.Center,
                        ) {
                            ProvideContentColor(supportingTextColor) {
                                ProvideTextStyle(Theme[textStyles][body]) {
                                    text()
                                }
                            }
                        }

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            positiveButton()
                            if (neutralButton != null) {
                                neutralButton()
                            }
                            if (negativeButton != null) {
                                negativeButton()
                            }
                        }
                    }
                }
            }
        }
    }
}
