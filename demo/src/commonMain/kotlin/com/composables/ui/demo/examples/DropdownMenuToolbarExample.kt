package com.composables.ui.demo.examples

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.EllipsisVertical
import com.composables.icons.lucide.Heart
import com.composables.icons.lucide.Link
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Share
import com.composables.icons.lucide.Trash2
import com.composables.ui.components.ButtonStyle
import com.composables.ui.components.DropdownMenu
import com.composables.ui.components.DropdownMenuAlignment
import com.composables.ui.components.DropdownMenuItem
import com.composables.ui.components.DropdownMenuItemStyle
import com.composables.ui.components.DropdownMenuPanel
import com.composables.ui.components.Icon
import com.composables.ui.components.IconButton
import com.composables.ui.components.Text
import com.composables.ui.components.Toolbar

@Composable
fun DropdownMenuToolbarExample() {
    var expanded by remember { mutableStateOf(false) }

    Toolbar(
        modifier = Modifier.fillMaxWidth(),
        title = { Text("Details") },
        trailing = {
            DropdownMenu(
                expanded = expanded,
                onExpandedChange = { expanded = it },
                alignment = DropdownMenuAlignment.End,
                panel = {
                    DropdownMenuPanel {
                        DropdownMenuItem(
                            onClick = { expanded = false },
                            leading = {
                                Icon(
                                    imageVector = Lucide.Share,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp),
                                )
                            },
                        ) {
                            Text("Share")
                        }
                        DropdownMenuItem(
                            onClick = { expanded = false },
                            leading = {
                                Icon(
                                    imageVector = Lucide.Heart,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp),
                                )
                            },
                        ) {
                            Text("Add to favorites")
                        }
                        DropdownMenuItem(
                            onClick = { expanded = false },
                            leading = {
                                Icon(
                                    imageVector = Lucide.Link,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp),
                                )
                            },
                        ) {
                            Text("Copy link")
                        }
                        DropdownMenuItem(
                            onClick = { expanded = false },
                            style = DropdownMenuItemStyle.Destructive,
                            leading = {
                                Icon(
                                    imageVector = Lucide.Trash2,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp),
                                )
                            },
                        ) {
                            Text("Delete")
                        }
                    }
                },
            ) {
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
            }
        },
    )
}
