package com.paper.domain.repos

import com.paper.domain.entities.Comment

interface ICommentsRepository {

    fun leaveComment(
        postId: Int,
        creatorId: Int,
        text: String
    )

    fun getPostComments(
        postId: Int
    ): List<Comment>

    fun deleteComment(
        commentId: Int
    )
}