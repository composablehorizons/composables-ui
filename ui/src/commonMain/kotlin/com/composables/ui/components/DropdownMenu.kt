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

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.platform.WindowInfo
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composables.ui.theme.InteractionMode
import com.composables.ui.theme.LocalInteractionMode
import com.composables.ui.theme.alphas
import com.composables.ui.theme.borderColor
import com.composables.ui.theme.colors
import com.composables.ui.theme.defaultIndication
import com.composables.ui.theme.destructiveColor
import com.composables.ui.theme.disabledAlpha
import com.composables.ui.theme.indications
import com.composables.ui.theme.menuShape
import com.composables.ui.theme.mutedColor
import com.composables.ui.theme.onPanelColor
import com.composables.ui.theme.panelColor
import com.composables.ui.theme.raisedShadow
import com.composables.ui.theme.secondaryColor
import com.composables.ui.theme.shadows
import com.composables.ui.theme.shapes
import com.composeunstyled.AnchorAlignment
import com.composeunstyled.AnchorSide
import com.composeunstyled.DropdownMenuPanelScope
import com.composeunstyled.LocalTextStyle
import com.composeunstyled.MenuItem
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.ProvideTextStyle
import com.composeunstyled.UnstyledDropdownMenu
import com.composeunstyled.outline
import com.composeunstyled.theme.Theme
import kotlin.jvm.JvmInline
import com.composeunstyled.DropdownMenuPanel as UnstyledDropdownMenuPanel
import com.composeunstyled.DropdownMenuScope as UnstyledDropdownMenuScope

private const val DropdownMenuEnterDurationMillis = 120
private const val DropdownMenuExitDurationMillis = 75

val LocalDropdownMenuWindowInfo = staticCompositionLocalOf<WindowInfo?> { null }

class DropdownMenuScope internal constructor(
  internal val unstyledScope: UnstyledDropdownMenuScope,
  internal val side: DropdownMenuSide,
  internal val alignment: DropdownMenuAlignment,
)

class DropdownMenuPanelContentScope internal constructor(
  internal val unstyledScope: DropdownMenuPanelScope,
)

/**
 * Side options for menu placement relative to its anchor.
 */
@JvmInline
value class DropdownMenuSide internal constructor(@Suppress("unused") private val value: Int) {
  internal val anchorSide: AnchorSide
    get() = when (this) {
      Top -> AnchorSide.Top
      Start -> AnchorSide.Start
      End -> AnchorSide.End
      else -> AnchorSide.Bottom
    }

  companion object {
    /**
     * Places the menu above its anchor.
     */
    val Top = DropdownMenuSide(0)

    /**
     * Places the menu below its anchor.
     */
    val Bottom = DropdownMenuSide(1)

    /**
     * Places the menu before its anchor in the layout direction.
     */
    val Start = DropdownMenuSide(2)

    /**
     * Places the menu after its anchor in the layout direction.
     */
    val End = DropdownMenuSide(3)
  }
}

/**
 * Alignment options for menu placement along the anchor edge.
 */
@JvmInline
value class DropdownMenuAlignment internal constructor(@Suppress("unused") private val value: Int) {
  internal val anchorAlignment: AnchorAlignment
    get() = when (this) {
      Center -> AnchorAlignment.Center
      End -> AnchorAlignment.End
      else -> AnchorAlignment.Start
    }

  companion object {
    /**
     * Aligns the panel to the start edge of the anchor.
     */
    val Start = DropdownMenuAlignment(0)

    /**
     * Centers the panel against the anchor.
     */
    val Center = DropdownMenuAlignment(1)

    /**
     * Aligns the panel to the end edge of the anchor.
     */
    val End = DropdownMenuAlignment(2)
  }
}

/**
 * Visual style variants for dropdown menu items.
 */
@JvmInline
value class DropdownMenuItemStyle internal constructor(@Suppress("unused") private val value: Int) {
  internal val destructive: Boolean
    get() = this == Destructive

  companion object {
    /**
     * Uses the default menu item colors.
     */
    val Default = DropdownMenuItemStyle(0)

    /**
     * Uses destructive emphasis for dangerous actions.
     */
    val Destructive = DropdownMenuItemStyle(1)
  }
}

/**
 * An anchored dropdown menu with a trigger and floating panel.
 * @param expanded Whether the dropdown menu is currently open.
 * @param onExpandedChange Called when the dropdown menu should open or close.
 * @param modifier Modifier applied to the component.
 * @param side Side of the anchor where the menu panel should appear.
 * @param alignment Alignment of the menu panel along the chosen side.
 * @param sideOffset Distance between the menu panel and its anchor.
 * @param alignmentOffset Offset applied along the anchor edge.
 * @param panel Composable menu panel content displayed by the dropdown.
 * @param anchor Composable anchor that opens the dropdown menu.
 */
@Composable
fun DropdownMenu(
  expanded: Boolean,
  onExpandedChange: (Boolean) -> Unit,
  modifier: Modifier = Modifier,
  side: DropdownMenuSide = DropdownMenuSide.Bottom,
  alignment: DropdownMenuAlignment = DropdownMenuAlignment.Start,
  sideOffset: Dp = 4.dp,
  alignmentOffset: Dp = 0.dp,
  panel: @Composable DropdownMenuScope.() -> Unit,
  anchor: @Composable () -> Unit,
) {
  val dropdownMenuWindowInfo = LocalDropdownMenuWindowInfo.current
  val dropdownMenuContent = @Composable {
    UnstyledDropdownMenu(
      expanded = expanded,
      onExpandedChange = onExpandedChange,
      modifier = modifier,
      side = side.anchorSide,
      alignment = alignment.anchorAlignment,
      sideOffset = sideOffset,
      alignmentOffset = alignmentOffset,
      panel = {
        DropdownMenuScope(
          unstyledScope = this,
          side = side,
          alignment = alignment,
        ).panel()
      },
      anchor = anchor,
    )
  }

  if (dropdownMenuWindowInfo == null) {
    dropdownMenuContent()
  } else {
    CompositionLocalProvider(LocalWindowInfo provides dropdownMenuWindowInfo) {
      dropdownMenuContent()
    }
  }
}

/**
 * A styled dropdown panel surface.
 * @param modifier Modifier applied to the component.
 * @param shape Shape used for the dropdown panel.
 * @param backgroundColor Background color used for the menu panel.
 * @param contentColor Color used for menu content.
 * @param shadow Shadow applied to the menu panel.
 * @param minWidth Minimum width used for the menu panel.
 * @param maxWidth Maximum width used for the menu panel.
 * @param enter Transition used when the menu panel appears.
 * @param exit Transition used when the menu panel disappears.
 * @param content Composable content displayed by the component.
 */
@Composable
fun DropdownMenuScope.DropdownMenuPanel(
  modifier: Modifier = Modifier,
  shape: Shape = Theme[shapes][menuShape],
  backgroundColor: Color = Theme[colors][panelColor],
  contentColor: Color = Theme[colors][onPanelColor],
  shadow: Shadow = Theme[shadows][raisedShadow],
  minWidth: Dp = 160.dp,
  maxWidth: Dp = 320.dp,
  enter: EnterTransition? = null,
  exit: ExitTransition? = null,
  content: @Composable DropdownMenuPanelContentScope.() -> Unit,
) {
  val transformOrigin = dropdownMenuTransformOrigin(
    side = side,
    alignment = alignment,
  )
  val enterTransition = enter ?: dropdownMenuEnterTransition(transformOrigin)
  val exitTransition = exit ?: dropdownMenuExitTransition(transformOrigin)

  with(unstyledScope) {
    UnstyledDropdownMenuPanel(
      modifier = modifier
        .dropShadow(shape, shadow)
        .width(IntrinsicSize.Max)
        .widthIn(min = minWidth, max = maxWidth)
        .outline(1.dp, Theme[colors][borderColor], shape)
        .clip(shape)
        .background(backgroundColor, shape)
        .padding(6.dp),
      enter = enterTransition,
      exit = exitTransition,
    ) {
      ProvideContentColor(contentColor) {
        ProvideTextStyle(LocalTextStyle.current.merge(DropdownMenuTextStyle)) {
          val panelScope = this@UnstyledDropdownMenuPanel
          DropdownMenuPanelContentScope(panelScope).content()
        }
      }
    }
  }
}

private fun dropdownMenuEnterTransition(transformOrigin: TransformOrigin): EnterTransition {
  return scaleIn(
    animationSpec = tween(
      durationMillis = DropdownMenuEnterDurationMillis,
      easing = LinearOutSlowInEasing,
    ),
    initialScale = 0.96f,
    transformOrigin = transformOrigin,
  ) + fadeIn(tween(durationMillis = 30))
}

private fun dropdownMenuExitTransition(transformOrigin: TransformOrigin): ExitTransition {
  return scaleOut(
    animationSpec = tween(durationMillis = 1, delayMillis = DropdownMenuExitDurationMillis),
    targetScale = 1f,
    transformOrigin = transformOrigin,
  ) + fadeOut(tween(durationMillis = DropdownMenuExitDurationMillis))
}

private fun dropdownMenuTransformOrigin(
  side: DropdownMenuSide,
  alignment: DropdownMenuAlignment,
): TransformOrigin {
  return when (side) {
    DropdownMenuSide.Top -> TransformOrigin(
      pivotFractionX = alignment.transformOriginFraction,
      pivotFractionY = 1f,
    )

    DropdownMenuSide.Bottom -> TransformOrigin(
      pivotFractionX = alignment.transformOriginFraction,
      pivotFractionY = 0f,
    )

    DropdownMenuSide.Start -> TransformOrigin(
      pivotFractionX = 1f,
      pivotFractionY = alignment.transformOriginFraction,
    )

    else -> TransformOrigin(
      pivotFractionX = 0f,
      pivotFractionY = alignment.transformOriginFraction,
    )
  }
}

private val DropdownMenuAlignment.transformOriginFraction: Float
  get() = when (this) {
    DropdownMenuAlignment.Start -> 0f
    DropdownMenuAlignment.Center -> 0.5f
    else -> 1f
  }

/**
 * A selectable action inside a dropdown menu.
 * @param onClick Called when the menu item is selected.
 * @param modifier Modifier applied to the component.
 * @param enabled Whether the item can be interacted with.
 * @param closeOnClick Whether clicking the item should close the menu.
 * @param style Visual style used by the menu item.
 * @param leading Optional leading content shown before the menu item label.
 * @param trailing Optional trailing content shown after the menu item label.
 * @param content Composable content displayed by the component.
 */
@Composable
fun DropdownMenuPanelContentScope.DropdownMenuItem(
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  closeOnClick: Boolean = true,
  style: DropdownMenuItemStyle = DropdownMenuItemStyle.Default,
  leading: (@Composable () -> Unit)? = null,
  trailing: (@Composable () -> Unit)? = null,
  content: @Composable RowScope.() -> Unit,
) {
  val interactionSource = remember { MutableInteractionSource() }
  val hovered by interactionSource.collectIsHoveredAsState()
  val focused by interactionSource.collectIsFocusedAsState()
  val pressed by interactionSource.collectIsPressedAsState()
  val active = enabled && (hovered || focused || pressed)
  val itemShape = RoundedCornerShape(12.dp)
  val contentColor = if (style.destructive) Theme[colors][destructiveColor] else Theme[colors][onPanelColor]

  with(unstyledScope) {
    MenuItem(
      onClick = onClick,
      enabled = enabled,
      closeOnClick = closeOnClick,
      interactionSource = interactionSource,
      indication = Theme[indications][defaultIndication],
      modifier = modifier
        .clip(itemShape)
        .background(if (active) Theme[colors][secondaryColor] else Color.Transparent, itemShape)
        .alpha(if (enabled) 1f else Theme[alphas][disabledAlpha]),
    ) {
      ProvideContentColor(contentColor) {
        Row(
          modifier = Modifier
            .heightIn(min = dropdownMenuItemMinHeight())
            .padding(horizontal = 12.dp, vertical = 6.dp),
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          verticalAlignment = Alignment.CenterVertically,
        ) {
          if (leading != null) {
            Box(
              modifier = Modifier.size(16.dp),
              contentAlignment = Alignment.Center,
            ) {
              leading()
            }
          }
          Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            content = content,
          )
          if (trailing != null) {
            ProvideContentColor(Theme[colors][mutedColor]) {
              trailing()
            }
          }
        }
      }
    }
  }
}

/**
 * A non-interactive label row for grouping menu items.
 * @param modifier Modifier applied to the component.
 * @param contentColor Color used for menu content.
 * @param content Composable content displayed by the component.
 */
@Composable
fun DropdownMenuPanelContentScope.DropdownMenuLabel(
  modifier: Modifier = Modifier,
  contentColor: Color = Theme[colors][mutedColor],
  content: @Composable RowScope.() -> Unit,
) {
  ProvideContentColor(contentColor) {
    ProvideTextStyle(LocalTextStyle.current.merge(DropdownMenuLabelTextStyle)) {
      Row(
        modifier = modifier
          .padding(horizontal = 8.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        content = content,
      )
    }
  }
}

private val DropdownMenuTextStyle = TextStyle()

private val DropdownMenuLabelTextStyle = TextStyle(
  fontWeight = FontWeight.SemiBold,
)

@Composable
private fun dropdownMenuItemMinHeight(): Dp {
  return if (LocalInteractionMode.current == InteractionMode.Touch) 48.dp else 36.dp
}

/**
 * A visual separator between menu groups.
 * @param modifier Modifier applied to the component.
 */
@Composable
fun DropdownMenuPanelContentScope.DropdownMenuSeparator(
  modifier: Modifier = Modifier,
) {
  Spacer(
    modifier = modifier
      .padding(vertical = 4.dp)
      .height(1.dp)
      .background(Theme[colors][borderColor]),
  )
}
