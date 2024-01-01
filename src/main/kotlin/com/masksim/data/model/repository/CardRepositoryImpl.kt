package com.masksim.data.model.repository

import com.masksim.data.model.CardModel
import com.masksim.data.model.tables.CardTable
import com.masksim.domain.repository.CardRepository
import com.masksim.plugins.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class CardRepositoryImpl : CardRepository {

    override suspend fun addCard(card: CardModel) {
        dbQuery {
            CardTable.insert {
                it[owner] = card.owner
                it[title] = card.title
                it[description] = card.description
                it[createdAt] = card.createdAt
            }
        }
    }

    override suspend fun updateCard(card: CardModel) {
        dbQuery {
            CardTable.update(where = {
                CardTable.owner.eq(card.owner) and CardTable.id.eq(card.id)
            }, body = {
                it[owner] = card.owner
                it[title] = card.title
                it[description] = card.description
            })
        }
    }

    override suspend fun getCard(cardId: Int, ownerId: Int): CardModel? {
        return dbQuery {
            CardTable.select(where = {
                CardTable.id.eq(cardId) and CardTable.owner.eq(ownerId)
            }).firstNotNullOfOrNull { rowToCard(it) }
        }
    }
    override suspend fun getAllCards(): List<CardModel> {
        return dbQuery {
            CardTable.selectAll().mapNotNull { rowToCard(it) }
        }
    }

    override suspend fun deleteCard(cardId: Int, ownerId: Int) {
        dbQuery {
            CardTable.deleteWhere { id.eq(cardId) and owner.eq(ownerId) }
        }
    }

    private fun rowToCard(row: ResultRow): CardModel {
        return CardModel(
            id = row[CardTable.id],
            owner = row[CardTable.owner],
            title = row[CardTable.title],
            description = row[CardTable.description],
            createdAt = row[CardTable.createdAt]
        )
    }
}