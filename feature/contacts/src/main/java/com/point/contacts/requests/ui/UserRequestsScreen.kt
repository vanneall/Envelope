package com.point.contacts.requests.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.point.contacts.requests.viewModel.RequestsAction
import com.point.contacts.requests.viewModel.RequestsState

@Composable
fun UserRequestsScreen(
    state: RequestsState,
    onAction: (RequestsAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val swipeRefreshState = rememberSwipeRefreshState(state.isRefreshing)
    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { onAction(RequestsAction.Refresh) }
    ) {
        UserRequestsScreenContent(
            state = state,
            onAction = onAction,
            modifier = modifier,
        )
    }
}