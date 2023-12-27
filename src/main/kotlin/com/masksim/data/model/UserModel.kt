package com.masksim.data.model

import io.ktor.server.auth.*

@kotlinx.serialization.Serializable
data class UserModel(
    val id: Int = 0,
    val username: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val isActive: Boolean = false,
    val role: RoleModel
): Principal
