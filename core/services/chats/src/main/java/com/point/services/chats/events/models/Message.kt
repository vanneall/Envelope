package com.point.services.chats.events.models

import java.time.Instant

data class Message(
    override val id: String,
    override val timestamp: Instant,
    val sender: String,
    val senderName: String,
    val senderPhoto: String? = null,
    val text: String,
    val attachments: List<Long>,
    val isPinned: Boolean,
    val isEdited: Boolean,
) : Event