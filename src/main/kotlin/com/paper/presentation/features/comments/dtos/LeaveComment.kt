package com.paper.presentation.features.comments.dtos

import kotlinx.serialization.Serializable

@Serializable
data class LeaveCommentRequest(
    val postId: Int,
    val text: String,
)