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
package com.composables.ui.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Indication
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.compose.ripple.rememberRippleIndication
import com.composables.interactioncapabilities.currentInteractionCapabilities
import com.composeunstyled.theme.ThemeComposable
import com.composeunstyled.theme.ThemeProperty
import com.composeunstyled.theme.ThemeToken
import com.composeunstyled.theme.buildTheme
import kotlin.jvm.JvmInline

val colors: ThemeProperty<Color> = ThemeProperty("colors")

/** Background color of a given screen. */
val backgroundColor: ThemeToken<Color> = ThemeToken("background")

/** Content color that sits directly on [backgroundColor]. */
val onBackgroundColor: ThemeToken<Color> = ThemeToken("on_background")

/** Background color for components such as panels, cards, dialogs, sheets, and menus. */
val panelColor: ThemeToken<Color> = ThemeToken("panel")

/** Content color that sits directly on [panelColor]. */
val onPanelColor: ThemeToken<Color> = ThemeToken("on_panel")

/** Lower-emphasis content color. */
val mutedColor: ThemeToken<Color> = ThemeToken("muted")

/** Color used for primary actions. */
val primaryColor: ThemeToken<Color> = ThemeToken("primary")

/** Content color that sits directly on [primaryColor]. */
val onPrimaryColor: ThemeToken<Color> = ThemeToken("on_primary")

/** Color used for secondary actions. */
val secondaryColor: ThemeToken<Color> = ThemeToken("secondary")

/** Content color that sits directly on [secondaryColor]. */
val onSecondaryColor: ThemeToken<Color> = ThemeToken("on_secondary")

/** Background color for controls, such as progressbar track. */
val controlColor: ThemeToken<Color> = ThemeToken("control")

/** Content color that sits directly on [controlColor]. */
val onControlColor: ThemeToken<Color> = ThemeToken("on_control")

/** Thumb color for controls such as sliders and toggles. */
val thumbColor: ThemeToken<Color> = ThemeToken("thumb_color")

/** Default [com.composables.ui.components.Switch] track color. */
val switchTrackColor: ThemeToken<Color> = ThemeToken("switch_track_color")

/** Selected [com.composables.ui.components.Switch] track color. */
val switchSelectedTrackColor: ThemeToken<Color> = ThemeToken("switch_selected_track_color")

/** Switch [com.composables.ui.components.Switch]'s Thumb color. */
val switchThumbColor: ThemeToken<Color> = ThemeToken("switch_thumb_color")

/** Background color for selected controls. */
val selectedControlColor: ThemeToken<Color> = ThemeToken("selected_control")

/** Content color that sits directly on [selectedControlColor]. */
val onSelectedControlColor: ThemeToken<Color> = ThemeToken("on_selected_control")

/** Color used for destructive actions such as deletion. */
val destructiveColor: ThemeToken<Color> = ThemeToken("destructive")

/** Content color that sits directly on [destructiveColor]. */
val onDestructiveColor: ThemeToken<Color> = ThemeToken("on_destructive")

/** Border and separator color. */
val borderColor: ThemeToken<Color> = ThemeToken("border")

/** Background color used for input fields such as text fields. */
val fieldColor: ThemeToken<Color> = ThemeToken("field")

/** Content color that sits directly on [fieldColor]. */
val onFieldColor: ThemeToken<Color> = ThemeToken("on_field")

/** Scrim color behind modal content. */
val scrimColor: ThemeToken<Color> = ThemeToken("scrim")

/** Focus ring color. */
val ringColor: ThemeToken<Color> = ThemeToken("ring")

val shapes: ThemeProperty<Shape> = ThemeProperty("shapes")

/** Small shape used by compact controls. */
val smallShape: ThemeToken<Shape> = ThemeToken("small_shape")

/** Medium shape used by medium-sized containers. */
val mediumShape: ThemeToken<Shape> = ThemeToken("medium_shape")

/** Large shape used by larger surfaces. */
val largeShape: ThemeToken<Shape> = ThemeToken("large_shape")

/** Shape for buttons. */
val buttonShape: ThemeToken<Shape> = ThemeToken("button_shape")

/** Shape for dialogs. */
val dialogShape: ThemeToken<Shape> = ThemeToken("dialog_shape")

/** Shape for bottom sheets. */
val sheetShape: ThemeToken<Shape> = ThemeToken("sheet_shape")

/** Shape for menus. */
val menuShape: ThemeToken<Shape> = ThemeToken("menu_shape")

/** Shape for text fields. */
val fieldShape: ThemeToken<Shape> = ThemeToken("field_shape")

val shadows: ThemeProperty<Shadow> = ThemeProperty("shadows")

/** Shadow for raised controls and surfaces. */
val raisedShadow: ThemeToken<Shadow> = ThemeToken("raised_shadow")

/** Shadow for overlay surfaces such as dialogs, sheets, and menus. */
val overlayShadow: ThemeToken<Shadow> = ThemeToken("overlay_shadow")

val indications: ThemeProperty<Indication> = ThemeProperty("indications")

/** Indication for controls. */
val defaultIndication: ThemeToken<Indication> = ThemeToken("default_indication")

/** Ripple indication for content on inverse or high-contrast surfaces. */
val inverseIndication: ThemeToken<Indication> = ThemeToken("inverse_indication")

/** Theme property that contains alpha tokens used by Composables UI components. */
val alphas: ThemeProperty<Float> = ThemeProperty("alphas")

/** Alpha value applied to disabled components. */
val disabledAlpha: ThemeToken<Float> = ThemeToken("disabled_alpha")

/** Theme property that contains text selection color tokens. */
val textSelectionColors: ThemeProperty<TextSelectionColors> = ThemeProperty("text_selection_colors")

/** Default handle and background colors for text selection. */
val defaultTextSelectionColors: ThemeToken<TextSelectionColors> =
  ThemeToken("default_text_selection_colors")

@Composable
private fun themeColor(useDarkColors: Boolean, light: Color, dark: Color): Color {
  return animateColorAsState(
    targetValue = if (useDarkColors) dark else light,
    animationSpec =
      tween(
        durationMillis = 180,
        easing = FastOutSlowInEasing,
      ),
  )
    .value
}

/** Default Composables UI theme. */
val ComposablesTheme: ThemeComposable = buildTheme {
  val useDarkColors = LocalColorScheme.current == ColorScheme.Dark
  val textSelectionHandleColor = if (useDarkColors) Color(0xFFE5E5E5) else Color.Black
  val textSelectionBackgroundColor = textSelectionHandleColor.copy(alpha = 0.24f)
  val interactionMode =
    LocalInteractionMode.current
      ?: if (currentInteractionCapabilities().hasPointer) InteractionMode.Pointer
      else InteractionMode.Touch

  val useTouchSizes = interactionMode == InteractionMode.Touch

  properties[colors] =
    mapOf(
      backgroundColor to
        themeColor(useDarkColors, light = Color(0xFFF4F4F4), dark = Color(0xFF0A0A0A)),
      onBackgroundColor to
        themeColor(useDarkColors, light = Color.Black, dark = Color(0xFFE5E5E5)),
      panelColor to themeColor(useDarkColors, light = Color.White, dark = Color(0xFF171717)),
      onPanelColor to themeColor(useDarkColors, light = Color.Black, dark = Color(0xFFE5E5E5)),
      mutedColor to
        themeColor(useDarkColors, light = Color(0xFF777777), dark = Color(0xFFA3A3A3)),
      primaryColor to themeColor(useDarkColors, light = Color.Black, dark = Color(0xFFF5F5F5)),
      onPrimaryColor to
        themeColor(useDarkColors, light = Color.White, dark = Color(0xFF171717)),
      secondaryColor to
        themeColor(useDarkColors, light = Color(0xFFF1F1F1), dark = Color(0xFF262626)),
      onSecondaryColor to
        themeColor(useDarkColors, light = Color.Black, dark = Color(0xFFE5E5E5)),
      controlColor to
        themeColor(useDarkColors, light = Color(0xFFE5E5E5), dark = Color(0xFF262626)),
      onControlColor to
        themeColor(useDarkColors, light = Color(0xFF777777), dark = Color(0xFFA3A3A3)),
      thumbColor to themeColor(useDarkColors, light = Color.White, dark = Color(0xFFE5E5E5)),
      switchTrackColor to
        themeColor(
          useDarkColors,
          light = Color(0xFFE4E4E7),
          dark = Color.White.copy(alpha = 0.05f),
        ),
      switchSelectedTrackColor to
        themeColor(
          useDarkColors,
          light = Color(0xFF18181B),
          dark = Color.White.copy(alpha = 0.25f),
        ),
      switchThumbColor to Color.White,
      selectedControlColor to
        themeColor(useDarkColors, light = Color(0xFFECECEC), dark = Color(0xFF404040)),
      onSelectedControlColor to
        themeColor(useDarkColors, light = Color.Black, dark = Color(0xFFF5F5F5)),
      destructiveColor to if (useDarkColors) Color(0xFFF87171) else Color(0xFFDC2626),
      onDestructiveColor to if (useDarkColors) Color(0xFF171717) else Color.White,
      borderColor to
        themeColor(useDarkColors, light = Color(0xFFE0E0E0), dark = Color(0xFF404040)),
      fieldColor to
        themeColor(useDarkColors, light = Color(0xFFF1F1F1), dark = Color(0xFF262626)),
      onFieldColor to themeColor(useDarkColors, light = Color.Black, dark = Color(0xFFE5E5E5)),
      scrimColor to Color.Black.copy(alpha = if (useDarkColors) 0.48f else 0.38f),
      ringColor to
        themeColor(
          useDarkColors,
          light = Color.Black.copy(alpha = 0.24f),
          dark = Color(0xFFE5E5E5).copy(alpha = 0.28f),
        ),
    )
  properties[textSelectionColors] =
    mapOf(
      com.composables.ui.theme.defaultTextSelectionColors to
        TextSelectionColors(
          handleColor = textSelectionHandleColor,
          backgroundColor = textSelectionBackgroundColor,
        ),
    )
  properties[shapes] =
    mapOf(
      smallShape to RoundedCornerShape(6.dp),
      mediumShape to RoundedCornerShape(12.dp),
      largeShape to RoundedCornerShape(16.dp),
      buttonShape to RoundedCornerShape(if (useTouchSizes) 100.dp else 10.dp),
      dialogShape to RoundedCornerShape(16.dp),
      sheetShape to RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
      menuShape to RoundedCornerShape(16.dp),
      fieldShape to RoundedCornerShape(if (useTouchSizes) 16.dp else 12.dp),
    )
  properties[shadows] =
    mapOf(
      raisedShadow to
        Shadow(
          radius = 16.dp,
          color = Color.Black,
          offset = DpOffset(x = 0.dp, y = 4.dp),
          alpha = 0.14f,
        ),
      overlayShadow to
        Shadow(
          radius = 24.dp,
          color = Color.Black,
          offset = DpOffset(x = 0.dp, y = 8.dp),
          alpha = if (useDarkColors) 0.36f else 0.22f,
        ),
    )
  properties[alphas] =
    mapOf(
      disabledAlpha to 0.33f,
    )

  val bodyStyle =
    TextStyle(
      fontSize = if (useTouchSizes) 17.sp else 15.sp,
      lineHeight = if (useTouchSizes) 24.sp else 20.sp,
      fontWeight = FontWeight.Normal,
    )
  defaultTextStyle = bodyStyle

  val defaultIndicationValue =
    rememberRippleIndication(
      if (useDarkColors) {
        Color(0xFFE5E5E5).copy(alpha = 0.10f)
      } else {
        Color.Black.copy(alpha = 0.08f)
      },
    )
  val inverseIndicationValue =
    rememberRippleIndication(
      if (useDarkColors) {
        Color(0xFF171717).copy(alpha = 0.10f)
      } else {
        Color.White.copy(alpha = 0.12f)
      },
    )
  this.defaultIndication = defaultIndicationValue
  properties[indications] =
    mapOf(
      com.composables.ui.theme.defaultIndication to defaultIndicationValue,
      inverseIndication to inverseIndicationValue,
    )

  extend { content ->
    CompositionLocalProvider(
      LocalInteractionMode provides interactionMode,
      LocalSpacingUnit provides 4.dp
    ) { content() }
  }
}

/**
 * Used by elements to choose colors that maintain a consistent look and feel across the
 * application.
 */
@JvmInline
value class ColorScheme internal constructor(@Suppress("unused") private val value: Int) {
  override fun toString() =
    when (this) {
      Light -> "Light"
      Dark -> "Dark"
      else -> "Error"
    }

  companion object {
    val Light = ColorScheme(0)
    val Dark = ColorScheme(1)
  }
}

/** The current [ColorScheme] to use in this composition. */
val LocalColorScheme: ProvidableCompositionLocal<ColorScheme> = staticCompositionLocalOf {
  ColorScheme.Light
}

/** Preferred interaction mode for Composables UI components. */
@JvmInline
value class InteractionMode internal constructor(@Suppress("unused") private val value: Int) {
  override fun toString() =
    when (this) {
      Touch -> "Touch"
      Pointer -> "Pointer"
      else -> "Error"
    }

  companion object {
    val Touch = InteractionMode(1)

    val Pointer = InteractionMode(2)
  }
}

/**
 * Information about the preferred interaction mode given the current device's capabilities.
 *
 * Components can use this information to adjust their sizing and behavior.
 *
 * For example, clickable components might want to grow bigger and rounder on
 * [InteractionMode.Touch] to make it simpler to press with a finger, while turning narrower and
 * sharper on [InteractionMode.Pointer] to save screen space.
 */
val LocalInteractionMode: ProvidableCompositionLocal<InteractionMode?> = staticCompositionLocalOf {
  null
}

val LocalSpacingUnit = staticCompositionLocalOf { 0.dp }

val Spacing: Dp
  @Composable
  get() = LocalSpacingUnit.current

@Composable
fun VerticalSpacing(times: Int) {
  Spacer(Modifier.height(Spacing * times))
}

@Composable
fun HorizontalSpacing(times: Int) {
  Spacer(Modifier.width(Spacing * times))
}
