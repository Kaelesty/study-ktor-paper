package com.paper.features.auth

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

        authenticate("auth-jwt") {
            get("$path/getUser") {
                val principal = call.principal<JWTPrincipal>()
                val username = principal!!.payload.getClaim("userId").asInt().toString()
                val expiresAt = principal.expiresAt?.time?.minus(System.currentTimeMillis())
                call.respondText("Hello, $username! Token is expired at $expiresAt ms.")
            }
        }
    }
}