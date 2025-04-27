package com.point.chats.multi.info.viewmodel

import com.point.services.chats.models.GroupChatInfo

sealed interface MultiChatInfoAction {

    sealed interface UiAction : MultiChatInfoAction {

        data class DeleteUser(val id: String) : UiAction
    }

    sealed interface EventAction : MultiChatInfoAction {

        data class OnDataLoaded(val groupChatInfo: GroupChatInfo) : EventAction
    }
}