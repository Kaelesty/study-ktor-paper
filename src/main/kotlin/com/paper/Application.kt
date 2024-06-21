package com.paper

import com.paper.database.configureDatabases
import com.paper.features.auth.InMemoryAuthRepository
import com.paper.features.auth.configureAuth
import com.paper.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*

fun main() {
    embeddedServer(CIO, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureAuth(
        path = "auth",
        repository = InMemoryAuthRepository()
    )
    configureDatabases()
}
