package com.point.services.chats.responses

import com.point.services.chats.models.ChatType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class ChatInfoResponse(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("type")
    val type: ChatType,
    @SerialName("photo")
    val photo: Long? = null,
    @SerialName("last_message")
    val lastMessage: MessageResponse? = null,
)