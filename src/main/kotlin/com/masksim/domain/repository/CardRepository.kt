package com.masksim.domain.repository

import com.masksim.data.model.CardModel

interface CardRepository {
    suspend fun addCard(card: CardModel)
    suspend fun updateCard(card: CardModel)
    suspend fun getAllCards(): List<CardModel>
    suspend fun deleteCard(cardId: Int, ownerId: Int)
}