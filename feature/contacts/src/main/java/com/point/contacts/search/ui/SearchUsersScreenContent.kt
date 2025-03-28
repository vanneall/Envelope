package com.point.contacts.search.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.point.contacts.main.presenter.ui.ContactComposable
import com.point.contacts.search.viewModel.SearchContactsAction
import com.point.contacts.search.viewModel.SearchContactsState

@Composable
fun SearchUsersScreenContent(
    state: SearchContactsState,
    onAction: (SearchContactsAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        TextField(
            value = state.query,
            onValueChange = { onAction(SearchContactsAction.OnNameTyped(it)) },
            modifier = Modifier.fillMaxWidth(),
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
        ) {
            items(
                items = state.contacts,
                key = { it.id }
            ) { contact ->
                ContactComposable(
                    contact = contact,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}