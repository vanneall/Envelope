package com.point.contacts.search.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MarkEmailRead
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.point.contacts.main.presenter.ui.ContactComposable
import com.point.contacts.search.viewModel.SearchContactsAction
import com.point.contacts.search.viewModel.SearchContactsState
import com.point.ui.Theme

@Composable
fun SearchUsersScreenContent(
    state: SearchContactsState,
    onAction: (SearchContactsAction) -> Unit,
    onNavigation: (String) -> Unit,
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNavigation(contact.id) },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    ContactComposable(
                        contact = contact,
                        modifier = Modifier.weight(1f),
                    )

                    if (contact.isSentRequest) {
                        Icon(
                            imageVector = Icons.Default.MarkEmailRead,
                            contentDescription = null,
                            tint = Theme.colorScheme.accent,
                            modifier = Modifier
                                .padding(end = 20.dp)
                                .size(38.dp)
                                .clip(CircleShape)
                                .padding(6.dp)
                        )
                    } else if (!contact.inContacts) {
                        Icon(
                            imageVector = Icons.Default.PersonAdd,
                            contentDescription = null,
                            tint = Theme.colorScheme.accent,
                            modifier = Modifier
                                .padding(end = 20.dp)
                                .size(38.dp)
                                .clip(CircleShape)
                                .clickable {
                                    onAction(SearchContactsAction.SendRequest(contact.id))
                                }
                                .padding(6.dp)
                        )
                    }
                }

            }
        }
    }
}