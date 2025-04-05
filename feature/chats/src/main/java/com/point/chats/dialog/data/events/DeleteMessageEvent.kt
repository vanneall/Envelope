package com.point.chats.dialog.data.events

import com.point.network.di.InstantSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
@SerialName("message_delete")
data class DeleteMessageEvent(
    @SerialName("id")
    override val id: String,
    @Serializable(with = InstantSerializer::class)
    @SerialName("timestamp")
    override val timestamp: Instant,
    @SerialName("message_id")
    val messageId: String,
) : BaseEvent