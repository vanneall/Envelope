package com.point.chats.dialog.viewmodel

import com.point.network.di.InstantSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Instant

data class ChatDialogState(
    val message: String = "",
    val events: List<Message> = emptyList(),
)

@Serializable
data class Message(
    @SerialName("id")
    val id: String,
    @Serializable(with = InstantSerializer::class)
    @SerialName("timestamp")
    val timestamp: Instant,
    @SerialName("senderId")
    val senderId: String,
    @SerialName("content")
    val content: String,
    @SerialName("photos")
    val photos: MutableList<Long>?,
)