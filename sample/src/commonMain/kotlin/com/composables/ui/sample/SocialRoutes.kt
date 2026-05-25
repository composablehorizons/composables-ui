package com.composables.ui.sample

import kotlinx.serialization.Serializable

@Serializable
internal data object HomeRoute

@Serializable
internal data class PostRoute(val postId: String)

@Serializable
internal data class ProfileRoute(val profileId: String)
