package com.composables.one.demo

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.composables.icons.lucide.ArrowLeft
import com.composables.icons.lucide.Lucide
import com.composables.one.AppScaffold
import com.composables.one.Button
import com.composables.one.ButtonStyle
import com.composables.one.Icon
import com.composables.one.IconButton
import com.composables.one.Text
import com.composables.one.Toolbar
import com.composables.one.ToolbarSize
import com.composables.one.demo.examples.ButtonsExample
import com.composables.one.demo.examples.CenteredToolbarExample
import com.composables.one.demo.examples.LargeToolbarExample
import com.composables.one.demo.examples.ToolbarWithActionsExample
import com.composables.one.demo.examples.TypographyExample
import com.composables.one.styling.body
import com.composables.one.styling.textStyles
import com.composeunstyled.theme.Theme

private data class DemoItem(
    val name: String,
    val id: String,
    val content: @Composable () -> Unit,
    val previewOptions: PreviewOptions = PreviewOptions(),
)

private data class PreviewOptions(
    val contentAlignment: Alignment = Alignment.Center,
    val padding: PaddingValues = PaddingValues(0.dp),
)

private val componentDemos = listOf(
    DemoItem("Buttons", "buttons", content = { ButtonsExample() }),
    DemoItem(
        name = "Toolbar (Large)",
        id = "large-toolbar",
        content = { LargeToolbarExample() },
        previewOptions = PreviewOptions(
            contentAlignment = Alignment.TopCenter,
            padding = PaddingValues(vertical = 24.dp),
        ),
    ),
    DemoItem(
        name = "Toolbar (Actions)",
        id = "toolbar-actions",
        content = { ToolbarWithActionsExample() },
        previewOptions = PreviewOptions(
            contentAlignment = Alignment.TopCenter,
            padding = PaddingValues(vertical = 24.dp),
        ),
    ),
    DemoItem(
        name = "Toolbar (Centered)",
        id = "centered-toolbar",
        content = { CenteredToolbarExample() },
        previewOptions = PreviewOptions(
            contentAlignment = Alignment.TopCenter,
            padding = PaddingValues(vertical = 24.dp),
        ),
    ),
)

private val themingDemos = listOf(
    DemoItem("Typography", "typography", content = { TypographyExample() }),
)

private val demos = themingDemos + componentDemos

@Composable
fun Demo() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val currentDemo = demos.firstOrNull { it.id == currentRoute }
    AppScaffold {
        Column(Modifier.fillMaxSize()) {
            if (currentRoute != null && currentRoute != "home") {
                Toolbar(
                    leading = {
                        IconButton(
                            onClick = { navController.navigateUp() },
                            style = ButtonStyle.Ghost,
                        ) {
                            Icon(Lucide.ArrowLeft, contentDescription = "Go back")
                        }
                        Text(currentDemo?.name ?: "")
                    },
                )
            } else {
                Toolbar(
                    title = { Text("Composables One") },
                    size = ToolbarSize.Large,
                )
            }
            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.weight(1f),
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
                popEnterTransition = { EnterTransition.None },
                popExitTransition = { ExitTransition.None },
            ) {
                composable("home") {
                    DemoList(onSelectDemo = { navController.navigate(it.id) })
                }

                demos.forEach { demo ->
                    composable(demo.id) {
                        DemoRoute(demo)
                    }
                }
            }
        }
    }
}

@Composable
private fun DemoList(
    onSelectDemo: (DemoItem) -> Unit,
) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.TopStart) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(8.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Spacer(Modifier.height(8.dp))
            DemoSection("Theming", themingDemos, onSelectDemo)
            Spacer(Modifier.height(12.dp))
            DemoSection("Components", componentDemos, onSelectDemo)
        }
    }
}

@Composable
private fun DemoRoute(
    demo: DemoItem,
) {
    Column(Modifier.fillMaxSize()) {
        DemoContainer(
            previewOptions = demo.previewOptions,
        ) {
            demo.content()
        }
    }
}

@Composable
private fun ColumnScope.DemoContainer(
    previewOptions: PreviewOptions,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .padding(previewOptions.padding),
        contentAlignment = previewOptions.contentAlignment,
    ) {
        content()
    }
}

@Composable
private fun DemoSection(
    title: String,
    demos: List<DemoItem>,
    onClick: (DemoItem) -> Unit,
) {
    Text(
        text = title,
        modifier = Modifier.padding(horizontal = 16.dp),
        style = TextStyle(fontWeight = FontWeight.SemiBold),
    )
    demos.forEach { demo ->
        DemoListButton(
            onClick = { onClick(demo) },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = demo.name,
                style = Theme[textStyles][body],
            )
        }
    }
}

@Composable
private fun DemoListButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Button(
        onClick = onClick,
        style = ButtonStyle.Ghost,
        modifier = modifier
            .clip(RoundedCornerShape(8.dp)),
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterStart,
        ) {
            content()
        }
    }
}
