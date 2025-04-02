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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.point.contacts.main.presenter.ui.ContactComposable
import com.point.contacts.search.viewModel.SearchContactsAction
import com.point.contacts.search.viewModel.SearchContactsState
import com.point.navigation.Route
import com.point.ui.Theme

@Composable
fun SearchUsersScreenContent(
    state: SearchContactsState,
    onAction: (SearchContactsAction) -> Unit,
    onNavigation: (Route) -> Unit,
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
            if (state.inContacts.isNotEmpty()) {

                item {
                    Text(
                        text = "В контактах",
                        style = Theme.typography.titleS,
                        color = Theme.colorScheme.textSecondary,
                        modifier = Modifier.padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
                    )
                }

                items(
                    items = state.inContacts,
                    key = { "contact1_" + it.username }
                ) { contact ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onNavigation(Route.ContactsFeature.UserProfile(contact.username)) },
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
                                        onAction(SearchContactsAction.SendRequest(contact.username))
                                    }
                                    .padding(6.dp)
                            )
                        }
                    }
                }
            }

            if (state.allContacts.isNotEmpty()) {

                item {
                    Text(
                        text = "Все пользователи",
                        style = Theme.typography.titleS,
                        color = Theme.colorScheme.textSecondary,
                        modifier = Modifier.padding(start = 8.dp, top = 16.dp, bottom = 8.dp)
                    )
                }

                items(
                    items = state.allContacts,
                    key = { "all_" + it.username }
                ) { contact ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onNavigation(Route.ContactsFeature.UserProfile(contact.username)) },
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
                                        onAction(SearchContactsAction.SendRequest(contact.username))
                                    }
                                    .padding(6.dp)
                            )
                        }
                    }
                }


            }
        }


    }
}