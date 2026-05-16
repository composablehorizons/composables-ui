package com.composables.one.demo.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.one.components.Avatar
import composables_one.demo.generated.resources.Res
import composables_one.demo.generated.resources.woman_1
import org.jetbrains.compose.resources.painterResource

@Composable
fun AvatarExample() {
    Row(horizontalArrangement = Arrangement.spacedBy(24.dp), verticalAlignment = Alignment.CenterVertically) {
        Avatar(
            painter = painterResource(Res.drawable.woman_1),
            initials = "Maya",
            modifier = Modifier.size(32.dp),
        )
        Avatar(
            painter = painterResource(Res.drawable.woman_1),
            initials = "Maya",
            modifier = Modifier.size(48.dp),
        )
        Avatar(
            painter = painterResource(Res.drawable.woman_1),
            initials = "Maya",
            modifier = Modifier.size(64.dp),
        )
    }
}