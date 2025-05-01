package com.point.chats.chats.mvi.viewmodel

import androidx.lifecycle.viewModelScope
import com.point.chats.chats.mvi.actions.ChatsAction
import com.point.chats.chats.mvi.state.ChatMode
import com.point.chats.chats.mvi.state.ChatState
import com.point.chats.chats.mvi.state.toChatsUi
import com.point.services.chats.repository.ChatsRepository
import com.point.tea.LoadableState
import com.point.tea.doOnSuccess
import com.point.tea.mapSuccess
import com.point.tea.toLoading
import com.point.tea.toSuccess
import com.point.viewmodel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class ChatsViewModel @Inject constructor(private val chatsRepository: ChatsRepository) :
    MviViewModel<LoadableState<ChatState>, ChatsAction, Any>(initialValue = LoadableState.loadingOf()) {

    init {
        viewModelScope.launch { getChats() }
        collectChatsDelete()
        collectRefresh()
    }

    override fun reduce(action: ChatsAction, state: LoadableState<ChatState>): LoadableState<ChatState> =
        when (action) {
            is ChatsAction.Event.ChangeChatsMode -> state.mapSuccess { it.copy(mode = action.mode) }
            is ChatsAction.Event.ChatsDeleted -> state.mapSuccess {
                it.copy(chats = it.chats.filter { chat -> chat.id !in action.ids })
            }

            is ChatsAction.Event.ChatsLoaded -> state.toSuccess(ChatState()) {
                it.copy(chats = action.chats.map { chatInfo -> chatInfo.toChatsUi() })
            }

            ChatsAction.UiEvent.DeleteSelectedChats -> state
            is ChatsAction.UiEvent.SelectChat -> state.mapSuccess { selectChat(state = it, action = action) }
            ChatsAction.UiEvent.Refresh -> state.toLoading()
        }

    private fun selectChat(state: ChatState, action: ChatsAction.UiEvent.SelectChat): ChatState {
        return if (state.mode == ChatMode.Idle && action.checked) {
            state.copy(mode = ChatMode.Edit(selected = setOf(action.id)))
        } else if (state.mode is ChatMode.Edit && state.mode.selected.size == 1 && !action.checked) {
            state.copy(mode = ChatMode.Idle)
        } else if (state.mode is ChatMode.Edit && action.checked) {
            state.copy(mode = ChatMode.Edit(selected = state.mode.selected + action.id))
        } else if (state.mode is ChatMode.Edit) {
            state.copy(mode = ChatMode.Edit(selected = state.mode.selected - action.id))
        } else {
            state
        }
    }

    private suspend fun getChats() {
        delay(2_000)
        chatsRepository.getChats().fold(
            onSuccess = { emitAction(ChatsAction.Event.ChatsLoaded(it)) },
            onFailure = { Timber.tag("Error").e(it.stackTraceToString()) }
        )
    }

    private fun collectChatsDelete() {
        handleAction<ChatsAction.UiEvent.DeleteSelectedChats> {
            state.doOnSuccess { state ->
                val mode = state.mode
                if (mode is ChatMode.Edit) {
                    chatsRepository.delete(mode.selected.toList())
                }
            }
        }
    }

    private fun collectRefresh() {
        handleAction<ChatsAction.UiEvent.Refresh> {
            getChats()
        }
    }
}