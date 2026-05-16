package com.composables.one.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.unit.dp
import com.composables.one.Sample
import com.composables.one.styling.card
import com.composables.one.styling.colors
import com.composables.one.styling.focusRing
import com.composables.one.styling.medium
import com.composables.one.styling.onCard
import com.composables.one.styling.onPrimary
import com.composables.one.styling.outline
import com.composables.one.styling.primary
import com.composables.one.styling.shadows
import com.composables.one.styling.shapes
import com.composables.one.styling.subtle
import com.composables.one.styling.textStyles
import com.composeunstyled.LocalTextStyle
import com.composeunstyled.ProvideTextStyle
import com.composeunstyled.minimumInteractiveComponentSize
import com.composeunstyled.outline
import com.composeunstyled.theme.Theme
import com.composeunstyled.RadioButton as UnstyledRadioButton
import com.composeunstyled.RadioGroup as UnstyledRadioGroup


@Sample("RadioGroupExample")
@Composable
fun RadioGroup(
    value: String?,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    title: (@Composable () -> Unit)? = null,
    contentDescription: String? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(modifier) {
        if (title != null) {
            ProvideTextStyle(Theme[textStyles][com.composables.one.styling.title]) {
                title()
                Spacer(Modifier.height(24.dp))
            }
        }
        UnstyledRadioGroup(
            value = value,
            onValueChange = onValueChange,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                content()
            }
        }
    }
}

@Composable
fun RadioButton(
    option: String,
    selected: Boolean,
    content: @Composable () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val focused by interactionSource.collectIsFocusedAsState()
    val outlineColor = if (focused) Theme[colors][focusRing] else Theme[colors][outline]

    UnstyledRadioButton(
        value = option,
        shape = Theme[shapes][medium],
        backgroundColor = Theme[colors][card],
        contentColor = Theme[colors][onCard],
        modifier = Modifier.fillMaxWidth().outline(2.dp, outlineColor, Theme[shapes][medium]).minimumInteractiveComponentSize(),
        contentPadding = PaddingValues(16.dp),
        interactionSource = interactionSource,
    ) {
        RadioIcon(selected)
        Spacer(Modifier.width(16.dp))
        Column {
            content()
        }
    }
}

@Composable
private fun RadioIcon(selected: Boolean) {
    Box(
        Modifier
            .size(20.dp)
            .dropShadow(shape = CircleShape, shadow = Theme[shadows][subtle])
            .outline(1.dp, Theme[colors][outline], CircleShape)
            .background(if (selected) Theme[colors][primary] else Theme[colors][card], CircleShape),
        contentAlignment = Alignment.Center
    ) {
        if (selected) {
            Box(
                Modifier
                    .size(8.dp)
                    .background(Theme[colors][onPrimary], CircleShape)
            )
        }
    }
}