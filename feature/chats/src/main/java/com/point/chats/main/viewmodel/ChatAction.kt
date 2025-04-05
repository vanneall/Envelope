package com.point.chats.main.viewmodel

import com.point.chats.main.data.entity.response.ChatInfoShort

sealed interface ChatAction {

    sealed interface Action : ChatAction {

        data class OnChatsLoadSuccess(val chats: List<ChatInfoShort>) : Action

        data class DeleteDialog(val id: String) : Action

        data object Refresh : Action
    }

    sealed interface Event : ChatAction {

        data class DialogDeleted(val id: String) : Event

    }
}