package com.point.contacts.main.presenter.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.point.contacts.R
import com.point.contacts.main.presenter.viewmodel.ContactsActions
import com.point.contacts.main.presenter.viewmodel.UserContactsViewModel
import com.point.navigation.Route
import com.point.ui.scaffold.topappbar.state.TopAppBarAction
import com.point.ui.scaffold.topappbar.state.TopAppBarState
import com.point.ui.scaffold.topappbar.type.AppBarType

@Composable
fun ContactsScreen(
    topAppBarState: MutableState<TopAppBarState>,
    onNavigation: (Route) -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel = hiltViewModel<UserContactsViewModel>()

    topAppBarState.value = TopAppBarState(
        appBarType = AppBarType.HeaderAppBar(
            headerRes = R.string.contacts_screen_title,
        ),
        actions = listOf(
            TopAppBarAction(
                icon = Icons.Default.Search,
                action = { onNavigation(Route.ContactsFeature.ContactsSearch) },
            ),
            TopAppBarAction(
                icon = Icons.Default.Notifications,
                action = { onNavigation(Route.ContactsFeature.ContactsRequests) },
            )
        ),
    )

    val state = viewModel.composableState.value
    if (state.isInitialLoading) {
        ContactsInitialLoading(modifier = Modifier.fillMaxWidth(),)
    } else {
        SwipeRefresh(
            state = rememberSwipeRefreshState(state.isRefreshing),
            swipeEnabled = !state.isRefreshing,
            onRefresh = { viewModel.emitAction(ContactsActions.Refresh) }
        ) {
            ContactsScreenContent(
                state = state,
                action = viewModel::emitAction,
                navigate = onNavigation,
                modifier = modifier,
            )
        }
    }
}
