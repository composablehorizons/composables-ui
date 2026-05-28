package com.composables.ui.sample

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.composables.icons.lucide.ArrowLeft
import com.composables.icons.lucide.Bell
import com.composables.icons.lucide.EllipsisVertical
import com.composables.icons.lucide.House
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Plus
import com.composables.icons.lucide.Search
import com.composables.icons.lucide.User
import com.composables.ui.components.ButtonSize
import com.composables.ui.components.ButtonStyle
import com.composables.ui.components.DropdownMenu
import com.composables.ui.components.DropdownMenuAlignment
import com.composables.ui.components.DropdownMenuItem
import com.composables.ui.components.DropdownMenuItemStyle
import com.composables.ui.components.DropdownMenuPanel
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
import com.composables.ui.sample.data.authenticatedUser
import com.composables.ui.theme.AppScaffold
import com.composables.ui.theme.Large
import com.composables.ui.theme.Medium
import com.composables.ui.theme.border
import com.composables.ui.theme.colors
import com.composables.ui.theme.onPanel
import com.composables.ui.theme.panel
import com.composeunstyled.LocalContentColor
import com.composeunstyled.buildModifier
import com.composeunstyled.currentWidthBreakpoint
import com.composeunstyled.outline
import com.composeunstyled.theme.Theme
import kotlinx.serialization.Serializable

private const val NavigationTransitionDurationMillis = 350
private const val NavigationParallaxDivisor = 5
private const val NavigationDimmedAlpha = 0.86f
private val NavigationTransitionEasing = CubicBezierEasing(0.32f, 0.72f, 0f, 1f)

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
fun SocialApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val homeSelected = currentDestination == null ||
            currentDestination.hasRoute<HomeRoute>() ||
            currentDestination.hasRoute<PostDetailsRoute>()
    val searchSelected = currentDestination?.hasRoute<SearchRoute>() == true
    val composeSelected = currentDestination?.hasRoute<NewPostRoute>() == true
    val activitySelected = currentDestination?.hasRoute<ActivityRoute>() == true
    val profileSelected = currentDestination?.hasRoute<ProfileRoute>() == true

    AppScaffold {
        Box(Modifier.fillMaxSize()) {
            val widthBreakpoint = currentWidthBreakpoint()

            Column(
                Modifier
                    .align(Alignment.TopCenter)
                    .padding(horizontal = if (widthBreakpoint isAtLeast Medium) 80.dp else 0.dp)
                    .widthIn(max = if (widthBreakpoint isAtLeast Medium) 700.dp else Dp.Unspecified)
                    .fillMaxSize(),
            ) {
                val toolbarColor = if (widthBreakpoint isAtLeast Medium) Color.Transparent else Theme[colors][panel]
                val toolbarContentColor =
                    if (widthBreakpoint isAtLeast Medium) LocalContentColor.current else Theme[colors][onPanel]
                Toolbar(
                    backgroundColor = toolbarColor,
                    contentColor = toolbarContentColor,
                    leading = {
                        val canGoBack = navController.previousBackStackEntry != null
                        if (canGoBack) {
                            IconButton(
                                onClick = { navController.navigateUp() },
                                style = ButtonStyle.Ghost,
                            ) {
                                Icon(Lucide.ArrowLeft, contentDescription = "Go back")
                            }
                        }

                        if (widthBreakpoint isAtLeast Medium) {
                            when {
                                homeSelected -> Text("My Feed")
                                searchSelected -> Text("Search")
                                activitySelected -> Text("Activity")
                                profileSelected -> Text("Profile")
                            }
                        }
                    },
                    trailing = {
                        if ((widthBreakpoint isAtLeast Medium).not()) {
                            var expanded by remember { mutableStateOf(false) }

                            DropdownMenu(
                                expanded = expanded,
                                onExpandedChange = { expanded = it },
                                side = DropdownMenuSide.Bottom,
                                alignment = DropdownMenuAlignment.End,
                                panel = {
                                    DropdownMenuPanel {
                                        DropdownMenuItem(onClick = {}) {
                                            Text("Do thing")
                                        }

                                        DropdownMenuItem(onClick = {}, style = DropdownMenuItemStyle.Destructive) {
                                            Text("Log out")
                                        }
                                    }
                                }
                            ) {
                                IconButton(
                                    onClick = { expanded = expanded.not() },
                                    style = ButtonStyle.Ghost
                                ) {
                                    Icon(Lucide.EllipsisVertical, contentDescription = "More")
                                }
                            }
                        }
                    }
                )
                Box(
                    buildModifier {
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
                    popUpTo(route)
                    launchSingleTop = true
                }
            }

            if (widthBreakpoint isAtLeast Medium) {
                Sidebar(
                    modifier = Modifier.align(Alignment.CenterStart),
                    mode = if (widthBreakpoint isAtLeast Large) SidebarMode.Expanded else SidebarMode.Compact,
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
                        onClick = { navController.navigateToProfile(authenticatedUser.id) },
                        icon = { Icon(Lucide.User, contentDescription = "Profile") })
                }
            }
        }
    }
}

@Composable
private fun TabHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val widthBreakpoint = currentWidthBreakpoint()
    val animateTransition = (widthBreakpoint isAtLeast Medium).not()

    NavHost(
        navController = navController,
        startDestination = HomeRoute,
        modifier = modifier,
        enterTransition = {
            if (targetState.destination.isTabDestination() || !animateTransition) {
                EnterTransition.None
            } else {
                slideInHorizontally(
                    animationSpec = tween(
                        durationMillis = NavigationTransitionDurationMillis,
                        easing = NavigationTransitionEasing,
                    ),
                    initialOffsetX = { it },
                )
            }
        },
        exitTransition = {
            if (targetState.destination.isTabDestination() || !animateTransition) {
                ExitTransition.None
            } else {
                slideOutHorizontally(
                    animationSpec = tween(
                        durationMillis = NavigationTransitionDurationMillis,
                        easing = NavigationTransitionEasing,
                    ),
                    targetOffsetX = { -it / NavigationParallaxDivisor },
                ) + fadeOut(
                    animationSpec = tween(
                        durationMillis = NavigationTransitionDurationMillis,
                        easing = NavigationTransitionEasing,
                    ),
                    targetAlpha = NavigationDimmedAlpha,
                )
            }
        },
        popEnterTransition = {
            if (targetState.destination.isTabDestination() || !animateTransition) {
                EnterTransition.None
            } else {
                slideInHorizontally(
                    animationSpec = tween(
                        durationMillis = NavigationTransitionDurationMillis,
                        easing = NavigationTransitionEasing,
                    ),
                    initialOffsetX = { -it / NavigationParallaxDivisor },
                ) + fadeIn(
                    animationSpec = tween(
                        durationMillis = NavigationTransitionDurationMillis,
                        easing = NavigationTransitionEasing,
                    ),
                    initialAlpha = NavigationDimmedAlpha,
                )
            }
        },
        popExitTransition = {
            if (targetState.destination.isTabDestination() || !animateTransition) {
                ExitTransition.None
            } else {
                slideOutHorizontally(
                    animationSpec = tween(
                        durationMillis = NavigationTransitionDurationMillis,
                        easing = NavigationTransitionEasing,
                    ),
                    targetOffsetX = { it },
                )
            }
        },
    ) {
        composable<HomeRoute> {
            SocialFeed(
                onPostClick = { post -> navController.navigate(PostDetailsRoute(post.id)) },
                onProfileClick = { profileId -> navController.navigate(ProfileRoute(profileId)) },
                onNewPostClick = { navController.navigateToComposeTab() },
            )
        }
        composable<PostDetailsRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<PostDetailsRoute>()
            PostDetails(
                postId = route.postId,
            )
        }
        composable<SearchRoute> {
            Search()
        }
        composable<NewPostRoute> {
            PostComposer()
        }
        composable<ActivityRoute> {
            Activity()
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

private fun NavDestination.isTabDestination(): Boolean {
    return hasRoute<SearchRoute>() ||
            hasRoute<NewPostRoute>() ||
            hasRoute<ActivityRoute>()
}

private fun NavHostController.navigateToProfile(profileId: String) {
    navigate(ProfileRoute(profileId)) {
        launchSingleTop = true
    }
}

private fun NavHostController.navigateToComposeTab() {
    navigate(NewPostRoute) {
        popUpTo(HomeRoute)
        launchSingleTop = true
    }
}
