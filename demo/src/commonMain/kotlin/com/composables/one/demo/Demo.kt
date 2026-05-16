package com.composables.one.demo

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.composables.icons.lucide.ArrowLeft
import com.composables.icons.lucide.Lucide
import com.composables.one.Icon
import com.composables.one.IconButton
import com.composables.one.Text
import com.composables.one.TopAppBar
import com.composables.one.demo.examples.AlertExample
import com.composables.one.demo.examples.AppScaffoldExample
import com.composables.one.demo.examples.AuthScreenScaffoldExample
import com.composables.one.demo.examples.AvatarExample
import com.composables.one.demo.examples.BottomNavigationBarExample
import com.composables.one.demo.examples.BottomSheetExample
import com.composables.one.demo.examples.CardExample
import com.composables.one.demo.examples.CardWithImageExample
import com.composables.one.demo.examples.CardWithTitleExample
import com.composables.one.demo.examples.CenteredAlertExample
import com.composables.one.demo.examples.CheckboxExample
import com.composables.one.demo.examples.ChipExample
import com.composables.one.demo.examples.DestructiveButtonExample
import com.composables.one.demo.examples.DialogExample
import com.composables.one.demo.examples.DividerExample
import com.composables.one.demo.examples.DropdownMenuExample
import com.composables.one.demo.examples.GhostButtonExample
import com.composables.one.demo.examples.HorizontalDividerExample
import com.composables.one.demo.examples.IconButtonExample
import com.composables.one.demo.examples.IconExample
import com.composables.one.demo.examples.IntermediateProgressIndicatorExample
import com.composables.one.demo.examples.LinearProgressIndicatorExample
import com.composables.one.demo.examples.ModalBottomSheetExample
import com.composables.one.demo.examples.OutlinedButtonExample
import com.composables.one.demo.examples.PickerExample
import com.composables.one.demo.examples.PrimaryButtonExample
import com.composables.one.demo.examples.RadioGroupExample
import com.composables.one.demo.examples.ScreenScaffoldExample
import com.composables.one.demo.examples.SecondaryButtonExample
import com.composables.one.demo.examples.SideBarExample
import com.composables.one.demo.examples.SliderExample
import com.composables.one.demo.examples.TabBarExample
import com.composables.one.demo.examples.TextExample
import com.composables.one.demo.examples.TextFieldExample
import com.composables.one.demo.examples.TimePickerExample
import com.composables.one.demo.examples.ToggleSwitchExample
import com.composables.one.demo.examples.TopAppBarExample
import com.composables.one.demo.examples.VerticalDividerExample
import com.composables.one.styling.OneTheme

private data class DemoItem(
    val name: String,
    val id: String,
    val edgeToEdge: Boolean = false,
    val content: @Composable () -> Unit,
)

private val componentDemos = listOf(
    DemoItem("App Scaffold", "app-scaffold", true) { AppScaffoldExample() },
    DemoItem("Auth Screen Scaffold", "auth-screen-scaffold", true) { AuthScreenScaffoldExample() },
    DemoItem("Screen Scaffold", "screen-scaffold", true) { ScreenScaffoldExample() },
    DemoItem("Top App Bar", "top-app-bar", true) { TopAppBarExample() },
    DemoItem("Side Bar", "side-bar", true) { Box(Modifier.fillMaxSize()) { SideBarExample() } },
    DemoItem("Bottom Navigation Bar", "bottom-navigation-bar", true) { BottomNavigationBarExample() },
    DemoItem("Primary Button", "primary-button") { PrimaryButtonExample() },
    DemoItem("Secondary Button", "secondary-button") { SecondaryButtonExample() },
    DemoItem("Outlined Button", "outlined-button") { OutlinedButtonExample() },
    DemoItem("Ghost Button", "ghost-button") { GhostButtonExample() },
    DemoItem("Destructive Button", "destructive-button") { DestructiveButtonExample() },
    DemoItem("Icon Button", "icon-button") { IconButtonExample() },
    DemoItem("Icon", "icon") { IconExample() },
    DemoItem("Text", "text") { TextExample() },
    DemoItem("Text Field", "text-field") { TextFieldExample() },
    DemoItem("Checkbox", "checkbox") { CheckboxExample() },
    DemoItem("Toggle Switch", "toggle-switch") { ToggleSwitchExample() },
    DemoItem("Radio Group", "radio-group") { RadioGroupExample() },
    DemoItem("Picker", "picker") { PickerExample() },
    DemoItem("Slider", "slider") { SliderExample() },
    DemoItem("Chip", "chip") { ChipExample() },
    DemoItem("Card", "card") { CardExample() },
    DemoItem("Card with Image", "card-with-image") { CardWithImageExample() },
    DemoItem("Card with Title", "card-with-title") { CardWithTitleExample() },
    DemoItem("Avatar", "avatar") { AvatarExample() },
    DemoItem("Alert", "alert") { AlertExample() },
    DemoItem("Centered Alert", "centered-alert") { CenteredAlertExample() },
    DemoItem("Dialog", "dialog") { DialogExample() },
    DemoItem("Dropdown Menu", "dropdown-menu") { DropdownMenuExample() },
    DemoItem("Bottom Sheet", "bottom-sheet", true) { BottomSheetExample() },
    DemoItem("Modal Bottom Sheet", "modal-bottom-sheet") { ModalBottomSheetExample() },
    DemoItem("Linear Progress Indicator", "linear-progress-indicator") { LinearProgressIndicatorExample() },
    DemoItem("Intermediate Progress Indicator", "intermediate-progress-indicator") { IntermediateProgressIndicatorExample() },
    DemoItem("Divider", "divider") { DividerExample() },
    DemoItem("Horizontal Divider", "horizontal-divider") { HorizontalDividerExample() },
    DemoItem("Vertical Divider", "vertical-divider") { VerticalDividerExample() },
    DemoItem("Tab Bar", "tab-bar", true) { TabBarExample() },
    DemoItem("Time Picker", "time-picker") { TimePickerExample() },
)

@Composable
fun Demo() {
    OneTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = "home",
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None },
        ) {
            composable("home") {
                DemoList(onSelectDemo = { navController.navigate(it.id) })
            }

            componentDemos.forEach { demo ->
                composable(demo.id) {
                    DemoRoute(
                        demo = demo,
                        onBack = { navController.navigateUp() },
                    )
                }
            }
        }
    }
}

@Composable
private fun DemoList(
    onSelectDemo: (DemoItem) -> Unit,
) {
    Box(Modifier.fillMaxSize().background(Color(0xFFFAFAFA)), contentAlignment = Alignment.TopStart) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(8.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Spacer(Modifier.height(8.dp))
            DemoSection("Components", componentDemos, onSelectDemo)
        }
    }
}

@Composable
private fun DemoRoute(
    demo: DemoItem,
    onBack: () -> Unit,
) {
    Column(Modifier.fillMaxSize()) {
        TopAppBar(
            navigation = {
                IconButton(onClick = onBack) {
                    Icon(Lucide.ArrowLeft, contentDescription = "Go back")
                }
            },
            title = { Text(demo.name) },
        )
        DemoContainer(
            padding = if (demo.edgeToEdge) PaddingValues(0.dp) else PaddingValues(24.dp),
        ) {
            demo.content()
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
    Box(
        modifier = modifier
            .sizeIn(minWidth = 40.dp, minHeight = 48.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick),
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
