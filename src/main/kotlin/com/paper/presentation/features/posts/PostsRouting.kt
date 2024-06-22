package com.paper.presentation.features.posts

import com.paper.domain.repos.IAuthRepository
import com.paper.domain.repos.IPostsRepository
import com.paper.presentation.features.posts.dtos.*
import com.paper.presentation.runAuthorized
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configurePosts(
    path: String,
    repository: IPostsRepository,
    authRepository: IAuthRepository
) {
    routing {
        authenticate("jwt-auth") {
            post("$path/createPost") {
                call.runAuthorized<CreatePostRequest> { userId, req ->
                    repository.createPost(
                        creatorId = userId,
                        title = req.title,
                        desc = req.desc,
                        text = req.text,
                    )
                }
            }

            post("$path/likePost") {
                call.runAuthorized<LikePostRequest> { _, req ->
                    call.respond(LikePostResponse(repository.likePost(req.postId)))
                }
            }

            get("$path/getPosts") {
                call.runAuthorized<Any> { userId, _ ->
                    val posts = repository.getPosts()
                    call.respond(
                        GetPostsResponse(
                            posts = posts.map {
                                PostResponse.fromPost(it, authRepository.getNameById(it.authorId) ?: "...")
                            }
                        )
                    )
                }
            }

            delete("$path/deletePost") {
                call.runAuthorized<DeletePostRequest> { userId, req ->
                    repository.deletePost(req.postId)
                }
            }
        }
    }
}