package com.composables.ui.sample

import kotlinx.serialization.Serializable

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

