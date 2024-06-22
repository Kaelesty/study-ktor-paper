package com.paper.presentation

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*

suspend inline fun <reified T : Any> ApplicationCall.runAuthorized(
    block: suspend (Int, T) -> Unit,
) {
    principal<JWTPrincipal>()?.let { jwtPrincipal ->
        val req = receive<T>()
        block(jwtPrincipal.payload.getClaim("userId").asInt(), req)
        return
    }
    respond(HttpStatusCode.Unauthorized)
}