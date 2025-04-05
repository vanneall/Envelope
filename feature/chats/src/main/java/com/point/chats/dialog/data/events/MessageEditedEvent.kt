package com.point.chats.dialog.data.events

import com.point.network.di.InstantSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class MessageEditedEvent(
    @SerialName("id")
    override val id: String,
    @Serializable(InstantSerializer::class)
    @SerialName("timestamp")
    override val timestamp: Instant,
    @SerialName("edited_message_id")
    val editedMessageId: String,
    @SerialName("new_content")
    val newContent: String,
): BaseEvent