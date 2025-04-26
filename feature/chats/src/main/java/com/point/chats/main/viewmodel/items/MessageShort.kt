package com.point.chats.main.viewmodel.items

import java.time.Instant

data class MessageShort(
    val id: String,
    val text: String,
    val timestamp: Instant,
)