package com.masksim.plugins

import com.masksim.auth.JwtService
import com.masksim.domain.usecase.UserUseCase
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureSecurity(userUseCase: UserUseCase, jwtService: JwtService) {

    authentication {
        jwt("jwt") {
            verifier(jwtService.getVerifier())
            realm = "Service server"
            validate {
                val payload = it.payload
                val login = payload.getClaim("login").asString()
                val user = userUseCase.findUserByLogin(login)
                user
            }
        }
    }
}
