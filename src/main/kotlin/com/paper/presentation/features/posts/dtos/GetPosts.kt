package com.paper.presentation.features.posts.dtos

import com.paper.domain.entities.Post
import com.paper.domain.entities.User
import kotlinx.serialization.Serializable

@Serializable
data class GetPostsResponse(
    val posts: List<PostResponse>
)

@Serializable
data class PostResponse(
    val id: Int,
    val title: String,
    val authorName: String,
    val desc: String,
    val text: String,
    val createdTimeMillis: Long,
    val likesCount: Int
) {
    companion object {

        fun fromPost(post: Post, authorName: String) = with(post) {
            PostResponse(id, title, authorName, desc, text, createdTimeMillis, likesCount)
        }
    }
}