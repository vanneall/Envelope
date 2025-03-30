package com.point.contacts.main.presenter.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.point.contacts.main.presenter.viewmodel.ContactState
import com.point.contacts.main.presenter.viewmodel.ContactsActions
import com.point.navigation.Route

@Composable
fun ContactsScreen(
    state: ContactState,
    onAction: (ContactsActions) -> Unit,
    onNavigation: (Route) -> Unit,
    modifier: Modifier = Modifier,
) {
    val swipeRefreshState = rememberSwipeRefreshState(state.isRefreshing)

    if (state.isInitialLoading) {
        ContactsInitialLoading(modifier = Modifier.fillMaxWidth(),)
    } else {
        SwipeRefresh(
            state = swipeRefreshState,
            swipeEnabled = state.isRefreshingEnabled,
            onRefresh = { onAction(ContactsActions.Refresh) }
        ) {
            ContactsScreenContent(
                state = state,
                onAction = onAction,
                onNavigation = onNavigation,
                modifier = modifier,
            )
        }
    }
}
