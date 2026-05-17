package com.composables.one

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
    val titleContent = title

    UnstyledDialog(
        visible = visible,
        onDismissRequest = onDismissRequest,
        properties = properties,
        overlay = {
            // TODO update unstyled and use dialog scrim
        },
    ) {
        DialogPanel(
            modifier = Modifier.fillMaxSize(),
            paneTitle = "Alert dialog",
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .padding(vertical = 24.dp),
                contentAlignment = Alignment.Center,
            ) {
                ProvideContentColor(contentColor) {
                    Column(
                        modifier = modifier
                            .widthIn(min = 280.dp, max = 560.dp)
                            .fillMaxWidth()
                            .heightIn(max = 560.dp)
                            .clip(shape)
                            .background(backgroundColor, shape)
                            .border(1.dp, borderColor, shape)
                            .padding(24.dp),
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

                        if (titleContent != null) {
                            ProvideTextStyle(Theme[textStyles][titleTextStyle]) {
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = if (icon == null) Alignment.CenterStart else Alignment.Center,
                                ) {
                                    titleContent()
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
