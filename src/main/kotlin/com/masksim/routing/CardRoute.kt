package com.masksim.routing

import com.masksim.data.model.CardModel
import com.masksim.data.model.UserModel
import com.masksim.data.model.requests.CreateCardRequest
import com.masksim.data.model.requests.UpdateCardRequest
import com.masksim.data.model.response.BaseErrorResponse
import com.masksim.data.model.response.ResponseErrorCode
import com.masksim.domain.usecase.CardUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.cardRoute(cardUseCase: CardUseCase) {

    authenticate("jwt") {

        get("api/v1/card/{id}") {
            try {
                val cardId = call.parameters["id"] ?: throw BadRequestException("No cardId provided")
                val ownerId = call.principal<UserModel>()?.id
                val cardModel = cardUseCase.getCard(cardId.toInt(), ownerId!!)
                if (cardModel != null)
                    call.respond(cardModel)
                else throw NotFoundException("Card with id: $cardId for user: $ownerId, are not found")
            } catch (e: NotFoundException) {
                call.respond(
                    HttpStatusCode.NotFound,
                    BaseErrorResponse(e.message ?: "", ResponseErrorCode.CARD_NOT_FOUND)
                )
            } catch (e: Throwable) {
                unknownErrorResponse(call)
            }
        }

        get("api/v1/card") {
            try {
                val cards = cardUseCase.getAllCards()
                call.respond(cards)
            } catch (e: Exception) {
                unknownErrorResponse(call)
            }
        }

        post("api/v1/card/create") {
            try {
                val request = call.receive<CreateCardRequest>()
                //Get the owner id from the token
                val ownerId = call.principal<UserModel>()?.id
                println("ownerId: $ownerId")
                val card = CardModel(
                    owner = ownerId!!,
                    title = request.title,
                    description = request.description,
                    createdAt = request.createdAt
                )
                cardUseCase.addCard(card)
                //call.respond(HttpStatusCode.Created)
                call.respond(HttpStatusCode.Created, card)
            } catch (e: BadRequestException) {
                badRequestResponse(call)
            } catch (e: Exception) {
                unknownErrorResponse(call)
            }
        }

        patch("api/v1/card") {
            try {
                val request = call.receive<UpdateCardRequest>()
                val ownerId = call.principal<UserModel>()?.id
                val card = CardModel(
                    id = request.id,
                    owner = ownerId!!,
                    title = request.title,
                    description = request.description,
                    createdAt = request.createdAt
                )
                cardUseCase.updateCard(card)
                call.respond(HttpStatusCode.OK)
            } catch (e: BadRequestException) {
                badRequestResponse(call)
            } catch (e: Exception) {
                unknownErrorResponse(call)
            }
        }

        delete("api/v1/card/{id}") {
            try {
                val cardId = call.parameters["id"] ?: throw BadRequestException("No cardId provided")
                val ownerId = call.principal<UserModel>()?.id

                cardUseCase.deleteCard(cardId.toInt(), ownerId!!)
                call.respond(HttpStatusCode.Accepted)
            } catch (e: BadRequestException) {
                badRequestResponse(call)
            } catch (e: Exception) {
                unknownErrorResponse(call)
            }
        }
    }
}