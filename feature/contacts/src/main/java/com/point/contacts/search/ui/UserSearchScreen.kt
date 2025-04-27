package com.point.contacts.search.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.point.contacts.R
import com.point.contacts.search.viewModel.SearchContactsViewModel
import com.point.contacts.search.viewModel.UserSearchAction.UiAction
import com.point.navigation.Route
import com.point.ui.scaffold.topappbar.state.TopAppBarState
import com.point.ui.scaffold.topappbar.type.AppBarType

@Composable
fun UserSearchScreen(
    onBack: () -> Unit,
    onNavigation: (Route) -> Unit,
    topAppBarState: MutableState<TopAppBarState>,
    modifier: Modifier = Modifier,
) {
    val viewModel = hiltViewModel<SearchContactsViewModel>()

    topAppBarState.value = TopAppBarState(
        appBarType = AppBarType.SearchAppBar(
            placeHolder = R.string.search_screen_title,
            onInput = { name -> viewModel.emitAction(UiAction.OnNameTyped(name)) }
        ),
        onBack = onBack,
    )

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = viewModel.composableState.value.isRefreshing),
        onRefresh = { viewModel.emitAction(UiAction.Refresh) },
        swipeEnabled = !viewModel.composableState.value.isRefreshing
    ) {
        UserSearchScreenContent(
            state = viewModel.composableState.value,
            onAction = viewModel::emitAction,
            onNavigation = onNavigation,
            modifier = modifier,
        )
    }
}