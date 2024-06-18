package com.paper.features.auth

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureAuth(
    path: String,
    repository: IAuthRepository
) {
    routing {
        get("$path/register") {
            val req = call.receive(RegisterRequest::class)
            val token = repository.registerUser(req)
            if (token != null) {
                call.respond(
                    RegisterResponse(
                        token = token
                    )
                )
            }
            else {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = "Can't register with received data"
                )
            }
        }
        get("$path/login") {
            val req = call.receive(LoginRequest::class)
            val token = repository.loginUser(req)
            if (token != null) {
                call.respond(
                    LoginResponse(
                        token = token
                    )
                )
            }
            else {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = "Can't login with received data"
                )
            }
        }

        get("$path/getUser") {
            val token = call.receive<GetUserRequest>().token
            val user = repository.getUserByToken(token)
            if (user == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid token")
            }
            else {
                call.respond(GetUserResponse(
                    name = user.name,
                    login = user.login
                ))
            }
        }
    }
}