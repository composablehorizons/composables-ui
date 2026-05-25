package com.composables.ui.sample

import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

@Serializable
data object HomeTabRoute

@Serializable
data class PostRoute(val postId: String)

@Serializable
data class ProfileRoute(val profileId: String)

@Serializable
data object ProfileTabRoute
