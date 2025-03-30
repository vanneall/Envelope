package com.point.chats.main.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.point.chats.main.viewmodel.ChatAction
import com.point.chats.main.viewmodel.ChatEvents
import com.point.chats.main.viewmodel.ChatsState
import com.point.navigation.Route
import kotlinx.coroutines.flow.Flow

@Composable
fun ChatsScreen(
    state: ChatsState,
    events: Flow<ChatEvents>,
    onAction: (ChatAction) -> Unit,
    onNavigate: (Route) -> Unit,
    modifier: Modifier = Modifier,
) {
    ChatsScreenContent(
        state = state,
        onAction = onAction,
        onNavigate = onNavigate,
        modifier = modifier,
    )
}