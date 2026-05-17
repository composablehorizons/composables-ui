package com.composables.one.styling

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Indication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.composables.one.styling.Appearance.Dark
import com.composables.one.styling.Appearance.Light
import com.composables.one.styling.Appearance.System
import com.composeunstyled.platformtheme.buildPlatformTheme
import com.composeunstyled.platformtheme.bright as platformBright
import com.composeunstyled.platformtheme.dimmed as platformDimmed
import com.composeunstyled.platformtheme.heading6 as platformHeading6
import com.composeunstyled.platformtheme.indications as platformIndications
import com.composeunstyled.platformtheme.interactiveSizes as platformInteractiveSizes
import com.composeunstyled.platformtheme.roundedLarge as platformRoundedLarge
import com.composeunstyled.platformtheme.roundedMedium as platformRoundedMedium
import com.composeunstyled.platformtheme.roundedSmall as platformRoundedSmall
import com.composeunstyled.platformtheme.shapes as platformShapes
import com.composeunstyled.platformtheme.sizeDefault as platformSizeDefault
import com.composeunstyled.platformtheme.sizeMinimum as platformSizeMinimum
import com.composeunstyled.platformtheme.text4 as platformText4
import com.composeunstyled.platformtheme.text7 as platformText7
import com.composeunstyled.platformtheme.textStyles as platformTextStyles
import com.composeunstyled.theme.ComponentInteractiveSize
import com.composeunstyled.theme.ThemeProperty
import com.composeunstyled.theme.ThemeToken

// properties
val colors = ThemeProperty<Color>("colors")
val shapes = ThemeProperty<Shape>("shapes")
val textStyles = ThemeProperty<TextStyle>("textStyles")
val indications = ThemeProperty<Indication>("indications")
val shadows = ThemeProperty<Shadow>("shadows")

// indications
val bright = ThemeToken<Indication>("bright")
val dim = ThemeToken<Indication>("dim")

// shadow levels
val subtle = ThemeToken<Shadow>("subtle")
val elevated = ThemeToken<Shadow>("elevated")
val modal = ThemeToken<Shadow>("modal")

// colors
val background = ThemeToken<Color>("background")
val onBackground = ThemeToken<Color>("on_background")
val accent = ThemeToken<Color>("accent")
val onAccent = ThemeToken<Color>("on_accent")
val primary = ThemeToken<Color>("primary")
val onPrimary = ThemeToken<Color>("on_primary")
val secondary = ThemeToken<Color>("secondary")
val onSecondary = ThemeToken<Color>("on_secondary")
val card = ThemeToken<Color>("card")
val onCard = ThemeToken<Color>("on_card")
val destructive = ThemeToken<Color>("destructive")
val onDestructive = ThemeToken<Color>("on_destructive")
val outline = ThemeToken<Color>("outline")
val focusRing = ThemeToken<Color>("focus_ring")
val navigation = ThemeToken<Color>("navigation")
val onNavigation = ThemeToken<Color>("on_navigation")
val scrim = ThemeToken<Color>("scrim")

val small = ThemeToken<Shape>("small")
val medium = ThemeToken<Shape>("medium")
val bottomSheet = ThemeToken<Shape>("bottomSheet")

val title = ThemeToken<TextStyle>("title")
val body = ThemeToken<TextStyle>("body")
val caption = ThemeToken<TextStyle>("caption")


enum class Appearance {
    Light, Dark, System
}

class AppearanceController {
    var appearance by mutableStateOf<Appearance>(Appearance.System)
}

val LocalAppearanceController = compositionLocalOf { AppearanceController() }

@Composable
private fun animateColorPalette(targetPalette: Map<ThemeToken<Color>, Color>): Map<ThemeToken<Color>, Color> {
    return targetPalette.mapValues { (_, color) ->
        animateColorAsState(targetValue = color, animationSpec = tween(450)).value
    }
}

val LightPalette = mapOf(
    background to Color(0xFFFAFAFA),
    onBackground to Color(0XFF0C0A09),
    accent to Color(0xFF3B82F6),
    onAccent to Color.White,
    primary to Color(0XFF0C0A09),
    onPrimary to Color.White,
    secondary to Color(0xFFf4f4f5),
    onSecondary to Color(0xFF1c1917),

    outline to Color(0XFF09090b).copy(alpha = 0.2f),
    card to Color.White,
    onCard to Color(0xFF18181B),
    destructive to Color(0xFFDC2626),
    onDestructive to Color.White,
    focusRing to Color(0xFF3B82F6).copy(alpha = 0.8f),

    navigation to Color(0xFFf4f4f5),
    onNavigation to Color(0xFF1c1917),

    scrim to Color.Black.copy(alpha = 0.6f),
)

val DarkPalette = mapOf(
    background to Color(0xFF27272A),
    onBackground to Color(0xFFF4F4F5),
    accent to Color(0xFF3B82F6),
    onAccent to Color.White,
    primary to Color(0xFFF4F4F5),
    onPrimary to Color(0xFF18181B),
    secondary to Color(0xFF3F3F46),
    onSecondary to Color(0xFFF4F4F5),

    outline to Color(0xFF52525B),
    card to Color(0xFF3F3F46),
    onCard to Color(0xFFF4F4F5),
    destructive to Color(0xFFEF4444),
    onDestructive to Color.White,
    focusRing to Color(0xFF3B82F6).copy(alpha = 0.8f),

    navigation to Color(0xFF3F3F46),
    onNavigation to Color(0xFFF4F4F5),

    scrim to Color.Black.copy(alpha = 0.6f),
)


val OneTheme = buildPlatformTheme {
    defaultComponentInteractiveSize = ComponentInteractiveSize(
        nonTouchInteractionSize = properties[platformInteractiveSizes][platformSizeMinimum],
        touchInteractionSize = properties[platformInteractiveSizes][platformSizeDefault],
    )
    defaultTextSelectionColors = rememberTextSelectionColors(Color(0xFF3B82F6))

    val targetPalette = when (LocalAppearanceController.current.appearance) {
        Light -> LightPalette
        Dark -> DarkPalette
        System -> if (isSystemInDarkTheme()) DarkPalette else LightPalette
    }

    properties[colors] = animateColorPalette(targetPalette)
    properties[shapes] = mapOf(
        small to properties[platformShapes][platformRoundedMedium],
        medium to properties[platformShapes][platformRoundedLarge],
        bottomSheet to properties[platformShapes][platformRoundedLarge],
    )

    val bodyStyle = properties[platformTextStyles][platformText4]
    defaultTextStyle = bodyStyle

    properties[textStyles] = mapOf(
        title to properties[platformTextStyles][platformHeading6].copy(fontWeight = FontWeight.SemiBold),
        body to bodyStyle,
        caption to properties[platformTextStyles][platformText7],
    )

    val brightIndication = properties[platformIndications][platformBright]
    defaultIndication = brightIndication
    properties[indications] = mapOf(
        bright to brightIndication,
        dim to properties[platformIndications][platformDimmed],
    )
    properties[shadows] = mapOf(
        subtle to Shadow(
            offset = DpOffset(0.dp, 4.dp),
            radius = 4.dp,
            spread = 0.dp,
            color = Color.Black.copy(alpha = 0.25f),
            blendMode = BlendMode.SrcOver
        ),
        elevated to Shadow(
            offset = DpOffset(0.dp, 14.dp),
            radius = 18.dp,
            spread = 0.dp,
            color = Color.Black.copy(alpha = 0.15f),
            blendMode = BlendMode.SrcOver
        ),
        modal to Shadow(
            offset = DpOffset(0.dp, 14.dp),
            radius = 18.dp,
            spread = 0.dp,
            color = Color.Black.copy(alpha = 0.15f),
            blendMode = BlendMode.SrcOver
        )
    )
}

@Composable
private fun rememberTextSelectionColors(base: Color): TextSelectionColors {
    return remember(base) {
        TextSelectionColors(
            handleColor = base,
            backgroundColor = base.copy(0.4f)
        )
    }
}

fun Color.mutate(enabled: Boolean): Color {
    if (this == Color.Transparent || this == Color.Unspecified) return this
    return if (enabled) return this else this.copy(alpha = 0.6f)
}

fun isBright(color: Color): Boolean = color.luminance() > 0.5f
