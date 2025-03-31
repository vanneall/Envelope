package com.point.chats.main.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.utils.Real
import com.point.chats.main.data.entity.response.ChatInfoShort
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
    }

    override fun reduce(action: ChatAction, state: ChatsState): ChatsState = when (action) {
        is ChatAction.Action.OnChatsLoadSuccess -> state.copy(chats = action.chats.toChat())
    }

}

private fun List<ChatInfoShort>.toChat() = map {
    Chat(
        id = it.id,
        name = it.name,
        lastMessage = it.lastMessage.orEmpty()
    )
}