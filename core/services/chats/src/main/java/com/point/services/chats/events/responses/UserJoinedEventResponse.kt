package com.point.services.chats.events.responses

import com.point.network.di.serializers.InstantSerializer
import com.point.services.chats.events.models.UserJoined
import com.point.services.chats.events.responses.UserJoinedEventResponse.Companion.DISCRIMINATOR
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
@SerialName(DISCRIMINATOR)
internal class UserJoinedEventResponse(
    override val id: String,
    @Serializable(with = InstantSerializer::class)
    override val timestamp: Instant,
    val userId: String,
    val invitedBy: String? = null,
) : BaseEventResponse {

    override fun toModel() = UserJoined(
        id = id,
        timestamp = timestamp,
        userId = userId,
        invitedBy = invitedBy,
    )

    companion object {
        const val DISCRIMINATOR = "notification"
    }
}