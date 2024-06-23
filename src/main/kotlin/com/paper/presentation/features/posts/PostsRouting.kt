package com.paper.presentation.features.posts

import com.paper.domain.repos.IAuthRepository
import com.paper.domain.repos.IPostsRepository
import com.paper.presentation.features.posts.dtos.*
import com.paper.presentation.runAuthorized
import io.ktor.http.*
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
        authenticate("auth-jwt") {
            post("$path/createPost") {
                call.runAuthorized<CreatePostRequest> { userId, req ->
                    repository.createPost(
                        creatorId = userId,
                        title = req.title,
                        desc = req.desc,
                        text = req.text,
                    )
                    call.respond(HttpStatusCode.Created)
                }
            }

            post("$path/likePost") {
                call.runAuthorized<LikePostRequest> { userId, req ->
                    call.respond(HttpStatusCode.Accepted, LikePostResponse(repository.likePost(userId, req.postId)))
                }
            }

            post("$path/likePost") {
                call.runAuthorized<LikePostRequest> { userId, req ->
                    call.respond(HttpStatusCode.Accepted, LikePostResponse(repository.dislikePost(userId, req.postId)))
                }
            }

            get("$path/getPosts") {
                call.runAuthorized<GetPostsRequest> { userId, req ->
                    val posts = repository.getPosts(req.limit, req.page, userId)
                    call.respond(HttpStatusCode.OK,
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
                    call.respond(HttpStatusCode.Accepted)
                }
            }
        }
    }
}