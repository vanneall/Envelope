package com.point.chats.create.presenter.viewmodel

import androidx.compose.ui.graphics.Color

data class CreateChatState(
    val contacts: List<Contact> = emptyList(),
    val search: String = "",
    val isError: Boolean = false,
)

data class Contact(
    val id: String = "",
    val name: String = "",
    val image: Color = Color.Gray,
)