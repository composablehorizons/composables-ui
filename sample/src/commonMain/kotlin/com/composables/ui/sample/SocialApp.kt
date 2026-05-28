package com.composables.ui.sample

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
import com.composables.ui.components.NavigationBar
import com.composables.ui.components.NavigationBarItem
import com.composables.ui.components.Sidebar
import com.composables.ui.components.SidebarItem
import com.composables.ui.components.SidebarMode
import com.composables.ui.components.Text
import com.composables.ui.sample.data.authenticatedUser
import com.composables.ui.theme.AppScaffold
import com.composables.ui.theme.Large
import com.composables.ui.theme.Medium
import com.composeunstyled.currentWidthBreakpoint
import kotlinx.serialization.Serializable

private const val NavigationTransitionDurationMillis = 350
private const val NavigationParallaxDivisor = 5
private const val NavigationDimmedAlpha = 0.86f
private val NavigationTransitionEasing = CubicBezierEasing(0.32f, 0.72f, 0f, 1f)

@Serializable
data object SocialFeedRoute

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
            currentDestination.hasRoute<SocialFeedRoute>() ||
            currentDestination.hasRoute<PostDetailsRoute>()
    val searchSelected = currentDestination?.hasRoute<SearchRoute>() == true
    val composeSelected = currentDestination?.hasRoute<NewPostRoute>() == true
    val notificationsSelected = currentDestination?.hasRoute<ActivityRoute>() == true
    val profileSelected = currentDestination?.hasRoute<ProfileRoute>() == true

    AppScaffold {
        Box(modifier = Modifier.fillMaxSize()) {
            val widthBreakpoint = currentWidthBreakpoint()

            TabHost(
                navController = navController,
                modifier = Modifier
                    .padding(horizontal = if (widthBreakpoint isAtLeast Medium) 80.dp else 0.dp)
                    .widthIn(max = 700.dp)
                    .fillMaxSize(),
            )

            if (widthBreakpoint isAtLeast Medium) {
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
                            onClick = { navController.navigateToProfile(authenticatedUser.id) },
                            text = {
                                Text(text = "Profile", singleLine = true)
                            },
                        )
                    },
                )
            } else {
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
        startDestination = SocialFeedRoute,
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
        composable<SocialFeedRoute> {
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
                onBack = { navController.navigateUp() },
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
                onBack = { navController.navigateUp() },
                onPostClick = { postId -> navController.navigate(PostDetailsRoute(postId)) },
                onProfileClick = { navController.navigateToProfile(authenticatedUser.id) },
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

private fun NavHostController.navigateToSearchTab() {
    navigate(SearchRoute) {
        popUpTo(SocialFeedRoute)
        launchSingleTop = true
    }
}

private fun NavHostController.navigateToComposeTab() {
    navigate(NewPostRoute) {
        popUpTo(SocialFeedRoute)
        launchSingleTop = true
    }
}

private fun NavHostController.navigateToNotificationsTab() {
    navigate(ActivityRoute) {
        popUpTo(SocialFeedRoute)
        launchSingleTop = true
    }
}

private fun NavHostController.navigateToHomeTab() {
    navigate(SocialFeedRoute) {
        popUpTo(SocialFeedRoute)
        launchSingleTop = true
    }
}
