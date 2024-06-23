package com.paper.data.database.schemas

import com.paper.domain.entities.Comment
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import javax.inject.Inject

class CommentsService @Inject constructor(
    database: Database
) {

    object Comments: Table() {
        val id = integer("id").autoIncrement()
        val postId = integer("postId")
            .references(PostService.Posts.id)
        val text = varchar("text", length = 50)
        val authorId = integer("authorId")
            .references(UserService.Users.id)
        val createdTimeMillis = long("createdTimeMillis")

        override val primaryKey = PrimaryKey(id)
    }

    init {
        transaction(database) {
            SchemaUtils.createMissingTablesAndColumns(Comments)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T) =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    suspend fun create(comment: Comment) = dbQuery {
        Comments.insert {
            it[text] = comment.text
            it[authorId] = comment.authorId
            it[createdTimeMillis] = comment.createdTimeMillis
            it[postId] = comment.postId
        }[Comments.id]
    }

    suspend fun getPostComments(postId_: Int) = dbQuery {
        Comments.selectAll()
            .where { Comments.postId eq postId_ }
            .map {
                Comment(
                    id = it[Comments.id],
                    postId = postId_,
                    authorId = it[Comments.authorId],
                    text = it[Comments.text],
                    createdTimeMillis = it[Comments.createdTimeMillis]
                )
            }
    }

    suspend fun deleteComment(commentId: Int) = dbQuery {
        Comments
            .deleteWhere { id eq commentId }
    }

    suspend fun deleteAllPostComments(postId_: Int) = dbQuery {
        Comments
            .deleteWhere { postId eq postId_ }
    }
}