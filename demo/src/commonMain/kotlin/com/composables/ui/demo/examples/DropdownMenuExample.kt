package com.composables.ui.demo.examples

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
import com.composables.icons.lucide.Check
import com.composables.icons.lucide.ChevronsUpDown
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Monitor
import com.composables.icons.lucide.Moon
import com.composables.icons.lucide.Palette
import com.composables.icons.lucide.Sun
import com.composables.ui.components.Button
import com.composables.ui.components.ButtonStyle
import com.composables.ui.components.DropdownMenu
import com.composables.ui.components.DropdownMenuAlignment
import com.composables.ui.components.DropdownMenuItem
import com.composables.ui.components.DropdownMenuPanel
import com.composables.ui.components.Icon
import com.composables.ui.components.Text

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
