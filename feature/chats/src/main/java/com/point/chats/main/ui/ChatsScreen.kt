package com.point.chats.main.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
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
    val swipeRefreshState = rememberSwipeRefreshState(state.isRefreshing)
    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { onAction(ChatAction.Action.Refresh) },
        swipeEnabled = !state.isRefreshing,
    ) {
        if (state.isInitialLoading) {
            ChatsInitialLoading(modifier = modifier)
        } else {
            ChatsScreenContent(
                state = state,
                onAction = onAction,
                onNavigate = onNavigate,
                modifier = modifier,
            )
        }
    }
}