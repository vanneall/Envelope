package com.point.services.chats.requests

import com.point.services.chats.models.ChatCreationData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class CreateChatRequest(
    @SerialName("name")
    val name: String? = null,
    @SerialName("participants")
    val participantIds: List<String>,
)

internal fun ChatCreationData.toRequest() = CreateChatRequest(name = name, participantIds = participants)