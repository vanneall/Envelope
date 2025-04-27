package com.point.services.chats.models

import com.point.services.chats.responses.MessageResponse
import java.time.Instant

data class LastMessage(
    val id: String,
    val text: String,
    val timestamp: Instant,
)

internal fun MessageResponse.toModel() = LastMessage(
    id = id,
    text = text,
    timestamp = timestamp,
)