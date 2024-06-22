package com.paper.domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class Comment(
    val id: Int,
    val authorId: Int,
    val text: String,
    val createdTimeMillis: String
)