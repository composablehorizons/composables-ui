/*
 * Copyright (c) 2026 Composable Horizons
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.composables.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.composables.ui.theme.InteractionMode
import com.composables.ui.theme.LocalInteractionMode
import com.composables.ui.theme.alphas
import com.composables.ui.theme.borderColor
import com.composables.ui.theme.colors
import com.composables.ui.theme.defaultIndication
import com.composables.ui.theme.disabledAlpha
import com.composables.ui.theme.indications
import com.composables.ui.theme.mutedColor
import com.composables.ui.theme.primaryColor
import com.composables.ui.theme.ringColor
import com.composeunstyled.LocalTextStyle
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.ProvideTextStyle
import com.composeunstyled.Stack
import com.composeunstyled.StackOrientation
import com.composeunstyled.StackScope
import com.composeunstyled.UnstyledTab
import com.composeunstyled.UnstyledTabList
import com.composeunstyled.UnstyledTabPanel
import com.composeunstyled.buildModifier
import com.composeunstyled.theme.Theme
import kotlin.jvm.JvmInline
import com.composeunstyled.UnstyledTabGroup as UnstyledTabs

class TabsScope<T> internal constructor(
  internal val selectedTab: T,
  internal val orderedTabs: List<T>,
)

/**
 * An individual tab inside a tab list.
 * @param key Key represented by this tab or tab panel.
 * @param modifier Modifier applied to the component.
 * @param enabled Whether the tab can be interacted with.
 * @param activateOnFocus Whether focusing the tab should immediately select it.
 * @param interactionSource Interaction source used for focus, hover, and press state.
 * @param indication Indication used when the tab is pressed or focused.
 * @param icon Optional icon content shown before the tab text.
 * @param text Text content shown inside the tab.
 */
@Composable
fun <T> Tab(
  key: T,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  activateOnFocus: Boolean = true,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  indication: Indication? = Theme[indications][defaultIndication],
  icon: (@Composable () -> Unit)? = null,
  text: @Composable () -> Unit,
) {
  val shape = RoundedCornerShape(4.dp)
  val updateTabIndicatorBounds = LocalTabIndicatorBoundsUpdater.current
  UnstyledTab(
    key = key,
    enabled = enabled,
    activateOnFocus = activateOnFocus,
    interactionSource = interactionSource,
    indication = indication,
    contentAlignment = Alignment.Center,
    modifier = modifier
      .then(
        buildModifier {
          if (updateTabIndicatorBounds != null) {
            add(Modifier.onGloballyPositioned { updateTabIndicatorBounds(key, it) })
          }
          if (!enabled) add(Modifier.alpha(Theme[alphas][disabledAlpha]))
        },
      )
      .bouncyPress(
        interactionSource = interactionSource,
        enabled = enabled,
      )
      .focusRing(
        interactionSource = interactionSource,
        color = Theme[colors][ringColor],
        shape = shape,
      )
      .clip(shape),
  ) {
    Row(
      modifier = Modifier
        .heightIn(min = tabMinHeight())
        .padding(horizontal = TabContentHorizontalPadding),
      horizontalArrangement = Arrangement.spacedBy(2.dp),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      ProvideContentColor(if (selected) Theme[colors][primaryColor] else Theme[colors][mutedColor]) {
        if (icon != null) {
          Box(
            modifier = Modifier.size(TabIconSize),
            contentAlignment = Alignment.Center,
          ) {
            icon()
          }
          Box(Modifier.width(TabIconSpacing))
        }
        ProvideTextStyle(LocalTextStyle.current.merge(TabTextStyle)) {
          text()
        }
      }
    }
  }
}

/**
 * Orientation options for arranging tab lists.
 */
@JvmInline
value class TabOrientation internal constructor(internal val orientation: Orientation) {
  companion object {
    /**
     * Arranges tabs in a horizontal row.
     */
    val Horizontal = TabOrientation(Orientation.Horizontal)

    /**
     * Arranges tabs in a vertical column.
     */
    val Vertical = TabOrientation(Orientation.Vertical)
  }
}

/**
 * Tabs that coordinate tab triggers and panels.
 * @param selectedTab Currently selected tab key.
 * @param onSelectedTabChange Called when the selected tab changes.
 * @param orderedTabs Ordered list of tab keys used for indicator placement and navigation.
 * @param modifier Modifier applied to the component.
 * @param content Composable content displayed by the component.
 */
@Composable
fun <T> Tabs(
  selectedTab: T,
  onSelectedTabChange: (T) -> Unit,
  orderedTabs: List<T>,
  modifier: Modifier = Modifier,
  content: @Composable TabsScope<T>.() -> Unit,
) {
  UnstyledTabs(selectedTab, onSelectedTabChange, orderedTabs, modifier) {
    TabsScope(
      selectedTab = selectedTab,
      orderedTabs = orderedTabs,
    ).content()
  }
}

/**
 * A container that arranges tab triggers and draws the active indicator.
 * @param modifier Modifier applied to the component.
 * @param orientation Orientation used to lay out the tab list.
 * @param dividerColor Color used for the indicator divider line.
 * @param content Composable content displayed by the component.
 */
@Composable
fun <T> TabsScope<T>.TabList(
  modifier: Modifier = Modifier,
  orientation: TabOrientation = TabOrientation.Horizontal,
  dividerColor: Color = Theme[colors][borderColor],
  content: @Composable StackScope.() -> Unit,
) {
  val tabIndicatorBoundsByKey = remember { mutableStateMapOf<Any?, TabIndicatorBounds>() }
  var indicatorContainerCoordinates by remember { mutableStateOf<LayoutCoordinates?>(null) }
  var indicatorContainerSize by remember { mutableStateOf(IntSize.Zero) }
  val density = LocalDensity.current
  val updateTabIndicatorBounds: (Any?, LayoutCoordinates) -> Unit = { key, tabCoordinates ->
    val containerCoordinates = indicatorContainerCoordinates
    if (containerCoordinates != null && containerCoordinates.isAttached && tabCoordinates.isAttached) {
      if (orderedTabs.contains(key)) {
        val tabPosition = containerCoordinates.localPositionOf(tabCoordinates, Offset.Zero)
        tabIndicatorBoundsByKey[key] = with(density) {
          if (orientation == TabOrientation.Horizontal) {
            TabIndicatorBounds(
              start = tabPosition.x.toDp(),
              size = tabCoordinates.size.width.toDp(),
            )
          } else {
            TabIndicatorBounds(
              start = tabPosition.y.toDp(),
              size = tabCoordinates.size.height.toDp(),
            )
          }
        }
      }
    }
  }
  val indicatorBounds = resolveTabIndicatorBounds(
    selectedTab = selectedTab,
    tabBoundsByKey = tabIndicatorBoundsByKey,
  )
  val tabListWidth = with(density) { indicatorContainerSize.width.toDp() }
  val tabListHeight = with(density) { indicatorContainerSize.height.toDp() }

  if (orientation == TabOrientation.Horizontal) {
    Column(modifier = modifier) {
      Box(
        modifier = Modifier.onGloballyPositioned {
          indicatorContainerCoordinates = it
          indicatorContainerSize = it.size
        },
      ) {
        UnstyledTabList(
          orientation = orientation.orientation,
        ) {
          Stack(orientation = orientation.toStackOrientation()) {
            TabIndicatorBoundsProvider(updateTabIndicatorBounds) {
              content()
            }
          }
        }
      }
      Spacer(Modifier.height(TabListBottomPadding))
      TabIndicatorLine(
        modifier = Modifier.width(tabListWidth),
        bounds = indicatorBounds,
        dividerColor = dividerColor,
      )
    }
  } else {
    Row(modifier = modifier.height(IntrinsicSize.Min)) {
      Box(
        modifier = Modifier.onGloballyPositioned {
          indicatorContainerCoordinates = it
          indicatorContainerSize = it.size
        },
      ) {
        UnstyledTabList(
          orientation = orientation.orientation,
        ) {
          Stack(orientation = orientation.toStackOrientation()) {
            TabIndicatorBoundsProvider(updateTabIndicatorBounds) {
              content()
            }
          }
        }
      }
      Spacer(Modifier.width(TabListBottomPadding))
      VerticalTabIndicatorLine(
        modifier = Modifier.height(tabListHeight),
        bounds = indicatorBounds,
        dividerColor = dividerColor,
      )
    }
  }
}

private fun TabOrientation.toStackOrientation(): StackOrientation {
  return if (this == TabOrientation.Horizontal) StackOrientation.Horizontal else StackOrientation.Vertical
}

private val LocalTabIndicatorBoundsUpdater =
  androidx.compose.runtime.compositionLocalOf<((Any?, LayoutCoordinates) -> Unit)?> { null }

@Composable
private fun TabIndicatorBoundsProvider(
  updateTabIndicatorBounds: ((Any?, LayoutCoordinates) -> Unit)? = null,
  content: @Composable () -> Unit,
) {
  androidx.compose.runtime.CompositionLocalProvider(
    LocalTabIndicatorBoundsUpdater provides updateTabIndicatorBounds,
  ) {
    content()
  }
}

@Composable
private fun TabIndicatorLine(
  modifier: Modifier = Modifier,
  bounds: TabIndicatorBounds?,
  dividerColor: Color,
) {
  val placedBounds = placedTabIndicatorBounds(
    bounds = bounds,
    startLabel = "TabIndicatorStart",
    sizeLabel = "TabIndicatorWidth",
  )

  Box(
    modifier = modifier
      .height(TabIndicatorHeight)
      .background(dividerColor, TabIndicatorShape),
  ) {
    if (bounds != null) {
      Box(
        modifier = Modifier
          .offset(x = placedBounds.start)
          .width(placedBounds.size)
          .height(TabIndicatorHeight)
          .background(
            color = Theme[colors][primaryColor],
            shape = TabIndicatorShape,
          ),
      )
    }
  }
}

@Composable
private fun VerticalTabIndicatorLine(
  modifier: Modifier = Modifier,
  bounds: TabIndicatorBounds?,
  dividerColor: Color,
) {
  val placedBounds = placedTabIndicatorBounds(
    bounds = bounds,
    startLabel = "VerticalTabIndicatorStart",
    sizeLabel = "VerticalTabIndicatorHeight",
  )

  Box(
    modifier = modifier
      .width(TabIndicatorHeight)
      .background(dividerColor, TabIndicatorShape),
  ) {
    if (bounds != null) {
      Box(
        modifier = Modifier
          .offset(y = placedBounds.start)
          .width(TabIndicatorHeight)
          .height(placedBounds.size)
          .background(
            color = Theme[colors][primaryColor],
            shape = TabIndicatorShape,
          ),
      )
    }
  }
}

@Composable
private fun placedTabIndicatorBounds(
  bounds: TabIndicatorBounds?,
  startLabel: String,
  sizeLabel: String,
): TabIndicatorBounds {
  var initialBoundsPlaced by remember { mutableStateOf(false) }
  if (bounds == null) {
    SideEffect { initialBoundsPlaced = false }
    return TabIndicatorBounds(start = 0.dp, size = 0.dp)
  }

  if (!initialBoundsPlaced) {
    SideEffect { initialBoundsPlaced = true }
    return bounds
  }

  return TabIndicatorBounds(
    start = animateTabIndicatorDp(bounds.start, startLabel),
    size = animateTabIndicatorDp(bounds.size, sizeLabel),
  )
}

@Composable
private fun animateTabIndicatorDp(
  targetValue: Dp,
  label: String,
): Dp {
  val animatedValue by animateDpAsState(
    targetValue = targetValue,
    animationSpec = tween(durationMillis = 180),
    label = label,
  )
  return animatedValue
}

private fun resolveTabIndicatorBounds(
  selectedTab: Any?,
  tabBoundsByKey: Map<Any?, TabIndicatorBounds>,
): TabIndicatorBounds? {
  return tabBoundsByKey[selectedTab]
}

private data class TabIndicatorBounds(
  val start: Dp,
  val size: Dp,
)

private val TabTextStyle = TextStyle(
  fontWeight = FontWeight.SemiBold,
)

private val TabContentHorizontalPadding = 12.dp
private val TabIconSize = 24.dp
private val TabIconSpacing = 8.dp
private val TabIndicatorHeight = 1.dp
private val TabIndicatorShape = RoundedCornerShape(100)
private val TabListBottomPadding = 2.dp

@Composable
private fun tabMinHeight(): Dp {
  return if (LocalInteractionMode.current == InteractionMode.Touch) 48.dp else 36.dp
}

/**
 * Content associated with a specific tab key.
 * @param key Key represented by this tab or tab panel.
 * @param modifier Modifier applied to the component.
 * @param content Composable content displayed by the component.
 */
@Composable
fun <T> TabsScope<T>.TabPanel(
  key: T,
  modifier: Modifier = Modifier,
  content: @Composable () -> Unit,
) {
  UnstyledTabPanel(key = key, modifier = modifier) {
    content()
  }
}
