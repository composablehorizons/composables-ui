package com.composables.ui.theme

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Indication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
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
import com.composeunstyled.theme.ThemeProperty
import com.composeunstyled.theme.ThemeToken
import com.composeunstyled.theme.buildTheme

// properties
val colors = ThemeProperty<Color>("colors")
val shapes = ThemeProperty<Shape>("shapes")
val shadows = ThemeProperty<Shadow>("shadows")
val indications = ThemeProperty<Indication>("indications")
val componentSizes = ThemeProperty<Dp>("componentSizes")
val alphas = ThemeProperty<Float>("alphas")
val textSelectionColors = ThemeProperty<TextSelectionColors>("textSelectionColors")

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
val control = ThemeToken<Color>("control")
val onControl = ThemeToken<Color>("on_control")
val selectedControl = ThemeToken<Color>("selected_control")
val onSelectedControl = ThemeToken<Color>("on_selected_control")
val destructive = ThemeToken<Color>("destructive")
val onDestructive = ThemeToken<Color>("on_destructive")
val border = ThemeToken<Color>("border")
val field = ThemeToken<Color>("field")
val onField = ThemeToken<Color>("on_field")
val input = ThemeToken<Color>("input")
val onInput = ThemeToken<Color>("on_input")
val inputPlaceholder = ThemeToken<Color>("input_placeholder")
val inputDisabled = ThemeToken<Color>("input_disabled")
val textSelectionHandle = ThemeToken<Color>("text_selection_handle")
val textSelectionBackground = ThemeToken<Color>("text_selection_background")
val scrim = ThemeToken<Color>("scrim")
val focusRing = ThemeToken<Color>("focus_ring")

val buttonShape = ThemeToken<Shape>("button_shape")
val alertDialogShape = ThemeToken<Shape>("alert_dialog_shape")
val bottomSheetShape = ThemeToken<Shape>("bottom_sheet_shape")
val dropdownMenuShape = ThemeToken<Shape>("dropdown_menu_shape")
val textFieldShape = ThemeToken<Shape>("text_field_shape")

val dropdownMenuShadow = ThemeToken<Shadow>("dropdown_menu_shadow")

val textFieldTextSelectionColors = ThemeToken<TextSelectionColors>("text_field_text_selection_colors")

val buttonHeight = ThemeToken<Dp>("button_height")
val buttonHorizontalPadding = ThemeToken<Dp>("button_horizontal_padding")
val iconButtonSize = ThemeToken<Dp>("icon_button_size")
val dropdownMenuItemHeight = ThemeToken<Dp>("dropdown_menu_item_height")
val textFieldHeight = ThemeToken<Dp>("text_field_height")
val textFieldHorizontalPadding = ThemeToken<Dp>("text_field_horizontal_padding")
val focusRingWidth = ThemeToken<Dp>("focus_ring_width")
val focusRingOffset = ThemeToken<Dp>("focus_ring_offset")
val disabledAlpha = ThemeToken<Float>("disabled_alpha")

@kotlin.jvm.JvmInline
value class ColorScheme internal constructor(@Suppress("unused") private val value: Int) {
    override fun toString() =
        when (this) {
            System -> "System"
            Light -> "Light"
            Dark -> "Dark"
            else -> "Error"
        }

    companion object {
        val System = ColorScheme(0)
        val Light = ColorScheme(1)
        val Dark = ColorScheme(2)
    }
}

val LocalColorScheme = staticCompositionLocalOf { ColorScheme.System }

@Composable
fun currentColorScheme(): ColorScheme {
    return when (LocalColorScheme.current) {
        ColorScheme.System -> {
            if (isSystemInDarkTheme()) {
                ColorScheme.Dark
            } else {
                ColorScheme.Light
            }
        }

        else -> LocalColorScheme.current
    }
}

@kotlin.jvm.JvmInline
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

val LocalInteractionMode = staticCompositionLocalOf { InteractionMode.Touch }

val AppTheme = buildTheme {
    val useDarkColors = currentColorScheme() == ColorScheme.Dark
    val textSelectionHandleColor = if (useDarkColors) Color.White else Color.Black
    val textSelectionBackgroundColor = textSelectionHandleColor.copy(alpha = 0.24f)
    val colorAnimationSpec = tween<Color>(
        durationMillis = ColorSchemeAnimationDurationMillis,
        easing = FastOutSlowInEasing,
    )
    val animatedBackground by animateColorAsState(
        targetValue = if (useDarkColors) Color.Black else Color(0xFFF9F9F9),
        animationSpec = colorAnimationSpec,
        label = "BackgroundColor",
    )
    val animatedOnBackground by animateColorAsState(
        targetValue = if (useDarkColors) Color.White else Color.Black,
        animationSpec = colorAnimationSpec,
        label = "OnBackgroundColor",
    )
    val animatedPanel by animateColorAsState(
        targetValue = if (useDarkColors) Color(0xFF101010) else Color.White,
        animationSpec = colorAnimationSpec,
        label = "PanelColor",
    )
    val animatedOnPanel by animateColorAsState(
        targetValue = if (useDarkColors) Color.White else Color.Black,
        animationSpec = colorAnimationSpec,
        label = "OnPanelColor",
    )
    val animatedMuted by animateColorAsState(
        targetValue = if (useDarkColors) Color(0xFFA8A8A8) else Color(0xFF777777),
        animationSpec = colorAnimationSpec,
        label = "MutedColor",
    )
    val animatedPrimary by animateColorAsState(
        targetValue = if (useDarkColors) Color.White else Color.Black,
        animationSpec = colorAnimationSpec,
        label = "PrimaryColor",
    )
    val animatedOnPrimary by animateColorAsState(
        targetValue = if (useDarkColors) Color.Black else Color.White,
        animationSpec = colorAnimationSpec,
        label = "OnPrimaryColor",
    )
    val animatedSecondary by animateColorAsState(
        targetValue = if (useDarkColors) Color(0xFF2F2F2F) else Color(0xFFF1F1F1),
        animationSpec = colorAnimationSpec,
        label = "SecondaryColor",
    )
    val animatedOnSecondary by animateColorAsState(
        targetValue = if (useDarkColors) Color.White else Color.Black,
        animationSpec = colorAnimationSpec,
        label = "OnSecondaryColor",
    )
    val animatedControl by animateColorAsState(
        targetValue = if (useDarkColors) Color(0xFF262626) else Color(0xFFF5F5F5),
        animationSpec = colorAnimationSpec,
        label = "ControlColor",
    )
    val animatedOnControl by animateColorAsState(
        targetValue = if (useDarkColors) Color(0xFFA8A8A8) else Color(0xFF777777),
        animationSpec = colorAnimationSpec,
        label = "OnControlColor",
    )
    val animatedSelectedControl by animateColorAsState(
        targetValue = if (useDarkColors) Color(0xFF3A3A3A) else Color.White,
        animationSpec = colorAnimationSpec,
        label = "SelectedControlColor",
    )
    val animatedOnSelectedControl by animateColorAsState(
        targetValue = if (useDarkColors) Color.White else Color.Black,
        animationSpec = colorAnimationSpec,
        label = "OnSelectedControlColor",
    )
    val animatedBorder by animateColorAsState(
        targetValue = if (useDarkColors) Color(0xFF3A3A3A) else Color(0xFFE0E0E0),
        animationSpec = colorAnimationSpec,
        label = "BorderColor",
    )
    val animatedField by animateColorAsState(
        targetValue = if (useDarkColors) Color(0xFF262626) else Color(0xFFF1F1F1),
        animationSpec = colorAnimationSpec,
        label = "FieldColor",
    )
    val animatedOnField by animateColorAsState(
        targetValue = if (useDarkColors) Color.White else Color.Black,
        animationSpec = colorAnimationSpec,
        label = "OnFieldColor",
    )
    val animatedInputPlaceholder by animateColorAsState(
        targetValue = if (useDarkColors) Color(0xFFA8A8A8) else Color(0xFF999999),
        animationSpec = colorAnimationSpec,
        label = "InputPlaceholderColor",
    )
    val animatedFocusRing by animateColorAsState(
        targetValue = if (useDarkColors) Color.White.copy(alpha = 0.32f) else Color.Black.copy(alpha = 0.24f),
        animationSpec = colorAnimationSpec,
        label = "FocusRingColor",
    )
    val useTouchSizes = LocalInteractionMode.current == InteractionMode.Touch
    val buttonHeightValue = if (useTouchSizes) 48.dp else 36.dp
    val iconButtonSizeValue = if (useTouchSizes) 48.dp else 36.dp
    val dropdownMenuItemHeightValue = if (useTouchSizes) 48.dp else 36.dp
    val textFieldHeightValue = if (useTouchSizes) 48.dp else 40.dp

    properties[colors] = mapOf(
        background to animatedBackground,
        onBackground to animatedOnBackground,
        panel to animatedPanel,
        onPanel to animatedOnPanel,
        muted to animatedMuted,
        primary to animatedPrimary,
        onPrimary to animatedOnPrimary,
        secondary to animatedSecondary,
        onSecondary to animatedOnSecondary,
        control to animatedControl,
        onControl to animatedOnControl,
        selectedControl to animatedSelectedControl,
        onSelectedControl to animatedOnSelectedControl,
        destructive to Color(0xFFFF3040),
        onDestructive to Color.White,
        border to animatedBorder,
        field to animatedField,
        onField to animatedOnField,
        input to animatedField,
        onInput to animatedOnField,
        inputPlaceholder to animatedInputPlaceholder,
        inputDisabled to animatedField,
        textSelectionHandle to textSelectionHandleColor,
        textSelectionBackground to textSelectionBackgroundColor,
        scrim to Color.Black.copy(alpha = if (useDarkColors) 0.48f else 0.38f),
        focusRing to animatedFocusRing,
    )
    properties[textSelectionColors] = mapOf(
        textFieldTextSelectionColors to TextSelectionColors(
            handleColor = textSelectionHandleColor,
            backgroundColor = textSelectionBackgroundColor,
        ),
    )
    properties[shapes] = mapOf(
        buttonShape to RoundedCornerShape(100),
        alertDialogShape to RoundedCornerShape(16.dp),
        bottomSheetShape to RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        dropdownMenuShape to RoundedCornerShape(16.dp),
        textFieldShape to RoundedCornerShape(if (useTouchSizes) 16.dp else 12.dp),
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
        buttonHeight to buttonHeightValue,
        buttonHorizontalPadding to if (useTouchSizes) 20.dp else 16.dp,
        iconButtonSize to iconButtonSizeValue,
        dropdownMenuItemHeight to dropdownMenuItemHeightValue,
        textFieldHeight to textFieldHeightValue,
        textFieldHorizontalPadding to 12.dp,
        focusRingWidth to 2.dp,
        focusRingOffset to 2.dp,
    )

    properties[alphas] = mapOf(
        disabledAlpha to 0.65f,
    )

    val bodyStyle = TextStyle(
        fontSize = if (useTouchSizes) 17.sp else 15.sp,
        lineHeight = if (useTouchSizes) 24.sp else 20.sp,
        fontWeight = FontWeight.Normal,
    )
    defaultTextStyle = bodyStyle

    val brightIndication = rememberRippleIndication(Color.White.copy(alpha = 0.18f))
    val dimIndication = rememberRippleIndication(Color.Black.copy(alpha = if (useDarkColors) 0.18f else 0.08f))
    defaultIndication = dimIndication
    properties[indications] = mapOf(
        bright to brightIndication,
        dim to dimIndication,
    )
}

private const val ColorSchemeAnimationDurationMillis = 180
