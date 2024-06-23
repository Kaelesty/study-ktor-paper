package com.paper.domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class Comment(
    val id: Int,
    val postId: Int,
    val authorId: Int,
    val text: String,
    val createdTimeMillis: Long
)