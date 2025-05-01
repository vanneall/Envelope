package com.point.chats.creation.group.confirm.mvi.actions

import android.net.Uri
import com.point.chats.creation.single.mvi.state.UserUi

internal sealed interface GroupChatConfirmAction {

    sealed interface UiEvent : GroupChatConfirmAction {

        @JvmInline
        value class NameEntered(val name: String) : UiEvent

        @JvmInline
        value class PhotoSelected(val uri: Uri) : UiEvent

        data object CreateGroupChat : UiEvent
    }

    sealed interface Event : GroupChatConfirmAction {

        data class UsersLoaded(val users: List<UserUi>) : Event
    }
}