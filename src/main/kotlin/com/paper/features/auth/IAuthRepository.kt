package com.paper.features.auth

interface IAuthRepository {

    data class User(
        val login: String,
        val name: String,
        val password: String
    )

    suspend fun registerUser(user: RegisterRequest): String?

    suspend fun loginUser(user: LoginRequest): String?

    suspend fun getUserByToken(token: String): User?
}