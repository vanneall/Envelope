package com.point.chats.creation.viewmodel

import ru.point.user.models.UserContact

sealed interface MultiCreationAction {

    sealed interface UiAction : MultiCreationAction {

        data class CheckUser(val username: String): UiAction

        data object CreateMultiChat : UiAction
    }

    sealed interface EventAction : MultiCreationAction {

        data class ContactsLoaded(val users: List<UserContact>) : EventAction
    }
}