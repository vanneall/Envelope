package com.point.chats.creation.group.mvi.actions

import ru.point.user.models.UserContact

internal sealed interface ChatCreationGroupAction {

    sealed interface UiEvent : ChatCreationGroupAction {

        @JvmInline
        value class PickUser(val username: String) : UiEvent

        data object Confirm : UiEvent
    }

    sealed interface Event : ChatCreationGroupAction {

        data class ContactsLoaded(val contacts: List<UserContact>) : Event
    }
}