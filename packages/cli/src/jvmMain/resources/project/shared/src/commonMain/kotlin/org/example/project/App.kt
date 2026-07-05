package {{namespace}}

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.composables.ui.components.Text
import com.composables.ui.theme.ComposablesTheme

@Composable
fun App() {
  ComposablesTheme {
    Box(
      modifier = Modifier.safeDrawingPadding().fillMaxSize().padding(16.dp),
      contentAlignment = Alignment.Center,
    ) {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
      ) {
        Text(
          text = "Hello Beautiful World!",
          textAlign = TextAlign.Center,
        )
        Text(
          text = "Go to App.kt to edit your app",
          textAlign = TextAlign.Center,
        )
        Text(
          text =
            "Pro tip: Use the `dev` configuration in your IDE to auto-reload your app when you edit your code",
          textAlign = TextAlign.Center,
        )
      }
    }
  }
}

@Preview
@Composable
fun AppPreview() {
  App()
}
