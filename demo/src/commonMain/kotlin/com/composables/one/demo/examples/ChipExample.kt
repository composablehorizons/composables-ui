package com.composables.one.demo.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.one.components.Icon
import com.composables.one.components.Chip
import com.composables.icons.lucide.Bath
import com.composables.icons.lucide.BedDouble
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Ruler
import com.composables.one.components.Text

@Composable
fun ChipExample() {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Chip {
            Icon(Lucide.BedDouble, contentDescription = null, modifier = Modifier.size(14.dp))
            Text("2 bedroom(s)")
        }
        Chip {
            Icon(Lucide.Bath, contentDescription = null, modifier = Modifier.size(14.dp))
            Text("3 bathroom(s)")
        }
        Chip {
            Icon(Lucide.Ruler, contentDescription = null, modifier = Modifier.size(14.dp))
            Text("150.0 m²")
        }
    }
}