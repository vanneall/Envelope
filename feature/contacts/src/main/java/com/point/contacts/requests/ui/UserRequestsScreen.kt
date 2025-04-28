package com.point.contacts.requests.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.point.contacts.R
import com.point.contacts.main.presenter.ui.ContactsInitialLoading
import com.point.contacts.requests.viewModel.RequestsAction.UiAction
import com.point.contacts.requests.viewModel.RequestsContactsViewModel
import com.point.navigation.Route
import com.point.ui.scaffold.topappbar.state.TopAppBarState
import com.point.ui.scaffold.topappbar.type.AppBarType

@Composable
fun UserRequestsScreen(
    onBack: () -> Unit,
    onNavigation: (Route) -> Unit,
    topAppBarState: MutableState<TopAppBarState>,
    modifier: Modifier = Modifier,
) {
    val viewModel = hiltViewModel<RequestsContactsViewModel>()

    topAppBarState.value = TopAppBarState(
        appBarType = AppBarType.HeaderAppBar(
            headerRes = R.string.search_requests_title
        ),
        onBack = onBack,
    )

    if (viewModel.composableState.value.isInitialLoading) {
        ContactsInitialLoading(modifier = Modifier.fillMaxSize())
    } else {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = viewModel.composableState.value.isRefreshing),
            onRefresh = { viewModel.emitAction(UiAction.Refresh) },
            swipeEnabled = !viewModel.composableState.value.isRefreshing
        ) {
            UserRequestsScreenContent(
                state = viewModel.composableState.value,
                onAction = viewModel::emitAction,
                onNavigation = onNavigation,
                modifier = modifier,
            )
        }
    }
}