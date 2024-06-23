package com.paper.domain.repos

import com.paper.domain.entities.Post

interface IPostsRepository {

    suspend fun createPost(
        creatorId: Int,
        text: String,
        title: String,
        desc: String,
    )

    suspend fun getPosts(limit: Int, page: Int, userId: Int): List<Post>

    suspend fun deletePost(postId: Int)

    suspend fun likePost(userId: Int, postId: Int): Int // new count of likes

    suspend fun dislikePost(userId: Int, postId: Int): Int
}