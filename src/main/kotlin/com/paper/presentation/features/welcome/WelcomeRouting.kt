package com.paper.presentation.features.welcome

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureWelcome() {

    routing {
        get("/") {
            call.respondText("Welcome to Ktor Paper!")
        }
    }
}