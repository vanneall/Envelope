package com.point.chats.create.presenter.viewmodel

import com.point.chats.create.repository.ContactResponse

sealed interface CreateChatAction {

    sealed interface Action : CreateChatAction {

        data class OnValueChanged(val value: String) : Action

        data class CreateChatWithContact(val id: String) : Action
    }

    sealed interface Event: CreateChatAction {

        data class OnContactLoadSuccessfully(val contacts: List<ContactResponse>) : Event
        data object OnContactLoadFailed : Event
    }

}