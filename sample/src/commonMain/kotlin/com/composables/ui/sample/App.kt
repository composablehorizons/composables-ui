package com.composables.ui.sample

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.composables.tooling.insets.previewNavigationBarPadding
import com.composables.tooling.insets.previewNavigationBarPaddingValue
import com.composables.ui.components.ButtonStyle
import com.composables.ui.components.DropdownMenuAlignment
import com.composables.ui.components.DropdownMenuSide
import com.composables.ui.components.Icon
import com.composables.ui.components.IconButton
import com.composables.ui.components.NavigationBar
import com.composables.ui.components.NavigationBarItem
import com.composables.ui.components.Text
import com.composables.ui.components.Toolbar
import com.composables.ui.sample.iconography.ArrowLeft
import com.composables.ui.sample.iconography.Bell
import com.composables.ui.sample.iconography.House
import com.composables.ui.sample.iconography.Icons
import com.composables.ui.sample.iconography.Menu
import com.composables.ui.sample.iconography.Plus
import com.composables.ui.sample.iconography.Search
import com.composables.ui.sample.iconography.User
import com.composables.ui.sample.components.OtherMenuDropdown
import com.composables.ui.sample.components.Sidebar
import com.composables.ui.sample.components.SidebarItem
import com.composables.ui.sample.data.UserProfiles
import com.composables.ui.theme.borderColor
import com.composables.ui.theme.buttonShape
import com.composables.ui.theme.colors
import com.composables.ui.theme.overlayShadow
import com.composables.ui.theme.shadows
import com.composables.ui.theme.shapes
import com.composeunstyled.buildModifier
import com.composeunstyled.currentWindowWidthBreakpoint
import com.composeunstyled.outline
import com.composeunstyled.theme.Theme

val signedInProfile = UserProfiles.johnMobbin

@Composable
fun App() {
    val backStack = remember { NavBackStack<NavKey>(HomeFeedRoute) }
    var appearance by remember { mutableStateOf(Appearance.System) }
    val visibleRoute = backStack.asReversed().firstOrNull { it !is ModalRoute }
    val canGoBack = backStack.count { it !is ModalRoute } > 1

    val homeSelected = visibleRoute is HomeFeedRoute
    val searchSelected = visibleRoute is SearchRoute
    val activitySelected = visibleRoute is ActivityRoute
    val profileSelected = visibleRoute == ProfileRoute(signedInProfile.id) ||
            visibleRoute is PostRoute && visibleRoute.profileId == signedInProfile.id

    fun navigate(route: NavKey) {
        if (backStack.lastOrNull() != route) {
            backStack.add(route)
        }
    }

    fun resetBackstack(route: NavKey) {
        if (backStack.size != 1 || backStack.lastOrNull() != route) {
            backStack.clear()
            backStack.add(route)
        }
    }

    fun popBackStack() {
        if (backStack.size > 1) {
            backStack.removeLastOrNull()
        }
    }
    AppScaffold(appearance = appearance) {
        val widthBreakpoint = currentWindowWidthBreakpoint()

        Box(
            Modifier
                .fillMaxSize()
                .then(
                    buildModifier {
                        if (widthBreakpoint isAtLeast Medium) {
                            add(Modifier.previewNavigationBarPadding())
                        }
                    }
                )
        ) {
            Column(
                Modifier
                    .align(Alignment.TopCenter)
                    .padding(horizontal = if (widthBreakpoint isAtLeast Medium) 80.dp else 0.dp)
                    .widthIn(max = if (widthBreakpoint isAtLeast Medium) 700.dp else Dp.Unspecified)
                    .fillMaxSize(),
            ) {
                if (widthBreakpoint isAtLeast Medium) {
                    Toolbar(
                        modifier = Modifier.fillMaxWidth(),
                        title = { Text(visibleRoute.screenTitle) },
                        leading = if (canGoBack) {
                            {
                                IconButton(
                                    onClick = { popBackStack() },
                                    style = ButtonStyle.Ghost,
                                ) {
                                    Icon(Icons.ArrowLeft, contentDescription = "Go back")
                                }
                            }
                        } else {
                            null
                        },
                    )
                }
                Box(
                    modifier = buildModifier {
                        if (widthBreakpoint isAtLeast Medium) {
                            val contentShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                            add(
                                Modifier
                                    .outline(1.dp, Theme[colors][borderColor], contentShape)
                                    .clip(contentShape)
                            )
                        }
                    }
                        // we want the exiting screen to look dimmed. to achieve this effect,
                        // we set a black background, while fading the page itself during the transition
                        .background(Color.Black)
                ) {
                    NavDisplay(
                        backStack = backStack,
                        modifier = Modifier.fillMaxSize(),
                        sceneStrategies = listOf(ModalOverlaySceneStrategy()),
                        transitionSpec = { EnterTransition.None togetherWith ExitTransition.None },
                        popTransitionSpec = { EnterTransition.None togetherWith ExitTransition.None },
                        predictivePopTransitionSpec = { _ -> EnterTransition.None togetherWith ExitTransition.None },
                        entryProvider = entryProvider {
                            entry<HomeFeedRoute> {
                                SocialFeed(
                                    onPostClick = { post ->
                                        navigate(
                                            PostRoute(
                                                profileId = post.authorId,
                                                postId = post.id
                                            )
                                        )
                                    },
                                    onProfileClick = { profileId -> navigate(ProfileRoute(profileId)) },
                                    onNewPostClick = { navigate(NewPostRoute) },
                                )
                            }
                            entry<SearchRoute> {
                                Search(
                                    onProfileClick = { profileId -> navigate(ProfileRoute(profileId)) }
                                )
                            }
                            entry<ActivityRoute> {
                                Activity(
                                    onProfileClick = { profileId -> navigate(ProfileRoute(profileId)) }
                                )
                            }
                            entry<ProfileRoute> { route ->
                                Profile(
                                    profileId = route.profileId,
                                    signedInProfileId = signedInProfile.id,
                                    onPostClick = { post ->
                                        navigate(
                                            PostRoute(
                                                profileId = post.authorId,
                                                postId = post.id
                                            )
                                        )
                                    },
                                )
                            }
                            entry<PostRoute> { route ->
                                PostDetails(
                                    postId = route.postId,
                                    onBackClick = { popBackStack() },
                                    onProfileClick = { profileId -> navigate(ProfileRoute(profileId)) },
                                )
                            }
                            entry<NewPostRoute>(
                                metadata = ModalOverlaySceneStrategy.modalOverlay(),
                            ) {
                                NewPost(onBackClick = { popBackStack() })
                            }
                        },
                    )
                }
            }

            if (widthBreakpoint isAtLeast Medium) {
                IconButton(
                    onClick = { navigate(NewPostRoute) },
                    style = ButtonStyle.Outlined,
                    modifier = Modifier.align(Alignment.BottomEnd)
                        .padding(16.dp)
                        .size(if (widthBreakpoint isAtLeast Large) 64.dp else Dp.Unspecified)
                        .dropShadow(Theme[shapes][buttonShape], Theme[shadows][overlayShadow]),
                ) {
                    Icon(Icons.Plus, contentDescription = "New Post")
                }
            }

            if (widthBreakpoint isAtLeast Medium) {
                Sidebar(
                    modifier = Modifier.align(Alignment.CenterStart),
                    expanded = widthBreakpoint isAtLeast Large,
                    footer = {
                        var expanded by remember { mutableStateOf(false) }

                        OtherMenuDropdown(
                            appearance = appearance,
                            onAppearanceChange = { appearance = it },
                            side = DropdownMenuSide.Top,
                            alignment = DropdownMenuAlignment.Start,
                            expanded = expanded,
                            onExpandedChange = { expanded = it }
                        ) {
                            SidebarItem(
                                selected = false,
                                icon = { Icon(Icons.Menu) },
                                onClick = { expanded = true },
                                text = { Text(text = "More", singleLine = true) },
                            )
                        }
                    },
                    content = {
                        SidebarItem(
                            selected = homeSelected,
                            icon = { Icon(Icons.House) },
                            onClick = { resetBackstack(HomeFeedRoute) },
                            text = {
                                Text(text = "Home", singleLine = true)
                            },
                        )
                        SidebarItem(
                            selected = searchSelected,
                            icon = { Icon(Icons.Search) },
                            onClick = { resetBackstack(SearchRoute) },
                            text = {
                                Text(text = "Search", singleLine = true)
                            },
                        )
                        SidebarItem(
                            selected = activitySelected,
                            icon = { Icon(Icons.Bell) },
                            onClick = { resetBackstack(ActivityRoute) },
                            text = { Text(text = "Activity", singleLine = true) },
                        )
                        SidebarItem(
                            selected = profileSelected,
                            icon = { Icon(Icons.User) },
                            onClick = { resetBackstack(ProfileRoute(signedInProfile.id)) },
                            text = { Text(text = "Profile", singleLine = true) },
                        )
                    },
                )
            } else {
                NavigationBar(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    windowInsets = WindowInsets(bottom = previewNavigationBarPaddingValue()),
                ) {
                    NavigationBarItem(
                        selected = homeSelected,
                        onClick = { resetBackstack(HomeFeedRoute) },
                        content = { Icon(Icons.House, contentDescription = "Home") },
                        modifier = Modifier.weight(1f),
                    )
                    NavigationBarItem(
                        selected = searchSelected,
                        onClick = { resetBackstack(SearchRoute) },
                        content = { Icon(Icons.Search, contentDescription = "Search") },
                        modifier = Modifier.weight(1f),
                    )
                    NavigationBarItem(
                        selected = false,
                        onClick = { navigate(NewPostRoute) },
                        modifier = Modifier.weight(1f),
                    ) {
                        Icon(Icons.Plus, contentDescription = "New post")
                    }
                    NavigationBarItem(
                        selected = activitySelected,
                        onClick = { resetBackstack(ActivityRoute) },
                        content = { Icon(Icons.Bell, contentDescription = "Activity") },
                        modifier = Modifier.weight(1f),
                    )
                    NavigationBarItem(
                        selected = profileSelected,
                        onClick = { resetBackstack(ProfileRoute(signedInProfile.id)) },
                        content = { Icon(Icons.User, contentDescription = "Profile") },
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
    }
}

private val NavKey?.screenTitle: String
    get() = when (this) {
        is HomeFeedRoute -> "My Feed"
        is SearchRoute -> "Search"
        is ActivityRoute -> "Activity"
        is ProfileRoute -> "Profile"
        is PostRoute -> "Post"
        else -> "My Feed"
    }
