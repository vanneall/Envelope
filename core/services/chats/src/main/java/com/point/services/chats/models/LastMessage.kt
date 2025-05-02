package com.point.services.chats.models

import com.point.services.chats.responses.MessageResponse
import java.time.Instant

data class LastMessage(
    val id: String,
    val text: String,
    val type: MessageType,
    val timestamp: Instant,
)

enum class MessageType {
    TEXT,
    IMAGE,
    CREATED,
}

internal fun MessageResponse.toModel() = LastMessage(
    id = id,
    text = text,
    type = type,
    timestamp = timestamp,
)