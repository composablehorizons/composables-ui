package com.composables.ui.sample

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.composables.icons.lucide.Bell
import com.composables.icons.lucide.House
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Menu
import com.composables.icons.lucide.Plus
import com.composables.icons.lucide.Search
import com.composables.icons.lucide.User
import com.composables.ui.components.ButtonSize
import com.composables.ui.components.ButtonStyle
import com.composables.ui.components.DropdownMenuAlignment
import com.composables.ui.components.DropdownMenuSide
import com.composables.ui.components.Icon
import com.composables.ui.components.IconButton
import com.composables.ui.components.NavigationBar
import com.composables.ui.components.NavigationBarItem
import com.composables.ui.components.Sidebar
import com.composables.ui.components.SidebarItem
import com.composables.ui.components.SidebarMode
import com.composables.ui.components.Text
import com.composables.ui.components.Toolbar
import com.composables.ui.sample.components.OtherMenuDropdown
import com.composables.ui.sample.data.authenticatedUser
import com.composables.ui.theme.AppScaffold
import com.composables.ui.theme.ColorScheme
import com.composables.ui.theme.Large
import com.composables.ui.theme.Medium
import com.composables.ui.theme.border
import com.composables.ui.theme.colors
import com.composeunstyled.buildModifier
import com.composeunstyled.currentWidthBreakpoint
import com.composeunstyled.outline
import com.composeunstyled.theme.Theme
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

@Serializable
data object SearchRoute

@Serializable
data object NewPostRoute

@Serializable
data object ActivityRoute

@Serializable
data class PostDetailsRoute(val postId: String)

@Serializable
data class ProfileRoute(val profileId: String)

@Composable
fun App() {
    val navController = rememberNavController()
    var colorScheme by remember { mutableStateOf(ColorScheme.System) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val homeSelected = currentDestination == null ||
            currentDestination.hasRoute<HomeRoute>() ||
            currentDestination.hasRoute<PostDetailsRoute>()
    val searchSelected = currentDestination?.hasRoute<SearchRoute>() == true
    val composeSelected = currentDestination?.hasRoute<NewPostRoute>() == true
    val activitySelected = currentDestination?.hasRoute<ActivityRoute>() == true
    val profileSelected = currentDestination?.hasRoute<ProfileRoute>() == true

    AppScaffold(colorScheme = colorScheme) {
        Box(Modifier.fillMaxSize()) {
            val widthBreakpoint = currentWidthBreakpoint()

            Column(
                Modifier
                    .align(Alignment.TopCenter)
                    .padding(horizontal = if (widthBreakpoint isAtLeast Medium) 80.dp else 0.dp)
                    .widthIn(max = if (widthBreakpoint isAtLeast Medium) 700.dp else Dp.Unspecified)
                    .fillMaxSize(),
            ) {
                if (widthBreakpoint isAtLeast Medium) {
                    Toolbar(
                        leading = {
                            when {
                                homeSelected -> Text("My Feed")
                                searchSelected -> Text("Search")
                                activitySelected -> Text("Activity")
                                profileSelected -> Text("Profile")
                            }
                        },
                    )
                }
                Box(
                    modifier = buildModifier {
                        if (widthBreakpoint isAtLeast Medium) {
                            val contentShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                            add(
                                Modifier
                                    .outline(1.dp, Theme[colors][border], contentShape)
                                    .clip(contentShape)
                            )
                        }
                    }
                        // we want the exiting screen to look dimmed. to achieve this effect,
                        // we set a black background, while fading the page itself during the transition
                        .background(Color.Black)
                ) {
                    TabHost(
                        navController = navController,
                        colorScheme = colorScheme,
                        onColorSchemeChange = { colorScheme = it },
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }

            if (widthBreakpoint isAtLeast Medium) {
                IconButton(
                    onClick = { navController.navigate(NewPostRoute) },
                    style = ButtonStyle.Primary,
                    modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp),
                    buttonSize = ButtonSize.Large
                ) {
                    Icon(Lucide.Plus, contentDescription = "New Post")
                }
            }

            fun onTabSelected(route: Any) {
                navController.navigate(route) {
                    popUpTo(HomeRoute)
                    launchSingleTop = true
                }
            }

            if (widthBreakpoint isAtLeast Medium) {
                Sidebar(
                    modifier = Modifier.align(Alignment.CenterStart),
                    mode = if (widthBreakpoint isAtLeast Large) SidebarMode.Expanded else SidebarMode.Compact,
                    footer = {
                        var expanded by remember { mutableStateOf(false) }

                        OtherMenuDropdown(
                            colorScheme = colorScheme,
                            onColorSchemeChange = { colorScheme = it },
                            side = DropdownMenuSide.Top,
                            alignment = DropdownMenuAlignment.Start,
                            expanded = expanded,
                            onExpandedChange = { expanded = it }
                        ) {
                            SidebarItem(
                                selected = false,
                                icon = { Icon(Lucide.Menu) },
                                onClick = { expanded = true },
                                text = { Text(text = "More", singleLine = true) },
                            )
                        }
                    },
                    content = {
                        SidebarItem(
                            selected = homeSelected,
                            icon = { Icon(Lucide.House) },
                            onClick = { onTabSelected(HomeRoute) },
                            text = {
                                Text(text = "Home", singleLine = true)
                            },
                        )
                        SidebarItem(
                            selected = searchSelected,
                            icon = { Icon(Lucide.Search) },
                            onClick = { onTabSelected(SearchRoute) },
                            text = {
                                Text(text = "Search", singleLine = true)
                            },
                        )
                        SidebarItem(
                            selected = composeSelected,
                            icon = { Icon(Lucide.Plus) },
                            onClick = { onTabSelected(NewPostRoute) },
                            text = { Text(text = "New post", singleLine = true) },
                        )
                        SidebarItem(
                            selected = activitySelected,
                            icon = { Icon(Lucide.Bell) },
                            onClick = { onTabSelected(ActivityRoute) },
                            text = { Text(text = "Activity", singleLine = true) },
                        )
                        SidebarItem(
                            selected = profileSelected,
                            icon = { Icon(Lucide.User) },
                            onClick = { onTabSelected(ProfileRoute(authenticatedUser.id)) },
                            text = { Text(text = "Profile", singleLine = true) },
                        )
                    },
                )
            } else {
                NavigationBar(modifier = Modifier.align(Alignment.BottomCenter)) {
                    NavigationBarItem(
                        modifier = Modifier.weight(1f),
                        selected = homeSelected,
                        onClick = { onTabSelected(HomeRoute) },
                        icon = { Icon(Lucide.House, contentDescription = "Home") }
                    )
                    NavigationBarItem(
                        modifier = Modifier.weight(1f),
                        selected = searchSelected,
                        onClick = { onTabSelected(SearchRoute) },
                        icon = { Icon(Lucide.Search, contentDescription = "Search") })
                    NavigationBarItem(
                        modifier = Modifier.weight(1f),
                        selected = composeSelected,
                        onClick = { onTabSelected(NewPostRoute) },
                        icon = { Icon(Lucide.Plus, contentDescription = "New post") }
                    )
                    NavigationBarItem(
                        modifier = Modifier.weight(1f),
                        selected = activitySelected,
                        onClick = { onTabSelected(ActivityRoute) },
                        icon = { Icon(Lucide.Bell, contentDescription = "Activity") })
                    NavigationBarItem(
                        modifier = Modifier.weight(1f),
                        selected = profileSelected,
                        onClick = { onTabSelected(ProfileRoute(authenticatedUser.id)) },
                        icon = { Icon(Lucide.User, contentDescription = "Profile") })
                }
            }
        }
    }
}

@Composable
private fun TabHost(
    navController: NavHostController,
    colorScheme: ColorScheme,
    onColorSchemeChange: (ColorScheme) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeRoute,
        modifier = modifier,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {
        composable<HomeRoute> {
            SocialFeed(
                onPostClick = { post -> navController.navigate(PostDetailsRoute(post.id)) },
                onProfileClick = { profileId -> navController.navigate(ProfileRoute(profileId)) },
                onNewPostClick = {
                    navController.navigate(NewPostRoute) {
                        popUpTo(HomeRoute)
                        launchSingleTop = true
                    }
                },
            )
        }
        composable<PostDetailsRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<PostDetailsRoute>()
            PostDetails(
                postId = route.postId,
            )
        }
        composable<SearchRoute> {
            Search(
                onProfileClick = { profileId -> navController.navigate(ProfileRoute(profileId)) }
            )
        }
        dialog<NewPostRoute> {
            PostComposer()
        }
        composable<ActivityRoute> {
            Activity(
                onProfileClick = { profileId -> navController.navigate(ProfileRoute(profileId)) }
            )
        }
        composable<ProfileRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<ProfileRoute>()

            Profile(
                profileId = route.profileId,
                onPostClick = { postId -> navController.navigate(PostDetailsRoute(postId)) },
            )
        }
    }
}
