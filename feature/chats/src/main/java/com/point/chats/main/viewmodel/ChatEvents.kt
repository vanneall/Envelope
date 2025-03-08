package com.point.chats.main.viewmodel

sealed interface ChatEvents {

    data object ShowSomethingWentWrong : ChatEvents

}