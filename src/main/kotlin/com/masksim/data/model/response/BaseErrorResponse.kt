package com.masksim.data.model.response

@kotlinx.serialization.Serializable
data class BaseErrorResponse(val message: String, val code: ResponseErrorCode)
