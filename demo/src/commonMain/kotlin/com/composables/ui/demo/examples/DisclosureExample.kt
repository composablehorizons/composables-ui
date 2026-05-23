package com.composables.ui.demo.examples

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.ui.components.AccordionPanel
import com.composables.ui.components.Disclosure
import com.composables.ui.components.DisclosureHeading
import com.composables.ui.components.DisclosurePanel
import com.composables.ui.components.Text
import com.composables.ui.components.rememberDisclosureState

@Composable
fun DisclosureExample() {
    val state = rememberDisclosureState(initiallyExpanded = true)
    Disclosure(state = state) {
        Column(Modifier.widthIn(max = 360.dp)) {
            DisclosureHeading {
                Text(if (state.expanded) "Hide details" else "Show details")
            }
            DisclosurePanel {
                AccordionPanel {
                    Text("Compose Unstyled owns the disclosure behavior. Composables UI owns this surface.")
                }
            }
        }
    }
}
