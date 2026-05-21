package com.composables.ui.theme

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
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
import com.composeunstyled.platformtheme.buildPlatformTheme
import com.composeunstyled.platformtheme.platformIndication
import com.composeunstyled.theme.ThemeProperty
import com.composeunstyled.theme.ThemeToken
import com.composables.interactioncapabilities.currentInteractionCapabilities
import com.composeunstyled.platformtheme.text4 as platformText4
import com.composeunstyled.platformtheme.textStyles as platformTextStyles

// properties
val colors = ThemeProperty<Color>("colors")
val shapes = ThemeProperty<Shape>("shapes")
val shadows = ThemeProperty<Shadow>("shadows")
val textStyles = ThemeProperty<TextStyle>("textStyles")
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

val body = ThemeToken<TextStyle>("body")
val title = ThemeToken<TextStyle>("title")
val header = ThemeToken<TextStyle>("header")
val buttonLabel = ThemeToken<TextStyle>("button_label")
val textFieldInput = ThemeToken<TextStyle>("text_field_input")

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

val LocalInteractionMode = staticCompositionLocalOf<InteractionMode?> { null }

@Composable
fun currentInteractionMode(): InteractionMode {
    LocalInteractionMode.current?.let { interactionMode ->
        return interactionMode
    }

    val capabilities = currentInteractionCapabilities()
    return if (capabilities.hasTouch) {
        InteractionMode.Touch
    } else {
        InteractionMode.Pointer
    }
}

val AppTheme = buildPlatformTheme {
    val useDarkColors = currentColorScheme() == ColorScheme.Dark
    val textSelectionHandleColor = if (useDarkColors) Color.White else Color(0XFF0C0A09)
    val textSelectionBackgroundColor = textSelectionHandleColor.copy(alpha = 0.24f)
    val colorAnimationSpec = tween<Color>(
        durationMillis = ColorSchemeAnimationDurationMillis,
        easing = FastOutSlowInEasing,
    )
    val animatedBackground by animateColorAsState(
        targetValue = if (useDarkColors) Color(0xFF09090B) else Color(0xFFFAFAFA),
        animationSpec = colorAnimationSpec,
        label = "BackgroundColor",
    )
    val animatedOnBackground by animateColorAsState(
        targetValue = if (useDarkColors) Color(0xFFFAFAFA) else Color(0XFF0C0A09),
        animationSpec = colorAnimationSpec,
        label = "OnBackgroundColor",
    )
    val animatedPanel by animateColorAsState(
        targetValue = if (useDarkColors) Color(0xFF18181B) else Color.White,
        animationSpec = colorAnimationSpec,
        label = "PanelColor",
    )
    val animatedOnPanel by animateColorAsState(
        targetValue = if (useDarkColors) Color(0xFFFAFAFA) else Color.Black,
        animationSpec = colorAnimationSpec,
        label = "OnPanelColor",
    )
    val animatedMuted by animateColorAsState(
        targetValue = if (useDarkColors) Color.White.copy(alpha = 0.64f) else Color.Black.copy(alpha = 0.6f),
        animationSpec = colorAnimationSpec,
        label = "MutedColor",
    )
    val animatedPrimary by animateColorAsState(
        targetValue = if (useDarkColors) Color.White else Color(0XFF0C0A09),
        animationSpec = colorAnimationSpec,
        label = "PrimaryColor",
    )
    val animatedOnPrimary by animateColorAsState(
        targetValue = if (useDarkColors) Color(0XFF0C0A09) else Color.White,
        animationSpec = colorAnimationSpec,
        label = "OnPrimaryColor",
    )
    val animatedSecondary by animateColorAsState(
        targetValue = if (useDarkColors) Color(0xFF27272A) else Color(0xFFf4f4f5),
        animationSpec = colorAnimationSpec,
        label = "SecondaryColor",
    )
    val animatedOnSecondary by animateColorAsState(
        targetValue = if (useDarkColors) Color(0xFFFAFAFA) else Color(0XFF0C0A09),
        animationSpec = colorAnimationSpec,
        label = "OnSecondaryColor",
    )
    val animatedControl by animateColorAsState(
        targetValue = if (useDarkColors) Color(0xFF27272A) else Color(0xFFf4f4f5),
        animationSpec = colorAnimationSpec,
        label = "ControlColor",
    )
    val animatedOnControl by animateColorAsState(
        targetValue = if (useDarkColors) Color.White.copy(alpha = 0.64f) else Color.Black.copy(alpha = 0.6f),
        animationSpec = colorAnimationSpec,
        label = "OnControlColor",
    )
    val animatedSelectedControl by animateColorAsState(
        targetValue = if (useDarkColors) Color(0xFF3F3F46) else Color.White,
        animationSpec = colorAnimationSpec,
        label = "SelectedControlColor",
    )
    val animatedOnSelectedControl by animateColorAsState(
        targetValue = if (useDarkColors) Color(0xFFFAFAFA) else Color(0XFF0C0A09),
        animationSpec = colorAnimationSpec,
        label = "OnSelectedControlColor",
    )
    val animatedBorder by animateColorAsState(
        targetValue = if (useDarkColors) Color.White.copy(alpha = 0.14f) else Color(0xFF1F2328).copy(alpha = 0.15f),
        animationSpec = colorAnimationSpec,
        label = "BorderColor",
    )
    val animatedField by animateColorAsState(
        targetValue = if (useDarkColors) Color(0xFF18181B) else Color.White,
        animationSpec = colorAnimationSpec,
        label = "FieldColor",
    )
    val animatedOnField by animateColorAsState(
        targetValue = if (useDarkColors) Color(0xFFFAFAFA) else Color(0XFF0C0A09),
        animationSpec = colorAnimationSpec,
        label = "OnFieldColor",
    )
    val animatedInputPlaceholder by animateColorAsState(
        targetValue = if (useDarkColors) Color.White.copy(alpha = 0.48f) else Color.Black.copy(alpha = 0.48f),
        animationSpec = colorAnimationSpec,
        label = "InputPlaceholderColor",
    )
    val animatedFocusRing by animateColorAsState(
        targetValue = if (useDarkColors) Color(0xFFA1A1AA) else Color(0xFF44403C),
        animationSpec = colorAnimationSpec,
        label = "FocusRingColor",
    )
    val useTouchSizes = currentInteractionMode() == InteractionMode.Touch
    val sizeAnimationSpec = tween<Dp>(
        durationMillis = InteractionModeSizeAnimationDurationMillis,
        easing = FastOutSlowInEasing,
    )
    val animatedButtonHeight by animateDpAsState(
        targetValue = if (useTouchSizes) 44.dp else 32.dp,
        animationSpec = sizeAnimationSpec,
        label = "ButtonHeight",
    )
    val animatedIconButtonSize by animateDpAsState(
        targetValue = if (useTouchSizes) 44.dp else 32.dp,
        animationSpec = sizeAnimationSpec,
        label = "IconButtonSize",
    )
    val animatedDropdownMenuItemHeight by animateDpAsState(
        targetValue = if (useTouchSizes) 44.dp else 32.dp,
        animationSpec = sizeAnimationSpec,
        label = "DropdownMenuItemHeight",
    )
    val animatedTextFieldHeight by animateDpAsState(
        targetValue = if (useTouchSizes) 48.dp else 40.dp,
        animationSpec = sizeAnimationSpec,
        label = "TextFieldHeight",
    )

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
        destructive to Color(0xFFDC2626),
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
        scrim to Color.Black.copy(alpha = 0.12f),
        focusRing to animatedFocusRing,
    )
    properties[textSelectionColors] = mapOf(
        textFieldTextSelectionColors to TextSelectionColors(
            handleColor = textSelectionHandleColor,
            backgroundColor = textSelectionBackgroundColor,
        ),
    )
    properties[shapes] = mapOf(
        buttonShape to RoundedCornerShape(6.dp),
        alertDialogShape to RoundedCornerShape(16.dp),
        bottomSheetShape to RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        dropdownMenuShape to RoundedCornerShape(6.dp),
        textFieldShape to RoundedCornerShape(6.dp),
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
        buttonHeight to animatedButtonHeight,
        buttonHorizontalPadding to 16.dp,
        iconButtonSize to animatedIconButtonSize,
        dropdownMenuItemHeight to animatedDropdownMenuItemHeight,
        textFieldHeight to animatedTextFieldHeight,
        textFieldHorizontalPadding to 12.dp,
        focusRingWidth to 3.dp,
        focusRingOffset to 0.dp,
    )

    properties[alphas] = mapOf(
        disabledAlpha to 0.65f,
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
        textFieldInput to bodyStyle,
    )

    properties[indications] = mapOf(
        bright to platformIndication(Color.White.copy(alpha = 0.18f)),
        dim to platformIndication(Color.Black.copy(alpha = 0.08f)),
    )
}

private const val InteractionModeSizeAnimationDurationMillis = 180
private const val ColorSchemeAnimationDurationMillis = 180
