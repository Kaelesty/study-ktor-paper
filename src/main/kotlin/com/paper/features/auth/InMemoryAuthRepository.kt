package com.paper.features.auth
import java.util.*

class InMemoryAuthRepository: IAuthRepository {

    internal data class UserToken(
        val login: String,
        val token: String
    )

    private val users: MutableList<IAuthRepository.User> = mutableListOf()
    private val tokens: MutableList<UserToken> = mutableListOf()

    override suspend fun getUserByToken(token: String): IAuthRepository.User? {
        val login = tokens.find { it.token == token }?.login ?: return null
        return users.find { it.login == login }
    }

    override suspend fun registerUser(user: RegisterRequest): String? {
        if (users.map { it.login }.contains(user.login)) {
            return null
        }
        users.add(user.toUser())
        return createAndSaveToken(user.login)
    }

    override suspend fun loginUser(user: LoginRequest): String? {
        if (users.find { it.login == user.login }?.password == user.password) {
            return createAndSaveToken(user.login)
        }
        return null
    }

    private fun createAndSaveToken(login: String): String {
        val token = UUID.randomUUID().toString()
        tokens.removeIf { it.login == login }
        tokens.add(
            UserToken(
                login = login,
                token = token
            )
        )
        return token
    }

    fun RegisterRequest.toUser() = IAuthRepository.User(
        name = name,
        login = login,
        password = password,
    )
}