package com.masksim.data.model.requests

@kotlinx.serialization.Serializable
data class UpdateCardRequest(
    val id: Int,
    val title: String,
    val description: String,
    val verified: Boolean,
    val createdAt: String
)
