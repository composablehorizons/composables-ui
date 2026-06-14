package com.composables.ui.sample.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.composables.ui.components.DropdownMenu
import com.composables.ui.components.DropdownMenuAlignment
import com.composables.ui.components.DropdownMenuItem
import com.composables.ui.components.DropdownMenuItemStyle
import com.composables.ui.components.DropdownMenuPanel
import com.composables.ui.components.DropdownMenuSeparator
import com.composables.ui.components.DropdownMenuSide
import com.composables.ui.components.Text
import com.composables.ui.sample.Appearance

@Composable
fun OtherMenuDropdown(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    appearance: Appearance,
    onAppearanceChange: (Appearance) -> Unit,
    side: DropdownMenuSide = DropdownMenuSide.Bottom,
    alignment: DropdownMenuAlignment = DropdownMenuAlignment.Start,
    anchor: @Composable () -> Unit,
) {
    DropdownMenu(
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        side = side,
        alignment = alignment,
        panel = {
            DropdownMenuPanel(minWidth = 280.dp) {
                AppearanceSelector(
                    selectedAppearance = appearance,
                    onSelectedAppearanceChange = onAppearanceChange,
                )
                DropdownMenuSeparator()
                DropdownMenuItem(
                    onClick = {  },
                    style = DropdownMenuItemStyle.Destructive,
                ) {
                    Text("Log out")
                }
            }
        },
    ) {
        anchor()
    }
}
