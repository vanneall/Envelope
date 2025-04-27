package com.point.services.chats.events.models

import java.time.Instant

data class MessageEdited(
    override val id: String,
    override val timestamp: Instant,
    val editedMessageId: String,
    val newContent: String,
) : Event