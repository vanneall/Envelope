package com.point.chats.creation.viewmodel

import androidx.lifecycle.viewModelScope
import com.point.chats.create.repository.ChatsRepository
import com.point.chats.create.repository.CreateChatRequest
import com.point.chats.creation.data.User
import com.point.viewmodel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MultiCreationViewModel @Inject constructor(private val chatsRepository: ChatsRepository) :
    MviViewModel<MultiCreationState, MultiCreationAction, Any>(
        initialValue = MultiCreationState()
    ) {

    init {
        viewModelScope.launch {
            chatsRepository.fetchContacts().fold(
                onSuccess = { emitAction(MultiCreationAction.EventAction.ContactsLoaded(it)) },
                onFailure = { it.printStackTrace() }
            )
        }

        createChat()
    }

    override fun reduce(action: MultiCreationAction, state: MultiCreationState) = when (action) {
        is MultiCreationAction.UiAction.CheckUser -> state.copy(
            users = state.users.map { if (it.username == action.username) it.copy(checked = !it.checked) else it }
        )

        is MultiCreationAction.EventAction.ContactsLoaded -> state.copy(
            users = action.users.map {
                User(
                    username = it.username,
                    name = it.name,
                    photoId = it.photo,
                    checked = false
                )
            }
        )

        is MultiCreationAction.UiAction.CreateMultiChat -> state
    }


    private fun createChat() {
        handleAction<MultiCreationAction.UiAction.CreateMultiChat> {
            chatsRepository.createChat(
                CreateChatRequest(
                    participantIds = state.users.map { it.username },
                    name = state.users.joinToString { it.username + "," }
                )
            )
        }
    }
}