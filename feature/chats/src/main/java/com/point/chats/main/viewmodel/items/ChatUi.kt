package com.point.chats.main.viewmodel.items

import com.point.ui.items.UiItem

data class ChatUi(
    val id: String = "",
    val name: String = "",
    val checked: Boolean = false,
    val photoUrl: String? = null,
    val lastMessage: MessageShort? = null,
): UiItem