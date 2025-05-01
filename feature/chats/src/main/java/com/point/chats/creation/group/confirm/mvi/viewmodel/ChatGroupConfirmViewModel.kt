package com.point.chats.creation.group.confirm.mvi.viewmodel

import androidx.lifecycle.viewModelScope
import com.point.chats.creation.group.confirm.mvi.actions.GroupChatConfirmAction
import com.point.chats.creation.group.confirm.mvi.events.ChatGroupConfirmEvents
import com.point.chats.creation.group.confirm.mvi.state.GroupChatConfirmState
import com.point.chats.creation.single.mvi.state.UserUi
import com.point.services.chats.models.ChatCreationData
import com.point.services.chats.repository.ChatsRepository
import com.point.viewmodel.MviViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.point.user.repository.UserRepository
import timber.log.Timber

@HiltViewModel(assistedFactory = ChatGroupConfirmViewModel.Factory::class)
internal class ChatGroupConfirmViewModel @AssistedInject constructor(
    @Assisted("users") users: List<String>,
    private val usersRepository: UserRepository,
    private val chatsRepository: ChatsRepository,
) : MviViewModel<GroupChatConfirmState, GroupChatConfirmAction, ChatGroupConfirmEvents>(
    initialValue = GroupChatConfirmState()
) {

    init {
        viewModelScope.launch { fetchUsers(users) }
        createGroupChat()
    }

    override fun reduce(action: GroupChatConfirmAction, state: GroupChatConfirmState) = when (action) {
        is GroupChatConfirmAction.Event.UsersLoaded -> state.copy(users = action.users)
        is GroupChatConfirmAction.UiEvent.NameEntered -> state.copy(chatName = action.name)
        is GroupChatConfirmAction.UiEvent.PhotoSelected -> state.copy(chatPhoto = action.uri)
        GroupChatConfirmAction.UiEvent.CreateGroupChat -> state
    }

    private suspend fun fetchUsers(ids: List<String>) {
        usersRepository.getLightInfo(ids).fold(
            onSuccess = {
                emitAction(GroupChatConfirmAction.Event.UsersLoaded(it.map {
                    UserUi(
                        it.username,
                        it.name,
                        it.photo
                    )
                }))
            },
            onFailure = { Timber.tag("Error").e(it.stackTraceToString()) }
        )
    }

    private fun createGroupChat() {
        handleAction<GroupChatConfirmAction.UiEvent.CreateGroupChat> {
            chatsRepository.createChat(
                data = ChatCreationData(
                    name = state.chatName,
                    participants = state.users.map { it.username },
                ),
                uri = state.chatPhoto,
            ).fold(
                onSuccess = { emitEvent(ChatGroupConfirmEvents.GroupChatCreated(it)) },
                onFailure = { Timber.tag("Error").e(it.stackTraceToString()) }
            )
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted("users") ids: List<String>): ChatGroupConfirmViewModel
    }
}