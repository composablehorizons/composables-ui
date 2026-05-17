package com.composables.one.demo

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.composables.icons.lucide.ArrowLeft
import com.composables.icons.lucide.Hand
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.MousePointer
import com.composables.one.AppScaffold
import com.composables.one.Icon
import com.composables.one.InteractionTarget
import com.composables.one.LocalInteractionTarget
import com.composables.one.demo.examples.ButtonsExample
import com.composables.one.demo.examples.TypographyExample
import com.composeunstyled.UnstyledButton

private data class DemoItem(
    val name: String,
    val id: String,
    val content: @Composable () -> Unit,
)

private val componentDemos = listOf(
    DemoItem("Buttons", "buttons") { ButtonsExample() },
)

private val themingDemos = listOf(
    DemoItem("Typography", "typography") { TypographyExample() },
)

private val demos = themingDemos + componentDemos

@Composable
fun Demo() {
    var interactionTarget by remember { mutableStateOf(InteractionTarget.NonTouch) }

    CompositionLocalProvider(LocalInteractionTarget provides interactionTarget) {
        val navController = rememberNavController()
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = currentBackStackEntry?.destination?.route
        val currentDemo = demos.firstOrNull { it.id == currentRoute }
        AppScaffold {
            Column(Modifier.fillMaxSize()) {
                DemoTopBar(
                    title = currentDemo?.name ?: "Composables One",
                    canGoBack = currentRoute != null && currentRoute != "home",
                    interactionTarget = interactionTarget,
                    onBack = { navController.navigateUp() },
                    onToggleInteractionTarget = {
                        interactionTarget = when (interactionTarget) {
                            InteractionTarget.NonTouch -> InteractionTarget.Touch
                            InteractionTarget.Touch -> InteractionTarget.NonTouch
                        }
                    },
                )
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
                            DemoRoute(demo = demo)
                        }
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
            padding = PaddingValues(24.dp),
        ) {
            demo.content()
        }
    }
}

@Composable
private fun DemoTopBar(
    title: String,
    canGoBack: Boolean,
    interactionTarget: InteractionTarget,
    onBack: () -> Unit,
    onToggleInteractionTarget: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(WindowInsets.statusBars.asPaddingValues())
            .height(56.dp)
            .padding(horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (canGoBack) {
            UnstyledButton(
                onClick = onBack,
                modifier = Modifier.clip(CircleShape),
            ) {
                Box(Modifier.padding(12.dp)) {
                    Icon(Lucide.ArrowLeft, contentDescription = "Go back")
                }
            }
            Spacer(Modifier.width(8.dp))
        }
        BasicText(title)
        Spacer(Modifier.weight(1f))
        UnstyledButton(
            onClick = onToggleInteractionTarget,
            modifier = Modifier.clip(RoundedCornerShape(8.dp)),
        ) {
            Box(Modifier.padding(12.dp)) {
                Icon(
                    imageVector = when (interactionTarget) {
                        InteractionTarget.NonTouch -> Lucide.MousePointer
                        InteractionTarget.Touch -> Lucide.Hand
                    },
                    contentDescription = when (interactionTarget) {
                        InteractionTarget.NonTouch -> "Current mode: pointer"
                        InteractionTarget.Touch -> "Current mode: touch"
                    },
                )
            }
        }
    }
}

@Composable
private fun ColumnScope.DemoContainer(
    padding: PaddingValues,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .background(Color.White)
            .padding(padding),
        contentAlignment = Alignment.Center,
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
    BasicText(
        text = title,
        modifier = Modifier.padding(horizontal = 16.dp),
        style = TextStyle(fontWeight = FontWeight.SemiBold),
    )
    demos.forEach { demo ->
        DemoListButton(
            onClick = { onClick(demo) },
            modifier = Modifier.fillMaxWidth(),
        ) {
            BasicText(demo.name)
        }
    }
}

@Composable
private fun DemoListButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    UnstyledButton(
        onClick = onClick,
        modifier = modifier
            .sizeIn(minWidth = 40.dp, minHeight = 48.dp)
            .clip(RoundedCornerShape(8.dp)),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            contentAlignment = Alignment.CenterStart,
        ) {
            content()
        }
    }
}
