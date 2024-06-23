package com.paper.data.database.repoimpls

import com.paper.data.database.schemas.CommentsService
import com.paper.domain.entities.Comment
import com.paper.domain.repos.ICommentsRepository
import javax.inject.Inject

class CommentsRepository @Inject constructor(
    private val commentsService: CommentsService
) : ICommentsRepository {
    override suspend fun leaveComment(postId: Int, creatorId: Int, text: String) {
        commentsService.create(
            Comment(
                id = -1,
                postId, creatorId, text,
                createdTimeMillis = System.currentTimeMillis()
            )
        )
    }

    override suspend fun getPostComments(postId: Int): List<Comment> {
        return commentsService.getPostComments(postId)
    }

    override suspend fun deleteComment(commentId: Int) {
        commentsService.deleteComment(commentId)
    }
}