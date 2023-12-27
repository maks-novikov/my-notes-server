package com.masksim.domain.repository

import com.masksim.data.model.UserModel

interface UserRepository {

    suspend fun getUserByUsername(username: String): UserModel?
    suspend fun insertUser(userModel: UserModel)
}