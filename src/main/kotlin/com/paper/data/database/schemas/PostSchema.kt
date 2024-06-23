package com.paper.data.database.schemas

import com.paper.domain.entities.Post
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import javax.inject.Inject

class PostService @Inject constructor(database: Database) {
    object Posts : Table() {
        val id = integer("id").autoIncrement()
        val title = varchar("title", length = 100)
        val desc = varchar("desc", length = 100)
        val text = varchar("text", length = 1500)
        val createdTimeMillis = long("createTimeMillis")
        val authorId = integer("creatorId")
            .references(UserService.Users.id)

        override val primaryKey = PrimaryKey(id)
    }

    init {
        transaction(database) {
            SchemaUtils.createMissingTablesAndColumns(Posts)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    suspend fun create(post: Post): Int = dbQuery {
        Posts.insert {
            it[title] = post.title
            it[desc] = post.desc
            it[text] = post.text
            it[createdTimeMillis] = post.createdTimeMillis
            it[authorId] = post.authorId
        }[Posts.id]
    }

    suspend fun getAll(limit: Int = 4, page: Int = 0): List<Post> = dbQuery {
        Posts
            .selectAll()
            .limit(limit, offset = page.toLong() * limit)
            .map { Post(
                it[Posts.id], it[Posts.title], it[Posts.authorId],
                it[Posts.desc], it[Posts.text], it[Posts.createdTimeMillis], 0
            ) }
    }

    suspend fun delete(id: Int) = dbQuery {
        Posts
            .deleteWhere { Posts.id eq id }
    }

}