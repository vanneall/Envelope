package com.point.chats.create.presenter.viewmodel

sealed interface CreateChatEvent {

    data object ChatCreatedSuccessfully : CreateChatEvent

}