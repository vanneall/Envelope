package com.point.services.chats.responses

import com.point.network.di.serializers.InstantSerializer
import com.point.services.chats.models.MessageType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
internal class MessageResponse(
    @SerialName("id")
    val id: String,
    @SerialName("text")
    val text: String,
    val type: MessageType,
    @Serializable(InstantSerializer::class)
    @SerialName("timestamp")
    val timestamp: Instant,
)