package com.point.contacts.requests.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.point.contacts.main.presenter.ui.ContactsInitialLoading
import com.point.contacts.requests.viewModel.RequestsAction
import com.point.contacts.requests.viewModel.RequestsState

@Composable
fun UserRequestsScreen(
    state: RequestsState,
    onAction: (RequestsAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val swipeRefreshState = rememberSwipeRefreshState(state.isRefreshing)
    if (state.isInitialLoading) {
        ContactsInitialLoading(modifier = Modifier.fillMaxSize())
    } else {
        SwipeRefresh(
            state = swipeRefreshState,
            swipeEnabled = state.isRefreshingEnabled,
            onRefresh = { onAction(RequestsAction.Refresh) }
        ) {
            UserRequestsScreenContent(
                state = state,
                onAction = onAction,
                modifier = modifier,
            )
        }
    }
}