package com.paper.presentation.features.auth.dtos

import com.paper.domain.entities.User
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val name: String,
    val login: String,
    val password: String
) {
    fun toUser() = User(
        name, login, password
    )
}

@Serializable
data class RegisterResponse(
    val token: String
)