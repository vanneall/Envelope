package com.point.services.chats.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class ChatIdResponse(
    @SerialName("id")
    val id: String,
)