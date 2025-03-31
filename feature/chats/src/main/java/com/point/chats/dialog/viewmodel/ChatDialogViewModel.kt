package com.point.chats.dialog.viewmodel

import androidx.lifecycle.viewModelScope
import com.point.chats.dialog.data.ChatDialogRepository
import com.point.chats.dialog.data.events.DeleteMessageEvent
import com.point.viewmodel.MviViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = ChatDialogViewModel.Factory::class)
class ChatDialogViewModel @AssistedInject constructor(
    @Assisted(FACTORY_CHAT_ID)
    private val chatId: String,
    private val chatDialogRepository: ChatDialogRepository,
) : MviViewModel<ChatDialogState, ChatDialogAction, Any>(
    initialValue = ChatDialogState()
) {

    init {
        viewModelScope.launch {
            launch { onConnectToChat() }
            launch {
                chatDialogRepository.fetchInfo(chatId).fold(
                    onSuccess = {
                        emitAction(ChatDialogAction.EventsLoaded(it))
                    },
                    onFailure = { it.printStackTrace() }
                )
            }
        }

        onSendMessage()
        onDeleteMessage()
    }

    override fun reduce(action: ChatDialogAction, state: ChatDialogState) = when (action) {
        is ChatDialogAction.TypeMessage -> state.copy(message = action.value)
        ChatDialogAction.Send -> state
        is ChatDialogAction.UpdateList -> state.copy(events = listOf(action.text) + state.events)
        is ChatDialogAction.EventsLoaded -> state.copy(
            events = action.list
        )

        is ChatDialogAction.DeleteSuccess -> state.copy(events = state.events.filter { it.id != action.id })
        ChatDialogAction.ClearField -> state.copy(message = "")
        is ChatDialogAction.Delete -> state
    }

    private suspend fun onConnectToChat() {
        chatDialogRepository.connectToChat(chatId = chatId).collect { event ->
            when (event) {
                is DeleteMessageEvent -> emitAction(ChatDialogAction.DeleteSuccess(event.messageId))
                else -> {
                    emitAction(ChatDialogAction.UpdateList(event))
                    emitAction(ChatDialogAction.ClearField)
                }
            }
        }
    }

    private fun onSendMessage() {
        handleAction<ChatDialogAction.Send> {
            chatDialogRepository.sendMessage(text = state.message)
        }
    }

    private fun onDeleteMessage() {
        handleAction<ChatDialogAction.Delete> {
            chatDialogRepository.deleteMessage(id = it.id)
        }
    }

    override fun onCleared() {
        super.onCleared()
        chatDialogRepository.disconnect()
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted(FACTORY_CHAT_ID) chatId: String): ChatDialogViewModel
    }

    private companion object {
        const val FACTORY_CHAT_ID = "chatId"
    }
}