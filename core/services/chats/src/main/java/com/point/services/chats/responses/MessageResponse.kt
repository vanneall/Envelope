package com.point.services.chats.responses

import com.point.network.di.serializers.InstantSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
internal class MessageResponse(
    @SerialName("id")
    val id: String,
    @SerialName("text")
    val text: String,
    @Serializable(InstantSerializer::class)
    @SerialName("timestamp")
    val timestamp: Instant,
)