package com.masksim.domain.usecase

import com.masksim.auth.JwtService
import com.masksim.data.model.UserModel
import com.masksim.domain.repository.UserRepository

class UserUseCase(private val repository: UserRepository, private val jwtService: JwtService) {

    suspend fun createUser(userModel: UserModel) {
        repository.insertUser(userModel)
    }

    suspend fun findUserByUsername(username: String): UserModel? {
        return repository.getUserByUsername(username)
    }

    fun generateToken(userModel: UserModel): String {
        return jwtService.generateToken(userModel)
    }
}