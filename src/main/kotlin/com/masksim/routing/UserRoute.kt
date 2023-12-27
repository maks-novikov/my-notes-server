package com.masksim.routing

import com.masksim.auth.hash
import com.masksim.data.model.RoleModel
import com.masksim.data.model.UserModel
import com.masksim.data.model.requests.LoginRequest
import com.masksim.data.model.requests.RegisterRequest
import com.masksim.data.model.response.BaseErrorResponse
import com.masksim.data.model.response.LoginResponse
import com.masksim.data.model.response.RegisterResponse
import com.masksim.data.model.response.ResponseErrorCode
import com.masksim.domain.usecase.UserUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.exceptions.ExposedSQLException

fun Route.userRoute(userUseCase: UserUseCase) {
    post("api/v1/register") {
        try {
            val request = call.receive<RegisterRequest>()
            val user = UserModel(
                username = request.username.trim().lowercase(),
                password = hash(request.password),
                firstName = request.firstName,
                lastName = request.lastName,
                isActive = request.isActive,
                role = RoleModel.valueOf(request.role)
            )
            userUseCase.createUser(user)
            call.respond(HttpStatusCode.Created, RegisterResponse(user.username, userUseCase.generateToken(user)))
        } catch (e: BadRequestException) {
            println(e.message)
            badRequestResponse(call)
        } catch (e: ExposedSQLException) {
            call.respond(
                HttpStatusCode.Conflict,
                BaseErrorResponse("User already exists", ResponseErrorCode.USERNAME_TAKEN)
            )
        } catch (e: Exception) {
            unknownErrorResponse(call)
        }
    }

    post("api/v1/login") {
        try {
            val request = call.receive<LoginRequest>()
            val user = userUseCase.findUserByUsername(request.username.trim().lowercase())
            if (user != null) {
                val requestPasswordHash = hash(request.password)
                if (requestPasswordHash == user.password) {
                    call.respond(HttpStatusCode.OK, LoginResponse(userUseCase.generateToken(user)))
                } else {
                    call.respond(
                        HttpStatusCode.Unauthorized,
                        BaseErrorResponse("Unauthorized", ResponseErrorCode.INCORRECT_PASSWORD)
                    )
                }
            } else {
                call.respond(
                    HttpStatusCode.NotFound,
                    BaseErrorResponse("Not found", ResponseErrorCode.USER_NOT_FOUND)
                )
            }
        } catch (e: BadRequestException) {
            badRequestResponse(call)
        } catch (e: Exception) {
            unknownErrorResponse(call)
        }
    }

    authenticate("jwt") {
        get("api/v1/user") {
            try {
                val user = call.principal<UserModel>()
                if (user != null) {
                    call.respond(HttpStatusCode.OK, user)
                } else {
                    call.respond(
                        HttpStatusCode.NotFound,
                        BaseErrorResponse("Not found", ResponseErrorCode.USER_NOT_FOUND)
                    )
                }
            } catch (e: Exception) {
                unknownErrorResponse(call)
            }
        }
    }
}

suspend fun badRequestResponse(call: ApplicationCall) {
    call.respond(
        HttpStatusCode.BadRequest,
        BaseErrorResponse(
            "Invalid info, make sure to fill are required fields",
            ResponseErrorCode.INVALID_DATA_FORMAT
        )
    )
}

suspend fun unknownErrorResponse(call: ApplicationCall) {
    call.respond(
        HttpStatusCode.BadRequest,
        BaseErrorResponse(
            "Unknown error happened, please try again",
            ResponseErrorCode.UNKNOWN
        )
    )
}