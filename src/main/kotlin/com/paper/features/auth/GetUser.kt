package com.paper.features.auth

import kotlinx.serialization.Serializable

@Serializable
data class GetUserRequest(
    val token: String
)

@Serializable
data class GetUserResponse(
    val login: String,
    val name: String
)