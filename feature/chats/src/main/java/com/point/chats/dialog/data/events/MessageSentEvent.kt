package com.point.chats.dialog.data.events

import com.point.network.di.InstantSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
@SerialName("message")
data class MessageSentEvent(
    @SerialName("id")
    override val id: String,
    @Serializable(with = InstantSerializer::class)
    @SerialName("timestamp")
    override val timestamp: Instant,
    @SerialName("userName")
    val userName: String,
    @SerialName("userPhotoId")
    val userPhotoId: Long? = null,
    @SerialName("senderId")
    val senderId: String,
    @SerialName("text")
    val text: String,
    @SerialName("attachments")
    val attachments: List<Long> = emptyList(),
    @SerialName("is_pinned")
    val isPinned: Boolean,
    @SerialName("is_edited")
    val isEdited: Boolean,
) : BaseEvent