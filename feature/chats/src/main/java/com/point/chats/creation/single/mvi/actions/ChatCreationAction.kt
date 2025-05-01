package com.point.chats.creation.single.mvi.actions

import ru.point.user.models.UserContact

internal sealed interface ChatCreationAction {

    sealed interface UiEvent : ChatCreationAction {

        @JvmInline
        value class ContactSelected(val username: String) : UiEvent
    }

    sealed interface Event : ChatCreationAction {

        data class ContactLoaded(val contacts: List<UserContact>) : Event
    }
}