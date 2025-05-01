package com.point.chats.creation.single.mvi.viewmodel

import androidx.lifecycle.viewModelScope
import com.point.chats.creation.single.mvi.actions.ChatCreationAction
import com.point.chats.creation.single.mvi.events.ChatCreationEvent
import com.point.chats.creation.single.mvi.state.ChatCreationState
import com.point.chats.creation.single.mvi.state.toUi
import com.point.services.chats.models.ChatCreationData
import com.point.services.chats.repository.ChatsRepository
import com.point.viewmodel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.point.user.repository.UserRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class ChatCreationViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val chatsRepository: ChatsRepository,
) : MviViewModel<ChatCreationState, ChatCreationAction, ChatCreationEvent>(
    initialValue = ChatCreationState()
) {

    init {
        viewModelScope.launch { getUsers() }
        createChat()
    }

    override fun reduce(action: ChatCreationAction, state: ChatCreationState) = when (action) {
        is ChatCreationAction.UiEvent.ContactSelected -> state

        is ChatCreationAction.Event.ContactLoaded -> state.copy(
            users = action.contacts
                .groupBy { contact -> contact.name.first().uppercase() }
                .mapValues { (_, contacts) ->
                    contacts.map { contact -> contact.toUi() }
                }
        )
    }

    private suspend fun getUsers() {
        userRepository.fetchUserContacts().fold(
            onSuccess = { emitAction(ChatCreationAction.Event.ContactLoaded(it)) },
            onFailure = { Timber.tag("Error").e(it.stackTraceToString()) }
        )
    }

    private fun createChat() {
        handleAction<ChatCreationAction.UiEvent.ContactSelected> { action ->
            chatsRepository.createChat(ChatCreationData(name = null, participants = listOf(action.username))).fold(
                onSuccess = { emitEvent(ChatCreationEvent.OpenChat(it)) },
                onFailure = { Timber.tag("Error").e(it.stackTraceToString()) }
            )
        }
    }
}