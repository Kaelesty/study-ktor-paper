package com.paper.data.database.repoimpls

import com.paper.data.database.schemas.CommentsService
import com.paper.data.database.schemas.PostLikeService
import com.paper.data.database.schemas.PostService
import com.paper.domain.entities.Post
import com.paper.domain.repos.IPostsRepository
import javax.inject.Inject

class PostsRepository @Inject constructor(
    private val postService: PostService,
    private val postLikeService: PostLikeService,
    private val commentsService: CommentsService
) : IPostsRepository {
    override suspend fun createPost(creatorId: Int, text: String, title: String, desc: String) {
        postService.create(
            Post(
                id = -1, // unused in db's row creation
                title = title,
                authorId = creatorId,
                desc = desc,
                text = text,
                createdTimeMillis = System.currentTimeMillis(),
                likesCount = -1 // unused in db's row creation
            )
        )
    }

    override suspend fun getPosts(limit: Int, page: Int, userId: Int): List<Post> {
        return postService.getAll(limit, page).map {
            it.copy(
                isLiked = postLikeService.isPostLikedByUser(userId, it.id)
            )
        }
    }

    override suspend fun deletePost(postId: Int) {
        postService.delete(postId)
        postLikeService.deleteAllPostLikes(postId)
        commentsService.deleteAllPostComments(postId)
    }

    override suspend fun likePost(userId: Int, postId: Int): Int {
        postLikeService.create(userId, postId)
        return postLikeService.getPostLikesCount(postId)
    }

    override suspend fun dislikePost(userId: Int, postId: Int): Int {
        postLikeService.delete(userId, postId)
        return postLikeService.getPostLikesCount(postId)
    }
}