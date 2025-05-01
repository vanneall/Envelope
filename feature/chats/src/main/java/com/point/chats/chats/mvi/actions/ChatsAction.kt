package com.point.chats.chats.mvi.actions

import com.point.chats.chats.mvi.state.ChatMode
import com.point.services.chats.models.ChatInfo

internal sealed interface ChatsAction {

    sealed interface UiEvent : ChatsAction {

        data class SelectChat(val id: String, val checked: Boolean) : UiEvent

        data object DeleteSelectedChats : UiEvent

        data object Refresh : UiEvent
    }

    sealed interface Event : ChatsAction {

        data class ChatsLoaded(val chats: List<ChatInfo>) : Event

        data class ChangeChatsMode(val mode: ChatMode) : Event

        data class ChatsDeleted(val ids: List<String>) : Event
    }
}