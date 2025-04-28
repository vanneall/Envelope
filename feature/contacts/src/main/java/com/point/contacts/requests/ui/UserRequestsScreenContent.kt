package com.point.contacts.requests.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.point.contacts.R
import com.point.contacts.requests.viewModel.RequestsAction.UiAction
import com.point.contacts.requests.viewModel.RequestsState
import com.point.navigation.Route
import com.point.ui.EnvelopeTheme
import com.point.ui.components.user.UserButtonCard

@Composable
internal fun UserRequestsScreenContent(
    state: RequestsState,
    onAction: (UiAction) -> Unit,
    onNavigation: (Route) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier.fillMaxWidth()) {
        items(
            items = state.contacts,
            key = { request -> request.id }
        ) { request ->
            UserButtonCard(
                user = request.userBase,
                primaryText = stringResource(R.string.accept_request),
                onPrimaryClick = { onAction(UiAction.AcceptRequest(request.id)) },
                secondaryText = stringResource(R.string.reject_request),
                onSecondaryClick = { onAction(UiAction.DenyRequest(request.id)) },
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(),
                        onClick = { onNavigation(Route.ContactsFeature.UserProfile(request.username)) }
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .animateItem()
            )
        }
    }
}

@Preview(locale = "ru")
@Composable
private fun ContentPreview() {
    EnvelopeTheme {
        UserRequestsScreenContent(
            state = RequestsState(
                query = "Query",
                contacts = buildList {
                    repeat(5) {
//                        add(Contact(username = "$it", name = "User#$it"))
                    }
                }
            ),
            onAction = {},
            onNavigation = {},
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .padding(end = 10.dp)
        )
    }
}
