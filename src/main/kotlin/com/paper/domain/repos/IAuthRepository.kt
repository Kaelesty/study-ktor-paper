package com.paper.domain.repos

import com.paper.domain.entities.User
import com.paper.presentation.features.auth.dtos.LoginRequest
import com.paper.presentation.features.auth.dtos.RegisterRequest

interface IAuthRepository {

    suspend fun registerUser(user: User): String?

    suspend fun loginUser(login: String, password: String): String?

    suspend fun getNameById(id: Int): String?
}