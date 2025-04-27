package com.point.contacts.search.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.point.contacts.R
import com.point.contacts.search.viewModel.SearchContactsState
import com.point.contacts.search.viewModel.UserSearchAction.UiAction
import com.point.navigation.Route

@Composable
internal fun UserSearchScreenContent(
    state: SearchContactsState,
    onAction: (UiAction) -> Unit,
    onNavigation: (Route) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(vertical = 12.dp),
    ) {
        if (state.inContacts.isNotEmpty()) {
            contactUsers(
                title = R.string.in_contacts_title,
                users = state.inContacts,
                onAction = onAction,
                onNavigation = onNavigation,
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (state.globalContacts.isNotEmpty()) {
            contactUsers(
                title = R.string.global_search_title,
                users = state.globalContacts,
                onAction = onAction,
                onNavigation = onNavigation,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
