package com.composables.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composeunstyled.CrossAxisAlignment
import com.composeunstyled.MainAxisArrangement
import com.composeunstyled.Stack as UnstyledStack
import com.composeunstyled.StackOrientation as UnstyledStackOrientation
import com.composeunstyled.StackScope as UnstyledStackScope

class StackScope internal constructor(private val unstyledScope: UnstyledStackScope) {
    fun Modifier.weight(weight: Float, fill: Boolean = true): Modifier = with(unstyledScope) { weight(weight, fill) }
}

@kotlin.jvm.JvmInline
value class StackOrientation internal constructor(internal val orientation: UnstyledStackOrientation) {
    companion object {
        val Horizontal = StackOrientation(UnstyledStackOrientation.Horizontal)
        val Vertical = StackOrientation(UnstyledStackOrientation.Vertical)
    }
}

class StackMainAxisArrangement private constructor(internal val arrangement: MainAxisArrangement) {
    companion object {
        val Start = StackMainAxisArrangement(MainAxisArrangement.Start)
        val Center = StackMainAxisArrangement(MainAxisArrangement.Center)
        val End = StackMainAxisArrangement(MainAxisArrangement.End)
        val SpaceEvenly = StackMainAxisArrangement(MainAxisArrangement.SpaceEvenly)
        val SpaceBetween = StackMainAxisArrangement(MainAxisArrangement.SpaceBetween)
        val SpaceAround = StackMainAxisArrangement(MainAxisArrangement.SpaceAround)
    }
}

class StackCrossAxisAlignment private constructor(internal val alignment: CrossAxisAlignment) {
    companion object {
        val Start = StackCrossAxisAlignment(CrossAxisAlignment.Start)
        val Center = StackCrossAxisAlignment(CrossAxisAlignment.Center)
        val End = StackCrossAxisAlignment(CrossAxisAlignment.End)
    }
}

@Composable
fun Stack(
    modifier: Modifier = Modifier,
    orientation: StackOrientation = StackOrientation.Horizontal,
    mainAxisArrangement: StackMainAxisArrangement = StackMainAxisArrangement.Start,
    crossAxisAlignment: StackCrossAxisAlignment = StackCrossAxisAlignment.Start,
    spacing: Dp = 0.dp,
    content: @Composable StackScope.() -> Unit,
) {
    UnstyledStack(modifier, orientation.orientation, mainAxisArrangement.arrangement, crossAxisAlignment.alignment, spacing) {
        StackScope(this).content()
    }
}
