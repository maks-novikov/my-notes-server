package com.masksim.data.model.response

@kotlinx.serialization.Serializable
data class RegisterResponse(val login: String, val token: String)