package com.point.contacts.main.presenter.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.point.contacts.main.presenter.viewmodel.ContactState
import com.point.contacts.main.presenter.viewmodel.ContactsActions

@Composable
fun ContactsScreenContent(
    state: ContactState,
    onAction: (ContactsActions) -> Unit,
    onNavigation: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        items(
            items = state.contacts,
            key = { it.id }
        ) { contact ->
            ContactComposable(
                contact = contact,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onNavigation(contact.id)
                    },
            )
        }
    }
}