package com.point.services.chats.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class MessageDeleteRequest(
    @SerialName("message_id")
    val id: String
)
