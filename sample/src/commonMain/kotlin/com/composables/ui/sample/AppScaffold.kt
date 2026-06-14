package com.composables.ui.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.ui.theme.ComposablesTheme
import com.composables.ui.theme.ColorScheme
import com.composables.ui.theme.LocalColorScheme
import com.composables.ui.theme.backgroundColor
import com.composables.ui.theme.colors
import com.composables.ui.theme.onBackgroundColor
import com.composeunstyled.FocusVisibilityProvider
import com.composeunstyled.HeightBreakpoint
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.ProvideWindowBreakpoints
import com.composeunstyled.TooltipHost
import com.composeunstyled.WidthBreakpoint
import com.composeunstyled.WindowHeightBreakpoints
import com.composeunstyled.WindowWidthBreakpoints
import com.composeunstyled.theme.Theme
import kotlin.jvm.JvmInline

val Compact = WidthBreakpoint("compact")
val Medium = WidthBreakpoint("medium")
val Expanded = WidthBreakpoint("expanded")
val Large = WidthBreakpoint("large")
val ExtraLarge = WidthBreakpoint("extraLarge")

val Short = HeightBreakpoint("short")
val Tall = HeightBreakpoint("tall")

val WidthBreakpoints = WindowWidthBreakpoints {
    Compact startsAt 0.dp
    Medium startsAt 600.dp
    Expanded startsAt 840.dp
    Large startsAt 1200.dp
    ExtraLarge startsAt 1600.dp
}

val HeightBreakpoints = WindowHeightBreakpoints {
    Short startsAt 0.dp
    Tall startsAt 1080.dp
}

@JvmInline
value class Appearance internal constructor(@Suppress("unused") private val value: Int) {
    companion object {
        val System = Appearance(0)
        val Light = Appearance(1)
        val Dark = Appearance(2)
    }
}

@Composable
fun AppScaffold(
    appearance: Appearance = Appearance.System,
    content: @Composable () -> Unit,
) {
    val colorScheme = when (appearance) {
        Appearance.System -> LocalColorScheme.current
        Appearance.Light -> ColorScheme.Light
        else -> ColorScheme.Dark
    }

    CompositionLocalProvider(LocalColorScheme provides colorScheme) {
        ComposablesTheme {
            ProvideWindowBreakpoints(width = WidthBreakpoints, height = HeightBreakpoints) {
                TooltipHost {
                    FocusVisibilityProvider {
                        ProvideContentColor(Theme[colors][onBackgroundColor]) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Theme[colors][backgroundColor]),
                            ) {
                                content()
                            }
                        }
                    }
                }
            }
        }
    }
}
