package {{namespace}}

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.composables.ui.components.Button
import com.composables.ui.components.Text

@Composable
fun HomeScreen(onDetailsClick: () -> Unit) {
  Box(
      modifier = Modifier.fillMaxSize().padding(16.dp),
      contentAlignment = Alignment.Center,
  ) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Text(
          text = "Hello Beautiful World!",
          textAlign = TextAlign.Center,
      )
      Text(
          text = "Go to App.kt to edit your app",
          textAlign = TextAlign.Center,
          modifier = Modifier.padding(top = 12.dp),
      )
      Text(
          text =
              "Pro tip: Use the dev configuration in your IDE to auto-reload your app when you edit your code",
          textAlign = TextAlign.Center,
          modifier = Modifier.padding(top = 12.dp),
      )
      Button(
          onClick = onDetailsClick,
          modifier = Modifier.padding(top = 24.dp),
      ) {
        Text("Open details")
      }
    }
  }
}
