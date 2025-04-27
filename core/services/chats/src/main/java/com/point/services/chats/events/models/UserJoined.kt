package com.point.services.chats.events.models

import java.time.Instant

class UserJoined(
    override val id: String,
    override val timestamp: Instant,
    val userId: String,
    val invitedBy: String? = null,
) : Event