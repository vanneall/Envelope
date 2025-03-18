package com.point.chats.main.ui

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.point.chats.main.viewmodel.ChatAction
import com.point.chats.main.viewmodel.ChatEvents
import com.point.chats.main.viewmodel.ChatsState
import kotlinx.coroutines.flow.Flow

@Composable
fun ChatsScreen(
    state: ChatsState,
    events: Flow<ChatEvents>,
    onAction: (ChatAction) -> Unit,
    onNavigate: () -> Unit,
    onNavigateToChat: (id: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    ChatsScreenContent(
        state = state,
        onAction = onAction,
        onNavigate = onNavigate,
        onNavigateToChat = onNavigateToChat,
        modifier = modifier,
    )
}