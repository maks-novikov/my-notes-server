package com.masksim.data.model

@kotlinx.serialization.Serializable
class CardModel(
    val id: Int = 0,
    val owner: Int,
    val title: String,
    val description: String,
    val createdAt: String
)