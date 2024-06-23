package com.paper.domain.repos

import com.paper.domain.entities.Comment

interface ICommentsRepository {

    suspend fun leaveComment(
        postId: Int,
        creatorId: Int,
        text: String
    )

    suspend fun getPostComments(
        postId: Int
    ): List<Comment>

    suspend fun deleteComment(
        commentId: Int
    )
}