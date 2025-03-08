package com.point.chats.main.viewmodel

import com.point.chats.main.data.entity.response.ChatInfoShort

sealed interface ChatAction {
    sealed interface Action : ChatAction {
        data class OnChatsLoadSuccess(val chats: List<ChatInfoShort>) : Action
    }

    sealed interface Event : ChatAction
}