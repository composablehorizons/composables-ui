package com.composables.one.demo.examples

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Sun
import com.composables.one.Card
import com.composables.one.Icon
import com.composables.one.OutlinedButton
import com.composables.one.PrimaryButton
import com.composables.one.Text
import com.composables.one.styling.accent
import com.composables.one.styling.colors
import com.composables.one.styling.onAccent
import com.composables.one.styling.textStyles
import com.composables.one.styling.title
import com.composeunstyled.theme.Theme
import composables_one.demo.generated.resources.Res
import composables_one.demo.generated.resources.dish_1
import org.jetbrains.compose.resources.painterResource

@Composable
fun CardExample() {
    Card(onClick = { /*TODO*/ }) {
        Row {
            Icon(
                imageVector = Lucide.Sun,
                contentDescription = "Sunny",
                tint = Theme[colors][onAccent],
                modifier = Modifier.background(Theme[colors][accent], CircleShape).padding(12.dp)
            )
            Spacer(Modifier.width(16.dp))
            Column(verticalArrangement = Arrangement.Center) {
                Text("33°C in California")
                Spacer(Modifier.height(4.dp))
                Text("Tap to learn more", modifier = Modifier.alpha(0.5f))
            }
        }
    }
}


