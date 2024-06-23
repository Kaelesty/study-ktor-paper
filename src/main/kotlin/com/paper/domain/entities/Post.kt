package com.paper.domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: Int,
    val title: String,
    val authorId: Int,
    val desc: String,
    val text: String,
    val createdTimeMillis: Long,
    val likesCount: Int,
    val isLiked: Boolean = false
)