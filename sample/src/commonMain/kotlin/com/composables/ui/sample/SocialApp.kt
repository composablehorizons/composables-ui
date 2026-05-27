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
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.composables.ui.components.AppScaffold
import com.composables.ui.components.ExpandedWidthBreakpoint
import com.composables.ui.theme.border
import com.composables.ui.theme.colors
import com.composeunstyled.currentWidthBreakpoint
import com.composeunstyled.theme.Theme

private const val NavigationTransitionDurationMillis = 350
private const val NavigationParallaxDivisor = 5
private const val NavigationDimmedAlpha = 0.86f
private val NavigationTransitionEasing = CubicBezierEasing(0.32f, 0.72f, 0f, 1f)

@Composable
fun SocialApp() {
    val navController = rememberNavController()

    AppScaffold {
        val widthBreakpoint = currentWidthBreakpoint()
        if (widthBreakpoint isAtLeast ExpandedWidthBreakpoint) {
            Row(modifier = Modifier.fillMaxSize()) {
                // placeholder for sidebar
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(300.dp)
                        .border(width = 1.dp, color = Theme[colors][border]),
                )
                SocialNavHost(
                    navController = navController,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                )
            }
        } else {
            Box(modifier = Modifier.fillMaxSize()) {
                SocialNavHost(
                    navController = navController,
                    modifier = Modifier.fillMaxSize(),
                )
                SocialBottomBar(
                    onHomeClick = { navController.navigateToHomeTab() },
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
            composable<PostRoute> { backStackEntry ->
                val route = backStackEntry.toRoute<PostRoute>()
                PostDetailScreen(
                    postId = route.postId,
                    onBack = { navController.navigateUp() },
                    onProfileClick = { profileId -> navController.navigate(ProfileRoute(profileId)) },
                )
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
    return hasRoute<HomeTabRoute>() || hasRoute<ProfileTabRoute>()
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

private fun NavHostController.navigateToProfileTab() {
    navigate(ProfileTabRoute) {
        popUpTo(HomeRoute)
        launchSingleTop = true
    }
}
