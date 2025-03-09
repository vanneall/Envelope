package com.point.chats.dialog.viewmodel

sealed interface ChatDialogAction {

    data object Send : ChatDialogAction

    data class TypeMessage(val value: String) : ChatDialogAction

}