package com.point.services.chats.events.responses

import com.point.network.di.serializers.InstantSerializer
import com.point.services.chats.events.models.MessageEdited
import com.point.services.chats.events.responses.MessageEditedEventResponse.Companion.DISCRIMINATOR
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
@SerialName(DISCRIMINATOR)
internal class MessageEditedEventResponse(
    @SerialName("id")
    override val id: String,
    @Serializable(InstantSerializer::class)
    @SerialName("timestamp")
    override val timestamp: Instant,
    @SerialName("edited_message_id")
    val editedMessageId: String,
    @SerialName("new_content")
    val newContent: String,
) : BaseEventResponse {

    override fun toModel() = MessageEdited(
        id = id,
        timestamp = timestamp,
        editedMessageId = editedMessageId,
        newContent = newContent,
    )

    companion object {
        const val DISCRIMINATOR = "message_edit"
    }
}