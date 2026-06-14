package com.composables.ui.components

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.ui.theme.colors
import com.composables.ui.theme.dialogShape
import com.composables.ui.theme.mutedColor
import com.composables.ui.theme.onPanelColor
import com.composables.ui.theme.overlayShadow
import com.composables.ui.theme.panelColor
import com.composables.ui.theme.scrimColor
import com.composables.ui.theme.shadows
import com.composables.ui.theme.shapes
import com.composeunstyled.DialogPanel
import com.composeunstyled.LocalTextStyle
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.ProvideTextStyle
import com.composeunstyled.Scrim
import com.composeunstyled.UnstyledDialog
import com.composeunstyled.theme.Theme

private const val DialogEnterDurationMillis = 400
private const val DialogExitDurationMillis = 200
private const val DialogEnterFadeDurationMillis = 150
private const val DialogExitFadeDurationMillis = 100

private val EmphasizedDecelerateEasing = CubicBezierEasing(0.05f, 0.7f, 0.1f, 1f)
private val EmphasizedAccelerateEasing = CubicBezierEasing(0.3f, 0f, 0.8f, 0.15f)

/**
 * A modal dialog for confirmations and focused decisions.
 * @param visible Whether the dialog is currently shown.
 * @param onDismissRequest Called when the dialog should close.
 * @param modifier Modifier applied to the dialog container.
 * @param icon Optional icon content shown above the title.
 * @param title Optional title content shown at the top of the dialog.
 * @param text Main supporting content shown inside the dialog.
 * @param positiveButton Primary action button content.
 * @param neutralButton Optional neutral action button content.
 * @param negativeButton Optional secondary or destructive action button content.
 * @param shape Shape used for the dialog container.
 * @param backgroundColor Background color used for the dialog surface.
 * @param contentColor Color used for main dialog content.
 * @param supportingTextColor Color used for supporting text content.
 * @param shadow Shadow applied to the dialog container.
 */
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
    shape: Shape = Theme[shapes][dialogShape],
    backgroundColor: Color = Theme[colors][panelColor],
    contentColor: Color = Theme[colors][onPanelColor],
    supportingTextColor: Color = Theme[colors][mutedColor],
    shadow: Shadow = Theme[shadows][overlayShadow],
) {
    AlertDialogPanel(
        visible = visible,
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        paneTitle = "Alert dialog",
        shape = shape,
        backgroundColor = backgroundColor,
        shadow = shadow,
        contentPadding = PaddingValues(24.dp),
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
                    ProvideTextStyle(LocalTextStyle.current.merge(AlertDialogTitleTextStyle)) {
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
                        ProvideTextStyle(LocalTextStyle.current.merge(AlertDialogBodyTextStyle)) {
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

/**
 * Variant of [AlertDialog] that can take any content.
 *
 * @param visible Whether the dialog is currently shown.
 * @param onDismissRequest Called when the dialog should close.
 * @param modifier Modifier applied to the dialog container.
 * @param paneTitle Accessible title announced for the dialog panel.
 * @param shape Shape used for the dialog container.
 * @param backgroundColor Background color used for the dialog surface.
 * @param contentColor Color used for the dialog content.
 * @param contentPadding Padding applied inside the dialog container.
 * @param shadow Shadow applied to the dialog container.
 * @param content Composable content displayed inside the dialog.
 */
@Composable
fun AlertDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    paneTitle: String = "Alert dialog",
    shape: Shape = Theme[shapes][dialogShape],
    backgroundColor: Color = Theme[colors][panelColor],
    contentColor: Color = Theme[colors][onPanelColor],
    contentPadding: PaddingValues = PaddingValues(24.dp),
    shadow: Shadow = Theme[shadows][overlayShadow],
    content: @Composable () -> Unit,
) {
    AlertDialogPanel(
        visible = visible,
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        paneTitle = paneTitle,
        shape = shape,
        backgroundColor = backgroundColor,
        shadow = shadow,
        contentPadding = contentPadding,
    ) {
        ProvideContentColor(contentColor) {
            content()
        }
    }
}

@Composable
private fun AlertDialogPanel(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier,
    paneTitle: String,
    shape: Shape,
    backgroundColor: Color,
    shadow: Shadow,
    contentPadding: PaddingValues,
    content: @Composable () -> Unit,
) {
    UnstyledDialog(
        visible = visible,
        onDismissRequest = onDismissRequest,
        overlay = {
            Scrim(
                scrimColor = Theme[colors][scrimColor],
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
                    .dropShadow(shape, shadow)
                    .clip(shape)
                    .background(backgroundColor, shape)
                    .padding(contentPadding),
                paneTitle = paneTitle,
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
                content()
            }
        }
    }
}

private val AlertDialogTitleTextStyle = TextStyle(
    fontSize = 20.sp,
    lineHeight = 24.sp,
    fontWeight = FontWeight.Medium,
)

private val AlertDialogBodyTextStyle = TextStyle()
