package com.point.chats.dialog.viewmodel

import com.point.chats.dialog.data.events.BaseEvent

sealed interface ChatDialogAction {

    data object Send : ChatDialogAction
    data class Delete(val id: String) : ChatDialogAction
    data class DeleteSuccess(val id: String) : ChatDialogAction

    data class TypeMessage(val value: String) : ChatDialogAction


    data class UpdateList(val text: BaseEvent) : ChatDialogAction

    data object ClearField : ChatDialogAction

    data class EventsLoaded(val list: List<BaseEvent>) : ChatDialogAction
}