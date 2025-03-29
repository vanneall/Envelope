package com.point.contacts.search.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.point.contacts.search.viewModel.SearchContactsAction
import com.point.contacts.search.viewModel.SearchContactsState

@Composable
fun SearchUsersScreen(
    state: SearchContactsState,
    onAction: (SearchContactsAction) -> Unit,
    onNavigation: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    SearchUsersScreenContent(
        state = state,
        onAction = onAction,
        onNavigation = onNavigation,
        modifier = modifier,
    )
}