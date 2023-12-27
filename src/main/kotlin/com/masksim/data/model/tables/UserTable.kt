package com.masksim.data.model.tables

import org.jetbrains.exposed.sql.Table

object UserTable : Table() {

    val id = integer("id").autoIncrement()
    val username = varchar("username", 50).uniqueIndex()
    val password = varchar("password", 50)
    val firstName = varchar("firstName", 50)
    val lastName = varchar("lastName", 50)
    val role = varchar("userRole", 20)
    val isActive = bool("isActive")

    override val primaryKey = PrimaryKey(id)
}