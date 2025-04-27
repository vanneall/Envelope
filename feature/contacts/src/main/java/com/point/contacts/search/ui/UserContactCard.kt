package com.point.contacts.search.ui

import androidx.compose.animation.Crossfade
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MarkEmailRead
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.point.contacts.search.data.ContactState
import com.point.contacts.search.data.ContactUserUi
import com.point.contacts.search.viewModel.UserSearchAction.UiAction
import com.point.ui.Theme
import com.point.ui.colors.new.NewEnvelopeTheme
import com.point.ui.components.user.UserCard
import com.point.ui.components.user.UserCardInfo
import com.point.ui.materials.icons.defaults.EnvelopeIconButton

@Composable
internal fun UserContactCard(
    contactUserUi: ContactUserUi,
    onAction: (UiAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    UserCard(
        user = contactUserUi.user,
        modifier = modifier,
        trailing = {
            Crossfade(targetState = contactUserUi.contactState) { content ->
                when (content) {
                    ContactState.NOT_IN_CONTACTS -> {
                        EnvelopeIconButton(
                            icon = Icons.Default.PersonAdd,
                            tint = Theme.colorScheme.accent,
                            onClick = { onAction(UiAction.SendRequest(contactUserUi.username)) }
                        )
                    }

                    ContactState.REQUEST_SENT -> {
                        EnvelopeIconButton(
                            icon = Icons.Default.MarkEmailRead,
                            tint = Theme.colorScheme.accent,
                            enabled = false,
                        )
                    }

                    else -> {}
                }
            }
        }
    )
}

@Preview
@Composable
private fun UserContactCardPreview() {
    NewEnvelopeTheme {
        UserContactCard(
            contactUserUi = ContactUserUi(
                username = "@username",
                user = UserCardInfo(
                    name = "User",
                    supportText = "Some description",
                ),
                contactState = ContactState.IN_CONTACTS,
            ),
            onAction = {},
        )
    }
}