package com.masksim.data.model.requests

@kotlinx.serialization.Serializable
data class RegisterRequest(
    val username: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val role: String,
    val isActive: Boolean
)