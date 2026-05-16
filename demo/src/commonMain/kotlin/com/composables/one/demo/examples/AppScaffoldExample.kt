package com.composables.one.demo.examples

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.ArrowLeft
import com.composables.icons.lucide.Lucide
import com.composables.one.components.AppScaffold
import com.composables.one.components.Icon
import com.composables.one.components.IconButton
import com.composables.one.components.Text
import com.composables.one.components.TopAppBar
import com.composables.one.styling.OneTheme
import kotlinx.coroutines.delay

@Composable
fun AppScaffoldExample() {
    // top level composable should be the theme
    OneTheme {
        // then wrap your app's contents with the AppScaffold to style the window
        AppScaffold {
            // inside the scaffold, goes the rest of your app's contents
            // this is where you would place your navigation graph if you use one
            TopAppBar(
                navigation = {
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(Lucide.ArrowLeft, contentDescription = null)
                    }
                    Text("Scaffold Example")
                }
            )
            Box(Modifier.weight(1f).padding(16.dp).fillMaxWidth(), contentAlignment = Alignment.Center) {
                Column {
                    Text("This is an example of how to setup your app")
                    Text("Go check the code below :)")
                }
            }
        }
    }
}
