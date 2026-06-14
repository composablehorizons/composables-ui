package com.composables.ui.demo.examples

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.ChevronDown
import com.composables.icons.lucide.Lucide
import com.composables.ui.components.Disclosure
import com.composables.ui.components.DisclosureButton
import com.composables.ui.components.DisclosurePanel
import com.composables.ui.components.Icon
import com.composables.ui.components.Text

@Composable
fun DisclosureWithIndicatorExample() {
    var expanded by remember { mutableStateOf(false) }
    Disclosure(
        expanded = expanded,
        onExpandedChange = { expanded = it },
    ) {
        DisclosureButton(
            indicator = {
                val rotation by animateFloatAsState(
                    targetValue = if (it) -180f else 0f,
                )
                Icon(
                    imageVector = Lucide.ChevronDown,
                    modifier = Modifier
                        .size(16.dp)
                        .rotate(rotation),
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("What is your return policy?")
        }
        DisclosurePanel {
            Text("Returns are accepted within 30 days in original condition. Refunds are issued to the original payment method.")
        }
    }
}
