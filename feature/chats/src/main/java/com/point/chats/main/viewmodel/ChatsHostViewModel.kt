package com.point.chats.main.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.utils.Real
import com.point.chats.main.data.entity.response.ChatInfoShort
import com.point.chats.main.data.entity.response.MessageInfoShort
import com.point.chats.main.data.reporitory.ChatRepository
import com.point.viewmodel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatsHostViewModel @Inject constructor(@Real private val chatRepository: ChatRepository) :
    MviViewModel<ChatsState, ChatAction, ChatEvents>(
        initialValue = ChatsState()
    ) {

    init {
        viewModelScope.launch {
            chatRepository.fetchChats().fold(
                onSuccess = {
                    emitAction(ChatAction.Action.OnChatsLoadSuccess(it))
                },
                onFailure = {
                    it.printStackTrace()
                    emitEvent(ChatEvents.ShowSomethingWentWrong)
                }
            )

        }

        handleDeleteDialog()
        handleRefresh()
    }

    override fun reduce(action: ChatAction, state: ChatsState): ChatsState = when (action) {
        is ChatAction.Action.OnChatsLoadSuccess -> state.copy(
            chats = action.chats.toChat(),
            isRefreshing = false,
            isInitialLoading = false
        )

        is ChatAction.Event.DialogDeleted -> state.copy(
            chats = state.chats.filter { it.id != action.id }
        )

        ChatAction.Action.Refresh -> state.copy(isRefreshing = true)

        is ChatAction.Action.DeleteDialog -> state
    }

    private fun handleRefresh() {
        handleAction<ChatAction.Action.Refresh> {
            chatRepository.fetchChats().fold(
                onSuccess = {
                    emitAction(ChatAction.Action.OnChatsLoadSuccess(it))
                },
                onFailure = {
                    it.printStackTrace()
                    emitEvent(ChatEvents.ShowSomethingWentWrong)
                }
            )
        }
    }

    private fun handleDeleteDialog() {
        handleAction<ChatAction.Action.DeleteDialog> { deleteAction ->
            chatRepository.deleteDialog(deleteAction.id).fold(
                onSuccess = {
                    emitAction(ChatAction.Event.DialogDeleted(deleteAction.id))
                },
                onFailure = { it.printStackTrace() }
            )
        }
    }

}

private fun List<ChatInfoShort>.toChat() = map {
    Chat(
        id = it.id,
        name = it.name,
        photoId = it.photoId,
        lastMessage = it.lastMessage?.toMessageInfo(),
    )
}

fun MessageInfoShort.toMessageInfo() = MessageShort(
    id = id,
    text = text,
    timestamp = timestamp,
)