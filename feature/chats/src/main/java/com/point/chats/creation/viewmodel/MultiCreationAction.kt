package com.point.chats.creation.viewmodel

import com.point.chats.creation.data.UserInfoShort

sealed interface MultiCreationAction {

    sealed interface UiAction : MultiCreationAction {

        data class CheckUser(val username: String): UiAction

        data object CreateMultiChat : UiAction
    }

    sealed interface EventAction : MultiCreationAction {

        data class ContactsLoaded(val users: List<UserInfoShort>) : EventAction
    }
}