package com.paper.presentation

import com.paper.di.ApplicationComponent
import com.paper.di.DaggerApplicationComponent
import com.paper.presentation.features.auth.configureAuth
import com.paper.presentation.features.comments.configureComments
import com.paper.presentation.features.posts.configurePosts
import com.paper.presentation.features.welcome.configureWelcome
import com.paper.presentation.plugins.configureJwt
import com.paper.presentation.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*

fun main() {
    embeddedServer(CIO, port = 8080, host = "127.0.0.1", module=Application::module)
        .start(wait = true)
}

fun Application.module() {

    val applicationComponent = DaggerApplicationComponent.create()

    configureSerialization()
    configureWelcome()
    configureJwt()
    with(applicationComponent) {
        configureAuth(
            path = "auth",
            repository = authRepository()
        )
        configurePosts(
            path = "posts",
            repository = postsRepository(),
            authRepository = authRepository()
        )
        configureComments(
            path = "comments",
            repository = commentsRepository(),
            authRepository = authRepository()
        )
    }
}
