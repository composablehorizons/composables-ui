package com.composables.one.demo.examples

import androidx.compose.runtime.Composable
import com.composables.one.components.Icon
import com.composables.icons.lucide.Bell
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Menu
import com.composables.icons.lucide.Search
import com.composables.one.components.IconButton
import com.composables.one.components.TopAppBar
import com.composables.one.components.Text

@Composable
fun TopAppBarExample() {
    TopAppBar(
        navigation = {
            IconButton(onClick = { /* TODO */ }) {
                Icon(Lucide.Menu, "Toggle Menu")
            }
        },
        title = {
            Text("News")
        },
        actions = {
            IconButton(onClick = { /* TODO */ }) {
                Icon(Lucide.Bell, "Notifications")
            }
            IconButton(onClick = { /* TODO */ }) {
                Icon(Lucide.Search, "Search")
            }
        }
    )
}
