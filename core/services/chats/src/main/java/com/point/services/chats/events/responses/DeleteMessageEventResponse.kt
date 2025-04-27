package com.point.services.chats.events.responses

import com.point.network.di.serializers.InstantSerializer
import com.point.services.chats.events.models.MessageDeleted
import com.point.services.chats.events.responses.DeleteMessageEventResponse.Companion.DISCRIMINATOR
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
@SerialName(DISCRIMINATOR)
internal class DeleteMessageEventResponse(
    @SerialName("id")
    override val id: String,
    @Serializable(with = InstantSerializer::class)
    @SerialName("timestamp")
    override val timestamp: Instant,
    @SerialName("message_id")
    val messageId: String,
) : BaseEventResponse {

    override fun toModel() = MessageDeleted(
        id = id,
        timestamp = timestamp,
        messageId = messageId,
    )

    companion object {
        const val DISCRIMINATOR = "message_delete"
    }
}