package com.point.chats.creation.group.mvi.state

import androidx.compose.runtime.Immutable
import com.point.chats.creation.single.mvi.state.UserUi

@Immutable
internal data class ChatCreationGroupState(
    val users: Map<String, List<UserUi>> = emptyMap(),
    val selected: Set<String> = emptySet(),
)