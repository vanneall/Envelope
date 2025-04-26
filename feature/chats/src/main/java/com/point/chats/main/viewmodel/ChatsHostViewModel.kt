package com.point.chats.main.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.utils.Real
import com.point.chats.main.data.entity.response.ChatInfoShort
import com.point.chats.main.data.entity.response.MessageInfoShort
import com.point.chats.main.data.reporitory.ChatRepository
import com.point.chats.main.viewmodel.items.ChatUi
import com.point.chats.main.viewmodel.items.MessageShort
import com.point.chats.main.viewmodel.items.Mode
import com.point.ui.items.mapUi
import com.point.chats.main.viewmodel.ChatAction.UiAction
import com.point.chats.main.viewmodel.ChatAction.Event
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
        viewModelScope.launch { fetchData() }

        handleDeleteDialog()
        handleRefresh()
        handleChangeEditMode()
    }

    override fun reduce(action: ChatAction, state: ChatsState): ChatsState = when (action) {
        is Event.OnChatsLoadSuccess -> state.copy(
            chats = action.chats.toChatsUi(),
            isRefreshing = false,
            isInitialLoading = false
        )

        is Event.ChatsDeleted -> state.copy(
            chats = state.chats.filter { (it as ChatUi).id !in action.ids }
        )

        UiAction.Refresh -> state.copy(isRefreshing = true)

        is UiAction.DeleteChats -> state
        is UiAction.OnChatCheckedChange -> state.copy(
            chats = state.chats.mapUi<ChatUi, ChatUi> { chat ->
                if (chat.id == action.id) {
                    chat.copy(checked = action.checked)
                } else {
                    chat
                }
            }
        )

        is Event.ChangeMode -> state.copy(mode = action.mode)
    }

    private fun handleRefresh() {
        handleAction<UiAction.Refresh> {
            fetchData()
        }
    }

    private suspend fun fetchData() {
        chatRepository.fetchChats().fold(
            onSuccess = {
                emitAction(Event.OnChatsLoadSuccess(it))
            },
            onFailure = {
                it.printStackTrace()
                emitEvent(ChatEvents.ShowSomethingWentWrong)
            }
        )
    }

    private fun handleDeleteDialog() {
        handleAction<UiAction.DeleteChats> { deleteAction ->
            val ids = state.chats.map { (it as ChatUi).id }
            chatRepository.deleteChats(ids).fold(
                onSuccess = {
                    emitAction(Event.ChatsDeleted(ids))
                },
                onFailure = { it.printStackTrace() }
            )
        }
    }

    private fun handleChangeEditMode() {
        handleAction<UiAction.OnChatCheckedChange> { action ->
            val state = state
            val newMode = when {
                action.checked && state.mode == Mode.IDLE -> Mode.EDIT
                !action.checked && state.mode == Mode.EDIT && state.chats.count { (it as? ChatUi)?.checked == true } == 1 -> Mode.IDLE
                else -> null
            }

            newMode?.let { mode ->
                emitAction(Event.ChangeMode(mode))
                emitEvent(ChatEvents.ChangeDeleteActionVisibility(mode == Mode.EDIT))
            }
        }
    }
}

private fun List<ChatInfoShort>.toChatsUi() = map {
    ChatUi(
        id = it.id,
        name = it.name,
        photoUrl = it.photoId?.let { uri -> "http://192.168.0.174:8084/photos/$uri" },
        lastMessage = it.lastMessage?.toMessageInfo(),
    )
}

fun MessageInfoShort.toMessageInfo() = MessageShort(
    id = id,
    text = text,
    timestamp = timestamp,
)