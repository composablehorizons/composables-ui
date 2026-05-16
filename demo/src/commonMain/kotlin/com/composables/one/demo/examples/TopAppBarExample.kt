package com.composables.one.demo.examples

import androidx.compose.runtime.Composable
import com.composables.one.Icon
import com.composables.icons.lucide.Bell
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Menu
import com.composables.icons.lucide.Search
import com.composables.one.IconButton
import com.composables.one.TopAppBar
import com.composables.one.Text

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
