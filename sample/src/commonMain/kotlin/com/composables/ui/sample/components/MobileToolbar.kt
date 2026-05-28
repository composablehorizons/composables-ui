package com.composables.ui.sample.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.composables.ui.components.Toolbar
import com.composables.ui.sample.data.authenticatedUser

@Composable
fun MobileToolbar() {
    var expanded by remember { mutableStateOf(false) }

    Toolbar(
        leading = {
            //
            AvatarButton(
                url = authenticatedUser.avatarUrl,
                onClick = {
                    expanded = true
                }
            )
        },
        centered = {
            AppIcon()
        }
    )
}
