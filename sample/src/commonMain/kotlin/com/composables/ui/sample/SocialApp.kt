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
import com.composables.ui.components.AppScaffold
import com.composables.ui.components.ExpandedWidthBreakpoint
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
        if (widthBreakpoint isAtLeast ExpandedWidthBreakpoint) {
            Box(modifier = Modifier.fillMaxSize()) {
                SocialNavHost(
                    navController = navController,
                    modifier = Modifier.fillMaxSize(),
                )
                SocialSidebar(
                    homeSelected = homeSelected,
                    searchSelected = searchSelected,
                    composeSelected = composeSelected,
                    notificationsSelected = notificationsSelected,
                    profileSelected = profileSelected,
                    onHomeClick = { navController.navigateToHomeTab() },
                    onSearchClick = { navController.navigateToSearchTab() },
                    onComposeClick = { navController.navigateToComposeTab() },
                    onNotificationsClick = { navController.navigateToNotificationsTab() },
                    onProfileClick = { navController.navigateToProfileTab() },
                    modifier = Modifier.align(Alignment.CenterStart),
                )
            }
        } else {
            Box(modifier = Modifier.fillMaxSize()) {
                SocialNavHost(
                    navController = navController,
                    modifier = Modifier.fillMaxSize(),
                )
                SocialBottomBar(
                    homeSelected = homeSelected,
                    searchSelected = searchSelected,
                    composeSelected = composeSelected,
                    notificationsSelected = notificationsSelected,
                    profileSelected = profileSelected,
                    onHomeClick = { navController.navigateToHomeTab() },
                    onSearchClick = { navController.navigateToSearchTab() },
                    onComposeClick = { navController.navigateToComposeTab() },
                    onNotificationsClick = { navController.navigateToNotificationsTab() },
                    onProfileClick = { navController.navigateToProfileTab() },
                    modifier = Modifier.align(Alignment.BottomCenter),
                )
            }
        }
    }
}

@Composable
private fun SocialNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.background(Color.Black)) {
        NavHost(
            navController = navController,
            startDestination = HomeRoute,
            modifier = Modifier.fillMaxSize(),
            enterTransition = {
                if (targetState.destination.isTabDestination()) {
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
                if (targetState.destination.isTabDestination()) {
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
                if (targetState.destination.isTabDestination()) {
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
                if (targetState.destination.isTabDestination()) {
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
            composable<PostRoute> {
                PostDetailScreen()
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
