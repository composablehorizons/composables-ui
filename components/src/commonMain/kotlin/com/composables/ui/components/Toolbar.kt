package com.composables.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composeunstyled.LocalContentColor
import com.composeunstyled.LocalTextStyle
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.ProvideTextStyle
import com.composeunstyled.buildModifier
import kotlin.jvm.JvmName

class ToolbarSize private constructor() {
    companion object {
        val Medium = ToolbarSize()
        val Large = ToolbarSize()
    }
}

@Composable
@JvmName("ToolbarWithTitle")
fun Toolbar(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Transparent,
    title: @Composable RowScope.() -> Unit,
    size: ToolbarSize = ToolbarSize.Medium,
    leading: @Composable (RowScope.() -> Unit)? = null,
    trailing: @Composable (RowScope.() -> Unit)? = null,
) {
    if (size == ToolbarSize.Large) {
        LargeTitleToolbar(
            modifier = modifier,
            backgroundColor = backgroundColor,
            title = title,
            size = size,
            leading = leading,
            trailing = trailing,
        )
    } else {
        MediumTitleToolbar(
            modifier = modifier,
            backgroundColor = backgroundColor,
            title = title,
            size = size,
            leading = leading,
            trailing = trailing,
        )
    }
}

@Composable
private fun MediumTitleToolbar(
    modifier: Modifier,
    backgroundColor: Color,
    title: @Composable RowScope.() -> Unit,
    size: ToolbarSize,
    leading: @Composable (RowScope.() -> Unit)?,
    trailing: @Composable (RowScope.() -> Unit)?,
) {
    ToolbarContainer(
        modifier = modifier,
        backgroundColor = backgroundColor,
        height = toolbarHeightFor(size),
    ) {
        Row(
            modifier = Modifier.align(Alignment.CenterStart),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (leading != null) {
                leading()
            }
            ProvideTextStyle(LocalTextStyle.current.merge(ToolbarTitleTextStyle)) {
                title()
            }
        }
        if (trailing != null) {
            Row(
                modifier = Modifier.align(Alignment.CenterEnd),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                content = trailing,
            )
        }
    }
}

@Composable
private fun LargeTitleToolbar(
    modifier: Modifier,
    backgroundColor: Color,
    title: @Composable RowScope.() -> Unit,
    size: ToolbarSize,
    leading: @Composable (RowScope.() -> Unit)?,
    trailing: @Composable (RowScope.() -> Unit)?,
) {
    ToolbarContainer(
        modifier = modifier,
        backgroundColor = backgroundColor,
        height = toolbarHeightFor(size),
    ) {
        if (leading != null) {
            Row(
                modifier = Modifier
                    .height(64.dp)
                    .align(Alignment.TopStart),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                content = leading,
            )
        }
        if (trailing != null) {
            Row(
                modifier = Modifier
                    .height(64.dp)
                    .align(Alignment.TopEnd),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                content = trailing,
            )
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ProvideTextStyle(LocalTextStyle.current.merge(ToolbarHeaderTextStyle)) {
                title()
            }
        }
    }
}

@Composable
fun Toolbar(
    modifier: Modifier = Modifier,
    leading: @Composable (RowScope.() -> Unit)? = null,
    centered: @Composable (RowScope.() -> Unit)? = null,
    trailing: @Composable (RowScope.() -> Unit)? = null,
    backgroundColor: Color = Color.Transparent,
    contentColor: Color = LocalContentColor.current,
) {
    ToolbarContainer(
        modifier = modifier,
        backgroundColor = backgroundColor,
        height = 64.dp,
        contentColor = contentColor,
    ) {
        if (leading != null) {
            Row(
                modifier = Modifier.align(Alignment.CenterStart),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ProvideTextStyle(LocalTextStyle.current.merge(ToolbarTitleTextStyle)) {
                    leading()
                }
            }
        }
        if (centered != null) {
            Row(
                modifier = Modifier.align(Alignment.Center),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ProvideTextStyle(LocalTextStyle.current.merge(ToolbarTitleTextStyle)) {
                    centered()
                }
            }
        }
        if (trailing != null) {
            Row(
                modifier = Modifier.align(Alignment.CenterEnd),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                content = trailing,
            )
        }
    }
}

private fun toolbarHeightFor(size: ToolbarSize): Dp = when (size) {
    ToolbarSize.Large -> 112.dp
    else -> 64.dp
}

private val ToolbarTitleTextStyle = TextStyle(
    fontSize = 20.sp,
    lineHeight = 24.sp,
    fontWeight = FontWeight.Medium,
)

private val ToolbarHeaderTextStyle = TextStyle(
    fontSize = 26.sp,
    lineHeight = 24.sp,
    fontWeight = FontWeight.SemiBold,
)

@Composable
private fun ToolbarContainer(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    height: Dp,
    contentColor: Color = LocalContentColor.current,
    content: @Composable BoxScope.() -> Unit,
) {
    ProvideContentColor(contentColor) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .then(buildModifier {
                    if (backgroundColor != Color.Transparent) {
                        add(Modifier.background(backgroundColor))
                    }
                })
                .padding(WindowInsets.statusBars.asPaddingValues())
                .height(height)
                .padding(horizontal = 12.dp),
            content = content,
        )
    }
}
