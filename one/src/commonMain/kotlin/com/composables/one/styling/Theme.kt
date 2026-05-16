package com.composables.one.styling

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Indication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.one.styling.Appearance.Dark
import com.composables.one.styling.Appearance.Light
import com.composables.one.styling.Appearance.System
import com.composeunstyled.theme.ComponentInteractiveSize
import com.composeunstyled.theme.ThemeProperty
import com.composeunstyled.theme.ThemeToken
import com.composeunstyled.theme.buildTheme
import com.composeunstyled.theme.rememberColoredIndication

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


val OneTheme = buildTheme {
    val defaultFontFamily = FontFamily.Default

    defaultComponentInteractiveSize = ComponentInteractiveSize(
        nonTouchInteractionSize = 32.dp,
        touchInteractionSize = 48.dp
    )
    defaultTextSelectionColors = rememberTextSelectionColors(Color(0xFF3B82F6))

    val targetPalette = when (LocalAppearanceController.current.appearance) {
        Light -> LightPalette
        Dark -> DarkPalette
        System -> if (isSystemInDarkTheme()) DarkPalette else LightPalette
    }

    properties[colors] = animateColorPalette(targetPalette)
    properties[shapes] = mapOf(
        small to RoundedCornerShape(6.dp),
        medium to RoundedCornerShape(8.dp),
        bottomSheet to RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    )

    val bodyStyle = TextStyle(
        fontFamily = defaultFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
    defaultTextStyle = bodyStyle

    properties[textStyles] = mapOf(
        title to TextStyle(
            fontFamily = defaultFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 22.sp,
        ),
        body to bodyStyle,
        caption to TextStyle(
            fontFamily = defaultFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        )
    )

    val brightIndication = rememberColoredIndication(
        hoveredColor = Color.White.copy(alpha = 0.1f),
        pressedColor = Color.White.copy(alpha = 0.3f),
    )
    defaultIndication = brightIndication
    properties[indications] = mapOf(
        bright to brightIndication,
        dim to rememberColoredIndication(
            hoveredColor = Color(0xFFa1a1aa).copy(alpha = 0.1f),
            pressedColor = Color(0xFFa1a1aa).copy(alpha = 0.3f),
        ),
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
