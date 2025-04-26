package com.point.contacts.main.presenter.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.point.contacts.main.presenter.viewmodel.Contact
import com.point.contacts.main.presenter.viewmodel.ContactState
import com.point.contacts.main.presenter.viewmodel.ContactsActions
import com.point.navigation.Route
import com.point.ui.EnvelopeTheme
import com.point.ui.Theme

@Composable
fun ContactsScreenContent(
    state: ContactState,
    onAction: (ContactsActions) -> Unit,
    onNavigation: (Route) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        state.contacts.forEach { (key, contacts) ->
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(horizontal = 8.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = key.toString(),
                        style = Theme.typography.bodyL,
                        color = Theme.colorScheme.textSecondary,
                    )

                    HorizontalDivider(
                        thickness = 1.dp,
                        color = Theme.colorScheme.textSecondary,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }

            items(
                items = contacts,
                key = { it.username }
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
                            onNavigation(Route.ContactsFeature.UserProfile(contact.username))
                        },
                )
            }
        }
    }
}

@Preview(locale = "ru")
@Composable
private fun ContentPreview() {
    EnvelopeTheme {
        ContactsScreenContent(
            state = ContactState(
                contacts = mapOf(
                    'U' to listOf(Contact(username = "username", name = "User")),
                    'R' to listOf(Contact(username = "user", name = "Ryan Gosling")),
                ),
            ),
            onAction = {},
            onNavigation = {},
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .padding(vertical = 10.dp)
        )
    }
}