package com.point.chats.dialog.viewmodel

import android.net.Uri
import com.point.services.chats.events.models.Event
import com.point.services.chats.models.ChatType

data class ChatDialogState(
    val chatType: ChatType = ChatType.PRIVATE,
    val isInitialLoading: Boolean = true,
    val message: String = "",
    val events: List<Event> = emptyList(),
    val isInEditMode: EditMode? = null,
    val photos: List<Uri> = emptyList(),
)

data class EditMode(
    val messageId: String,
    val text: String,
)