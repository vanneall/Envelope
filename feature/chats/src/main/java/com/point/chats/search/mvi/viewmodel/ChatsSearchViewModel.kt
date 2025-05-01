package com.point.chats.search.mvi.viewmodel

import com.point.chats.search.mvi.actions.ChatsSearchAction
import com.point.chats.search.mvi.state.ChatsSearchState
import com.point.services.chats.repository.ChatsRepository
import com.point.tea.LoadableState
import com.point.tea.mapSuccess
import com.point.viewmodel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class ChatsSearchViewModel @Inject constructor(private val chatsRepository: ChatsRepository) :
    MviViewModel<LoadableState<ChatsSearchState>, ChatsSearchAction, Any>(
        initialValue = LoadableState.successOf(ChatsSearchState())
    ) {

    override fun reduce(action: ChatsSearchAction, state: LoadableState<ChatsSearchState>) = when (action) {

        is ChatsSearchAction.UiEvent.OnChatType -> state.mapSuccess { it.copy(chatName = action.name) }

        else -> state
    }
}