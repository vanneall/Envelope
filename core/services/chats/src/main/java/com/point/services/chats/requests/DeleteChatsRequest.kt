package com.point.services.chats.requests

import com.point.services.chats.models.DeletedChats
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class DeleteChatsRequest(
    @SerialName("chat_ids")
    val chatIds: List<String>,
)

internal fun DeletedChats.toRequest() = DeleteChatsRequest(chatIds = chatIds)