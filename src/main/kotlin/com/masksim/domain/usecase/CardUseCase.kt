package com.masksim.domain.usecase

import com.masksim.data.model.CardModel
import com.masksim.domain.repository.CardRepository

class CardUseCase(private val cardRepository: CardRepository) {

    suspend fun addCard(cardModel: CardModel): CardModel {
        return cardRepository.addCard(cardModel)
    }

    suspend fun updateCard(cardModel: CardModel) {
        cardRepository.updateCard(cardModel)
    }

    suspend fun getCard(cardId: Int, ownerId: Int): CardModel? {
        return cardRepository.getCard(cardId, ownerId)
    }

    suspend fun getAllCards(): List<CardModel> {
        return cardRepository.getAllCards()
    }

    suspend fun deleteCard(cardId: Int, ownerId: Int) {
        cardRepository.deleteCard(cardId, ownerId)
    }
}