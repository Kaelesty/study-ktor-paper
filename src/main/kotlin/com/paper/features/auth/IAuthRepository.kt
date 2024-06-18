package com.paper.features.auth

interface IAuthRepository {

    data class User(
        val login: String,
        val name: String,
        val password: String
    )

    fun registerUser(user: RegisterRequest): String?

    fun loginUser(user: LoginRequest): String?

    fun getUserByToken(token: String): User?
}