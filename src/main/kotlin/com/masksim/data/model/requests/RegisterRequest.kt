package com.masksim.data.model.requests

@kotlinx.serialization.Serializable
data class RegisterRequest(
    val login: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val role: String,
    val isActive: Boolean
)