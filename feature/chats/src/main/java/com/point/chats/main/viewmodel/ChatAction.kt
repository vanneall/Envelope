package com.point.chats.main.viewmodel

import com.point.chats.main.data.entity.response.ChatInfoShort
import com.point.chats.main.viewmodel.items.Mode

sealed interface ChatAction {

    sealed interface UiAction : ChatAction {

        data class OnChatCheckedChange(val id: String, val checked: Boolean): UiAction

        data object DeleteChats : UiAction

        data object Refresh : UiAction
    }

    sealed interface Event : ChatAction {

        data class ChangeMode(val mode: Mode): Event

        data class OnChatsLoadSuccess(val chats: List<ChatInfoShort>) : Event

        data class ChatsDeleted(val ids: List<String>) : Event
    }
}