package com.paper.features.auth

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val name: String,
    val login: String,
    val password: String
)

@Serializable
data class RegisterResponse(
    val token: String
)