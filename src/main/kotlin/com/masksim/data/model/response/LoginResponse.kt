package com.masksim.data.model.response

@kotlinx.serialization.Serializable
data class LoginResponse(val username: String, val token: String)