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
fun DetailsScreen(onBackClick: () -> Unit) {
  Box(
      modifier = Modifier.fillMaxSize().padding(16.dp),
      contentAlignment = Alignment.Center,
  ) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Text(
          text = "Details",
          textAlign = TextAlign.Center,
      )
      Text(
          text = "This screen is powered by a Navigation 3 back stack.",
          textAlign = TextAlign.Center,
          modifier = Modifier.padding(top = 12.dp),
      )
      Button(
          onClick = onBackClick,
          modifier = Modifier.padding(top = 24.dp),
      ) {
        Text("Go back")
      }
    }
  }
}
