package com.point.contacts.requests.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.point.contacts.main.presenter.ui.ContactComposable
import com.point.contacts.requests.viewModel.RequestsAction
import com.point.contacts.requests.viewModel.RequestsState

@Composable
fun UserRequestsScreenContent(
    state: RequestsState,
    onAction: (RequestsAction) -> Unit,
    onNavigation: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier.fillMaxWidth()) {
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
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onNavigation(contact.id) },
                )

                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            onAction(RequestsAction.AcceptRequest(contact.id))
                        }
                )

                Spacer(modifier = Modifier.width(12.dp))

                Icon(
                    imageVector = Icons.Default.Cancel,
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            onAction(RequestsAction.DenyRequest(contact.id))
                        }
                )
            }
        }
    }
}