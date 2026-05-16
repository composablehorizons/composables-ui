package com.composables.one.demo.examples

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composables.core.SheetDetent
import com.composables.core.rememberBottomSheetState
import com.composables.icons.lucide.Clock3
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Navigation
import com.composables.icons.lucide.PhoneCall
import com.composables.icons.lucide.Share2
import com.composables.icons.lucide.Star
import com.composables.one.components.BottomSheet
import com.composables.one.components.Icon
import com.composables.one.components.PrimaryButton
import com.composables.one.components.SecondaryButton
import com.composables.one.components.Text
import com.composables.one.styling.medium
import com.composables.one.styling.shapes
import com.composables.one.styling.textStyles
import com.composables.one.styling.title
import com.composeunstyled.theme.Theme
import composables_one.demo.generated.resources.Res
import composables_one.demo.generated.resources.coffee_1
import composables_one.demo.generated.resources.coffee_2
import composables_one.demo.generated.resources.coffee_3
import composables_one.demo.generated.resources.coffee_4
import org.jetbrains.compose.resources.painterResource

@Composable
fun BottomSheetExample() {
    val imagePreviews = listOf(
        Res.drawable.coffee_1,
        Res.drawable.coffee_2,
        Res.drawable.coffee_3,
        Res.drawable.coffee_4,
    )
    val Peek = SheetDetent("peek") { containerHeight: Dp, sheetHeight: Dp ->
        280.dp
    }
    val bottomSheetState = rememberBottomSheetState(
        initialDetent = Peek,
        detents = listOf(SheetDetent.Hidden, Peek, SheetDetent.FullyExpanded),
    )
    Box(Modifier.fillMaxSize()) {

        PrimaryButton(
            onClick = { bottomSheetState.targetDetent = Peek },
            modifier = Modifier.align(Alignment.Center),
        ) {
            Text("Show Details")
        }

        BottomSheet(state = bottomSheetState) {
            Column(Modifier.fillMaxWidth()) {
                Text(
                    "Bloom & Brew", style = Theme[textStyles][title],
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                )
                Spacer(Modifier.height(12.dp))
                Column(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text("42 Brick Lane, London E1 6RF, UK")
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Lucide.Star,
                            contentDescription = "Staring",
                            modifier = Modifier.size(16.dp),
                            tint = Color(0xFFFACC15)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("4.7 (842 reviews)")
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Lucide.Clock3,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("Open now · Closes 8:00 PM")
                    }
                }
                Spacer(Modifier.height(12.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    PrimaryButton(onClick = {}) {
                        Icon(Lucide.Navigation, contentDescription = null)
                        Text("Directions")
                    }
                    SecondaryButton(onClick = {}) {
                        Icon(Lucide.PhoneCall, contentDescription = null)
                        Text("Call")
                    }
                    SecondaryButton(onClick = {}) {
                        Icon(Lucide.Share2, contentDescription = null)
                        Text("Share")
                    }
                    SecondaryButton(onClick = {}) {
                        Icon(Lucide.Star, contentDescription = null)
                        Text("Star it")
                    }
                }

                Spacer(Modifier.height(12.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    imagePreviews.forEach { resource ->
                        Image(
                            painter = painterResource(resource),
                            contentDescription = null,
                            modifier = Modifier.clip(Theme[shapes][medium]).size(190.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }
}