package com.point.contacts.main.presenter.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.point.contacts.main.presenter.viewmodel.ContactState
import com.point.contacts.main.presenter.viewmodel.ContactsActions

@Composable
fun ContactsScreen(
    state: ContactState,
    onAction: (ContactsActions) -> Unit,
    modifier: Modifier = Modifier,
) {
    val swipeRefreshState = rememberSwipeRefreshState(state.isRefreshing)
    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { onAction(ContactsActions.Refresh) }
    ) {
        ContactsScreenContent(
            state = state,
            onAction = onAction,
            modifier = modifier,
        )
    }
}
