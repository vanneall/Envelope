package com.point.chats.dialog.data.events

import com.point.network.di.InstantSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
@SerialName("notification")
data class UserJoinedEvent(
    override val id: String,
    @Serializable(with = InstantSerializer::class)
    override val timestamp: Instant,
    val userId: String,
    val invitedBy: String? = null,
) : BaseEvent