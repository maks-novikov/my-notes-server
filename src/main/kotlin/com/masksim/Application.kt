package com.masksim

import com.masksim.auth.JwtService
import com.masksim.data.model.repository.CardRepositoryImpl
import com.masksim.data.model.repository.UserRepositoryImpl
import com.masksim.domain.usecase.CardUseCase
import com.masksim.domain.usecase.UserUseCase
import com.masksim.plugins.*
import com.masksim.plugins.DatabaseFactory.initDatabase
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(
        Netty,
        port = 8080,
        host = "0.0.0.0",
        module = Application::module
    ).start(wait = true)
}

fun Application.module() {

    val jwtService = JwtService()
    val userRepository = UserRepositoryImpl()
    val userUseCase = UserUseCase(userRepository, jwtService)

    val cardRepository = CardRepositoryImpl()
    val cardUseCase = CardUseCase(cardRepository)

    initDatabase()
    configureSerialization()
    configureMonitoring()
    configureSecurity(userUseCase, jwtService)
    configureRouting(cardUseCase, userUseCase)
}
