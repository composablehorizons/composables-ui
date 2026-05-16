package com.composables.one.demo.examples

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.composables.one.ToggleSwitch

@Composable
fun ToggleSwitchExample() {
    var toggle by remember { mutableStateOf(true) }

    ToggleSwitch(
        toggled = toggle,
        onToggled = { toggle = it },
    )
}
