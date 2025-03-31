package com.point.chats.dialog.viewmodel

import androidx.lifecycle.viewModelScope
import com.point.chats.dialog.data.ChatDialogRepository
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
    }

    override fun reduce(action: ChatDialogAction, state: ChatDialogState) = when (action) {
        is ChatDialogAction.TypeMessage -> state.copy(message = action.value)
        ChatDialogAction.Send -> state
        is ChatDialogAction.UpdateList -> state.copy(events = state.events + listOf(action.text) )
        is ChatDialogAction.EventsLoaded -> state.copy(
            events = action.list
        )

        ChatDialogAction.ClearField -> state.copy(message = "")
    }

    private suspend fun onConnectToChat() {
        chatDialogRepository.connectToChat(chatId = chatId).collect { message ->
            emitAction(ChatDialogAction.UpdateList(message))
            emitAction(ChatDialogAction.ClearField)
        }
    }

    private fun onSendMessage() {
        handleAction<ChatDialogAction.Send> {
            chatDialogRepository.sendMessage(text = state.message)
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