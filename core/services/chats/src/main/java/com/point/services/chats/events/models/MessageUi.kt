package com.point.services.chats.events.models

import java.time.Instant
import javax.annotation.concurrent.Immutable

@Immutable
data class MessageUi(
    override val id: String,
    override val timestamp: Instant,
    val sender: String,
    val senderName: String,
    val senderPhoto: String? = null,
    val text: String,
    val attachments: List<String>,
    val isPinned: Boolean,
    val isEdited: Boolean,
) : Event