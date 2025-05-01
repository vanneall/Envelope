package com.point.chats.creation.group.confirm.mvi.events

sealed interface ChatGroupConfirmEvents {

    @JvmInline
    value class GroupChatCreated(val id: String) : ChatGroupConfirmEvents
}