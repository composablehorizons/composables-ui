package com.composables.one.demo.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Bookmark
import com.composables.icons.lucide.Check
import com.composables.icons.lucide.ChevronsUpDown
import com.composables.icons.lucide.Copy
import com.composables.icons.lucide.EllipsisVertical
import com.composables.icons.lucide.Flag
import com.composables.icons.lucide.Heart
import com.composables.icons.lucide.Link
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Monitor
import com.composables.icons.lucide.Moon
import com.composables.icons.lucide.Palette
import com.composables.icons.lucide.Share
import com.composables.icons.lucide.Sun
import com.composables.icons.lucide.Trash2
import com.composables.one.Button
import com.composables.one.ButtonStyle
import com.composables.one.DropdownMenu
import com.composables.one.DropdownMenuAlignment
import com.composables.one.DropdownMenuItem
import com.composables.one.DropdownMenuItemStyle
import com.composables.one.DropdownMenuPanel
import com.composables.one.DropdownMenuSeparator
import com.composables.one.Icon
import com.composables.one.IconButton
import com.composables.one.Text
import com.composables.one.Toolbar

@Composable
fun DropdownMenuExample() {
    var expanded by remember { mutableStateOf(false) }
    var selectedColorScheme by remember { mutableStateOf("Light") }

    DropdownMenu(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        alignment = DropdownMenuAlignment.End,
        panel = {
            DropdownMenuPanel {
                DropdownMenuItem(
                    onClick = {
                        selectedColorScheme = "System"
                        expanded = false
                    },
                    leading = {
                        if (selectedColorScheme == "System") {
                            Icon(
                                imageVector = Lucide.Check,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                            )
                        } else {
                            Spacer(Modifier.width(16.dp))
                        }
                    },
                    trailing = {
                        Icon(
                            imageVector = Lucide.Monitor,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                        )
                    },
                ) {
                    Text("System")
                }
                DropdownMenuItem(
                    onClick = {
                        selectedColorScheme = "Dark"
                        expanded = false
                    },
                    leading = {
                        if (selectedColorScheme == "Dark") {
                            Icon(
                                imageVector = Lucide.Check,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                            )
                        } else {
                            Spacer(Modifier.width(16.dp))
                        }
                    },
                    trailing = {
                        Icon(
                            imageVector = Lucide.Moon,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                        )
                    },
                ) {
                    Text("Dark")
                }
                DropdownMenuItem(
                    onClick = {
                        selectedColorScheme = "Light"
                        expanded = false
                    },
                    leading = {
                        if (selectedColorScheme == "Light") {
                            Icon(
                                imageVector = Lucide.Check,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                            )
                        } else {
                            Spacer(Modifier.width(16.dp))
                        }
                    },
                    trailing = {
                        Icon(
                            imageVector = Lucide.Sun,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                        )
                    },
                ) {
                    Text("Light")
                }
            }
        },
    ) {
        Button(
            onClick = { expanded = expanded.not() },
            modifier = Modifier.fillMaxWidth(),
            style = ButtonStyle.Ghost,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Lucide.Palette,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                )
                Text("Color scheme")
                Spacer(Modifier.weight(1f))
                Text(selectedColorScheme)
                Icon(
                    imageVector = Lucide.ChevronsUpDown,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                )
            }
        }
    }
}

@Composable
fun DropdownMenuToolbarExample() {
    var expanded by remember { mutableStateOf(false) }

    Toolbar(
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
                                    imageVector = Lucide.Bookmark,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp),
                                )
                            },
                        ) {
                            Text("Save for later")
                        }
                        DropdownMenuSeparator()
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
                            leading = {
                                Icon(
                                    imageVector = Lucide.Copy,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp),
                                )
                            },
                        ) {
                            Text("Duplicate")
                        }
                        DropdownMenuSeparator()
                        DropdownMenuItem(
                            onClick = { expanded = false },
                            leading = {
                                Icon(
                                    imageVector = Lucide.Flag,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp),
                                )
                            },
                        ) {
                            Text("Report")
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
