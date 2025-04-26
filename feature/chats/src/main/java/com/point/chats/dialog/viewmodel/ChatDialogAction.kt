package com.point.chats.dialog.viewmodel

import android.net.Uri
import com.point.chats.dialog.data.events.BaseEvent
import com.point.chats.main.data.entity.response.ChatType

sealed interface ChatDialogAction {

    sealed interface UiAction : ChatDialogAction {

        data class OnPhotoPicked(val photos: List<Uri>) : UiAction

        data class OnPhotoDeletedFromMessage(val uri: Uri) : UiAction
    }

    data object Send : ChatDialogAction
    data class Delete(val id: String) : ChatDialogAction
    data class DeleteSuccess(val id: String) : ChatDialogAction


    data class Edit(val id: String, val text: String): ChatDialogAction
    data class EditSuccess(val id: String, val text: String): ChatDialogAction

    data class TypeMessage(val value: String) : ChatDialogAction


    data class UpdateList(val text: BaseEvent) : ChatDialogAction

    data object ClearField : ChatDialogAction

    data class EventsLoaded(val list: List<BaseEvent>) : ChatDialogAction

    data class SetChatType(val chatType: ChatType) : ChatDialogAction
}