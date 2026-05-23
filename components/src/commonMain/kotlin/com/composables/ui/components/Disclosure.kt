package com.composables.ui.components

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.composables.ui.theme.alphas
import com.composables.ui.theme.buttonHeight
import com.composables.ui.theme.buttonShape
import com.composables.ui.theme.colors
import com.composables.ui.theme.componentSizes
import com.composables.ui.theme.dim
import com.composables.ui.theme.disabledAlpha
import com.composables.ui.theme.focusRing
import com.composables.ui.theme.focusRingOffset
import com.composables.ui.theme.focusRingWidth
import com.composables.ui.theme.indications
import com.composables.ui.theme.muted
import com.composables.ui.theme.onSecondary
import com.composables.ui.theme.secondary
import com.composables.ui.theme.shapes
import com.composeunstyled.DisclosedContent
import com.composeunstyled.DisclosureButton
import com.composeunstyled.DisclosureScope as UnstyledDisclosureScope
import com.composeunstyled.LocalTextStyle
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.ProvideTextStyle
import com.composeunstyled.UnstyledDisclosure
import com.composeunstyled.buildModifier
import com.composeunstyled.outline
import com.composeunstyled.theme.Theme

@Stable
class DisclosureState(initiallyExpanded: Boolean = false) {
    var expanded by mutableStateOf(initiallyExpanded)
}

@Composable
fun AccordionPanel(
    modifier: Modifier = Modifier,
    shape: Shape = Theme[shapes][buttonShape],
    backgroundColor: Color = Theme[colors][secondary],
    contentColor: Color = Theme[colors][onSecondary],
    borderColor: Color = Color.Unspecified,
    contentPadding: PaddingValues = PaddingValues(12.dp),
    content: @Composable () -> Unit,
) {
    Column(
        modifier
            .fillMaxWidth()
            .clip(shape)
            .background(backgroundColor, shape)
            .then(buildModifier {
                if (borderColor.isSpecified && borderColor != Color.Transparent) {
                    add(Modifier.outline(1.dp, borderColor, shape))
                }
            })
            .padding(contentPadding),
    ) {
        ProvideContentColor(contentColor) {
            ProvideTextStyle(LocalTextStyle.current.merge(DisclosureBodyTextStyle)) {
                content()
            }
        }
    }
}

class DisclosureScope internal constructor(
    internal val unstyledScope: UnstyledDisclosureScope,
)

@Composable
fun rememberDisclosureState(initiallyExpanded: Boolean = false): DisclosureState {
    return remember(initiallyExpanded) { DisclosureState(initiallyExpanded) }
}

@Composable
fun Disclosure(
    state: DisclosureState,
    modifier: Modifier = Modifier,
    content: @Composable DisclosureScope.() -> Unit,
) {
    UnstyledDisclosure(
        expanded = state.expanded,
        onExpandedChange = { state.expanded = it },
        modifier = modifier,
    ) {
        DisclosureScope(this).content()
    }
}

@Composable
fun DisclosureScope.DisclosureHeading(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = Theme[shapes][buttonShape],
    backgroundColor: Color = Theme[colors][secondary],
    contentColor: Color = Theme[colors][onSecondary],
    borderColor: Color = Color.Unspecified,
    contentPadding: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit,
) {
    with(unstyledScope) {
        DisclosureButton(
            modifier = modifier
                .fillMaxWidth()
                .focusRing(
                    interactionSource = interactionSource,
                    width = Theme[componentSizes][focusRingWidth],
                    color = Theme[colors][focusRing],
                    shape = shape,
                    offset = Theme[componentSizes][focusRingOffset],
                )
                .clip(shape)
                .background(backgroundColor, shape)
                .then(buildModifier {
                    if (borderColor.isSpecified && borderColor != Color.Transparent) {
                        add(Modifier.outline(1.dp, borderColor, shape))
                    }
                    if (!enabled) {
                        add(Modifier.alpha(Theme[alphas][disabledAlpha]))
                    }
                }),
            enabled = enabled,
            contentPadding = PaddingValues(0.dp),
            indication = Theme[indications][dim],
            interactionSource = interactionSource,
            contentAlignment = Alignment.CenterStart,
        ) {
            ProvideContentColor(contentColor) {
                ProvideTextStyle(LocalTextStyle.current.merge(DisclosureButtonTextStyle)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = Theme[componentSizes][buttonHeight])
                            .padding(contentPadding),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        content = content,
                    )
                }
            }
        }
    }
}

@Composable
fun DisclosureScope.DisclosurePanel(
    modifier: Modifier = Modifier,
    contentColor: Color = Theme[colors][muted],
    enter: EnterTransition = expandVertically(animationSpec = spring()) + fadeIn(),
    exit: ExitTransition = shrinkVertically(animationSpec = spring()) + fadeOut(),
    content: @Composable () -> Unit,
) {
    with(unstyledScope) {
        DisclosedContent(
            modifier = modifier.fillMaxWidth(),
            enter = enter,
            exit = exit,
        ) {
            ProvideContentColor(contentColor) {
                ProvideTextStyle(LocalTextStyle.current.merge(DisclosureBodyTextStyle)) {
                    content()
                }
            }
        }
    }
}

private val DisclosureBodyTextStyle = TextStyle()

private val DisclosureButtonTextStyle = TextStyle(
    fontWeight = FontWeight.SemiBold,
)
