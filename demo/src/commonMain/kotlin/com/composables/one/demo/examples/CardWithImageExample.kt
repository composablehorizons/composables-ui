package com.composables.one.demo.examples

import androidx.compose.foundation.Image
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.composables.one.Card
import com.composables.one.OutlinedButton
import com.composables.one.PrimaryButton
import com.composables.one.Text
import com.composables.one.styling.textStyles
import com.composables.one.styling.title
import com.composeunstyled.theme.Theme
import composables_one.demo.generated.resources.Res
import composables_one.demo.generated.resources.dish_1
import org.jetbrains.compose.resources.painterResource

@Composable
fun CardWithImageExample() {
    Card(
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier.width(380.dp)
    ) {
        Image(
            painter = painterResource(Res.drawable.dish_1),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth().aspectRatio(16 / 9f)
        )
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text("Smoky BBQ Classic Burger", style = Theme[textStyles][title])
            Text("Juicy beef, cheese, and smoky barbecue sauce on a toasted bun.")
            Text("$12.99 • 950 cals")
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                OutlinedButton(onClick = {}, modifier = Modifier.weight(1f)) {
                    Text("Customize")
                }
                PrimaryButton(onClick = {}, modifier = Modifier.weight(1f)) {
                    Text("Add to Bag")
                }
            }
        }
    }
}
