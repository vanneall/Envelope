package com.point.chats.main.data.entity.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatInfoShort(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("last_message")
    val lastMessage: String? = null,
)