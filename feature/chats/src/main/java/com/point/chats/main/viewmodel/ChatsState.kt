package com.point.chats.main.viewmodel

import androidx.compose.ui.graphics.Color

data class ChatsState(
    val chats: List<Chat> = emptyList(),
)

data class Chat(
    val id: String = "",
    val name: String = "",
    val image: Color = Color.Gray,
    val lastMessage: String = "",
)