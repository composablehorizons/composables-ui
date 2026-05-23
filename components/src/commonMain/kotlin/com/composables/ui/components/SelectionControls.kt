package com.composables.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.ui.theme.alphas
import com.composables.ui.theme.border
import com.composables.ui.theme.buttonHeight
import com.composables.ui.theme.colors
import com.composables.ui.theme.componentSizes
import com.composables.ui.theme.control
import com.composables.ui.theme.dim
import com.composables.ui.theme.disabledAlpha
import com.composables.ui.theme.focusRing
import com.composables.ui.theme.focusRingOffset
import com.composables.ui.theme.focusRingWidth
import com.composables.ui.theme.indications
import com.composables.ui.theme.onPanel
import com.composables.ui.theme.onSelectedControl
import com.composables.ui.theme.selectedControl
import com.composeunstyled.CheckedIndicator
import com.composeunstyled.LocalTextStyle
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.ProvideTextStyle
import com.composeunstyled.RadioButton as UnstyledRadioButton
import com.composeunstyled.RadioGroupScope as UnstyledRadioGroupScope
import com.composeunstyled.SwitchThumb
import com.composeunstyled.UnstyledCheckbox
import com.composeunstyled.UnstyledRadioGroup
import com.composeunstyled.UnstyledSwitch
import com.composeunstyled.buildModifier
import com.composeunstyled.theme.Theme

@kotlin.jvm.JvmInline
value class CheckboxState internal constructor(@Suppress("unused") private val value: Int) {
    companion object {
        val Unchecked = CheckboxState(0)
        val Checked = CheckboxState(1)
        val Indeterminate = CheckboxState(2)
    }
}

class RadioGroupScope<T> internal constructor(
    internal val unstyledScope: UnstyledRadioGroupScope,
    internal val value: T?,
)

@Composable
fun Checkbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    accessibilityLabel: String? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: (@Composable RowScope.() -> Unit)? = null,
) {
    CheckboxRow(checked, false, onCheckedChange, modifier, enabled, accessibilityLabel, interactionSource, content)
}

@Composable
fun TriStateCheckbox(
    state: CheckboxState,
    onStateChange: (CheckboxState) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    accessibilityLabel: String? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: (@Composable RowScope.() -> Unit)? = null,
) {
    CheckboxRow(
        checked = state != CheckboxState.Unchecked,
        mixed = state == CheckboxState.Indeterminate,
        onCheckedChange = {
            onStateChange(if (state == CheckboxState.Checked) CheckboxState.Unchecked else CheckboxState.Checked)
        },
        modifier = modifier,
        enabled = enabled,
        accessibilityLabel = accessibilityLabel,
        interactionSource = interactionSource,
        content = content,
    )
}

@Composable
private fun CheckboxRow(
    checked: Boolean,
    mixed: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier,
    enabled: Boolean,
    accessibilityLabel: String?,
    interactionSource: MutableInteractionSource,
    content: (@Composable RowScope.() -> Unit)?,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CheckboxBox(checked, mixed, onCheckedChange, enabled, accessibilityLabel, interactionSource)
        if (content != null) {
            ProvideTextStyle(LocalTextStyle.current.merge(SelectionControlTextStyle)) {
                ProvideContentColor(Theme[colors][onPanel]) {
                    content()
                }
            }
        }
    }
}

@Composable
private fun CheckboxBox(
    checked: Boolean,
    mixed: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    enabled: Boolean,
    accessibilityLabel: String?,
    interactionSource: MutableInteractionSource,
    size: Dp = 20.dp,
    shape: Shape = RoundedCornerShape(5.dp),
    indication: Indication? = Theme[indications][dim],
) {
    val backgroundColor = if (checked) Theme[colors][selectedControl] else Theme[colors][control]
    val contentColor = if (checked) Theme[colors][onSelectedControl] else Color.Transparent
    UnstyledCheckbox(
        checked = checked,
        onCheckedChange = onCheckedChange,
        enabled = enabled,
        accessibilityLabel = accessibilityLabel,
        interactionSource = interactionSource,
        indication = indication,
        modifier = Modifier
            .focusRing(interactionSource, Theme[componentSizes][focusRingWidth], Theme[colors][focusRing], shape, Theme[componentSizes][focusRingOffset])
            .clip(shape)
            .background(backgroundColor, shape)
            .border(1.dp, Theme[colors][border], shape)
            .size(size)
            .then(buildModifier { if (!enabled) add(Modifier.alpha(Theme[alphas][disabledAlpha])) }),
    ) {
        CheckedIndicator(enter = fadeIn(), exit = fadeOut(), modifier = Modifier.size(size)) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.size(size)) {
                if (mixed) {
                    Box(Modifier.width(10.dp).height(2.dp).background(contentColor, RoundedCornerShape(999.dp)))
                } else {
                    CheckMark(contentColor)
                }
            }
        }
    }
}

@Composable
private fun CheckMark(color: Color) {
    Canvas(Modifier.size(14.dp)) {
        val strokeWidth = 2.dp.toPx()
        drawLine(color, Offset(size.width * 0.2f, size.height * 0.52f), Offset(size.width * 0.42f, size.height * 0.74f), strokeWidth, cap = StrokeCap.Round)
        drawLine(color, Offset(size.width * 0.42f, size.height * 0.74f), Offset(size.width * 0.8f, size.height * 0.28f), strokeWidth, cap = StrokeCap.Round)
    }
}

@Composable
fun <T> RadioGroup(
    value: T?,
    onValueChange: (T) -> Unit,
    modifier: Modifier = Modifier,
    accessibilityLabel: String? = null,
    content: @Composable RadioGroupScope<T>.() -> Unit,
) {
    UnstyledRadioGroup(value, onValueChange, modifier, accessibilityLabel) {
        RadioGroupScope<T>(this, value).content()
    }
}

@Composable
fun <T> RadioGroupScope<T>.Radio(
    value: T,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: (@Composable RowScope.() -> Unit)? = null,
) {
    val selected = this.value == value
    val rowShape = RoundedCornerShape(8.dp)
    with(unstyledScope) {
        UnstyledRadioButton(
            value = value,
            enabled = enabled,
            interactionSource = interactionSource,
            indication = Theme[indications][dim],
            modifier = modifier
                .focusRing(
                    interactionSource,
                    Theme[componentSizes][focusRingWidth],
                    Theme[colors][focusRing],
                    rowShape,
                    Theme[componentSizes][focusRingOffset],
                )
                .clip(rowShape)
                .padding(2.dp)
                .then(buildModifier { if (!enabled) add(Modifier.alpha(Theme[alphas][disabledAlpha])) }),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Theme[colors][control], CircleShape)
                        .border(1.dp, Theme[colors][border], CircleShape)
                        .size(20.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    androidx.compose.animation.AnimatedVisibility(selected, enter = fadeIn(), exit = fadeOut()) {
                        Box(Modifier.size(10.dp).background(Theme[colors][onSelectedControl], CircleShape))
                    }
                }
                if (content != null) {
                    ProvideTextStyle(LocalTextStyle.current.merge(SelectionControlTextStyle)) {
                        ProvideContentColor(Theme[colors][onPanel]) {
                            content()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Switch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val trackShape = RoundedCornerShape(999.dp)
    UnstyledSwitch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        enabled = enabled,
        interactionSource = interactionSource,
        indication = Theme[indications][dim],
        modifier = modifier
            .focusRing(interactionSource, Theme[componentSizes][focusRingWidth], Theme[colors][focusRing], trackShape, Theme[componentSizes][focusRingOffset])
            .clip(trackShape)
            .background(if (checked) Theme[colors][selectedControl] else Theme[colors][control], trackShape)
            .border(1.dp, Theme[colors][border], trackShape)
            .size(width = 44.dp, height = 24.dp)
            .padding(2.dp)
            .then(buildModifier { if (!enabled) add(Modifier.alpha(Theme[alphas][disabledAlpha])) }),
    ) {
        SwitchThumb(animationSpec = androidx.compose.animation.core.spring()) {
            Box(Modifier.size(20.dp).background(if (checked) Theme[colors][onSelectedControl] else Theme[colors][onPanel], CircleShape))
        }
    }
}

@Composable
fun LabeledSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier.heightIn(min = Theme[componentSizes][buttonHeight]),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Switch(checked, onCheckedChange, enabled = enabled, interactionSource = interactionSource)
        ProvideTextStyle(LocalTextStyle.current.merge(SelectionControlTextStyle)) {
            ProvideContentColor(Theme[colors][onPanel]) {
                content()
            }
        }
        Spacer(Modifier.fillMaxWidth())
    }
}

private val SelectionControlTextStyle = TextStyle(
    fontSize = 16.sp,
    lineHeight = 24.sp,
)
