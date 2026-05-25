package com.composables.ui.sample

import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

@Serializable
data class PostRoute(val postId: String)

@Serializable
data class ProfileRoute(val profileId: String)
