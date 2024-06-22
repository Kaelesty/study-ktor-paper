package com.paper.domain.repos

import com.paper.domain.entities.Post

interface IPostsRepository {

    fun createPost(
        creatorId: Int,
        text: String,
        title: String,
        desc: String,
    )

    fun getPosts(): List<Post>

    fun deletePost(postId: Int)

    fun likePost(postId: Int): Int // new count of likes
}