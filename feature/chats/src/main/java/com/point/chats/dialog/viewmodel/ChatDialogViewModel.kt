package com.point.chats.dialog.viewmodel

import com.point.viewmodel.MviViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel(assistedFactory = ChatDialogViewModel.Factory::class)
class ChatDialogViewModel @AssistedInject constructor(
    @Assisted("chatId") private val chatId: String,
) : MviViewModel<ChatDialogState, ChatDialogAction, Any>(
    initialValue = ChatDialogState()
) {

    override fun reduce(action: ChatDialogAction, state: ChatDialogState) = when (action) {
        is ChatDialogAction.TypeMessage -> state.copy(message = action.value)
        ChatDialogAction.Send -> state
    }


    @AssistedFactory
    interface Factory {
        fun create(@Assisted("chatId") chatId: String): ChatDialogViewModel
    }
}