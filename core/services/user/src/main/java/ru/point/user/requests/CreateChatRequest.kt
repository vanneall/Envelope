package ru.point.user.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateChatRequest(
    @SerialName("participants")
    val participantIds: List<String>,
    @SerialName("name")
    val name: String? = null,
)