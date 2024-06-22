package com.paper.presentation.features.comments

import com.paper.domain.repos.IAuthRepository
import com.paper.domain.repos.ICommentsRepository
import com.paper.presentation.features.comments.dtos.*
import com.paper.presentation.runAuthorized
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
        authenticate("jwt-auth") {
            post("$path/leaveComment") {
                call.runAuthorized<LeaveCommentRequest> { userId, req ->
                    repository.leaveComment(req.postId, userId, req.text)
                }
            }

            get("$path/getPostComments") {
                call.runAuthorized<GetPostCommentsRequest> { _, req ->
                    val comments = repository.getPostComments(req.postId)
                    call.respond(
                        GetPostCommentsResponse(
                            comments = comments.map {
                                CommentResponse.fromComment(it, authRepository.getNameById(it.authorId) ?: "...")
                            }
                        )
                    )
                }
            }
            delete("$path/deleteComment") {
                call.runAuthorized<DeleteCommentRequest> { _, req ->
                    repository.deleteComment(req.commentId)
                }
            }
        }
    }
}