package com.masksim.plugins

import com.masksim.domain.usecase.CardUseCase
import com.masksim.domain.usecase.UserUseCase
import com.masksim.routing.cardRoute
import com.masksim.routing.userRoute
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(cardUseCase: CardUseCase,  userUseCase: UserUseCase) {
    routing {
        userRoute(userUseCase = userUseCase)
        cardRoute(cardUseCase)
    }
}
