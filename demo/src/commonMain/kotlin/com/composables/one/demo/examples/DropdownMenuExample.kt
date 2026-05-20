package com.composables.one.demo.examples

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Bookmark
import com.composables.icons.lucide.Copy
import com.composables.icons.lucide.EllipsisVertical
import com.composables.icons.lucide.Flag
import com.composables.icons.lucide.Heart
import com.composables.icons.lucide.Link
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Search
import com.composables.icons.lucide.Share
import com.composables.icons.lucide.Trash2
import com.composables.one.ButtonStyle
import com.composables.one.DropdownMenu
import com.composables.one.DropdownMenuAlignment
import com.composables.one.DropdownMenuContent
import com.composables.one.DropdownMenuItem
import com.composables.one.DropdownMenuItemStyle
import com.composables.one.DropdownMenuSeparator
import com.composables.one.Icon
import com.composables.one.IconButton
import com.composables.one.Text
import com.composables.one.Toolbar

@Composable
fun DropdownMenuExample() {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .widthIn(max = 390.dp)
            .fillMaxWidth(),
    ) {
        Toolbar(
            title = { Text("Details") },
            trailing = {
                IconButton(
                    onClick = {},
                    style = ButtonStyle.Ghost,
                ) {
                    Icon(
                        imageVector = Lucide.Search,
                        contentDescription = "Search",
                        modifier = Modifier.size(18.dp),
                    )
                }
                DropdownMenu(
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    alignment = DropdownMenuAlignment.End,
                    anchor = {
                        IconButton(
                            onClick = { expanded = expanded.not() },
                            style = ButtonStyle.Ghost,
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
                        DropdownMenuItem(
                            onClick = { expanded = false },
                            leading = { MenuIcon(Lucide.Share) },
                        ) {
                            Text("Share")
                        }
                        DropdownMenuItem(
                            onClick = { expanded = false },
                            leading = { MenuIcon(Lucide.Heart) },
                        ) {
                            Text("Add to favorites")
                        }
                        DropdownMenuItem(
                            onClick = { expanded = false },
                            leading = { MenuIcon(Lucide.Bookmark) },
                        ) {
                            Text("Save for later")
                        }
                        DropdownMenuSeparator()
                        DropdownMenuItem(
                            onClick = { expanded = false },
                            leading = { MenuIcon(Lucide.Link) },
                        ) {
                            Text("Copy link")
                        }
                        DropdownMenuItem(
                            onClick = { expanded = false },
                            leading = { MenuIcon(Lucide.Copy) },
                        ) {
                            Text("Duplicate")
                        }
                        DropdownMenuSeparator()
                        DropdownMenuItem(
                            onClick = { expanded = false },
                            leading = { MenuIcon(Lucide.Flag) },
                        ) {
                            Text("Report")
                        }
                        DropdownMenuItem(
                            onClick = { expanded = false },
                            style = DropdownMenuItemStyle.Destructive,
                            leading = { MenuIcon(Lucide.Trash2) },
                        ) {
                            Text("Delete")
                        }
                    }
                }
            }
        )
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
