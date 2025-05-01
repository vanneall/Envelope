package com.point.chats.creation.group.mvi.viewmodel

import androidx.lifecycle.viewModelScope
import com.point.chats.creation.group.mvi.actions.ChatCreationGroupAction
import com.point.chats.creation.group.mvi.state.ChatCreationGroupState
import com.point.chats.creation.single.mvi.state.toUi
import com.point.viewmodel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.point.user.repository.UserRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class ChatCreationGroupViewModel @Inject constructor(
    private val userRepository: UserRepository
) : MviViewModel<ChatCreationGroupState, ChatCreationGroupAction, Any>(
    initialValue = ChatCreationGroupState()
) {

    init {
        viewModelScope.launch { getUsers() }
    }

    override fun reduce(action: ChatCreationGroupAction, state: ChatCreationGroupState) = when (action) {

        is ChatCreationGroupAction.Event.ContactsLoaded -> state.copy(
            users = action.contacts
                .groupBy { contact -> contact.name.first().uppercase() }
                .mapValues { (_, contacts) ->
                    contacts.map { contact -> contact.toUi() }
                }
        )

        is ChatCreationGroupAction.UiEvent.PickUser -> state.copy(
            selected = if (action.username in state.selected) {
                state.selected - action.username
            } else {
                state.selected + action.username
            }
        )

        ChatCreationGroupAction.UiEvent.Confirm -> state
    }

    private suspend fun getUsers() {
        userRepository.fetchUserContacts().fold(
            onSuccess = { emitAction(ChatCreationGroupAction.Event.ContactsLoaded(it)) },
            onFailure = { Timber.tag("Error").e(it.stackTraceToString()) },
        )
    }
}