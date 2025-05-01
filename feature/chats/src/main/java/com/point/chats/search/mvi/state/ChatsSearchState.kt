package com.point.chats.search.mvi.state

import androidx.compose.runtime.Immutable
import com.point.chats.chats.mvi.state.ChatUi

@Immutable
internal data class ChatsSearchState(
    val chatName: String = "",
    val chats: List<ChatUi> = emptyList(),
) {
    val recentShowed get() = chatName.isEmpty()
}