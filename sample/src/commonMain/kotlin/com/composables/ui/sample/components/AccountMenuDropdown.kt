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
import com.composables.ui.components.DropdownMenuSide
import com.composables.ui.components.Text
import com.composables.ui.theme.ColorScheme

@Composable
fun AccountMenuDropdown(
    colorScheme: ColorScheme,
    onColorSchemeChange: (ColorScheme) -> Unit,
    side: DropdownMenuSide = DropdownMenuSide.Bottom,
    alignment: DropdownMenuAlignment = DropdownMenuAlignment.Start,
    anchor: @Composable (openMenu: () -> Unit) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    DropdownMenu(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        side = side,
        alignment = alignment,
        panel = {
            DropdownMenuPanel(minWidth = 280.dp) {
                AppearanceSelector(
                    selectedColorScheme = colorScheme,
                    onSelectedColorSchemeChange = onColorSchemeChange,
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
        anchor { expanded = true }
    }
}
