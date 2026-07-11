package {{namespace}}

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.composables.ui.theme.ComposablesTheme

@Composable
fun App() {
  val backStack = remember { NavBackStack<NavKey>(HomeRoute) }

  fun navigate(route: NavKey) {
    if (backStack.lastOrNull() != route) {
      backStack.add(route)
    }
  }

  fun popBackStack() {
    if (backStack.size > 1) {
      backStack.removeLastOrNull()
    }
  }

  ComposablesTheme {
    NavDisplay(
        backStack = backStack,
        modifier = Modifier.fillMaxSize(),
        entryProvider =
            entryProvider {
              entry<HomeRoute> { HomeScreen(onDetailsClick = { navigate(DetailsRoute) }) }
              entry<DetailsRoute> { DetailsScreen(onBackClick = { popBackStack() }) }
            },
    )
  }
}

data object HomeRoute : NavKey

data object DetailsRoute : NavKey

@Preview
@Composable
fun AppPreview() {
  App()
}
