package com.point.services.chats.events.responses

import com.point.network.di.serializers.InstantSerializer
import com.point.services.chats.events.models.Message
import com.point.services.chats.events.responses.MessageSentEventResponse.Companion.DISCRIMINATOR
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
@SerialName(DISCRIMINATOR)
internal class MessageSentEventResponse(
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
    val attachments: List<Long>,
    @SerialName("is_pinned")
    val isPinned: Boolean,
    @SerialName("is_edited")
    val isEdited: Boolean,
) : BaseEventResponse {

    override fun toModel() = Message(
        id = id,
        timestamp = timestamp,
        senderName = userName,
        senderPhoto = userPhotoId?.let { uri -> "http://192.168.0.174:8084/photos/$uri" },
        sender = senderId,
        text = text,
        attachments = attachments,
        isPinned = isPinned,
        isEdited = isEdited,
    )

    companion object {
        const val DISCRIMINATOR = "message"
    }
}