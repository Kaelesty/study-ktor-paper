package com.paper.presentation.features.comments

import com.paper.domain.repos.IAuthRepository
import com.paper.domain.repos.ICommentsRepository
import com.paper.presentation.features.comments.dtos.*
import com.paper.presentation.runAuthorized
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureComments(
    path: String,
    repository: ICommentsRepository,
    authRepository: IAuthRepository,
) {
    routing {
        authenticate("auth-jwt") {
            post("$path/leaveComment") {
                call.runAuthorized<LeaveCommentRequest> { userId, req ->
                    repository.leaveComment(req.postId, userId, req.text)
                    call.respond(HttpStatusCode.Accepted)
                }
            }

            get("$path/getPostComments") {
                call.runAuthorized<GetPostCommentsRequest> { userId, req ->
                    val comments = repository.getPostComments(req.postId)
                    call.respond(HttpStatusCode.OK,
                        GetPostCommentsResponse(
                            comments = comments.map {
                                CommentResponse.fromComment(
                                    it,
                                    authRepository.getNameById(it.authorId) ?: "...",
                                    userId
                                )
                            }
                        )
                    )
                }
            }
            delete("$path/deleteComment") {
                call.runAuthorized<DeleteCommentRequest> { _, req ->
                    call.respond(HttpStatusCode.Accepted)
                    repository.deleteComment(req.commentId)
                }
            }
        }
    }
}