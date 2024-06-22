package com.paper.domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val name: String,
    val login: String,
    val password: String,
)