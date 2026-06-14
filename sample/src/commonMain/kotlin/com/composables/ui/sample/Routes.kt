package com.composables.ui.sample

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

interface ModalRoute : NavKey

@Serializable
data object HomeFeedRoute : NavKey

@Serializable
data object SearchRoute : NavKey

@Serializable
data object NewPostRoute : ModalRoute

@Serializable
data object ActivityRoute : NavKey

@Serializable
data class ProfileRoute(val profileId: String) : NavKey

@Serializable
data class PostRoute(val profileId: String, val postId: String) : NavKey
