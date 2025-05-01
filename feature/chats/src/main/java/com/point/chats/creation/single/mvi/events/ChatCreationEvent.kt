package com.point.chats.creation.single.mvi.events

sealed interface ChatCreationEvent {

    @JvmInline
    value class OpenChat(val id: String) : ChatCreationEvent
}