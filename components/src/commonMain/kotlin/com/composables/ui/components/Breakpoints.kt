package com.composables.ui.components

import androidx.compose.ui.unit.dp
import com.composeunstyled.Breakpoint
import com.composeunstyled.ScreenBreakpoints

val MediumWidthBreakpoint = Breakpoint("medium")
val ExpandedWidthBreakpoint = Breakpoint("expanded")
val LargeWidthBreakpoint = Breakpoint("large")
val ExtraLargeWidthBreakpoint = Breakpoint("extraLarge")

val WidthBreakpoints = ScreenBreakpoints {
    MediumWidthBreakpoint at 600.dp
    ExpandedWidthBreakpoint at 840.dp
    LargeWidthBreakpoint at 1200.dp
    ExtraLargeWidthBreakpoint at 1600.dp
}
