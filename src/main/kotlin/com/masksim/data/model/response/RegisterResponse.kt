package com.masksim.data.model.response

@kotlinx.serialization.Serializable
data class RegisterResponse(val username: String, val token: String)