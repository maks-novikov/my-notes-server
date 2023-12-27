package com.masksim.domain.usecase

import com.masksim.data.model.CardModel
import com.masksim.domain.repository.CardRepository

class CardUseCase(private val cardRepository: CardRepository) {

    suspend fun addCard(cardModel: CardModel) {
        cardRepository.addCard(cardModel)
    }

    suspend fun updateCard(cardModel: CardModel) {
        cardRepository.updateCard(cardModel)
    }

    suspend fun getAllCards(): List<CardModel> {
        return cardRepository.getAllCards()
    }

    suspend fun deleteCard(cardId: Int, ownerId: Int) {
        cardRepository.deleteCard(cardId, ownerId)
    }
}