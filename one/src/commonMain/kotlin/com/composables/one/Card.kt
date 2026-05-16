package com.composables.one

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.composables.one.Sample
import com.composables.one.styling.card
import com.composables.one.styling.colors
import com.composables.one.styling.medium
import com.composables.one.styling.onCard
import com.composables.one.styling.outline
import com.composables.one.styling.shapes
import com.composables.one.styling.textStyles
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.ProvideTextStyle
import com.composeunstyled.buildModifier
import com.composeunstyled.minimumInteractiveComponentSize
import com.composeunstyled.outline
import com.composeunstyled.theme.Theme

@Sample("CardExample")
@Sample("CardWithImageExample")
@Sample("CardWithTitleExample")
@Composable
fun Card(
    modifier: Modifier = Modifier,
    title: (@Composable () -> Unit)? = null,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    shape: Shape = Theme[shapes][medium],
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier.semantics(mergeDescendants = true) {}
            .outline(1.dp, Theme[colors][outline].copy(0.1f), shape)
            .clip(shape)
            .background(Theme[colors][card], shape) then buildModifier {
            if (onClick != null) {
                add(
                    Modifier
                        .clickable(onClick = onClick)
                        .minimumInteractiveComponentSize()
                )
            }
            add(Modifier.padding(contentPadding))
        },
    ) {
        ProvideContentColor(Theme[colors][onCard]) {
            if (title != null) {
                ProvideTextStyle(Theme[textStyles][com.composables.one.styling.title]) {
                    title()
                }
                Spacer(Modifier.height(12.dp))
            }
            content()
        }
    }
}
