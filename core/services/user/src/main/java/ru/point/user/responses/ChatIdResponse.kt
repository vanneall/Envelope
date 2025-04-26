package ru.point.user.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatIdResponse(
    @SerialName("id")
    val id: String,
)