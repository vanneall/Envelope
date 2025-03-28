package com.point.contacts.search.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    ContactComposable(
                        contact = contact,
                        modifier = Modifier.weight(1f),
                    )

                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp).clickable {
                            onAction(SearchContactsAction.SendRequest(contact.id))
                        }
                    )
                }

            }
        }
    }
}