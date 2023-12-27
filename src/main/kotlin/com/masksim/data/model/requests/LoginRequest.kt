package com.masksim.data.model.requests

@kotlinx.serialization.Serializable
data class LoginRequest(val login: String, val password: String)