package com.paper.presentation.features.posts.dtos

import kotlinx.serialization.Serializable

@Serializable
data class LikePostRequest(
    val postId: Int,
)

@Serializable
data class LikePostResponse(
    val newLikesCount: Int,
)