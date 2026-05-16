package com.composables.one

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.composables.one.Sample
import com.composables.one.styling.colors
import com.composables.one.styling.onNavigation
import com.composables.one.styling.outline
import com.composables.one.styling.shadows
import com.composables.one.styling.subtle
import com.composables.one.styling.textStyles
import com.composeunstyled.LocalTextStyle
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.ProvideTextStyle
import com.composeunstyled.outline
import com.composeunstyled.theme.Theme

@Sample("TopAppBarExample")
@Composable
fun TopAppBar(
    navigation: @Composable (() -> Unit)? = null,
    title: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    actions: @Composable (RowScope.() -> Unit)? = null,
    shape: Shape = RectangleShape,
    backgroundColor: Color = Theme[colors][com.composables.one.styling.navigation],
    contentColor: Color = Theme[colors][onNavigation],
    elevated: Boolean = false,
) {
    val safeContentPadding = WindowInsets.safeDrawing.asPaddingValues()

    val layoutDirection = LocalLayoutDirection.current
    val topPadding = safeContentPadding.calculateTopPadding()
    val startPadding = safeContentPadding.calculateLeftPadding(layoutDirection)
    val endPadding = safeContentPadding.calculateRightPadding(layoutDirection)

    val shadow = Theme[shadows][subtle]

    val animatedColor by animateColorAsState(if (elevated) shadow.color else Color.Transparent)
    val animatedOffsetX by animateDpAsState(if (elevated) shadow.offset.x else 0.dp)
    val animatedOffsetY by animateDpAsState(if (elevated) shadow.offset.y else 0.dp)

    Column(
        modifier
            .fillMaxWidth()
            .dropShadow(shape = shape) {
                this.radius = shadow.radius.toPx()
                this.spread = shadow.spread.toPx()
                this.color = animatedColor
                this.offset = Offset(animatedOffsetX.toPx(), animatedOffsetY.toPx())
                this.blendMode = BlendMode.SrcOver
            }
            .clip(shape)
            .zIndex(if (elevated) 5f else 0f)
            .outline(Dp.Hairline, Theme[colors][outline])
            .background(backgroundColor)
            .padding(
                start = startPadding,
                end = endPadding,
            )
            .consumeWindowInsets(WindowInsets.safeContent)
    ) {
        Spacer(Modifier.height(topPadding))
        AppBar(contentColor = contentColor, navigation = navigation, title = title, actions = actions)
    }
}

@Composable
fun AppBar(
    navigation: @Composable (() -> Unit)? = null,
    title: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    actions: @Composable (RowScope.() -> Unit)? = null,
    shape: Shape = RectangleShape,
    backgroundColor: Color = Theme[colors][com.composables.one.styling.navigation],
    contentColor: Color = Theme[colors][onNavigation],
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(shape)
            .background(backgroundColor)
            .height(52.dp)
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        ProvideContentColor(contentColor) {
            if (navigation != null) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    navigation()
                    Spacer(Modifier.width(4.dp))
                }
            }
            Row(Modifier, verticalAlignment = Alignment.CenterVertically) {
                if (title != null) {
                    ProvideTextStyle(Theme[textStyles][com.composables.one.styling.title]) {
                        title()
                    }
                }
            }
            if (actions != null) {
                Spacer(Modifier.weight(1f))
                Spacer(Modifier.width(4.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    actions()
                }
            }
        }
    }
}


@Composable
fun CenteredAppBar(
    navigation: (@Composable () -> Unit)? = null,
    title: (@Composable () -> Unit)? = null,
    actions: (@Composable () -> Unit)? = null,
    modifier: Modifier = Modifier,
    contentColor: Color = Theme[colors][onNavigation],
) {
    Box(modifier = modifier.height(52.dp).padding(2.dp)) {
        ProvideContentColor(contentColor) {
            if (navigation != null) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .zIndex(1f),
                    contentAlignment = Alignment.Center
                ) {
                    navigation()
                }
            }

            if (actions != null) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .zIndex(1f),
                    contentAlignment = Alignment.Center
                ) {
                    actions()
                }
            }

            if (title != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .zIndex(2f),
                    contentAlignment = Alignment.Center
                ) {
                    ProvideTextStyle(Theme[textStyles][com.composables.one.styling.title]) {
                        title()
                    }
                }
            }
        }
    }
}

