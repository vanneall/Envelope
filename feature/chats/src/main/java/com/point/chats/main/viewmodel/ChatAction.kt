package com.point.chats.main.viewmodel

sealed interface ChatAction {
    sealed interface Action : ChatAction

    sealed interface Event : ChatAction
}