package com.paper.data.database

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.paper.domain.entities.User
import com.paper.domain.repos.IAuthRepository
import com.paper.presentation.features.auth.dtos.LoginRequest
import com.paper.presentation.features.auth.dtos.RegisterRequest
import java.util.*
import javax.inject.Inject

data class JwtEnvironment(
    val secret: String,
    val issuer: String,
    val audience: String,
    val realm: String,
    val tokenLifetimeMillis: Int
)

class AuthRepository @Inject constructor(
    private val userService: UserService,
    private val jwtEnvironment: JwtEnvironment
) : IAuthRepository {

    override suspend fun registerUser(user: User): String? {
        userService.getId(user.login)?.let {
            return null
        }
        val id = userService.create(
            User(
                id = 0,
                name = user.name,
                login = user.login,
                password = user.password,
            )
        )
        return createNewToken(id)
    }



    override suspend fun loginUser(login: String, password: String): String? {
        userService.getId(login)?.let { id ->
            if (userService.read(id)?.password == password) {
                return createNewToken(id)
            }
        }
        return null
    }

    override suspend fun getNameById(id: Int): String? {
        return userService.read(id)?.name
    }

    private fun createNewToken(userId: Int): String {
        return JWT.create()
            .withAudience(jwtEnvironment.audience)
            .withIssuer(jwtEnvironment.issuer)
            .withClaim("userId", userId)
            .withExpiresAt(
                Date(System.currentTimeMillis() + jwtEnvironment.tokenLifetimeMillis)
            )
            .sign(Algorithm.HMAC256(jwtEnvironment.secret))
    }
}