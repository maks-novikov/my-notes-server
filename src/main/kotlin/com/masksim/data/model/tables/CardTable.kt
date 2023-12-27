package com.masksim.data.model.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object CardTable : Table() {

    val id: Column<Int> = integer("id").autoIncrement()
    val owner: Column<Int> = integer("ownerId").references(UserTable.id)
    val title: Column<String> = varchar("title", 50)
    val description: Column<String> = varchar("description", 1000)
    val verified: Column<Boolean> = bool("verified")
    val createdAt: Column<String> = varchar("createdAt", 50)

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}