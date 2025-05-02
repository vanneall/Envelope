package com.point.chats.dialog.viewmodel

import android.net.Uri
import com.point.services.chats.events.models.Event
import com.point.services.chats.models.ChatType

sealed interface ChatDialogAction {

    sealed interface UiEvent : ChatDialogAction {

        data class OnPhotoPicked(val photos: List<Uri>) : UiEvent

        @JvmInline
        value class OnPhotoDeletedFromMessage(val uri: Uri) : UiEvent

        data class EditMessage(val id: String, val text: String) : UiEvent

        @JvmInline
        value class DeleteMessage(val id: String) : UiEvent

        @JvmInline
        value class TypeMessage(val value: String) : UiEvent

        data object SendMessage : UiEvent
    }

    data class DeleteSuccess(val id: String) : ChatDialogAction


    data class EditSuccess(val id: String, val text: String): ChatDialogAction


    data class UpdateList(val event: Event) : ChatDialogAction

    data object ClearField : ChatDialogAction

    data class EventsLoaded(val list: List<Event>) : ChatDialogAction

    data class SetChatType(val chatType: ChatType) : ChatDialogAction

    data class ChatInited(val name: String, val photo: String?) : ChatDialogAction
}