package com.composables.ui.sample.components

import androidx.compose.runtime.Composable
import com.composables.ui.components.Toolbar
import com.composables.ui.sample.data.authenticatedUser
import com.composables.ui.theme.ColorScheme

@Composable
fun MobileToolbar(
    onColorSchemeChange: (ColorScheme) -> Unit,
    colorScheme: ColorScheme,
) {
    Toolbar(
        leading = {
            OtherMenuDropdown(
                colorScheme = colorScheme,
                onColorSchemeChange = onColorSchemeChange,
            ) { openMenu ->
                AvatarButton(
                    url = authenticatedUser.avatarUrl,
                    onClick = openMenu,
                )
            }
        },
        centered = {
            AppIcon()
        }
    )
}
