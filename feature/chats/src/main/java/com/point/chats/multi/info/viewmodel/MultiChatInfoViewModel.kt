package com.point.chats.multi.info.viewmodel

import androidx.lifecycle.viewModelScope
import com.point.services.chats.repository.ChatsRepository
import com.point.viewmodel.MviViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = MultiChatInfoViewModel.Factory::class)
class MultiChatInfoViewModel @AssistedInject constructor(
    @Assisted("chatId") private val chatId: String,
    private val chatsRepository: ChatsRepository,
) : MviViewModel<MultiChatInfoState, MultiChatInfoAction, Any>(
    initialValue = MultiChatInfoState()
) {

    init {
        viewModelScope.launch {
            chatsRepository.getGroupChatInfo(chatId).fold(
                onSuccess = {
                    emitAction(MultiChatInfoAction.EventAction.OnDataLoaded(it))
                },
                onFailure = {
                    it.printStackTrace()
                },
            )
        }

        collectDeleteUser()
    }

    override fun reduce(action: MultiChatInfoAction, state: MultiChatInfoState) = when (action) {

        is MultiChatInfoAction.EventAction.OnDataLoaded -> state.copy(
            id = action.groupChatInfo.id,
            name = action.groupChatInfo.name,
            description = action.groupChatInfo.description,
            type = action.groupChatInfo.type,
            chatPreviewPhotos = action.groupChatInfo.chatPreviewPhotosIds.map { "" },
            mediaContentIds = action.groupChatInfo.mediaContentIds.map { "" },
            chatUsers = action.groupChatInfo.users.map {
                UserInfo(
                    id = it.id,
                    name = it.name,
                    photoId = it.photoId?.let { "" },
                    userRole = it.role,
                )
            }
        )

        is MultiChatInfoAction.UiAction.DeleteUser -> state
    }

    private fun collectDeleteUser() {
        handleAction<MultiChatInfoAction.UiAction.DeleteUser> {
            chatsRepository.deleteUserFromChat(chatId, it.id)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted("chatId") chatId: String): MultiChatInfoViewModel
    }
}