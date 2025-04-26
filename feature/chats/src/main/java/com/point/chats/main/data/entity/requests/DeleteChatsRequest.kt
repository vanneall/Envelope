package com.point.chats.main.data.entity.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeleteChatsRequest(
    @SerialName("chat_ids")
    val chatIds: List<String>
)