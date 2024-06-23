package com.paper.data.database.schemas

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import javax.inject.Inject

class PostLikeService @Inject constructor(
    database: Database
) {
    object PostLikes: Table() {
        val userId = integer("userId")
            .references(UserService.Users.id)
        val postId = integer("postId")
            .references(PostService.Posts.id)
    }

    init {
        transaction(database) {
            SchemaUtils.createMissingTablesAndColumns(PostLikes)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block () }

    suspend fun create(userId_: Int, postId_: Int) = dbQuery {
        PostLikes.insert {
            it[userId] = userId_
            it[postId] = postId_
        }
    }

    suspend fun delete(userId_: Int, postId_: Int) = dbQuery {
        PostLikes.deleteWhere {
            (postId eq postId_).and(userId eq userId_)
        }
    }

    suspend fun deleteAllPostLikes(postId_: Int) = dbQuery {
        PostLikes.deleteWhere {
            postId eq postId_
        }
    }

    suspend fun getPostLikesCount(postId_: Int): Int = dbQuery {
        PostLikes.selectAll()
            .where { PostLikes.postId eq postId_ }
            .map { null }
            .size
    }

    suspend fun isPostLikedByUser(userId_: Int, postId_: Int): Boolean = dbQuery {
        PostLikes.selectAll()
            .where { (PostLikes.postId eq postId_).and(PostLikes.userId eq userId_) }
            .singleOrNull()?.let {
                true
            }
        false
    }
}