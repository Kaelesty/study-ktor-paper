package com.paper.presentation.features.comments.dtos

import kotlinx.serialization.Serializable

@Serializable
data class DeleteCommentRequest(
    val commentId: Int
)