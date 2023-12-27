package com.masksim.domain.usecase

import com.masksim.auth.JwtService
import com.masksim.data.model.UserModel
import com.masksim.domain.repository.UserRepository

class UserUseCase(private val repository: UserRepository, private val jwtService: JwtService) {

    suspend fun createUser(userModel: UserModel) {
        repository.insertUser(userModel)
    }

    suspend fun findUserByLogin(login: String): UserModel? {
        return repository.getUserByLogin(login)
    }

    fun generateToken(userModel: UserModel): String {
        return jwtService.generateToken(userModel)
    }
}