package com.point.contacts.main.presenter.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
            val source = remember { MutableInteractionSource() }
            val ripple = rememberRipple(color = Color.Black)
            ContactComposable(
                contact = contact,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = source,
                        indication = ripple,
                    ) {
                        onNavigation(contact.id)
                    },
            )
        }
    }
}