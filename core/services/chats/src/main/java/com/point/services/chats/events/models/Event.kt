package com.point.services.chats.events.models

import java.time.Instant

sealed interface Event {
    val id: String
    val timestamp: Instant
}

