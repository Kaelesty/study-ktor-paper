package com.paper.presentation.features.posts.dtos

import kotlinx.serialization.Serializable

@Serializable
data class DeletePostRequest(
    val postId: Int
)