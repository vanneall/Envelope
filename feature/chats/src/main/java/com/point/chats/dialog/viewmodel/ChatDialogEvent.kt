package com.point.chats.dialog.viewmodel

sealed interface ChatDialogEvent {

    @JvmInline
    value class OnEditMessage(val text: String) : ChatDialogEvent

    data class ChatInited(val name: String, val photoId: Long?) : ChatDialogEvent

}