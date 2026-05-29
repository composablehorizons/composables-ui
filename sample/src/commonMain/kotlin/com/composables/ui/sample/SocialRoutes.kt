package com.composables.ui.sample

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface SocialRoute : NavKey

@Serializable
data object HomeRoute : SocialRoute

@Serializable
data object HomeTabRoute : SocialRoute

@Serializable
data object SearchTabRoute : SocialRoute

@Serializable
data object ComposeTabRoute : SocialRoute

@Serializable
data object NotificationsTabRoute : SocialRoute

@Serializable
data class PostRoute(val postId: String) : SocialRoute

@Serializable
data class ProfileRoute(val profileId: String) : SocialRoute

@Serializable
data object ProfileTabRoute : SocialRoute
