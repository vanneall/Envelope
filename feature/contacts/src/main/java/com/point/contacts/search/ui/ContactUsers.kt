package com.point.contacts.search.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.point.contacts.main.presenter.ui.UserCard
import com.point.contacts.main.presenter.viewmodel.Contact
import com.point.contacts.search.viewModel.UserSearchAction.UiAction
import com.point.navigation.Route
import com.point.ui.Theme

internal fun LazyListScope.contactUsers(
    @StringRes title: Int,
    users: List<Contact>,
    onAction: (UiAction) -> Unit,
    onNavigation: (Route) -> Unit,
    modifier: Modifier = Modifier,
) {
    item {
        Text(
            text = stringResource(title),
            style = Theme.typography.bodyM,
            color = Theme.colorScheme.textSecondary,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .animateItem()
        )
    }

    items(items = users, key = { user -> "${title}_${user.username}" }) { contact ->
        UserCard(
            contact = contact,
            modifier = modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(),
                ) {
                    onNavigation(Route.ContactsFeature.UserProfile(contact.username))
                }
                .padding(horizontal = 8.dp)
                .animateItem()
        )
    }
}