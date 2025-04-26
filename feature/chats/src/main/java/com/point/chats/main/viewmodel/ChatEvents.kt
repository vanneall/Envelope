package com.point.chats.main.viewmodel

sealed interface ChatEvents {

    data object ShowSomethingWentWrong : ChatEvents

    @JvmInline
    value class ChangeDeleteActionVisibility(val isVisible: Boolean) : ChatEvents

}