package com.paper.data.database.schemas

import com.paper.domain.entities.User
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import javax.inject.Inject

class UserService @Inject constructor(database: Database) {
    object Users : Table() {
        val id = integer("id").autoIncrement()
        val name = varchar("name", length = 25)
        val login = varchar("login", length = 25)
        val password = varchar("password", length = 25)

        override val primaryKey = PrimaryKey(id)
    }

    init {
        transaction(database) {
            SchemaUtils.createMissingTablesAndColumns(Users)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    suspend fun create(user: User): Int = dbQuery {
        Users.insert {
            it[name] = user.name
            it[password] = user.password
            it[login] = user.login
        }[Users.id]
    }

    suspend fun getId(login: String): Int? = dbQuery {
        Users
            .selectAll()
            .where( Users.login eq login)
            .map { it[Users.id] }
            .singleOrNull()
    }

    suspend fun read(id: Int): User? {
        return dbQuery {
            Users.selectAll()
                .where { Users.id eq id }
                .map { User(it[Users.id], it[Users.name], it[Users.login], it[Users.password]) }
                .singleOrNull()
        }
    }

}