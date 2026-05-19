package com.composables.one.demo.examples

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Copy
import com.composables.icons.lucide.CreditCard
import com.composables.icons.lucide.EllipsisVertical
import com.composables.icons.lucide.Keyboard
import com.composables.icons.lucide.LogOut
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Settings
import com.composables.icons.lucide.Trash2
import com.composables.one.ButtonStyle
import com.composables.one.DropdownMenu
import com.composables.one.DropdownMenuContent
import com.composables.one.DropdownMenuItem
import com.composables.one.DropdownMenuItemStyle
import com.composables.one.DropdownMenuLabel
import com.composables.one.DropdownMenuSeparator
import com.composables.one.Icon
import com.composables.one.IconButton
import com.composables.one.Text

@Composable
fun DropdownMenuExample() {
    var expanded by remember { mutableStateOf(false) }

    DropdownMenu(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        anchor = {
            IconButton(
                onClick = { expanded = expanded.not() },
                style = ButtonStyle.Outlined,
            ) {
                Icon(
                    imageVector = Lucide.EllipsisVertical,
                    contentDescription = "More options",
                    modifier = Modifier.size(18.dp),
                )
            }
        },
    ) {
        DropdownMenuContent {
            DropdownMenuLabel {
                Text("My Account")
            }
            DropdownMenuItem(
                onClick = {},
                leading = { MenuIcon(Lucide.Settings) },
                trailing = { MenuShortcut("⌘,") },
            ) {
                Text("Settings")
            }
            DropdownMenuItem(
                onClick = {},
                leading = { MenuIcon(Lucide.CreditCard) },
            ) {
                Text("Billing")
            }
            DropdownMenuItem(
                onClick = {},
                leading = { MenuIcon(Lucide.Keyboard) },
                trailing = { MenuShortcut("⌘K") },
            ) {
                Text("Keyboard shortcuts")
            }
            DropdownMenuSeparator()
            DropdownMenuItem(
                onClick = {},
                leading = { MenuIcon(Lucide.Copy) },
            ) {
                Text("Duplicate")
            }
            DropdownMenuItem(
                onClick = {},
                leading = { MenuIcon(Lucide.LogOut) },
            ) {
                Text("Log out")
            }
            DropdownMenuSeparator()
            DropdownMenuItem(
                onClick = {},
                style = DropdownMenuItemStyle.Destructive,
                leading = { MenuIcon(Lucide.Trash2) },
            ) {
                Text("Delete")
            }
        }
    }
}

@Composable
private fun MenuIcon(imageVector: androidx.compose.ui.graphics.vector.ImageVector) {
    Icon(
        imageVector = imageVector,
        contentDescription = null,
        modifier = Modifier.size(16.dp),
    )
}

@Composable
private fun MenuShortcut(text: String) {
    Text(text)
}
