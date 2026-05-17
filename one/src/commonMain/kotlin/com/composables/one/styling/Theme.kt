package com.composables.one.styling

import androidx.compose.foundation.Indication
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composables.one.InteractionTarget
import com.composables.one.LocalInteractionTarget
import com.composeunstyled.platformtheme.buildPlatformTheme
import com.composeunstyled.theme.ComponentInteractiveSize
import com.composeunstyled.theme.ThemeProperty
import com.composeunstyled.theme.ThemeToken
import com.composeunstyled.platformtheme.bright as platformBright
import com.composeunstyled.platformtheme.dimmed as platformDimmed
import com.composeunstyled.platformtheme.indications as platformIndications
import com.composeunstyled.platformtheme.interactiveSizes as platformInteractiveSizes
import com.composeunstyled.platformtheme.sizeDefault as platformSizeDefault
import com.composeunstyled.platformtheme.sizeMinimum as platformSizeMinimum
import com.composeunstyled.platformtheme.text4 as platformText4
import com.composeunstyled.platformtheme.textStyles as platformTextStyles

// properties
val colors = ThemeProperty<Color>("colors")
val shapes = ThemeProperty<Shape>("shapes")
val textStyles = ThemeProperty<TextStyle>("textStyles")
val indications = ThemeProperty<Indication>("indications")
val componentSizes = ThemeProperty<Dp>("componentSizes")

// indications
val bright = ThemeToken<Indication>("bright")
val dim = ThemeToken<Indication>("dim")

// colors
val background = ThemeToken<Color>("background")
val onBackground = ThemeToken<Color>("on_background")
val primary = ThemeToken<Color>("primary")
val onPrimary = ThemeToken<Color>("on_primary")
val secondary = ThemeToken<Color>("secondary")
val onSecondary = ThemeToken<Color>("on_secondary")
val destructive = ThemeToken<Color>("destructive")
val onDestructive = ThemeToken<Color>("on_destructive")
val border = ThemeToken<Color>("border")

val buttonShape = ThemeToken<Shape>("button_shape")

val body = ThemeToken<TextStyle>("body")
val buttonLabel = ThemeToken<TextStyle>("button_label")

val buttonHeight = ThemeToken<Dp>("button_height")
val iconButtonSize = ThemeToken<Dp>("icon_button_size")

val OneTheme = buildPlatformTheme {
    defaultComponentInteractiveSize = when (LocalInteractionTarget.current) {
        InteractionTarget.NonTouch -> ComponentInteractiveSize(properties[platformInteractiveSizes][platformSizeMinimum])
        InteractionTarget.Touch -> ComponentInteractiveSize(properties[platformInteractiveSizes][platformSizeDefault])
    }

    properties[colors] = mapOf(
        background to Color(0xFFFAFAFA),
        onBackground to Color(0XFF0C0A09),
        primary to Color(0XFF0C0A09),
        onPrimary to Color.White,
        secondary to Color(0xFFf4f4f5),
        onSecondary to Color(0XFF0C0A09),
        destructive to Color(0xFFDC2626),
        onDestructive to Color.White,
        border to Color(0xFF1F2328).copy(alpha = 0.15f),
    )
    properties[shapes] = mapOf(
        buttonShape to RoundedCornerShape(6.dp),
    )
    properties[componentSizes] = mapOf(
        buttonHeight to 36.dp,
        iconButtonSize to 36.dp,
    )

    val bodyStyle = properties[platformTextStyles][platformText4]
    defaultTextStyle = bodyStyle

    properties[textStyles] = mapOf(
        body to bodyStyle,
        buttonLabel to bodyStyle.copy(fontWeight = FontWeight.Medium),
    )

    val brightIndication = properties[platformIndications][platformBright]
    defaultIndication = brightIndication
    properties[indications] = mapOf(
        bright to brightIndication,
        dim to properties[platformIndications][platformDimmed],
    )
}

internal fun Color.mutate(enabled: Boolean): Color {
    if (this == Color.Transparent || this == Color.Unspecified) return this
    return if (enabled) return this else this.copy(alpha = 0.6f)
}

internal fun isBright(color: Color): Boolean = color.luminance() > 0.5f
