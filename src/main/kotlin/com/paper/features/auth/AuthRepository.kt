package com.paper.features.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.paper.database.User
import com.paper.database.UserService
import org.jetbrains.exposed.sql.Database
import java.util.*
import javax.management.monitor.StringMonitor

data class JwtEnvironment(
    val secret: String,
    val issuer: String,
    val audience: String,
    val realm: String,
    val tokenLifetimeMillis: Int
)

class AuthRepository(
    private val jwtEnvironment: JwtEnvironment
) : IAuthRepository {

    private val database = Database.connect(
        url = "jdbc:postgresql://localhost:5432/",
        user = "postgres",
        driver = "org.postgresql.Driver",
        password = "188348"
    )

    private val userService = UserService(database)

    override suspend fun registerUser(user: RegisterRequest): String {
        val id = userService.create(
            User(
                name = user.name,
                login = user.login,
                password = user.password,
            )
        )
        return createNewToken(id)
    }



    override suspend fun loginUser(user: LoginRequest): String? {
        userService.getId(user.login)?.let { id ->
            if (userService.read(id)?.password == user.password) {
                return createNewToken(id)
            }
        }
        return null
    }

    override suspend fun getUserByToken(token: String): IAuthRepository.User? {
        TODO()
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