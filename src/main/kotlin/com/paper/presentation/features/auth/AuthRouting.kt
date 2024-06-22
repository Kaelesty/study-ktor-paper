package com.paper.presentation.features.auth

import com.paper.domain.repos.IAuthRepository
import com.paper.presentation.features.auth.dtos.LoginRequest
import com.paper.presentation.features.auth.dtos.LoginResponse
import com.paper.presentation.features.auth.dtos.RegisterRequest
import com.paper.presentation.features.auth.dtos.RegisterResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureAuth(
    path: String,
    repository: IAuthRepository
) {
    routing {
        post("$path/register") {
            val req = call.receive(RegisterRequest::class)
            val token = repository.registerUser(req.toUser())
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
        post("$path/login") {
            val req = call.receive(LoginRequest::class)
            val token = repository.loginUser(req.login, req.password)
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

        authenticate("auth-jwt") {
            get("$path/getUser") {
                call.principal<JWTPrincipal>()?.let { principal ->
                    val username = principal.payload.getClaim("userId").asInt().toString()
                    val expiresAt = principal.expiresAt?.time?.minus(System.currentTimeMillis())
                    call.respondText("Hello, $username! Token is expired at $expiresAt ms.")
                    return@get
                }
                call.respond(HttpStatusCode.Unauthorized, "JWT is invalid")
            }
        }
    }
}