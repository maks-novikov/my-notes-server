package com.masksim.data.model.repository

import com.masksim.data.model.RoleModel
import com.masksim.data.model.UserModel
import com.masksim.data.model.tables.UserTable
import com.masksim.domain.repository.UserRepository
import com.masksim.plugins.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class UserRepositoryImpl : UserRepository {

    override suspend fun getUserByUsername(username: String): UserModel? {
        return dbQuery {
            UserTable.select { UserTable.username.eq(username) }
                .map { rowToModel(it) }
                .singleOrNull()
        }
    }

    override suspend fun insertUser(userModel: UserModel) {
        return dbQuery {
            UserTable.insert { table ->
                table[username] = userModel.username
                table[password] = userModel.password
                table[firstName] = userModel.firstName
                table[lastName] = userModel.lastName
                table[role] = userModel.role.name
                table[isActive] = userModel.isActive

            }
        }
    }

    private fun rowToModel(row: ResultRow?): UserModel? {
        if (row == null) return null
        return UserModel(
            row[UserTable.id],
            row[UserTable.username],
            row[UserTable.password],
            row[UserTable.firstName],
            row[UserTable.lastName],
            row[UserTable.isActive],
            RoleModel.valueOf(row[UserTable.role])
        )
    }
}