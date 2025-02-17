package com.nalldev.repository

import com.nalldev.models.User
import com.nalldev.services.IUserService
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepository(database: Database) : IUserService {

    object Users : IntIdTable("user") {
        val name = varchar("name", 50)
        val age = integer("age")
    }

    init {
        transaction(database) {
            SchemaUtils.create(Users)
        }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    override suspend fun addUser(user: User) {
        dbQuery {
            Users.insert {
                it[name] = user.name
                it[age] = user.age
            }
        }
    }

    override suspend fun getAllUser(): List<User> = dbQuery {
        Users.selectAll().map {
            User(
                id = it[Users.id].value,
                name = it[Users.name],
                age = it[Users.age]
            )
        }
    }

    override suspend fun getUser(id: Int): User? = dbQuery {
        Users.selectAll()
            .where { Users.id eq id }
            .map { User(name = it[Users.name], age = it[Users.age]) }
            .singleOrNull()
    }

    override suspend fun updateUser(id: Int, user: User): Boolean = dbQuery {
        val updatedRows = Users.update({ Users.id eq id }) {
            it[name] = user.name
            it[age] = user.age
        }
        updatedRows > 0
    }

    override suspend fun deleteUser(id: Int): Boolean = dbQuery {
        val deletedRows = Users.deleteWhere { Users.id eq id }
        deletedRows > 0
    }
}
