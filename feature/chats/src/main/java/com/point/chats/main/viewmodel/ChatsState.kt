package com.point.chats.main.viewmodel

import java.time.Instant

data class ChatsState(
    val chats: List<Chat> = emptyList(),
)

data class Chat(
    val id: String = "",
    val name: String = "",
    val photoId: Long? = null,
    val lastMessage: MessageShort? = null,
)

data class MessageShort(
    val id: String,
    val text: String,
    val timestamp: Instant,
)