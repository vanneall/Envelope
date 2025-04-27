package com.point.services.chats.requests

import com.point.services.chats.models.MessageEdit
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class MessageEditRequest(
    @SerialName("message_id")
    val messageId: String,
    @SerialName("content")
    val content: String,
)

internal fun MessageEdit.toRequest() = MessageEditRequest(messageId = id, content = newContent)
