package com.paper.presentation.features.posts.dtos

import kotlinx.serialization.Serializable

@Serializable
data class CreatePostRequest(
    val title: String,
    val text: String,
    val desc: String,
)