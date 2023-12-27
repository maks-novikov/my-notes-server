package com.masksim.plugins

import com.masksim.data.model.tables.CardTable
import com.masksim.data.model.tables.UserTable
import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import io.ktor.server.config.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    private val appConfig = HoconApplicationConfig(ConfigFactory.load())
    private val dbUrl = System.getenv("DB_POSTGRES_URL")
    private val dbUser = System.getenv("DB_POSTGRES_USER")
    private val dbPassword = System.getenv("DB_POSTGRES_PASSWORD")

    fun Application.initDatabase() {

        //We can directly connect to db, or use Hikari for connection.
       /* Database.connect(dbUrl, "org.postgresql.Driver", dbUser, dbPassword)
        transaction {
            SchemaUtils.create(
                UserTable,
                CardTable
            )
        }*/

         Database.connect(getHikariDataSource())
         transaction {
             SchemaUtils.create(
                 UserTable,
                 CardTable
             )
         }
    }

    private fun getHikariDataSource(): HikariDataSource {
        //println("DB URL: $dbUrl, DB_USER: $dbUser, DB_PASSWORD: $dbPassword")
        return HikariDataSource(HikariConfig().also {
            it.driverClassName = "org.postgresql.Driver"
            it.jdbcUrl = dbUrl
            it.username = dbUser
            it.password = dbPassword
            it.maximumPoolSize = 5
            it.isAutoCommit = false
            it.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            it.validate()
        })
    }

    suspend fun <T> dbQuery(block: () -> T): T {
        return withContext(Dispatchers.IO) {
            transaction { block() }
        }
    }
}