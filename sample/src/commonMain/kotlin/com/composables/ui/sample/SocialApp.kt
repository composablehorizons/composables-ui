package com.composables.ui.sample

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation3.runtime.NavMetadataKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.get
import androidx.navigation3.runtime.metadata
import androidx.navigation3.scene.DialogSceneStrategy
import androidx.navigation3.scene.Scene
import androidx.navigation3.ui.NavDisplay
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
import com.composables.ui.theme.AppScaffold
import com.composables.ui.theme.Large
import com.composables.ui.theme.Medium
import com.composeunstyled.currentWidthBreakpoint

private const val NavigationTransitionDurationMillis = 350
private const val NavigationParallaxDivisor = 5
private const val NavigationDimmedAlpha = 0.86f
private val NavigationTransitionEasing = CubicBezierEasing(0.32f, 0.72f, 0f, 1f)
private object TabDestinationKey : NavMetadataKey<Boolean>
private val TabDestinationMetadata = metadata { put(TabDestinationKey, true) }

@Composable
fun SocialApp() {
    val backStack = remember { mutableStateListOf<SocialRoute>(HomeRoute) }
    val currentRoute = backStack.lastOrNull()
    val homeSelected = currentRoute == null ||
            currentRoute is HomeRoute ||
            currentRoute is HomeTabRoute ||
            currentRoute is PostRoute
    val searchSelected = currentRoute is SearchTabRoute
    val composeSelected = currentRoute is ComposeTabRoute
    val notificationsSelected = currentRoute is NotificationsTabRoute
    val profileSelected = currentRoute is ProfileRoute || currentRoute is ProfileTabRoute

    fun navigate(route: SocialRoute) {
        if (backStack.lastOrNull() != route) {
            backStack.add(route)
        }
    }

    fun navigateToTab(route: SocialRoute) {
        while (backStack.size > 1) {
            backStack.removeLast()
        }
        if (backStack.lastOrNull() != route) {
            backStack.add(route)
        }
    }

    AppScaffold {
        val widthBreakpoint = currentWidthBreakpoint()

        if (widthBreakpoint isAtLeast Medium) {
            Box(modifier = Modifier.fillMaxSize()) {
                SocialNavHost(
                    backStack = backStack,
                    onNavigate = ::navigate,
                    onNavigateToTab = ::navigateToTab,
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
                            onClick = { navigateToTab(HomeTabRoute) },
                            text = {
                                Text(text = "Home", singleLine = true)
                            },
                        )
                        SidebarItem(
                            selected = searchSelected,
                            icon = { Icon(Lucide.Search) },
                            onClick = { navigateToTab(SearchTabRoute) },
                            text = {
                                Text(text = "Search", singleLine = true)
                            },
                        )
                        SidebarItem(
                            selected = composeSelected,
                            icon = { Icon(Lucide.Plus) },
                            onClick = { navigateToTab(ComposeTabRoute) },
                            text = {
                                Text(text = "New post", singleLine = true)
                            },
                        )
                        SidebarItem(
                            selected = notificationsSelected,
                            icon = { Icon(Lucide.Bell) },
                            onClick = { navigateToTab(NotificationsTabRoute) },
                            text = {
                                Text(text = "Activity", singleLine = true)
                            },
                        )
                        SidebarItem(
                            selected = profileSelected,
                            icon = { Icon(Lucide.User) },
                            onClick = { navigateToTab(ProfileTabRoute) },
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
                    backStack = backStack,
                    onNavigate = ::navigate,
                    onNavigateToTab = ::navigateToTab,
                    modifier = Modifier.fillMaxSize(),
                    animateStackTransitions = true,
                )
                NavigationBar(modifier = Modifier.align(Alignment.BottomCenter)) {
                    NavigationBarItem(
                        modifier = Modifier.weight(1f),
                        selected = homeSelected,
                        onClick = { navigateToTab(HomeTabRoute) },
                        icon = { Icon(Lucide.House, contentDescription = "Home") }
                    )
                    NavigationBarItem(
                        modifier = Modifier.weight(1f),
                        selected = searchSelected,
                        onClick = { navigateToTab(SearchTabRoute) },
                        icon = { Icon(Lucide.Search, contentDescription = "Search") })
                    NavigationBarItem(
                        modifier = Modifier.weight(1f),
                        selected = composeSelected,
                        onClick = { navigateToTab(ComposeTabRoute) },
                        icon = { Icon(Lucide.Plus, contentDescription = "New post") }
                    )
                    NavigationBarItem(
                        modifier = Modifier.weight(1f),
                        selected = notificationsSelected,
                        onClick = { navigateToTab(NotificationsTabRoute) },
                        icon = { Icon(Lucide.Bell, contentDescription = "Activity") })
                    NavigationBarItem(
                        modifier = Modifier.weight(1f),
                        selected = profileSelected,
                        onClick = { navigateToTab(ProfileTabRoute) },
                        icon = { Icon(Lucide.User, contentDescription = "Profile") })
                }
            }
        }
    }
}

@Composable
private fun SocialNavHost(
    backStack: MutableList<SocialRoute>,
    onNavigate: (SocialRoute) -> Unit,
    onNavigateToTab: (SocialRoute) -> Unit,
    modifier: Modifier = Modifier,
    animateStackTransitions: Boolean,
) {
    Box(modifier = modifier.background(Color.Black)) {
        NavDisplay(
            backStack = backStack,
            modifier = Modifier.fillMaxSize(),
            sceneStrategies = listOf(DialogSceneStrategy()),
            transitionSpec = {
                if (targetState.isTabScene() || !animateStackTransitions) {
                    EnterTransition.None togetherWith ExitTransition.None
                } else {
                    slideInHorizontally(
                        animationSpec = tween(
                            durationMillis = NavigationTransitionDurationMillis,
                            easing = NavigationTransitionEasing,
                        ),
                        initialOffsetX = { it },
                    ) togetherWith slideOutHorizontally(
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
            popTransitionSpec = {
                if (targetState.isTabScene() || !animateStackTransitions) {
                    EnterTransition.None togetherWith ExitTransition.None
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
                    ) togetherWith slideOutHorizontally(
                        animationSpec = tween(
                            durationMillis = NavigationTransitionDurationMillis,
                            easing = NavigationTransitionEasing,
                        ),
                        targetOffsetX = { it },
                    )
                }
            },
            predictivePopTransitionSpec = { _ ->
                if (targetState.isTabScene() || !animateStackTransitions) {
                    EnterTransition.None togetherWith ExitTransition.None
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
                    ) togetherWith slideOutHorizontally(
                        animationSpec = tween(
                            durationMillis = NavigationTransitionDurationMillis,
                            easing = NavigationTransitionEasing,
                        ),
                        targetOffsetX = { it },
                    )
                }
            },
            entryProvider = entryProvider {
                entry<HomeRoute> {
                    HomeScreen(
                        onPostClick = { post -> onNavigate(PostRoute(post.id)) },
                        onProfileClick = { profileId -> onNavigate(ProfileRoute(profileId)) },
                        onNewPostClick = { onNavigateToTab(ComposeTabRoute) },
                    )
                }
                entry<HomeTabRoute>(
                    metadata = TabDestinationMetadata,
                ) {
                    HomeScreen(
                        onPostClick = { post -> onNavigate(PostRoute(post.id)) },
                        onProfileClick = { profileId -> onNavigate(ProfileRoute(profileId)) },
                        onNewPostClick = { onNavigateToTab(ComposeTabRoute) },
                    )
                }
                entry<PostRoute> { route ->
                    PostDetailScreen(
                        postId = route.postId,
                        onBack = { backStack.removeLastOrNull() },
                    )
                }
                entry<SearchTabRoute>(
                    metadata = TabDestinationMetadata,
                ) {
                    SearchScreen()
                }
                entry<ComposeTabRoute>(
                    metadata = DialogSceneStrategy.dialog() + TabDestinationMetadata,
                ) {
                    ComposeScreen()
                }
                entry<NotificationsTabRoute>(
                    metadata = TabDestinationMetadata,
                ) {
                    NotificationsScreen()
                }
                entry<ProfileRoute> { route ->
                    ProfileScreen(
                        profileId = route.profileId,
                        onBack = { backStack.removeLastOrNull() },
                        onPostClick = { postId -> onNavigate(PostRoute(postId)) },
                        onProfileClick = { onNavigate(ProfileRoute("john_mobbin")) },
                    )
                }
                entry<ProfileTabRoute>(
                    metadata = TabDestinationMetadata,
                ) {
                    ProfileScreen(
                        profileId = "john_mobbin",
                        onBack = null,
                        onPostClick = { postId -> onNavigate(PostRoute(postId)) },
                        onProfileClick = { onNavigate(ProfileRoute("john_mobbin")) },
                    )
                }
            },
        )
    }
}

private fun Scene<SocialRoute>.isTabScene(): Boolean {
    return metadata[TabDestinationKey] == true
}
