package com.paper.presentation.features.comments.dtos

import com.paper.domain.entities.Comment
import kotlinx.serialization.Serializable

@Serializable
data class GetPostCommentsRequest(
    val postId: Int
)

@Serializable
data class GetPostCommentsResponse(
    val comments: List<CommentResponse>
)

@Serializable
data class CommentResponse(
    val id: Int,
    val authorName: String,
    val text: String,
    val createdTimeMillis: Long,
    val isAuthored: Boolean
) {

    companion object {
        fun fromComment(comment: Comment, authorName: String, userId: Int) = with(comment) {
            CommentResponse(
                id, authorName, text, createdTimeMillis,
                isAuthored = userId == authorId
            )
        }
    }
}