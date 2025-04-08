package com.point.chats.dialog.viewmodel

import android.net.Uri
import com.point.chats.dialog.data.events.BaseEvent
import com.point.network.di.InstantSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Instant

data class ChatDialogState(
    val isInitialLoading: Boolean = true,
    val message: String = "",
    val events: List<BaseEvent> = emptyList(),
    val isInEditMode: EditMode? = null,
    val photos: List<Uri> = emptyList(),
)

data class EditMode(
    val messageId: String,
    val text: String,
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

    @SerialName("text")
    val content: String,

    @SerialName("attachments")
    val photos: List<Long> = emptyList(),

    @SerialName("_class")
    val type: String,
)