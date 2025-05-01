package com.point.chats.search.mvi.actions

import com.point.services.chats.models.ChatInfo

internal interface ChatsSearchAction {

    sealed interface UiEvent : ChatsSearchAction {

        @JvmInline
        value class OnChatType(val name: String) : UiEvent
    }

    sealed interface Event : ChatsSearchAction {

        data class ChatsLoaded(val chats: List<ChatInfo>) : Event
    }
}