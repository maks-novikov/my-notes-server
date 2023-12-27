package com.masksim.domain.repository

import com.masksim.data.model.UserModel

interface UserRepository {

    suspend fun getUserByLogin(login: String): UserModel?
    suspend fun insertUser(userModel: UserModel)
}