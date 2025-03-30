package com.point.contacts.search.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.point.contacts.search.viewModel.SearchContactsAction
import com.point.contacts.search.viewModel.SearchContactsState
import com.point.navigation.Route

@Composable
fun SearchUsersScreen(
    state: SearchContactsState,
    onAction: (SearchContactsAction) -> Unit,
    onNavigation: (Route) -> Unit,
    modifier: Modifier = Modifier,
) {
    SearchUsersScreenContent(
        state = state,
        onAction = onAction,
        onNavigation = onNavigation,
        modifier = modifier,
    )
}