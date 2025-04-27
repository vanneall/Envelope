package com.point.services.chats.events.models

import java.time.Instant

data class MessageDeleted(
    override val id: String,
    override val timestamp: Instant,
    val messageId: String,
) : Event