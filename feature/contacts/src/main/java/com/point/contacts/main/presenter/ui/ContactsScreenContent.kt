package com.point.contacts.main.presenter.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ripple.rememberRipple
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
import com.point.ui.components.user.UserBase
import com.point.ui.components.user.UserCardInfo
import com.point.ui.components.user.UserTextCard

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
            itemsIndexed(
                items = contacts,
                key = { _, contact -> contact.username }
            ) { index, contact ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (index == 0) {
                        Text(
                            text = key.toString(),
                            style = Theme.typography.titleL,
                            color = Theme.colorScheme.textSecondary,
                            modifier = Modifier.width(24.dp),
                        )
                    } else {
                        Spacer(modifier = Modifier.width(24.dp))
                    }

                    UserTextCard(
                        user = UserCardInfo(UserBase(contact.name, contact.photo), supportText = contact.status),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(),
                            ) {
                                onNavigation(Route.ContactsFeature.UserProfile(contact.username))
                            },
                    )
                }
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