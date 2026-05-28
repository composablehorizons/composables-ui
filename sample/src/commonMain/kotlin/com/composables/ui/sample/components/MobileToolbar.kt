package com.composables.ui.sample.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
            var expanded by remember { mutableStateOf(false) }

            OtherMenuDropdown(
                colorScheme = colorScheme,
                onColorSchemeChange = onColorSchemeChange,
                expanded = expanded,
                onExpandedChange = { expanded = it }
            ) {
                AvatarButton(
                    url = authenticatedUser.avatarUrl,
                    onClick = {
                        expanded = true
                    },
                )
            }
        },
        centered = {
            AppIcon()
        }
    )
}
