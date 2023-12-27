package com.masksim.data.model.response

enum class ResponseErrorCode {
    UNKNOWN,
    USERNAME_TAKEN,
    PASSWORD_TOO_SHORT,
    PASSWORD_NOT_MATCH,
    INVALID_DATA_FORMAT,
    USER_NOT_FOUND,
    INCORRECT_PASSWORD
}