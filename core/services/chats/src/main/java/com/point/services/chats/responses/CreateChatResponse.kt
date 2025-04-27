package com.point.services.chats.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class CreateChatResponse(
    @SerialName("id")
    val id: String,
)