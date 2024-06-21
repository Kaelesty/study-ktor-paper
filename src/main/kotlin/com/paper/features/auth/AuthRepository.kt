//package com.paper.features.auth
//
//import com.paper.database.TokenDbModel
//import com.paper.database.TokenService
//import com.paper.database.UserDbModel
//import com.paper.database.UserService
//import org.jetbrains.exposed.sql.Database
//import java.util.UUID
//
//class AuthRepository : IAuthRepository {
//
//    private val database = Database.connect(
//        url = "jdbc:h2:mem:test",
//        user = "root",
//        driver = "org.h2.Driver",
//        password = ""
//    )
//
//    private val userService = UserService(database)
//    private val tokenService = TokenService(database)
//
//    override suspend fun registerUser(user: RegisterRequest): String? {
//        val id = userService.create(
//            UserDbModel(
//                name = user.name,
//                email = user.login,
//                password = user.password,
//            )
//        )
//        return createNewToken(id)
//    }
//
//    override suspend fun loginUser(user: LoginRequest): String? {
//        userService.getId(user.login)?.let { id ->
//            if (userService.read(id)?.password == user.password) {
//                return createNewToken(id)
//            }
//        }
//        return null
//    }
//
//    override suspend fun getUserByToken(token: String): IAuthRepository.User? {
//        tokenService.getUserIdByToken(token)?.let { userId ->
//            userService.read(userId)?.let { user ->
//                return IAuthRepository.User(
//                    login = user.email,
//                    password = user.password,
//                    name = user.name
//                )
//            }
//        }
//        return null
//    }
//
//    private suspend fun createNewToken(userId: Int): String {
//        val token = UUID.randomUUID().toString()
//        tokenService.create(
//            TokenDbModel(
//                userId = userId,
//                token = token
//            )
//        )
//        return token
//    }
//}