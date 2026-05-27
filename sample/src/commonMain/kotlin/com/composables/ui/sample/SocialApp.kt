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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.composables.icons.lucide.Bell
import com.composables.icons.lucide.House
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Plus
import com.composables.icons.lucide.Search
import com.composables.icons.lucide.User
import com.composables.ui.components.Icon
import com.composables.ui.theme.AppScaffold
import com.composables.ui.theme.Large
import com.composables.ui.theme.Medium
import com.composables.ui.components.NavigationBar
import com.composables.ui.components.NavigationBarItem
import com.composables.ui.components.Sidebar
import com.composables.ui.components.SidebarItem
import com.composables.ui.components.SidebarMode
import com.composables.ui.components.Text
import com.composeunstyled.currentWidthBreakpoint

private const val NavigationTransitionDurationMillis = 350
private const val NavigationParallaxDivisor = 5
private const val NavigationDimmedAlpha = 0.86f
private val NavigationTransitionEasing = CubicBezierEasing(0.32f, 0.72f, 0f, 1f)

@Composable
fun SocialApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val homeSelected = currentDestination == null ||
            currentDestination.hasRoute<HomeRoute>() ||
            currentDestination.hasRoute<HomeTabRoute>() ||
            currentDestination.hasRoute<PostRoute>()
    val searchSelected = currentDestination?.hasRoute<SearchTabRoute>() == true
    val composeSelected = currentDestination?.hasRoute<ComposeTabRoute>() == true
    val notificationsSelected = currentDestination?.hasRoute<NotificationsTabRoute>() == true
    val profileSelected = currentDestination?.hasRoute<ProfileRoute>() == true ||
            currentDestination?.hasRoute<ProfileTabRoute>() == true

    AppScaffold {
        val widthBreakpoint = currentWidthBreakpoint()

        if (widthBreakpoint isAtLeast Medium) {
            Box(modifier = Modifier.fillMaxSize()) {
                SocialNavHost(
                    navController = navController,
                    modifier = Modifier.fillMaxSize(),
                    animateStackTransitions = false,
                )
                Sidebar(
                    modifier = Modifier.align(Alignment.CenterStart),
                    mode = if (widthBreakpoint isAtLeast Large) SidebarMode.Expanded else SidebarMode.Compact,
                    content = {
                        SidebarItem(
                            selected = homeSelected,
                            icon = { Icon(Lucide.House) },
                            onClick = { navController.navigateToHomeTab() },
                            text = {
                                Text(text = "Home", singleLine = true)
                            },
                        )
                        SidebarItem(
                            selected = searchSelected,
                            icon = { Icon(Lucide.Search) },
                            onClick = { navController.navigateToSearchTab() },
                            text = {
                                Text(text = "Search", singleLine = true)
                            },
                        )
                        SidebarItem(
                            selected = composeSelected,
                            icon = { Icon(Lucide.Plus) },
                            onClick = { navController.navigateToComposeTab() },
                            text = {
                                Text(text = "New post", singleLine = true)
                            },
                        )
                        SidebarItem(
                            selected = notificationsSelected,
                            icon = { Icon(Lucide.Bell) },
                            onClick = { navController.navigateToNotificationsTab() },
                            text = {
                                Text(text = "Activity", singleLine = true)
                            },
                        )
                        SidebarItem(
                            selected = profileSelected,
                            icon = { Icon(Lucide.User) },
                            onClick = { navController.navigateToProfileTab() },
                            text = {
                                Text(text = "Profile", singleLine = true)
                            },
                        )
                    },
                )
            }
        } else {
            Box(modifier = Modifier.fillMaxSize()) {
                SocialNavHost(
                    navController = navController,
                    modifier = Modifier.fillMaxSize(),
                    animateStackTransitions = true,
                )
                NavigationBar(modifier = Modifier.align(Alignment.BottomCenter)) {
                    NavigationBarItem(
                        modifier = Modifier.weight(1f),
                        selected = homeSelected,
                        onClick = { navController.navigateToHomeTab() },
                        icon = { Icon(Lucide.House, contentDescription = "Home") }
                    )
                    NavigationBarItem(
                        modifier = Modifier.weight(1f),
                        selected = searchSelected,
                        onClick = { navController.navigateToSearchTab() },
                        icon = { Icon(Lucide.Search, contentDescription = "Search") })
                    NavigationBarItem(
                        modifier = Modifier.weight(1f),
                        selected = composeSelected,
                        onClick = { navController.navigateToComposeTab() },
                        icon = { Icon(Lucide.Plus, contentDescription = "New post") }
                    )
                    NavigationBarItem(
                        modifier = Modifier.weight(1f),
                        selected = notificationsSelected,
                        onClick = { navController.navigateToNotificationsTab() },
                        icon = { Icon(Lucide.Bell, contentDescription = "Activity") })
                    NavigationBarItem(
                        modifier = Modifier.weight(1f),
                        selected = profileSelected,
                        onClick = { navController.navigateToProfileTab() },
                        icon = { Icon(Lucide.User, contentDescription = "Profile") })
                }
            }
        }
    }
}

@Composable
private fun SocialNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    animateStackTransitions: Boolean,
) {
    Box(modifier = modifier.background(Color.Black)) {
        NavHost(
            navController = navController,
            startDestination = HomeRoute,
            modifier = Modifier.fillMaxSize(),
            enterTransition = {
                if (targetState.destination.isTabDestination() || !animateStackTransitions) {
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
                if (targetState.destination.isTabDestination() || !animateStackTransitions) {
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
                if (targetState.destination.isTabDestination() || !animateStackTransitions) {
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
                if (targetState.destination.isTabDestination() || !animateStackTransitions) {
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
                HomeScreen(
                    onPostClick = { post -> navController.navigate(PostRoute(post.id)) },
                    onProfileClick = { profileId -> navController.navigate(ProfileRoute(profileId)) },
                )
            }
            composable<HomeTabRoute>(
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
                popEnterTransition = { EnterTransition.None },
                popExitTransition = { ExitTransition.None },
            ) {
                HomeScreen(
                    onPostClick = { post -> navController.navigate(PostRoute(post.id)) },
                    onProfileClick = { profileId -> navController.navigate(ProfileRoute(profileId)) },
                )
            }
            composable<PostRoute> { backStackEntry ->
                val route = backStackEntry.toRoute<PostRoute>()
                PostDetailScreen(
                    postId = route.postId,
                    onBack = { navController.navigateUp() },
                )
            }
            composable<SearchTabRoute>(
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
                popEnterTransition = { EnterTransition.None },
                popExitTransition = { ExitTransition.None },
            ) {
                SearchScreen()
            }
            composable<ComposeTabRoute>(
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
                popEnterTransition = { EnterTransition.None },
                popExitTransition = { ExitTransition.None },
            ) {
                ComposeScreen()
            }
            composable<NotificationsTabRoute>(
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
                popEnterTransition = { EnterTransition.None },
                popExitTransition = { ExitTransition.None },
            ) {
                NotificationsScreen()
            }
            composable<ProfileRoute> { backStackEntry ->
                val route = backStackEntry.toRoute<ProfileRoute>()
                ProfileScreen(
                    profileId = route.profileId,
                    onBack = { navController.navigateUp() },
                    onPostClick = { postId -> navController.navigate(PostRoute(postId)) },
                    onProfileClick = { navController.navigateToProfile("john_mobbin") },
                )
            }
            composable<ProfileTabRoute>(
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
                popEnterTransition = { EnterTransition.None },
                popExitTransition = { ExitTransition.None },
            ) {
                ProfileScreen(
                    profileId = "john_mobbin",
                    onBack = null,
                    onPostClick = { postId -> navController.navigate(PostRoute(postId)) },
                    onProfileClick = { navController.navigateToProfile("john_mobbin") },
                )
            }
        }
    }
}

private fun NavDestination.isTabDestination(): Boolean {
    return hasRoute<HomeTabRoute>() ||
            hasRoute<SearchTabRoute>() ||
            hasRoute<ComposeTabRoute>() ||
            hasRoute<NotificationsTabRoute>() ||
            hasRoute<ProfileTabRoute>()
}

private fun NavHostController.navigateToProfile(profileId: String) {
    navigate(ProfileRoute(profileId)) {
        launchSingleTop = true
    }
}

private fun NavHostController.navigateToHomeTab() {
    navigate(HomeTabRoute) {
        popUpTo(HomeRoute)
        launchSingleTop = true
    }
}

private fun NavHostController.navigateToSearchTab() {
    navigate(SearchTabRoute) {
        popUpTo(HomeRoute)
        launchSingleTop = true
    }
}

private fun NavHostController.navigateToComposeTab() {
    navigate(ComposeTabRoute) {
        popUpTo(HomeRoute)
        launchSingleTop = true
    }
}

private fun NavHostController.navigateToNotificationsTab() {
    navigate(NotificationsTabRoute) {
        popUpTo(HomeRoute)
        launchSingleTop = true
    }
}

private fun NavHostController.navigateToProfileTab() {
    navigate(ProfileTabRoute) {
        popUpTo(HomeRoute)
        launchSingleTop = true
    }
}
