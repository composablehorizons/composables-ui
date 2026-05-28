package com.composables.ui.sample.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.composables.ui.components.DropdownMenu
import com.composables.ui.components.DropdownMenuAlignment
import com.composables.ui.components.DropdownMenuItem
import com.composables.ui.components.DropdownMenuItemStyle
import com.composables.ui.components.DropdownMenuPanel
import com.composables.ui.components.DropdownMenuSeparator
import com.composables.ui.components.Text
import com.composables.ui.components.Toolbar
import com.composables.ui.sample.data.authenticatedUser
import com.composables.ui.theme.ColorScheme

@Composable
fun MobileToolbar() {
    var expanded by remember { mutableStateOf(false) }
    var selectedColorScheme by remember { mutableStateOf(ColorScheme.System) }

    Toolbar(
        leading = {
            DropdownMenu(
                expanded = expanded,
                onExpandedChange = { expanded = it },
                alignment = DropdownMenuAlignment.Start,
                panel = {
                    DropdownMenuPanel(minWidth = 280.dp) {
                        AppearanceSelector(
                            selectedColorScheme = selectedColorScheme,
                            onSelectedColorSchemeChange = { selectedColorScheme = it },
                        )
                        DropdownMenuSeparator()
                        DropdownMenuItem(
                            onClick = { expanded = false },
                            style = DropdownMenuItemStyle.Destructive,
                        ) {
                            Text("Log out")
                        }
                    }
                },
            ) {
                AvatarButton(
                    url = authenticatedUser.avatarUrl,
                    onClick = {
                        expanded = true
                    }
                )
            }
        },
        centered = {
            AppIcon()
        }
    )
}
