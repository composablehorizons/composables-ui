package com.composables.one.styling

import androidx.compose.foundation.Indication
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composeunstyled.platformtheme.buildPlatformTheme
import com.composeunstyled.theme.ThemeProperty
import com.composeunstyled.theme.ThemeToken
import com.composeunstyled.platformtheme.bright as platformBright
import com.composeunstyled.platformtheme.dimmed as platformDimmed
import com.composeunstyled.platformtheme.indications as platformIndications
import com.composeunstyled.platformtheme.text4 as platformText4
import com.composeunstyled.platformtheme.textStyles as platformTextStyles

// properties
val colors = ThemeProperty<Color>("colors")
val shapes = ThemeProperty<Shape>("shapes")
val shadows = ThemeProperty<Shadow>("shadows")
val textStyles = ThemeProperty<TextStyle>("textStyles")
val indications = ThemeProperty<Indication>("indications")
val componentSizes = ThemeProperty<Dp>("componentSizes")

// indications
val bright = ThemeToken<Indication>("bright")
val dim = ThemeToken<Indication>("dim")

// colors
val background = ThemeToken<Color>("background")
val onBackground = ThemeToken<Color>("on_background")
val panel = ThemeToken<Color>("panel_background")
val onPanel = ThemeToken<Color>("on_panel_background")
val muted = ThemeToken<Color>("muted")
val primary = ThemeToken<Color>("primary")
val onPrimary = ThemeToken<Color>("on_primary")
val secondary = ThemeToken<Color>("secondary")
val onSecondary = ThemeToken<Color>("on_secondary")
val destructive = ThemeToken<Color>("destructive")
val onDestructive = ThemeToken<Color>("on_destructive")
val border = ThemeToken<Color>("border")
val scrim = ThemeToken<Color>("scrim")

val buttonShape = ThemeToken<Shape>("button_shape")
val alertDialogShape = ThemeToken<Shape>("alert_dialog_shape")
val dropdownMenuShape = ThemeToken<Shape>("dropdown_menu_shape")

val dropdownMenuShadow = ThemeToken<Shadow>("dropdown_menu_shadow")

val body = ThemeToken<TextStyle>("body")
val title = ThemeToken<TextStyle>("title")
val header = ThemeToken<TextStyle>("header")
val buttonLabel = ThemeToken<TextStyle>("button_label")

val buttonHeight = ThemeToken<Dp>("button_height")
val buttonHorizontalPadding = ThemeToken<Dp>("button_horizontal_padding")
val iconButtonSize = ThemeToken<Dp>("icon_button_size")

val OneTheme = buildPlatformTheme {
    properties[colors] = mapOf(
        background to Color(0xFFFAFAFA),
        onBackground to Color(0XFF0C0A09),
        panel to Color.White,
        onPanel to Color.Black,
        muted to Color.Black.copy(alpha = 0.6f),
        primary to Color(0XFF0C0A09),
        onPrimary to Color.White,
        secondary to Color(0xFFf4f4f5),
        onSecondary to Color(0XFF0C0A09),
        destructive to Color(0xFFDC2626),
        onDestructive to Color.White,
        border to Color(0xFF1F2328).copy(alpha = 0.15f),
        scrim to Color.Black.copy(alpha = 0.12f),
    )
    properties[shapes] = mapOf(
        buttonShape to RoundedCornerShape(6.dp),
        alertDialogShape to RoundedCornerShape(16.dp),
        dropdownMenuShape to RoundedCornerShape(6.dp),
    )
    properties[shadows] = mapOf(
        dropdownMenuShadow to Shadow(
            radius = 16.dp,
            color = Color.Black,
            offset = DpOffset(x = 0.dp, y = 4.dp),
            alpha = 0.14f,
        ),
    )
    properties[componentSizes] = mapOf(
        buttonHeight to 36.dp,
        buttonHorizontalPadding to 16.dp,
        iconButtonSize to 36.dp,
    )

    val bodyStyle = properties[platformTextStyles][platformText4]
    defaultTextStyle = bodyStyle

    properties[textStyles] = mapOf(
        body to bodyStyle,
        title to bodyStyle.copy(
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
        ),
        header to bodyStyle.copy(
            fontSize = 26.sp,
            fontWeight = FontWeight.SemiBold,
        ),
        buttonLabel to bodyStyle.copy(fontWeight = FontWeight.Medium),
    )

    properties[indications] = mapOf(
        bright to properties[platformIndications][platformBright],
        dim to properties[platformIndications][platformDimmed],
    )
}
