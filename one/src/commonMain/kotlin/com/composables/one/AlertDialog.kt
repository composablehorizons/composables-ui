package com.composables.one

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
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
import androidx.compose.ui.unit.dp
import com.composables.one.styling.alertDialogShape
import com.composables.one.styling.body
import com.composables.one.styling.border
import com.composables.one.styling.colors
import com.composables.one.styling.onPanel
import com.composables.one.styling.panel
import com.composables.one.styling.shapes
import com.composables.one.styling.textStyles
import com.composeunstyled.DialogPanel
import com.composeunstyled.DialogProperties
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.ProvideTextStyle
import com.composeunstyled.UnstyledDialog
import com.composeunstyled.theme.Theme
import com.composables.one.styling.title as titleTextStyle

private const val DialogEnterDurationMillis = 400
private const val DialogExitDurationMillis = 200
private const val DialogEnterFadeDurationMillis = 150
private const val DialogExitFadeDurationMillis = 100

private val EmphasizedDecelerateEasing = CubicBezierEasing(0.05f, 0.7f, 0.1f, 1f)
private val EmphasizedAccelerateEasing = CubicBezierEasing(0.3f, 0f, 0.8f, 0.15f)

@Composable
fun AlertDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    title: (@Composable () -> Unit)? = null,
    text: @Composable () -> Unit,
    confirmButton: @Composable RowScope.() -> Unit,
    dismissButton: (@Composable RowScope.() -> Unit)? = null,
    shape: Shape = Theme[shapes][alertDialogShape],
    backgroundColor: Color = Theme[colors][panel],
    contentColor: Color = Theme[colors][onPanel],
    borderColor: Color = Theme[colors][border],
    properties: DialogProperties = DialogProperties(),
) {
    UnstyledDialog(
        visible = visible,
        onDismissRequest = onDismissRequest,
        properties = properties,
        overlay = {
            // TODO update unstyled and use dialog scrim
            Box(Modifier.fillMaxSize().background(Color.Black.copy(0.3f)))
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
                    .clip(shape)
                    .background(backgroundColor, shape)
                    .border(1.dp, borderColor, shape)
                    .padding(24.dp),
                paneTitle = "Alert dialog",
                enter = scaleIn(
                    initialScale = 0.92f,
                    transformOrigin = androidx.compose.ui.graphics.TransformOrigin.Center,
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
                    transformOrigin = androidx.compose.ui.graphics.TransformOrigin.Center,
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
                                    contentAlignment = if (icon == null) Alignment.CenterStart else Alignment.Center,
                                ) {
                                    title()
                                }
                            }
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f, fill = false)
                                .verticalScroll(rememberScrollState()),
                        ) {
                            ProvideTextStyle(Theme[textStyles][body]) {
                                text()
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            if (dismissButton != null) {
                                dismissButton()
                            }
                            confirmButton()
                        }
                    }
                }
            }
        }
    }
}
