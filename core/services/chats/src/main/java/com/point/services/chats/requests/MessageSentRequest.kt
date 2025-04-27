package com.point.services.chats.requests

import com.point.services.chats.models.MessageCreate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class MessageSentRequest(
    @SerialName("content")
    val content: String,
    @SerialName("photos")
    val photos: List<Long>,
)

internal fun MessageCreate.toRequest() = MessageSentRequest(
    content = content,
    photos = photos,
)
