package com.masksim.data.model.requests

@kotlinx.serialization.Serializable
data class LoginRequest(val username: String, val password: String)